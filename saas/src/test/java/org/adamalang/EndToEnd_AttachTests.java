/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang;

import com.google.gson.stream.JsonWriter;
import org.adamalang.common.Hashing;
import org.adamalang.common.Json;
import org.adamalang.runtime.json.JsonStreamWriter;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Iterator;

public class EndToEnd_AttachTests {

  @Test
  public void cantAttach() throws Exception {
    try (TestFrontEnd fe = new TestFrontEnd()) {
      Iterator<String> c0 = fe.execute("{}");
      Assert.assertEquals("ERROR:233120", c0.next());
      Runnable latch1 = fe.latchOnEmail("x@x.com");
      Iterator<String> c1 = fe.execute("{\"id\":1,\"method\":\"init/start\",\"email\":\"x@x.com\"}");
      latch1.run();
      Iterator<String> c2 = fe.execute("{\"id\":2,\"connection\":1,\"method\":\"init/generate-identity\",\"code\":\"" + fe.codesSentToEmail.get("x@x.com") + "\"}");
      String result1 = c2.next();
      Assert.assertEquals("FINISH:{}", c1.next());
      Assert.assertTrue(result1.length() > 0);
      Assert.assertEquals("FINISH:{\"identity\":", result1.substring(0, 19));
      String devIdentity = Json.parseJsonObject(result1.substring(7)).get("identity").textValue();
      Iterator<String> c3 = fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"space/create\",\"space\":\"newspace\"}");
      Assert.assertEquals("FINISH:{}", c3.next());
      Iterator<String> c4  =
          fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"space/set\",\"space\":\"newspace\",\"plan\":"+EndToEnd_SpaceTests.planFor(
              "@static { create(who) { return true; } }" +
                  "@connected(who) { return true; }" +
                  "public int x = 1;" +
                  "message M { int z; }" +
                  "channel foo(M m) { x += m.z; }"
          ) + "}");
      Assert.assertEquals("FINISH:{}", c4.next());
      Iterator<String> c5 = fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"document/create\",\"space\":\"newspace\",\"key\":\"a\",\"arg\":{}}");
      Assert.assertEquals("FINISH:{}", c5.next());
      Iterator<String> c6 = fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"attachment/start\",\"space\":\"newspace\",\"key\":\"a\",\"filename\":\"thefilename\",\"content-type\":\"text/plain\"}");
      Assert.assertEquals("ERROR:966768", c6.next());
    }
  }

  @Test
  public void canAttach() throws Exception {
    try (TestFrontEnd fe = new TestFrontEnd()) {
      for (File attachment : fe.attachmentRoot.listFiles()) {
        if (attachment.getName().endsWith(".inflight")) {
          attachment.delete();
        }
      }
      Assert.assertEquals(0, fe.attachmentRoot.listFiles().length);
      Iterator<String> c0 = fe.execute("{}");
      Assert.assertEquals("ERROR:233120", c0.next());
      Runnable latch1 = fe.latchOnEmail("x@x.com");
      Iterator<String> c1 = fe.execute("{\"id\":1,\"method\":\"init/start\",\"email\":\"x@x.com\"}");
      latch1.run();
      Iterator<String> c2 = fe.execute("{\"id\":2,\"connection\":1,\"method\":\"init/generate-identity\",\"code\":\"" + fe.codesSentToEmail.get("x@x.com") + "\"}");
      String result1 = c2.next();
      Assert.assertEquals("FINISH:{}", c1.next());
      Assert.assertTrue(result1.length() > 0);
      Assert.assertEquals("FINISH:{\"identity\":", result1.substring(0, 19));
      String devIdentity = Json.parseJsonObject(result1.substring(7)).get("identity").textValue();
      Iterator<String> c3 = fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"space/create\",\"space\":\"newspace\"}");
      Assert.assertEquals("FINISH:{}", c3.next());
      Iterator<String> c4  =
          fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"space/set\",\"space\":\"newspace\",\"plan\":"+EndToEnd_SpaceTests.planFor(
              "@static { create(who) { return true; } }" +
                  "@connected(who) { return true; }" +
                  "@can_attach(who) { return true; }" +
                  "@attached(who, what) { }" +
                  "public int x = 1;" +
                  "message M { int z; }" +
                  "channel foo(M m) { x += m.z; }"
          ) + "}");
      Assert.assertEquals("FINISH:{}", c4.next());
      Iterator<String> c5 = fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"document/create\",\"space\":\"newspace\",\"key\":\"a\",\"arg\":{}}");
      Assert.assertEquals("FINISH:{}", c5.next());
      Iterator<String> c6 = fe.execute("{\"id\":100,\"identity\":\"" + devIdentity + "\",\"method\":\"attachment/start\",\"space\":\"newspace\",\"key\":\"a\",\"filename\":\"thefilename\",\"content-type\":\"text/plain\"}");
      Assert.assertEquals("STREAM:{\"chunk_request_size\":65536}", c6.next());
      Assert.assertEquals(1, fe.attachmentRoot.listFiles().length);
      byte[] chunkToSend = "[This is a chunk]".getBytes(StandardCharsets.UTF_8);

      for (int k = 0; k < 10; k++) {
        Iterator<String> c7 = fe.execute("{\"id\":8,\"upload\":100,\"identity\":\"" + devIdentity + "\",\"method\":\"attachment/append\"," + partialJsonChunk(chunkToSend, false) + "}");
        Assert.assertEquals("FINISH:{}", c7.next());
        Assert.assertEquals("STREAM:{\"chunk_request_size\":65536}", c6.next());
      }

      Iterator<String> c8 = fe.execute("{\"id\":8,\"upload\":100,\"identity\":\"" + devIdentity + "\",\"method\":\"attachment/finish\"}");
      Assert.assertEquals("FINISH:{}", c8.next());
      Assert.assertEquals("FINISH:{}", c6.next());
      Assert.assertEquals(1, fe.attachmentRoot.listFiles().length);
      File f = fe.attachmentRoot.listFiles()[0];
      Assert.assertEquals("[This is a chunk][This is a chunk][This is a chunk][This is a chunk][This is a chunk][This is a chunk][This is a chunk][This is a chunk][This is a chunk][This is a chunk]", Files.readString(f.toPath()));
    }
  }

  @Test
  public void attachmentDataCorruption() throws Exception {
    try (TestFrontEnd fe = new TestFrontEnd()) {
      for (File attachment : fe.attachmentRoot.listFiles()) {
        if (attachment.getName().endsWith(".inflight")) {
          attachment.delete();
        }
      }
      Assert.assertEquals(0, fe.attachmentRoot.listFiles().length);
      Iterator<String> c0 = fe.execute("{}");
      Assert.assertEquals("ERROR:233120", c0.next());
      Runnable latch1 = fe.latchOnEmail("x@x.com");
      Iterator<String> c1 = fe.execute("{\"id\":1,\"method\":\"init/start\",\"email\":\"x@x.com\"}");
      latch1.run();
      Iterator<String> c2 = fe.execute("{\"id\":2,\"connection\":1,\"method\":\"init/generate-identity\",\"code\":\"" + fe.codesSentToEmail.get("x@x.com") + "\"}");
      String result1 = c2.next();
      Assert.assertEquals("FINISH:{}", c1.next());
      Assert.assertTrue(result1.length() > 0);
      Assert.assertEquals("FINISH:{\"identity\":", result1.substring(0, 19));
      String devIdentity = Json.parseJsonObject(result1.substring(7)).get("identity").textValue();
      Iterator<String> c3 = fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"space/create\",\"space\":\"newspace\"}");
      Assert.assertEquals("FINISH:{}", c3.next());
      Iterator<String> c4  =
          fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"space/set\",\"space\":\"newspace\",\"plan\":"+EndToEnd_SpaceTests.planFor(
              "@static { create(who) { return true; } }" +
                  "@connected(who) { return true; }" +
                  "@can_attach(who) { return true; }" +
                  "@attached(who, what) { }" +
                  "public int x = 1;" +
                  "message M { int z; }" +
                  "channel foo(M m) { x += m.z; }"
          ) + "}");
      Assert.assertEquals("FINISH:{}", c4.next());
      Iterator<String> c5 = fe.execute("{\"id\":7,\"identity\":\"" + devIdentity + "\",\"method\":\"document/create\",\"space\":\"newspace\",\"key\":\"a\",\"arg\":{}}");
      Assert.assertEquals("FINISH:{}", c5.next());
      Iterator<String> c6 = fe.execute("{\"id\":100,\"identity\":\"" + devIdentity + "\",\"method\":\"attachment/start\",\"space\":\"newspace\",\"key\":\"a\",\"filename\":\"thefilename\",\"content-type\":\"text/plain\"}");
      Assert.assertEquals("STREAM:{\"chunk_request_size\":65536}", c6.next());
      Assert.assertEquals(1, fe.attachmentRoot.listFiles().length);
      byte[] chunkToSend = "[This is a chunk]".getBytes(StandardCharsets.UTF_8);

      Iterator<String> c7 = fe.execute("{\"id\":8,\"upload\":100,\"identity\":\"" + devIdentity + "\",\"method\":\"attachment/append\"," + partialJsonChunk(chunkToSend, true) + "}");
      Assert.assertEquals("ERROR:999472", c7.next());
      Assert.assertEquals("ERROR:920719", c6.next());
      Assert.assertEquals(0, fe.attachmentRoot.listFiles().length);
    }
  }

  private String partialJsonChunk(byte[] chunk, boolean corrupt) {
    JsonStreamWriter writer = new JsonStreamWriter();
    MessageDigest md5chunk = Hashing.md5();
    md5chunk.update(chunk);
    writer.writeObjectFieldIntro("chunk-md5");
    writer.writeString(Hashing.finishAndEncode(md5chunk));
    if (corrupt) {
      chunk[chunk.length / 2]++;
    }
    writer.writeObjectFieldIntro("base64-bytes");
    writer.writeString(Base64.encodeBase64String(chunk));
    return writer.toString();
  }
}
