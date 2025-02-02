/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.runtime;

import org.adamalang.common.Callback;
import org.adamalang.common.ErrorCodeException;
import org.adamalang.common.metrics.NoOpMetricsFactory;
import org.adamalang.runtime.contracts.DocumentMonitor;
import org.adamalang.runtime.data.Key;
import org.adamalang.runtime.contracts.Perspective;
import org.adamalang.common.SimpleExecutor;
import org.adamalang.runtime.json.JsonStreamReader;
import org.adamalang.runtime.json.PrivateView;
import org.adamalang.runtime.mocks.MockTime;
import org.adamalang.runtime.natives.NtClient;
import org.adamalang.runtime.ops.StdOutDocumentMonitor;
import org.adamalang.runtime.sys.CoreMetrics;
import org.adamalang.runtime.sys.DocumentThreadBase;
import org.adamalang.runtime.sys.DurableLivingDocument;
import org.adamalang.support.testgen.DumbDataService;
import org.adamalang.translator.jvm.LivingDocumentFactory;
import org.junit.Assert;

import java.util.ArrayList;

public class RealDocumentSetup {
  public final LivingDocumentFactory factory;
  public final MockTime time;
  public final DurableLivingDocument document;
  private DurableLivingDocument mirror;

  public RealDocumentSetup(final String code) throws Exception {
    this(code, null);
  }

  public RealDocumentSetup(final String code, final String json) throws Exception {
    this(code, json, true);
  }

  public RealDocumentSetup(final String code, final String json, final boolean stdout)
      throws Exception {
    this(code, json, stdout, new MockTime());
  }

  public RealDocumentSetup(
      final String code, final String json, final boolean stdout, MockTime time) throws Exception {
    this.time = time;
    DumbDataService dds =
        new DumbDataService(
            (update) -> {
              if (stdout) {
                System.out.println(" REQ :" + update.request);
                System.out.println("FORWARD:" + update.redo);
                System.out.println("REVERSE:" + update.undo);
              }
              if (mirror != null) {
                mirror.document().__insert(new JsonStreamReader(update.redo));
              }
            });
    DocumentThreadBase base = new DocumentThreadBase(dds, new CoreMetrics(new NoOpMetricsFactory()), SimpleExecutor.NOW, time);
    dds.setData(json);
    factory = LivingDocumentTests.compile(code);
    DumbDataService.DumbDurableLivingDocumentAcquire acquireReal =
        new DumbDataService.DumbDurableLivingDocumentAcquire();
    DumbDataService.DumbDurableLivingDocumentAcquire acquireMirror =
        new DumbDataService.DumbDurableLivingDocumentAcquire();
    DocumentMonitor monitor = stdout ? new StdOutDocumentMonitor() : null;
    Key key = new Key("space", "0");
    if (json == null) {
      DurableLivingDocument.fresh(
          key, factory, NtClient.NO_ONE, "{}", "123", monitor, base, acquireReal);
      DurableLivingDocument.fresh(
          key, factory, NtClient.NO_ONE, "{}", "123", monitor, base, acquireMirror);
    } else {
      DurableLivingDocument.load(key, factory, monitor, base, acquireReal);
      DurableLivingDocument.load(key, factory, monitor, base, acquireMirror);
    }
    document = acquireReal.get();
    mirror = acquireMirror.get();
  }

  public void assertCompare() {
    Assert.assertEquals(mirror.json(), document.json());
  }

  public static class GotView implements Callback<PrivateView> {

    public PrivateView view = null;

    @Override
    public void success(PrivateView value) {
      view = value;
    }

    @Override
    public void failure(ErrorCodeException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static class AssertInt implements Callback<Integer> {
    public final int expected;

    public AssertInt(int value) {
      this.expected = value;
    }

    @Override
    public void success(Integer actual) {
      Assert.assertEquals(expected, (int) actual);
    }

    @Override
    public void failure(ErrorCodeException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static class AssertFailure implements Callback<Integer> {
    private int codeToExpect;

    public AssertFailure(int codeToExpect) {
      this.codeToExpect = codeToExpect;
    }

    @Override
    public void success(Integer actual) {
      throw new RuntimeException("should have failed");
    }

    @Override
    public void failure(ErrorCodeException ex) {
      Assert.assertEquals(codeToExpect, ex.code);
    }
  }

  public static class AssertNoResponse implements Callback<Integer> {
    public AssertNoResponse() {
    }

    @Override
    public void success(Integer actual) {
      Assert.fail();
    }

    @Override
    public void failure(ErrorCodeException ex) {
      Assert.fail();
    }
  }

  public static class ArrayPerspective implements Perspective {
    public final ArrayList<String> datum;

    public ArrayPerspective() {
      this.datum = new ArrayList<>();
    }

    @Override
    public void data(String data) {
      this.datum.add(data);
    }

    @Override
    public void disconnect() {}
  }
}
