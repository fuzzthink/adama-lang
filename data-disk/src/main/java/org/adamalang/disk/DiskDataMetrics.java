/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.disk;

import org.adamalang.common.metrics.CallbackMonitor;
import org.adamalang.common.metrics.Inflight;
import org.adamalang.common.metrics.MetricsFactory;

public class DiskDataMetrics {
  public final CallbackMonitor disk_data_get;
  public final CallbackMonitor disk_data_initialize;
  public final CallbackMonitor disk_data_patch;
  public final CallbackMonitor disk_data_compute;
  public final CallbackMonitor disk_data_delete;
  public final CallbackMonitor disk_data_snapshot;
  public final Inflight disk_data_open_wal_files;
  public final Runnable disk_data_flush_file;
  public final Runnable disk_data_unload;

  public DiskDataMetrics(MetricsFactory factory) {
    this.disk_data_get = factory.makeCallbackMonitor("disk_data_get");
    this.disk_data_initialize = factory.makeCallbackMonitor("disk_data_initialize");
    this.disk_data_patch = factory.makeCallbackMonitor("disk_data_patch");
    this.disk_data_compute = factory.makeCallbackMonitor("disk_data_compute");
    this.disk_data_delete = factory.makeCallbackMonitor("disk_data_delete");
    this.disk_data_snapshot = factory.makeCallbackMonitor("disk_data_snapshot");
    this.disk_data_open_wal_files = factory.inflight("disk_data_open_wal_files");
    this.disk_data_flush_file = factory.counter("disk_data_flush_file");
    this.disk_data_unload = factory.counter("disk_data_unload");
  }
}
