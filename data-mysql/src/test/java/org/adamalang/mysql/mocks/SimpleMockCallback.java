/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.mysql.mocks;

import org.adamalang.common.Callback;
import org.adamalang.common.ErrorCodeException;
import org.junit.Assert;

public class SimpleMockCallback implements Callback<Void> {
  private boolean success;
  private int count;
  private int reason;

  public SimpleMockCallback() {
    this.success = false;
    this.count = 0;
    this.reason = 0;
  }

  @Override
  public void success(Void value) {
    count++;
    success = true;
  }

  @Override
  public void failure(ErrorCodeException ex) {
    ex.printStackTrace();
    count++;
    success = false;
    reason = ex.code;
  }

  public void assertSuccess() {
    Assert.assertEquals(1, count);
    Assert.assertTrue(success);
  }

  public void assertFailure(int code) {
    Assert.assertEquals(1, count);
    Assert.assertFalse(success);
    Assert.assertEquals(code, this.reason);
  }
}
