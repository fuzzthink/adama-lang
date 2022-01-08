/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.gossip;

import io.grpc.ChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.TlsChannelCredentials;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import io.grpc.netty.shaded.io.netty.handler.ssl.ClientAuth;
import io.grpc.stub.StreamObserver;
import org.adamalang.common.ExceptionRunnable;
import org.adamalang.common.ExceptionSupplier;
import org.adamalang.common.MachineIdentity;
import org.adamalang.common.TimeSource;
import org.adamalang.gossip.proto.Endpoint;
import org.adamalang.gossip.proto.GossipGrpc;
import org.adamalang.gossip.proto.GossipReverse;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** represents both the server and the client conjoined together in a gossipy fate */
public class Engine implements AutoCloseable {
  private final ChannelCredentials credentials;
  private final InstanceSetChain chain;
  private final Runnable me;
  private final Random jitter;
  private final GossipPartnerPicker picker;
  private final ScheduledExecutorService executor;
  private final String ip;
  private final Metrics metrics;
  private final AtomicBoolean alive;
  private final Supplier<Server> serverSupplier;
  private final HashMap<String, Link> links;
  private final HashMap<String, ArrayList<Consumer<Collection<String>>>> subscribersByApp;

  private String broadcastHash;
  private io.grpc.Server server;
  private ScheduledFuture<?> gossiper = null;

  public Engine(
      MachineIdentity identity, TimeSource time, HashSet<String> initial, int port, Metrics metrics)
      throws Exception {
    this.credentials =
        TlsChannelCredentials.newBuilder() //
            .keyManager(identity.getCert(), identity.getKey()) //
            .trustManager(identity.getTrust())
            .build(); //
    this.chain = new InstanceSetChain(time);
    String id = UUID.randomUUID().toString();
    chain.ingest(
        Collections.singleton(
            Endpoint.newBuilder()
                .setIp(identity.ip)
                .setId(id)
                .setPort(port)
                .setMonitoringPort(-1)
                .setCounter(0)
                .setRole("gossip")
                .build()),
        Collections.emptySet());
    me = chain.pick(id);
    this.jitter = new Random();
    this.picker = new GossipPartnerPicker(identity.ip + ":" + port, chain, initial, jitter);
    this.executor = Executors.newSingleThreadScheduledExecutor();
    this.ip = identity.ip;
    this.metrics = metrics;
    this.alive = new AtomicBoolean(false);
    this.links = new HashMap<>();
    this.subscribersByApp = new HashMap<>();
    this.broadcastHash = "";
    this.server = null;
    serverSupplier =
        ExceptionSupplier.TO_RUNTIME(
            () ->
                NettyServerBuilder.forPort(port)
                    .addService(new ServerHandler(executor, chain, alive, metrics))
                    .sslContext(
                        GrpcSslContexts //
                            .forServer(identity.getCert(), identity.getKey()) //
                            .trustManager(identity.getTrust()) //
                            .clientAuth(ClientAuth.REQUIRE) //
                            .build())
                    .keepAliveTimeout(2500, TimeUnit.MILLISECONDS)
                    .build());
  }

  public void newApp(String role, int port, int monitoringPort, Consumer<Runnable> callback) {
    executor.execute(
        () -> {
          String id = UUID.randomUUID().toString();
          chain.ingest(
              Collections.singleton(
                  Endpoint.newBuilder()
                      .setIp(ip)
                      .setId(id)
                      .setPort(port)
                      .setMonitoringPort(monitoringPort)
                      .setCounter(0)
                      .setRole(role)
                      .build()),
              Collections.emptySet());
          callback.accept(chain.pick(id));
        });
  }

  public void subscribe(String app, Consumer<Collection<String>> consumer) {
    executor.execute(
        () -> {
          ArrayList<Consumer<Collection<String>>> subscribers = subscribersByApp.get(app);
          if (subscribers == null) {
            subscribers = new ArrayList<>();
            subscribersByApp.put(app, subscribers);
          }
          subscribers.add(consumer);
          consumer.accept(chain.current().targetsFor(app));
        });
  }

  public void hash(Consumer<String> callback) {
    executor.execute(
        () -> {
          callback.accept(chain.current().hash());
        });
  }

  /** Start serving requests. */
  public void start() throws IOException {
    if (alive.compareAndExchange(false, true) == false) {
      server = serverSupplier.get();
      server.start();
      scheduleGossip();

      Runtime.getRuntime()
          .addShutdownHook(
              new Thread(
                  ExceptionRunnable.TO_RUNTIME(
                      () -> {
                        Engine.this.close();
                      })));
    }
  }

  private void scheduleGossip() {
    executor.execute(
        () -> {
          int wait = 100;
          for (int k = 0; k < 4; k++) {
            wait += jitter.nextInt(100);
          }
          gossiper =
              executor.schedule(
                  () -> {
                    gossipInExecutor();
                  },
                  wait,
                  TimeUnit.MILLISECONDS);
        });
  }

  private Link pick() {
    String target = picker.pick();
    if (target != null) {
      Link link = links.get(target);
      if (link == null) {
        link = new Link(target);
        links.put(target, link);
      }
      return link;
    } else {
      return null;
    }
  }

  private void doBroadcast() {
    executor.execute(
        () -> {
          String testHash = chain.current().hash();
          if (!broadcastHash.equals(testHash)) {
            for (Map.Entry<String, ArrayList<Consumer<Collection<String>>>> entry :
                subscribersByApp.entrySet()) {
              for (Consumer<Collection<String>> subscriber : entry.getValue()) {
                subscriber.accept(chain.current().targetsFor(entry.getKey()));
              }
            }
            broadcastHash = testHash;
          }
        });
  }

  private void gossipInExecutor() {
    // heartbeat myself
    me.run();
    // pick a random partner
    Link link = pick();
    if (link == null) {
      scheduleGossip();
      return;
    }
    ClientObserver observer = new ClientObserver(executor, chain, metrics);
    StreamObserver<GossipReverse> interceptObserver =
        new StreamObserver<>() {
          boolean finished = false;

          @Override
          public void onNext(GossipReverse gossipReverse) {
            observer.onNext(gossipReverse);
          }

          @Override
          public void onError(Throwable throwable) {
            executor.execute(
                () -> {
                  if (!finished) {
                    observer.onError(throwable);
                    link.channel.shutdown();
                    links.remove(link.target);
                    scheduleGossip();
                    finished = true;
                  }
                });
          }

          @Override
          public void onCompleted() {
            executor.execute(
                () -> {
                  if (!finished) {
                    observer.onCompleted();
                    doBroadcast();
                    scheduleGossip();
                    finished = true;
                  }
                });
          }
        };
    observer.initiate(link.stub.exchange(interceptObserver));
    executor.schedule(
        () -> {
          if (!observer.isDone() && alive.get()) {
            interceptObserver.onError(new RuntimeException("Gossip Time out"));
          }
        },
        jitter.nextInt(1000) + 2500,
        TimeUnit.MILLISECONDS);
  }

  /** Finish serving request */
  @Override
  public void close() throws InterruptedException {
    if (alive.compareAndExchange(true, false) == true) {
      CountDownLatch finished = new CountDownLatch(1);
      executor.execute(
          ExceptionRunnable.TO_RUNTIME(
              () -> {
                if (gossiper != null) {
                  gossiper.cancel(false);
                  gossiper = null;
                }
                for (Link link : new ArrayList<>(links.values())) {
                  link.channel.shutdown();
                }
                server.shutdown();
                server = null;
                gossiper = null;
                finished.countDown();
              }));
      finished.await(5000, TimeUnit.MILLISECONDS);
      executor.shutdown();
    }
  }

  public class Link {
    private final String target;
    private final ManagedChannel channel;
    private final GossipGrpc.GossipStub stub;

    public Link(String target) {
      this.target = target;
      this.channel =
          NettyChannelBuilder.forTarget(target, credentials)
              .withOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 150)
              .build();
      this.stub = GossipGrpc.newStub(channel);
    }
  }
}
