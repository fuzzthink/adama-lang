/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.runtime.sys;

/** Used to predict inventory by estimating growth of resources and correcting with a periodic precise inventory */
public class PredictiveInventory {
  private long memory;
  private long ticks;
  private long memory_growth;
  private long ticks_growth;
  private long messages;
  private long count;
  private Snapshot[] snapshots;

  private class Snapshot {
    private final long memory;
    private final long ticks;
    private final long count;

    public Snapshot(long memory, long ticks, long count) {
      this.memory = memory;
      this.ticks = ticks;
      this.count = count;
    }
  }

  public static class Billing {
    public final long memory;
    public final long cpu;
    public final long count;
    public final long messages;

    public Billing(long memory, long cpu, long count, long messages) {
      this.memory = memory;
      this.cpu = cpu;
      this.count = count;
      this.messages = messages;
    }

    public static Billing add(Billing a, Billing b) {
      return new Billing(a.memory + b.memory, a.cpu + b.cpu, a.count + b.count, a.messages + b.messages);
    }
  }

  public Billing toBill() {
    Billing billing = new Billing(memory, ticks, count, messages);
    messages = 0;
    return billing;
  }

  public static class PreciseSnapshotAccumulator {
    public long memory;
    public long ticks;
    public int count;

    public PreciseSnapshotAccumulator() {
      this.memory = 0;
      this.ticks = 0;
      this.count = 0;
    }
  }

  public PredictiveInventory() {
    this.memory = 0;
    this.ticks = 0;
    this.memory_growth = 0;
    this.ticks_growth = 0;
    this.messages = 0;
    this.count = 0;
    this.snapshots = new Snapshot[4];
  }

  /** provide a precise and accurate accounting of the state */
  public void accurate(PreciseSnapshotAccumulator preciseSnapshotAccumulator) {
    // we put this in the buffer
    for (int k = 0; k < snapshots.length - 1; k++) {
      snapshots[k] = snapshots[k+1];
    }
    snapshots[snapshots.length - 1] = new Snapshot(preciseSnapshotAccumulator.memory, preciseSnapshotAccumulator.ticks, preciseSnapshotAccumulator.count);

    // absorb the precision
    this.memory = preciseSnapshotAccumulator.memory;
    this.ticks = preciseSnapshotAccumulator.ticks;
    this.count = preciseSnapshotAccumulator.count;

    // compute the average document size and use that as the estimate growth
    this.memory_growth = 0;
    this.ticks_growth = 0;
    long n = 0;
    for (int k = 0; k < snapshots.length; k++) {
      if (snapshots[k] != null) {
        this.memory_growth += snapshots[k].memory;
        this.ticks_growth += snapshots[k].ticks;
        n += snapshots[k].count;
      }
    }
    if (n > 0) {
      this.memory_growth /= n;
      this.ticks_growth /= n;
    }
  }

  public void grow() {
    this.memory += memory_growth;
    this.ticks += ticks_growth;
    this.count ++;
  }

  public void message() {
    this.messages ++;
  }
}
