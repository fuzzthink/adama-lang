/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.mysql.frontend;

import org.adamalang.common.metrics.NoOpMetricsFactory;
import org.adamalang.mysql.DataBase;
import org.adamalang.mysql.DataBaseConfig;
import org.adamalang.mysql.DataBaseConfigTests;
import org.adamalang.mysql.DataBaseMetrics;
import org.adamalang.mysql.frontend.data.*;
import org.adamalang.mysql.frontend.metrics.MeteringMetrics;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class MeteringSampleTests {
  @Test
  public void lateEarly() throws Exception {
    DataBaseConfig dataBaseConfig = DataBaseConfigTests.getLocalIntegrationConfig();
    try (DataBase dataBase = new DataBase(dataBaseConfig, new DataBaseMetrics(new NoOpMetricsFactory(), "noop"))) {
      FrontendManagementInstaller installer = new FrontendManagementInstaller(dataBase);
      try {
        MeteringMetrics metrics = new MeteringMetrics(new NoOpMetricsFactory());
        installer.install();
        Assert.assertNull(Spaces.getLatestBillingHourCode(dataBase));
        Assert.assertNull(Metering.getEarliestRecordTimeOfCreation(dataBase));
        int userId = Users.getOrCreateUserId(dataBase, "user@user.com");
        int spaceId = Spaces.createSpace(dataBase, userId, "space");
        Assert.assertEquals(0, (int) Spaces.getLatestBillingHourCode(dataBase));
        long now = System.currentTimeMillis();
        Metering.recordBatch(dataBase, "target1", "{\"time\":\"" + (now - 100000000) + "\",\"spaces\":{\"space\":{\"cpu\":\"14812904860\",\"messages\":\"2830000\",\"count_p95\":\"4\",\"memory_p95\":\"1000\",\"connections_p95\":29}}}", now);
        Metering.recordBatch(dataBase, "target2", "{\"time\":\"" + (now + 100000000) + "\",\"spaces\":{\"space\":{\"cpu\":\"14812904860\",\"messages\":\"2830000\",\"count_p95\":\"4\",\"memory_p95\":\"1000\",\"connections_p95\":19}}}", now);
        Assert.assertNotNull(Metering.getEarliestRecordTimeOfCreation(dataBase));
        HashMap<String, MeteringSpaceSummary> summary1 = Metering.summarizeWindow(dataBase, metrics, now - 100000, now + 100000);
        Assert.assertEquals(1, summary1.size());
        HashMap<String, Long> inventory = new HashMap<>();
        inventory.put("space", 1024L);
        inventory.put("space2", 2048L);
        HashMap<String, Long> unbilled = Spaces.collectUnbilledStorage(dataBase);
        unbilled.put("space2", 1000000000L - 2000);
        Billing.mergeStorageIntoSummaries(summary1, inventory, unbilled);
        ResourcesPerPenny rates = new ResourcesPerPenny(1000000, 2000, 50, 1024, 500, 1000000000);
        {
          MeteredWindowSummary summarySpace = summary1.get("space").summarize(rates);
          Assert.assertEquals("{\"cpu\":\"29625809720\",\"messages\":\"5660000\",\"count\":\"8\",\"memory\":\"2000\",\"connections\":\"48\",\"storageBytes\":\"1024\"}", summarySpace.resources);
          Assert.assertEquals(1024, summarySpace.storageBytes);
          Assert.assertEquals(1024, summarySpace.changeUnbilledStorageByteHours);
          Assert.assertEquals(29626, summarySpace.pennies);
        }
        {
          MeteredWindowSummary summarySpace2 = summary1.get("space2").summarize(rates);
          Assert.assertEquals("{\"storageBytes\":\"2048\"}", summarySpace2.resources);
          Assert.assertEquals(2048, summarySpace2.storageBytes);
          Assert.assertEquals(-999997952, summarySpace2.changeUnbilledStorageByteHours);
          Assert.assertEquals(1, summarySpace2.pennies);
        }
        Assert.assertEquals(500, Users.getBalance(dataBase, userId));
        Billing.transcribeSummariesAndUpdateBalances(dataBase, 52, summary1, rates);
        Assert.assertEquals(-29126, Users.getBalance(dataBase, userId));
        Assert.assertEquals(52, (int) Spaces.getLatestBillingHourCode(dataBase));
        HashMap<String, Long> unbilledAfter = Spaces.collectUnbilledStorage(dataBase);
        Assert.assertEquals(1024, (long) unbilledAfter.get("space"));
        Billing.mergeStorageIntoSummaries(summary1, inventory, unbilled);

        ArrayList<BillingUsage> usages = Billing.usageReport(dataBase, spaceId, 2);
        Assert.assertEquals(1, usages.size());
        Assert.assertEquals(29625809720L, usages.get(0).cpu);
        Assert.assertEquals(2000, usages.get(0).memory);
        Assert.assertEquals(1024, usages.get(0).storageBytes);
        Assert.assertEquals(5660000, usages.get(0).messages);
        Assert.assertEquals(8, usages.get(0).documents);
        Assert.assertEquals(48, usages.get(0).connections);

      } finally {
        installer.uninstall();
      }
    }
  }
  @Test
  public void batches() throws Exception {
    DataBaseConfig dataBaseConfig = DataBaseConfigTests.getLocalIntegrationConfig();
    try (DataBase dataBase = new DataBase(dataBaseConfig, new DataBaseMetrics(new NoOpMetricsFactory(), "noop"))) {
      FrontendManagementInstaller installer = new FrontendManagementInstaller(dataBase);
      try {
        MeteringMetrics metrics = new MeteringMetrics(new NoOpMetricsFactory());
        installer.install();
        Assert.assertNull(Spaces.getLatestBillingHourCode(dataBase));
        Assert.assertNull(Metering.getEarliestRecordTimeOfCreation(dataBase));
        int userId = Users.getOrCreateUserId(dataBase, "user@user.com");
        int spaceId = Spaces.createSpace(dataBase, userId, "space");
        Assert.assertEquals(0, (int) Spaces.getLatestBillingHourCode(dataBase));
        long now = System.currentTimeMillis();
        Metering.recordBatch(dataBase, "target1", "{\"time\":\"" + (now - 10000) + "\",\"spaces\":{\"space\":{\"cpu\":\"14812904860\",\"messages\":\"2830000\",\"count_p95\":\"4\",\"memory_p95\":\"1000\",\"connections_p95\":29}}}", now);
        Metering.recordBatch(dataBase, "target2", "{\"time\":\"" + (now + 10000) + "\",\"spaces\":{\"space\":{\"cpu\":\"14812904860\",\"messages\":\"2830000\",\"count_p95\":\"4\",\"memory_p95\":\"1000\",\"connections_p95\":19}}}", now);
        Assert.assertNotNull(Metering.getEarliestRecordTimeOfCreation(dataBase));
        HashMap<String, MeteringSpaceSummary> summary1 = Metering.summarizeWindow(dataBase, metrics, now - 100000, now + 100000);
        Assert.assertEquals(1, summary1.size());
        HashMap<String, Long> inventory = new HashMap<>();
        inventory.put("space", 1024L);
        inventory.put("space2", 2048L);
        HashMap<String, Long> unbilled = Spaces.collectUnbilledStorage(dataBase);
        unbilled.put("space2", 1000000000L - 2000);
        Billing.mergeStorageIntoSummaries(summary1, inventory, unbilled);
        ResourcesPerPenny rates = new ResourcesPerPenny(1000000, 2000, 50, 1024, 500, 1000000000);
        {
          MeteredWindowSummary summarySpace = summary1.get("space").summarize(rates);
          Assert.assertEquals("{\"cpu\":\"29625809720\",\"messages\":\"5660000\",\"count\":\"8\",\"memory\":\"2000\",\"connections\":\"48\",\"storageBytes\":\"1024\"}", summarySpace.resources);
          Assert.assertEquals(1024, summarySpace.storageBytes);
          Assert.assertEquals(1024, summarySpace.changeUnbilledStorageByteHours);
          Assert.assertEquals(29626, summarySpace.pennies);
        }
        {
          MeteredWindowSummary summarySpace2 = summary1.get("space2").summarize(rates);
          Assert.assertEquals("{\"storageBytes\":\"2048\"}", summarySpace2.resources);
          Assert.assertEquals(2048, summarySpace2.storageBytes);
          Assert.assertEquals(-999997952, summarySpace2.changeUnbilledStorageByteHours);
          Assert.assertEquals(1, summarySpace2.pennies);
        }
        Assert.assertEquals(500, Users.getBalance(dataBase, userId));
        Billing.transcribeSummariesAndUpdateBalances(dataBase, 52, summary1, rates);
        Assert.assertEquals(-29126, Users.getBalance(dataBase, userId));
        Assert.assertEquals(52, (int) Spaces.getLatestBillingHourCode(dataBase));
        HashMap<String, Long> unbilledAfter = Spaces.collectUnbilledStorage(dataBase);
        Assert.assertEquals(1024, (long) unbilledAfter.get("space"));
        Billing.mergeStorageIntoSummaries(summary1, inventory, unbilled);

        ArrayList<BillingUsage> usages = Billing.usageReport(dataBase, spaceId, 2);
        Assert.assertEquals(1, usages.size());
        Assert.assertEquals(29625809720L, usages.get(0).cpu);
        Assert.assertEquals(2000, usages.get(0).memory);
        Assert.assertEquals(1024, usages.get(0).storageBytes);
        Assert.assertEquals(5660000, usages.get(0).messages);
        Assert.assertEquals(8, usages.get(0).documents);
        Assert.assertEquals(48, usages.get(0).connections);

        Assert.assertTrue(Spaces.getSpaceInfo(dataBase, "space").enabled);
        Users.disableSweep(dataBase);
        Assert.assertFalse(Spaces.getSpaceInfo(dataBase, "space").enabled);
        Users.addToBalance(dataBase, userId, 50000);
        Assert.assertEquals(50000-29126, Users.getBalance(dataBase, userId));
        Assert.assertTrue(Spaces.getSpaceInfo(dataBase, "space").enabled);
      } finally {
        installer.uninstall();
      }
    }
  }
}
