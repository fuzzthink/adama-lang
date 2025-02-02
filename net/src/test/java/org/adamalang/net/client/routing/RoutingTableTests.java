/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.net.client.routing;

import org.adamalang.runtime.data.Key;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class RoutingTableTests {
  @Test
  public void sanity_flow() {
    MockSpaceTrackingEvents events = new MockSpaceTrackingEvents();
    RoutingTable table = new RoutingTable(events);
    Assert.assertNull(table.random());
    ArrayList<String> decisions = new ArrayList<>();
    Assert.assertEquals(0, table.targetsFor("space").size());
    Runnable unsubscribe = table.subscribe(new Key("space", "key"), (x) -> decisions.add(x));
    Assert.assertEquals(1, decisions.size());
    Assert.assertNull(decisions.get(0));
    decisions.clear();
    Assert.assertEquals(0, table.targetsFor("space").size());
    table.integrate("t1", Collections.singleton("space"));
    Assert.assertEquals("t1", table.random());
    Assert.assertEquals("t1", table.get("space", "key"));
    Assert.assertEquals(1, table.targetsFor("space").size());
    Assert.assertEquals(0, decisions.size());
    table.broadcast();
    Assert.assertEquals(1, decisions.size());
    table.remove("t1");
    Assert.assertNull(table.random());
    Assert.assertEquals(0, table.targetsFor("space").size());
    Assert.assertEquals(1, decisions.size());
    table.broadcast();
    Assert.assertEquals(2, decisions.size());
    Assert.assertEquals("t1", decisions.get(0));
    Assert.assertNull(decisions.get(1));
    unsubscribe.run();
    table.integrate("t1", Collections.singleton("space"));
    Assert.assertEquals(2, decisions.size());
    table.integrate("t1", Collections.emptyList());
    table.broadcast();
    Assert.assertEquals(2, decisions.size());
    table.integrate("t1", Collections.singleton("space"));
    table.integrate("t2", Collections.singleton("space"));
    table.integrate("t3", Collections.singleton("space"));
    Assert.assertEquals(3, table.targetsFor("space").size());
    Assert.assertEquals("t3", table.get("space", "key"));
    table.broadcast();
    unsubscribe = table.subscribe(new Key("space", "key"), (x) -> decisions.add(x));
    Assert.assertEquals(3, decisions.size());
    Assert.assertEquals("t3", decisions.get(2));
    table.integrate("t1", Collections.emptyList());
    table.integrate("t2", Collections.emptyList());
    table.integrate("t3", Collections.emptyList());
    table.broadcast();
    Assert.assertEquals(4, decisions.size());
    Assert.assertNull(decisions.get(3));
    unsubscribe.run();
    table.broadcast();
    events.assertHistory(
        "[GAIN:space][SHARE:space=t1][SHARE:space=][SHARE:space=][LOST:space][GAIN:space][SHARE:space=t1,t2,t3][SHARE:space=][LOST:space]");
  }

  @Test
  public void singleKeyAgainstMany() {
    MockSpaceTrackingEvents events = new MockSpaceTrackingEvents();
    RoutingTable table = new RoutingTable(events);
    ArrayList<String> decisions = new ArrayList<>();
    table.subscribe(new Key("space", "key"), (x) -> decisions.add(x));
    Assert.assertEquals(1, decisions.size());
    Assert.assertNull(decisions.get(0));
    decisions.clear();
    Assert.assertEquals(0, decisions.size());
    table.integrate("t1", Collections.singleton("space"));
    table.integrate("t2", Collections.singleton("space"));
    table.integrate("t3", Collections.singleton("space"));
    Assert.assertEquals(0, decisions.size());
    table.broadcast();
    Assert.assertEquals(1, decisions.size());
    Assert.assertEquals("t3", decisions.get(0));
    table.remove("t3");
    Assert.assertEquals(1, decisions.size());
    table.broadcast();
    Assert.assertEquals(2, decisions.size());
    Assert.assertEquals("t2", decisions.get(1));
    events.assertHistory("[GAIN:space][SHARE:space=t1,t2,t3][SHARE:space=t1,t2]");
  }

  @Test
  public void multiKeysWithRebalanceByDeath() {
    MockSpaceTrackingEvents events = new MockSpaceTrackingEvents();
    RoutingTable table = new RoutingTable(events);
    ArrayList<String> decisions = new ArrayList<>();
    for (int k = 0; k < 100; k++) {
      table.subscribe(new Key("space", "key-" + k), (x) -> decisions.add(x));
      Assert.assertEquals(1, decisions.size());
      Assert.assertNull(decisions.get(0));
      decisions.clear();
    }
    Assert.assertEquals(0, decisions.size());
    table.integrate("t1", Collections.singleton("space"));
    table.integrate("t2", Collections.singleton("space"));
    table.integrate("t3", Collections.singleton("space"));
    Assert.assertEquals(0, decisions.size());
    table.broadcast();
    Assert.assertEquals(100, decisions.size());
    {
      int t1Count = 0;
      int t2Count = 0;
      int t3Count = 0;
      for (String decision : decisions) {
        if ("t1".equals(decision)) {
          t1Count++;
        } else if ("t2".equals(decision)) {
          t2Count++;
        } else if ("t3".equals(decision)) {
          t3Count++;
        } else {
          Assert.fail();
        }
      }
      Assert.assertEquals(31, t1Count);
      Assert.assertEquals(32, t2Count);
      Assert.assertEquals(37, t3Count);
    }
    decisions.clear();
    table.remove("t3");
    Assert.assertEquals(0, decisions.size());
    table.broadcast();
    Assert.assertEquals(37, decisions.size());
    {
      int t1Count = 0;
      int t2Count = 0;
      for (String decision : decisions) {
        if ("t1".equals(decision)) {
          t1Count++;
        } else if ("t2".equals(decision)) {
          t2Count++;
        } else {
          Assert.fail();
        }
      }
      Assert.assertEquals(19, t1Count);
      Assert.assertEquals(18, t2Count);
    }
    events.assertHistory("[GAIN:space][SHARE:space=t1,t2,t3][SHARE:space=t1,t2]");
  }

  @Test
  public void multiKeysWithRebalanceByShift() {
    MockSpaceTrackingEvents events = new MockSpaceTrackingEvents();
    RoutingTable table = new RoutingTable(events);
    ArrayList<String> decisions = new ArrayList<>();
    for (int k = 0; k < 100; k++) {
      table.subscribe(new Key("space", "key-" + k), (x) -> decisions.add(x));
      Assert.assertEquals(1, decisions.size());
      Assert.assertNull(decisions.get(0));
      decisions.clear();
    }
    Assert.assertEquals(0, decisions.size());
    table.integrate("t1", Collections.singleton("space"));
    table.integrate("t2", Collections.singleton("space"));
    table.integrate("t3", Collections.singleton("space"));
    Assert.assertEquals(0, decisions.size());
    table.broadcast();
    Assert.assertEquals(100, decisions.size());
    {
      int t1Count = 0;
      int t2Count = 0;
      int t3Count = 0;
      for (String decision : decisions) {
        if ("t1".equals(decision)) {
          t1Count++;
        } else if ("t2".equals(decision)) {
          t2Count++;
        } else if ("t3".equals(decision)) {
          t3Count++;
        } else {
          Assert.fail();
        }
      }
      Assert.assertEquals(31, t1Count);
      Assert.assertEquals(32, t2Count);
      Assert.assertEquals(37, t3Count);
    }
    decisions.clear();
    table.integrate("t3", Collections.emptyList());
    Assert.assertEquals(0, decisions.size());
    table.broadcast();
    Assert.assertEquals(37, decisions.size());
    {
      int t1Count = 0;
      int t2Count = 0;
      for (String decision : decisions) {
        if ("t1".equals(decision)) {
          t1Count++;
        } else if ("t2".equals(decision)) {
          t2Count++;
        } else {
          Assert.fail();
        }
      }
      Assert.assertEquals(19, t1Count);
      Assert.assertEquals(18, t2Count);
    }
    events.assertHistory("[GAIN:space][SHARE:space=t1,t2,t3][SHARE:space=t1,t2]");
  }

  @Test
  public void multiKeysWithRebalanceByAdd() {
    MockSpaceTrackingEvents events = new MockSpaceTrackingEvents();
    RoutingTable table = new RoutingTable(events);
    ArrayList<String> decisions = new ArrayList<>();
    for (int k = 0; k < 100; k++) {
      table.subscribe(new Key("space", "key-" + k), (x) -> decisions.add(x));
      Assert.assertEquals(1, decisions.size());
      Assert.assertNull(decisions.get(0));
      decisions.clear();
    }
    Assert.assertEquals(0, decisions.size());
    table.integrate("t1", Collections.singleton("space"));
    table.integrate("t2", Collections.singleton("space"));
    Assert.assertEquals(0, decisions.size());
    table.broadcast();
    Assert.assertEquals(100, decisions.size());
    {
      int t1Count = 0;
      int t2Count = 0;
      for (String decision : decisions) {
        if ("t1".equals(decision)) {
          t1Count++;
        } else if ("t2".equals(decision)) {
          t2Count++;
        } else {
          Assert.fail();
        }
      }
      Assert.assertEquals(50, t1Count);
      Assert.assertEquals(50, t2Count);
    }
    decisions.clear();
    table.integrate("t3", Collections.singleton("space"));
    Assert.assertEquals(0, decisions.size());
    table.broadcast();
    Assert.assertEquals(37, decisions.size());
    {
      int t1Count = 0;
      int t2Count = 0;
      int t3Count = 0;
      for (String decision : decisions) {
        if ("t1".equals(decision)) {
          t1Count++;
        } else if ("t2".equals(decision)) {
          t2Count++;
        } else if ("t3".equals(decision)) {
          t3Count++;
        } else {
          Assert.fail();
        }
      }
      Assert.assertEquals(0, t1Count);
      Assert.assertEquals(0, t2Count);
      Assert.assertEquals(37, t3Count);
    }
    events.assertHistory("[GAIN:space][SHARE:space=t1,t2][SHARE:space=t1,t2,t3]");
  }
}
