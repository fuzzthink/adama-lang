/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.runtime.delta;

import org.adamalang.runtime.contracts.DeltaNode;
import org.adamalang.runtime.json.PrivateLazyDeltaWriter;
import org.adamalang.runtime.natives.NtClient;

/** a client that will respect privacy and sends state to client only on changes */
public class DClient implements DeltaNode {
  private NtClient prior;

  public DClient() {
    prior = null;
  }

  /** the client is no longer visible (was made private) */
  public void hide(final PrivateLazyDeltaWriter writer) {
    if (prior != null) {
      writer.writeNull();
      prior = null;
    }
  }

  /** the client is visible, so show changes */
  public void show(final NtClient value, final PrivateLazyDeltaWriter writer) {
    if (prior == null || !value.equals(prior)) {
      final var obj = writer.planObject();
      obj.planField("@t").writeInt(1);
      obj.planField("agent").writeFastString(value.agent);
      obj.planField("authority").writeFastString(value.authority);
      obj.end();
    }
    prior = value;
  }

  /** memory usage */
  @Override
  public long __memory() {
    return (prior != null ? prior.memory() : 0) + 32;
  }
}
