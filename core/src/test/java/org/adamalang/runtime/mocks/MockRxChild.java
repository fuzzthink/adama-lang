/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.runtime.mocks;

import org.adamalang.runtime.contracts.RxChild;
import org.junit.Assert;

public class MockRxChild implements RxChild {
  public boolean alive;
  public int invalidCount;

  public MockRxChild() {
    invalidCount = 0;
    alive = true;
  }

  @Override
  public boolean __raiseInvalid() {
    invalidCount++;
    return alive;
  }

  public void assertInvalidateCount(final int expected) {
    Assert.assertEquals(expected, invalidCount);
  }
}
