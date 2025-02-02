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
import org.adamalang.runtime.natives.NtComplex;

public class DComplex implements DeltaNode {
  private NtComplex prior;

  public DComplex() {
    prior = null;
  }

  /** the double is no longer visible (was made private) */
  public void hide(final PrivateLazyDeltaWriter writer) {
    if (prior != null) {
      writer.writeNull();
      prior = null;
    }
  }

  /** the double is visible, so show changes */
  public void show(final NtComplex value, final PrivateLazyDeltaWriter writer) {
    if (prior == null || !value.equals(prior)) {
      writer.writeNtComplex(value);
    }
    prior = value;
  }

  /** memory usage */
  @Override
  public long __memory() {
    return (prior != null ? prior.memory() : 0) + 32;
  }
}
