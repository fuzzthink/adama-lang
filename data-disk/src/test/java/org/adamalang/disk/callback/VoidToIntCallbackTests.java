/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.disk.callback;

import org.adamalang.common.Callback;
import org.adamalang.common.ErrorCodeException;
import org.adamalang.disk.mocks.SimpleIntCallback;
import org.junit.Test;

public class VoidToIntCallbackTests {
  @Test
  public void proxy_success() throws Exception {
    SimpleIntCallback callback = new SimpleIntCallback();
    VoidToIntCallback adapt = new VoidToIntCallback(123, callback);
    adapt.success(null);
    callback.assertSuccess(123);
  }

  @Test
  public void proxy_failure() throws Exception {
    SimpleIntCallback callback = new SimpleIntCallback();
    VoidToIntCallback adapt = new VoidToIntCallback(123, callback);
    adapt.failure(new ErrorCodeException(-52));
    callback.assertFailure(-52);
  }
}
