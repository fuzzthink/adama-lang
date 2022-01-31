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
import org.adamalang.connection.Session;
import org.adamalang.web.io.*;

/**  */
public class ConnectionUpdateRequest {
  public final Long connection;
  public final ObjectNode viewerState;

  public ConnectionUpdateRequest(final Long connection, final ObjectNode viewerState) {
    this.connection = connection;
    this.viewerState = viewerState;
  }

  public static void resolve(ConnectionNexus nexus, JsonRequest request, Callback<ConnectionUpdateRequest> callback) {
    try {
      final Long connection = request.getLong("connection", true, 405505);
      final ObjectNode viewerState = request.getObject("viewer-state", false, 0);
      nexus.executor.execute(() -> {
        callback.success(new ConnectionUpdateRequest(connection, viewerState));
      });
    } catch (ErrorCodeException ece) {
      nexus.executor.execute(() -> {
        callback.failure(ece);
      });
    }
  }
}
