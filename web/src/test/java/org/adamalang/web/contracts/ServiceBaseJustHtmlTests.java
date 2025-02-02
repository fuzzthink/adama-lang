/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.web.contracts;

import org.adamalang.common.ErrorCodeException;
import org.adamalang.web.io.JsonResponder;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ServiceBaseJustHtmlTests {
  @Test
  public void coverage() {
    ServiceBase base = ServiceBase.JUST_HTTP(new HttpHandler() {
      @Override
      public HttpResult handleGet(String uri) {
        return new HttpResult("yay", "yay".getBytes(StandardCharsets.UTF_8));
      }

      @Override
      public HttpResult handlePost(String uri, String body) {
        return new HttpResult("post", "post".getBytes(StandardCharsets.UTF_8));
      }
    });
    base.establish(null).execute(null, new JsonResponder() {
      @Override
      public void stream(String json) {

      }

      @Override
      public void finish(String json) {

      }

      @Override
      public void error(ErrorCodeException ex) {

      }
    });
    base.establish(null).keepalive();
    base.establish(null).kill();
    base.downloader();
    Assert.assertEquals("yay", new String(base.http().handleGet("x").body, StandardCharsets.UTF_8));
    Assert.assertEquals("post", new String(base.http().handlePost("x", null).body, StandardCharsets.UTF_8));
  }
}
