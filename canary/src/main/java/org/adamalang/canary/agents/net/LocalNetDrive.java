/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.canary.agents.net;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adamalang.common.*;
import org.adamalang.common.metrics.NoOpMetricsFactory;
import org.adamalang.common.net.NetBase;
import org.adamalang.common.net.ServerHandle;
import org.adamalang.disk.*;
import org.adamalang.disk.demo.DiskMetrics;
import org.adamalang.disk.demo.SingleThreadDiskDataService;
import org.adamalang.net.client.Client;
import org.adamalang.net.client.ClientConfig;
import org.adamalang.net.client.ClientMetrics;
import org.adamalang.net.server.Handler;
import org.adamalang.net.server.ServerMetrics;
import org.adamalang.net.server.ServerNexus;
import org.adamalang.runtime.data.DataService;
import org.adamalang.runtime.data.InMemoryDataService;
import org.adamalang.runtime.deploy.DeploymentFactoryBase;
import org.adamalang.runtime.deploy.DeploymentPlan;
import org.adamalang.runtime.sys.CoreMetrics;
import org.adamalang.runtime.sys.CoreService;
import org.adamalang.runtime.sys.metering.DiskMeteringBatchMaker;
import org.adamalang.runtime.sys.metering.MeteringPubSub;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LocalNetDrive {
  public static void go(LocalNetCanaryConfig config) throws Exception {
    ExceptionLogger logger = new ExceptionLogger() {
      @Override
      public void convertedToErrorCode(Throwable t, int errorCode) {
        System.exit(100);
      }
    };
    SimpleExecutor executor = SimpleExecutor.create("billing");
    MachineIdentity identity = MachineIdentity.fromFile(config.identityFile);
    NetBase netBase = new NetBase(identity, 1, 4);
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    File billingRoot = new File(File.createTempFile("ADAMATEST_", "x23").getParentFile(), "Billing-" + System.currentTimeMillis());
    billingRoot.mkdir();
    SimpleExecutor walExecutor = SimpleExecutor.create("wal");
    try {
      if (config.role.equals("both") || config.role.equals("server")) {
        DeploymentFactoryBase deploymentFactoryBase = new DeploymentFactoryBase();
        String singleScript = Files.readString(new File(config.source).toPath());
        ObjectNode planNode = Json.newJsonObject();
        planNode.putObject("versions").put("file", singleScript);
        planNode.put("default", "file");
        planNode.putArray("plan");
        DeploymentPlan plan = new DeploymentPlan(planNode.toString(), logger);
        deploymentFactoryBase.deploy(config.space, plan);
        final DataService dataService;
        if (config.data.equals("disk")) {
          dataService = new SingleThreadDiskDataService(new File("./canary_data"), new DiskMetrics(new NoOpMetricsFactory()));
        } else if (config.data.equals("wal")) {
          File storageDirectory = new File("./canary_wal");
          storageDirectory.mkdirs();
          DiskBase diskBase = new DiskBase(new DiskDataMetrics(new NoOpMetricsFactory()), walExecutor, storageDirectory);
          Startup.transfer(diskBase);
          diskBase.start();
          WriteAheadLog log = new WriteAheadLog(diskBase, 32768, 50000, 32 * 1024 * 1024);
          dataService = new DiskDataService(diskBase, log);
        } else {
          dataService = new InMemoryDataService(Executors.newSingleThreadExecutor(), TimeSource.REAL_TIME);
        }
        MeteringPubSub meteringPubSub = new MeteringPubSub(TimeSource.REAL_TIME, deploymentFactoryBase);
        CoreMetrics coreMetrics = new CoreMetrics(new NoOpMetricsFactory());
        CoreService service = new CoreService(coreMetrics, deploymentFactoryBase, meteringPubSub.publisher(), dataService, TimeSource.REAL_TIME, config.coreThreads);

        DiskMeteringBatchMaker batchMaker = new DiskMeteringBatchMaker(TimeSource.REAL_TIME, executor, billingRoot, 1800000L);
        ServerNexus nexus = new ServerNexus(netBase, identity, service, new ServerMetrics(new NoOpMetricsFactory()), deploymentFactoryBase, (space) -> {
        }, meteringPubSub, batchMaker, config.port, 2);
        ServerHandle handle = netBase.serve(config.port, (upstream -> new Handler(nexus, upstream)));
        if (config.role.equals("server")) {
          System.err.println("starting just the server...");
          handle.waitForEnd();
        }
      }

      ClientConfig clientConfig = new ClientConfig();
      if (config.role.equals("both") || config.role.equals("client")) {
        LocalNetAgent[] agents = new LocalNetAgent[config.agents];
        Client client = new Client(netBase, clientConfig, new ClientMetrics(new NoOpMetricsFactory()), null);
        client.getTargetPublisher().accept(Collections.singletonList("127.0.0.1:" + config.port));
        for (int k = 0; k < agents.length; k++) {
          agents[k] = new LocalNetAgent(client, config, k, scheduler);
        }
        for (int k = 0; k < agents.length; k++) {
          agents[k].kickOff();
        }
        config.blockUntilQuit();
      }
    } finally {
      for (File file : billingRoot.listFiles()) {
        file.delete();
      }
      billingRoot.delete();
      executor.shutdown().await(1000, TimeUnit.MILLISECONDS);
      scheduler.shutdown();
      walExecutor.shutdown();
    }
  }
}
