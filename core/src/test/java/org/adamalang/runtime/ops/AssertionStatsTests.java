/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.runtime.ops;

import org.junit.Assert;
import org.junit.Test;

public class AssertionStatsTests {
  @Test
  public void coverage() {
    final var stats = new AssertionStats(52, 1);
    Assert.assertEquals(52, stats.total);
    Assert.assertEquals(1, stats.failures);
  }
}
