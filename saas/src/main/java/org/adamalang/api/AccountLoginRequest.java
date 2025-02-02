/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adamalang.common.Callback;
import org.adamalang.common.ErrorCodeException;
import org.adamalang.common.NamedRunnable;
import org.adamalang.connection.Session;
import org.adamalang.validators.ValidateEmail;
import org.adamalang.web.io.*;

/**  */
public class AccountLoginRequest {
  public final String email;
  public final Integer userId;
  public final String password;

  public AccountLoginRequest(final String email, final Integer userId, final String password) {
    this.email = email;
    this.userId = userId;
    this.password = password;
  }

  public static void resolve(ConnectionNexus nexus, JsonRequest request, Callback<AccountLoginRequest> callback) {
    try {
      final BulkLatch<AccountLoginRequest> _latch = new BulkLatch<>(nexus.executor, 1, callback);
      final String email = request.getString("email", true, 473103);
      ValidateEmail.validate(email);
      final LatchRefCallback<Integer> userId = new LatchRefCallback<>(_latch);
      final String password = request.getString("password", true, 465917);
      _latch.with(() -> new AccountLoginRequest(email, userId.get(), password));
      nexus.emailService.execute(nexus.session, email, userId);
    } catch (ErrorCodeException ece) {
      nexus.executor.execute(new NamedRunnable("accountlogin-error") {
        @Override
        public void execute() throws Exception {
          callback.failure(ece);
        }
      });
    }
  }

  public void logInto(ObjectNode _node) {
    _node.put("email", email);
    org.adamalang.transforms.UserIdResolver.logInto(userId, _node);
  }
}
