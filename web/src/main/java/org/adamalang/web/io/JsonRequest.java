/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.web.io;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adamalang.ErrorCodes;
import org.adamalang.common.ErrorCodeException;

/** a thin wrapper for easy access to a JSON request */
public class JsonRequest {
  public final ConnectionContext context;
  private final ObjectNode node;

  public JsonRequest(ObjectNode node, ConnectionContext context) {
    this.node = node;
    this.context = context;
  }

  public int id() throws ErrorCodeException {
    return getInteger("id", true, ErrorCodes.USERLAND_REQUEST_NO_ID_PROPERTY);
  }

  public Integer getInteger(String field, boolean mustExist, int errorIfDoesnt) throws ErrorCodeException {
    final var fieldNode = node.get(field);
    if (fieldNode == null || fieldNode.isNull() || !(fieldNode.isNumber() && fieldNode.isIntegralNumber() || fieldNode.isTextual())) {
      if (mustExist) {
        throw new ErrorCodeException(errorIfDoesnt);
      } else {
        return null;
      }
    }
    if (fieldNode.isTextual()) {
      try {
        return Integer.parseInt(fieldNode.textValue());
      } catch (NumberFormatException nfe) {
        throw new ErrorCodeException(errorIfDoesnt);
      }
    }
    return fieldNode.intValue();
  }

  public void dumpIntoLog(ObjectNode logItem) {
    logItem.put("ip", context.remoteIp);
    logItem.put("origin", context.origin);
  }

  public String method() throws ErrorCodeException {
    return getString("method", true, ErrorCodes.USERLAND_REQUEST_NO_METHOD_PROPERTY);
  }

  public String getString(String field, boolean mustExist, int errorIfDoesnt) throws ErrorCodeException {
    final var fieldNode = node.get(field);
    if (fieldNode == null || fieldNode.isNull() || !(fieldNode.isTextual() || fieldNode.isNumber())) {
      if (mustExist) {
        throw new ErrorCodeException(errorIfDoesnt);
      }
      return null;
    }
    if (fieldNode.isNumber()) {
      return fieldNode.numberValue().toString();
    }
    return fieldNode.textValue();
  }

  public Boolean getBoolean(String field, boolean mustExist, int errorIfDoesnt) throws ErrorCodeException {
    final var fieldNode = node.get(field);
    if (fieldNode == null || fieldNode.isNull() || !fieldNode.isBoolean()) {
      if (mustExist) {
        throw new ErrorCodeException(errorIfDoesnt);
      }
      return null;
    }
    return fieldNode.booleanValue();
  }

  public Long getLong(String field, boolean mustExist, int errorIfDoesnt) throws ErrorCodeException {
    final var fieldNode = node.get(field);
    if (fieldNode == null || fieldNode.isNull() || !(fieldNode.isNumber() && fieldNode.isIntegralNumber() || fieldNode.isTextual())) {
      if (mustExist) {
        throw new ErrorCodeException(errorIfDoesnt);
      } else {
        return null;
      }
    }
    if (fieldNode.isTextual()) {
      try {
        return Long.parseLong(fieldNode.textValue());
      } catch (NumberFormatException nfe) {
        throw new ErrorCodeException(errorIfDoesnt);
      }
    }
    return fieldNode.longValue();
  }

  public ObjectNode getObject(String field, boolean mustExist, int errorIfDoesnt) throws ErrorCodeException {
    final var fieldNode = node.get(field);
    if (fieldNode == null || fieldNode.isNull() || !fieldNode.isObject()) {
      if (mustExist) {
        throw new ErrorCodeException(errorIfDoesnt);
      } else {
        return null;
      }
    }
    return (ObjectNode) fieldNode;
  }
}
