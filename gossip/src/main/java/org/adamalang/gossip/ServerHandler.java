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

import io.grpc.stub.StreamObserver;
import org.adamalang.common.NamedRunnable;
import org.adamalang.common.SimpleExecutor;
import org.adamalang.gossip.proto.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

/** the server side of the gossip */
public class ServerHandler extends GossipGrpc.GossipImplBase {
  private final SimpleExecutor executor;
  private final InstanceSetChain chain;
  private final GossipMetrics metrics;
  private final AtomicBoolean alive;

  public ServerHandler(SimpleExecutor executor, InstanceSetChain chain, AtomicBoolean alive, GossipMetrics metrics) {
    this.executor = executor;
    this.chain = chain;
    this.metrics = metrics;
    this.alive = alive;
  }

  @Override
  public StreamObserver<GossipForward> exchange(StreamObserver<GossipReverse> responseObserver) {
    if (!alive.get()) {
      responseObserver.onCompleted();
      return new SilentGossipForwardObserver();
    }
    return new StreamObserver<>() {
      private InstanceSet set = null;

      @Override
      public void onNext(GossipForward gossipForward) {
        executor.execute(new NamedRunnable("gossip-server-on-next") {
          @Override
          public void execute() throws Exception {
            switch (gossipForward.getChatterCase()) {
              case START: {
                metrics.bump_start();
                BeginGossip start = gossipForward.getStart();
                chain.ingest(start.getRecentEndpointsList(), new HashSet<>(start.getRecentDeletesList()));
                set = chain.find(start.getHash());
                if (set != null) {
                  responseObserver.onNext(GossipReverse.newBuilder().setOptimisticReturn(HashFoundRequestForwardQuickGossip.newBuilder().addAllCounters(set.counters()).addAllRecentDeletes(chain.deletes()).addAllMissingEndpoints(chain.missing(set)).build()).build());
                } else {
                  set = chain.current();
                  responseObserver.onNext(GossipReverse.newBuilder().setSadReturn(HashNotFoundReverseConversation.newBuilder().setHash(set.hash()).addAllRecentEndpoints(chain.recent()).addAllRecentDeletes(chain.deletes()).build()).build());
                }
                return;
              }
              case FOUND_REVERSE: {
                metrics.bump_found_reverse();
                ReverseHashFound reverseHashFound = gossipForward.getFoundReverse();
                set.ingest(reverseHashFound.getCountersList(), chain.now());
                chain.ingest(reverseHashFound.getMissingEndpointsList(), Collections.emptySet());
                responseObserver.onNext(GossipReverse.newBuilder().setTurnTables(ReverseHashFound.newBuilder().addAllCounters(set.counters()).addAllMissingEndpoints(chain.missing(set)).build()).build());
                responseObserver.onCompleted();
                return;
              }
              case QUICK_GOSSIP: {
                metrics.bump_quick_gossip();
                set.ingest(gossipForward.getQuickGossip().getCountersList(), chain.now());
                responseObserver.onCompleted();
                return;
              }
              case SLOW_GOSSIP: {
                metrics.bump_server_slow_gossip();
                TreeSet<String> incoming = new TreeSet<>();
                for (Endpoint ep : gossipForward.getSlowGossip().getAllEndpointsList()) {
                  incoming.add(ep.getId());
                }
                chain.ingest(gossipForward.getSlowGossip().getAllEndpointsList(), Collections.emptySet());
                responseObserver.onNext(GossipReverse.newBuilder().setSlowGossip(SlowGossip.newBuilder().addAllAllEndpoints(chain.all()).build()).build());
                responseObserver.onCompleted();
                return;
              }
            }
          }
        });
      }

      @Override
      public void onError(Throwable throwable) {
        metrics.log_error(throwable);
      }

      @Override
      public void onCompleted() {
      }
    };
  }
}
