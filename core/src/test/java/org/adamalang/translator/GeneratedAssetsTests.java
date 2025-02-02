/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.translator;

import org.junit.Test;

public class GeneratedAssetsTests extends GeneratedBase {
  private String cached_Attachment_1 = null;
  private String get_Attachment_1() {
    if (cached_Attachment_1 != null) {
      return cached_Attachment_1;
    }
    cached_Attachment_1 = generateTestOutput(true, "Attachment_1", "./test_code/Assets_Attachment_success.a");
    return cached_Attachment_1;
  }

  @Test
  public void testAttachmentEmission() {
    assertEmissionGood(get_Attachment_1());
  }

  @Test
  public void testAttachmentSuccess() {
    assertLivePass(get_Attachment_1());
  }

  @Test
  public void testAttachmentGoodWillHappy() {
    assertGoodWillHappy(get_Attachment_1());
  }

  @Test
  public void testAttachmentExceptionFree() {
    assertExceptionFree(get_Attachment_1());
  }

  @Test
  public void testAttachmentTODOFree() {
    assertTODOFree(get_Attachment_1());
  }

  @Test
  public void stable_Attachment_1() {
    String live = get_Attachment_1();
    StringBuilder gold = new StringBuilder();
    gold.append("Path:Assets_Attachment_success.a");
    gold.append("\n--EMISSION-----------------------------------------");
    gold.append("\nEmission Success, Yay");
    gold.append("\n--ISSUES-------------------------------------------");
    gold.append("\n[]\"--JAVA---------------------------------------------");
    gold.append("\nimport org.adamalang.runtime.async.*;");
    gold.append("\nimport org.adamalang.runtime.contracts.*;");
    gold.append("\nimport org.adamalang.runtime.delta.*;");
    gold.append("\nimport org.adamalang.runtime.delta.secure.*;");
    gold.append("\nimport org.adamalang.runtime.exceptions.*;");
    gold.append("\nimport org.adamalang.runtime.index.*;");
    gold.append("\nimport org.adamalang.runtime.json.*;");
    gold.append("\nimport org.adamalang.runtime.natives.*;");
    gold.append("\nimport org.adamalang.runtime.natives.lists.*;");
    gold.append("\nimport org.adamalang.runtime.ops.*;");
    gold.append("\nimport org.adamalang.runtime.reactives.*;");
    gold.append("\nimport org.adamalang.runtime.stdlib.*;");
    gold.append("\nimport org.adamalang.runtime.sys.*;");
    gold.append("\nimport java.util.function.Consumer;");
    gold.append("\nimport java.util.function.Function;");
    gold.append("\nimport java.util.ArrayList;");
    gold.append("\nimport java.util.Comparator;");
    gold.append("\nimport java.util.HashMap;");
    gold.append("\nimport java.util.HashSet;");
    gold.append("\nimport java.util.Map;");
    gold.append("\nimport java.util.Set;");
    gold.append("\nimport java.lang.Math;");
    gold.append("\npublic class Attachment_1 extends LivingDocument {");
    gold.append("\n  private final RxAsset latest;");
    gold.append("\n  private final RxLazy<NtAsset> my_file;");
    gold.append("\n  private final RxLazy<String> my_name;");
    gold.append("\n  private final RxLazy<String> my_id;");
    gold.append("\n  private final RxLazy<Long> my_size;");
    gold.append("\n  private final RxLazy<String> my_type;");
    gold.append("\n  private final RxLazy<Boolean> my_valid;");
    gold.append("\n  @Override");
    gold.append("\n  public long __memory() {");
    gold.append("\n    long __sum = super.__memory();");
    gold.append("\n    __sum += latest.__memory();");
    gold.append("\n    __sum += my_file.__memory();");
    gold.append("\n    __sum += my_name.__memory();");
    gold.append("\n    __sum += my_id.__memory();");
    gold.append("\n    __sum += my_size.__memory();");
    gold.append("\n    __sum += my_type.__memory();");
    gold.append("\n    __sum += my_valid.__memory();");
    gold.append("\n    return __sum;");
    gold.append("\n  }");
    gold.append("\n  public Attachment_1(DocumentMonitor __monitor) {");
    gold.append("\n    super(__monitor);");
    gold.append("\n    latest = new RxAsset(this, NtAsset.NOTHING);");
    gold.append("\n    my_file = new RxLazy<NtAsset>(this, () -> (latest.get()));");
    gold.append("\n    latest.__subscribe(my_file);");
    gold.append("\n    my_name = new RxLazy<String>(this, () -> (latest.get().name()));");
    gold.append("\n    latest.__subscribe(my_name);");
    gold.append("\n    my_id = new RxLazy<String>(this, () -> (latest.get().id()));");
    gold.append("\n    latest.__subscribe(my_id);");
    gold.append("\n    my_size = new RxLazy<Long>(this, () -> (latest.get().size()));");
    gold.append("\n    latest.__subscribe(my_size);");
    gold.append("\n    my_type = new RxLazy<String>(this, () -> (latest.get().type()));");
    gold.append("\n    latest.__subscribe(my_type);");
    gold.append("\n    my_valid = new RxLazy<Boolean>(this, () -> (latest.get().valid()));");
    gold.append("\n    latest.__subscribe(my_valid);");
    gold.append("\n    __goodwillBudget = 100000;");
    gold.append("\n    __goodwillLimitOfBudget = 100000;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __insert(JsonStreamReader __reader) {");
    gold.append("\n    if (__reader.startObject()) {");
    gold.append("\n      while(__reader.notEndOfObject()) {");
    gold.append("\n        String __fieldName = __reader.fieldName();");
    gold.append("\n        switch (__fieldName) {");
    gold.append("\n          case \"latest\":");
    gold.append("\n            latest.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__state\":");
    gold.append("\n            __state.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__constructed\":");
    gold.append("\n            __constructed.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__next_time\":");
    gold.append("\n            __next_time.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__last_expire_time\":");
    gold.append("\n            __last_expire_time.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__blocked\":");
    gold.append("\n            __blocked.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__seq\":");
    gold.append("\n            __seq.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__entropy\":");
    gold.append("\n            __entropy.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__auto_future_id\":");
    gold.append("\n            __auto_future_id.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__connection_id\":");
    gold.append("\n            __connection_id.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__message_id\":");
    gold.append("\n            __message_id.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__time\":");
    gold.append("\n            __time.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__auto_table_row_id\":");
    gold.append("\n            __auto_table_row_id.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__dedupe\":");
    gold.append("\n            __hydrateDeduper(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__clients\":");
    gold.append("\n            __hydrateClients(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__messages\":");
    gold.append("\n            __hydrateMessages(__reader);");
    gold.append("\n            break;");
    gold.append("\n          default:");
    gold.append("\n            __reader.skipValue();");
    gold.append("\n        }");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __patch(JsonStreamReader __reader) {");
    gold.append("\n    if (__reader.startObject()) {");
    gold.append("\n      while(__reader.notEndOfObject()) {");
    gold.append("\n        String __fieldName = __reader.fieldName();");
    gold.append("\n        switch (__fieldName) {");
    gold.append("\n          case \"latest\":");
    gold.append("\n            latest.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__state\":");
    gold.append("\n            __state.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__constructed\":");
    gold.append("\n            __constructed.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__next_time\":");
    gold.append("\n            __next_time.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__last_expire_time\":");
    gold.append("\n            __last_expire_time.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__blocked\":");
    gold.append("\n            __blocked.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__seq\":");
    gold.append("\n            __seq.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__entropy\":");
    gold.append("\n            __entropy.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__auto_future_id\":");
    gold.append("\n            __auto_future_id.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__connection_id\":");
    gold.append("\n            __connection_id.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__message_id\":");
    gold.append("\n            __message_id.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__time\":");
    gold.append("\n            __time.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__auto_table_row_id\":");
    gold.append("\n            __auto_table_row_id.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__dedupe\":");
    gold.append("\n            __hydrateDeduper(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__clients\":");
    gold.append("\n            __hydrateClients(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__messages\":");
    gold.append("\n            __hydrateMessages(__reader);");
    gold.append("\n            break;");
    gold.append("\n          default:");
    gold.append("\n            __reader.skipValue();");
    gold.append("\n        }");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __dump(JsonStreamWriter __writer) {");
    gold.append("\n    __writer.beginObject();");
    gold.append("\n    __writer.writeObjectFieldIntro(\"latest\");");
    gold.append("\n    latest.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__state\");");
    gold.append("\n    __state.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__constructed\");");
    gold.append("\n    __constructed.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__next_time\");");
    gold.append("\n    __next_time.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__last_expire_time\");");
    gold.append("\n    __last_expire_time.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__blocked\");");
    gold.append("\n    __blocked.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__seq\");");
    gold.append("\n    __seq.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__entropy\");");
    gold.append("\n    __entropy.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__auto_future_id\");");
    gold.append("\n    __auto_future_id.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__connection_id\");");
    gold.append("\n    __connection_id.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__message_id\");");
    gold.append("\n    __message_id.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__time\");");
    gold.append("\n    __time.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__auto_table_row_id\");");
    gold.append("\n    __auto_table_row_id.__dump(__writer);");
    gold.append("\n    __dumpDeduper(__writer);");
    gold.append("\n    __dumpClients(__writer);");
    gold.append("\n    __dumpMessages(__writer);");
    gold.append("\n    __writer.endObject();");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __commit(String __name, JsonStreamWriter __forward, JsonStreamWriter __reverse) {");
    gold.append("\n    __state.__commit(\"__state\", __forward, __reverse);");
    gold.append("\n    __constructed.__commit(\"__constructed\", __forward, __reverse);");
    gold.append("\n    __next_time.__commit(\"__next_time\", __forward, __reverse);");
    gold.append("\n    __last_expire_time.__commit(\"__last_expire_time\", __forward, __reverse);");
    gold.append("\n    __blocked.__commit(\"__blocked\", __forward, __reverse);");
    gold.append("\n    __seq.__commit(\"__seq\", __forward, __reverse);");
    gold.append("\n    __entropy.__commit(\"__entropy\", __forward, __reverse);");
    gold.append("\n    __auto_future_id.__commit(\"__auto_future_id\", __forward, __reverse);");
    gold.append("\n    __connection_id.__commit(\"__connection_id\", __forward, __reverse);");
    gold.append("\n    __message_id.__commit(\"__message_id\", __forward, __reverse);");
    gold.append("\n    __time.__commit(\"__time\", __forward, __reverse);");
    gold.append("\n    __auto_table_row_id.__commit(\"__auto_table_row_id\", __forward, __reverse);");
    gold.append("\n    latest.__commit(\"latest\", __forward, __reverse);");
    gold.append("\n    /* root */");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __revert() {");
    gold.append("\n    __state.__revert();");
    gold.append("\n    __constructed.__revert();");
    gold.append("\n    __next_time.__revert();");
    gold.append("\n    __last_expire_time.__revert();");
    gold.append("\n    __blocked.__revert();");
    gold.append("\n    __seq.__revert();");
    gold.append("\n    __entropy.__revert();");
    gold.append("\n    __auto_future_id.__revert();");
    gold.append("\n    __connection_id.__revert();");
    gold.append("\n    __message_id.__revert();");
    gold.append("\n    __time.__revert();");
    gold.append("\n    __auto_table_row_id.__revert();");
    gold.append("\n    latest.__revert();");
    gold.append("\n    /* root */");
    gold.append("\n  }");
    gold.append("\n  private class DeltaAttachment_1 implements DeltaNode {");
    gold.append("\n    private DAsset __dlatest;");
    gold.append("\n    private int __gmy_file;");
    gold.append("\n    private DAsset __dmy_file;");
    gold.append("\n    private int __gmy_name;");
    gold.append("\n    private DString __dmy_name;");
    gold.append("\n    private int __gmy_id;");
    gold.append("\n    private DString __dmy_id;");
    gold.append("\n    private int __gmy_size;");
    gold.append("\n    private DInt64 __dmy_size;");
    gold.append("\n    private int __gmy_type;");
    gold.append("\n    private DString __dmy_type;");
    gold.append("\n    private int __gmy_valid;");
    gold.append("\n    private DBoolean __dmy_valid;");
    gold.append("\n    private boolean __emitted;");
    gold.append("\n    private DeltaAttachment_1() {");
    gold.append("\n      __dlatest = new DAsset();");
    gold.append("\n      __gmy_file = -1;");
    gold.append("\n      __dmy_file = new DAsset();");
    gold.append("\n      __gmy_name = -1;");
    gold.append("\n      __dmy_name = new DString();");
    gold.append("\n      __gmy_id = -1;");
    gold.append("\n      __dmy_id = new DString();");
    gold.append("\n      __gmy_size = -1;");
    gold.append("\n      __dmy_size = new DInt64();");
    gold.append("\n      __gmy_type = -1;");
    gold.append("\n      __dmy_type = new DString();");
    gold.append("\n      __gmy_valid = -1;");
    gold.append("\n      __dmy_valid = new DBoolean();");
    gold.append("\n      __emitted = false;");
    gold.append("\n    }");
    gold.append("\n    @Override");
    gold.append("\n    public long __memory() {");
    gold.append("\n      long __sum = 40;");
    gold.append("\n      __sum += __dlatest.__memory();");
    gold.append("\n      __sum += __dmy_file.__memory();");
    gold.append("\n      __sum += __dmy_name.__memory();");
    gold.append("\n      __sum += __dmy_id.__memory();");
    gold.append("\n      __sum += __dmy_size.__memory();");
    gold.append("\n      __sum += __dmy_type.__memory();");
    gold.append("\n      __sum += __dmy_valid.__memory();");
    gold.append("\n      return __sum;");
    gold.append("\n    }");
    gold.append("\n    public void show(Attachment_1 __item, PrivateLazyDeltaWriter __writer) {");
    gold.append("\n      __code_cost += 7;");
    gold.append("\n      PrivateLazyDeltaWriter __obj = __writer.planObject();");
    gold.append("\n      __obj.manifest();");
    gold.append("\n      __dlatest.show(__item.latest.get(), __obj.planField(\"latest\"));");
    gold.append("\n      if (__gmy_file != __item.my_file.getGeneration()) {");
    gold.append("\n        __dmy_file.show(__item.my_file.get(), __obj.planField(\"my_file\"));");
    gold.append("\n        __gmy_file = __item.my_file.getGeneration();");
    gold.append("\n      }");
    gold.append("\n      if (__gmy_name != __item.my_name.getGeneration()) {");
    gold.append("\n        __dmy_name.show(__item.my_name.get(), __obj.planField(\"my_name\"));");
    gold.append("\n        __gmy_name = __item.my_name.getGeneration();");
    gold.append("\n      }");
    gold.append("\n      if (__gmy_id != __item.my_id.getGeneration()) {");
    gold.append("\n        __dmy_id.show(__item.my_id.get(), __obj.planField(\"my_id\"));");
    gold.append("\n        __gmy_id = __item.my_id.getGeneration();");
    gold.append("\n      }");
    gold.append("\n      if (__gmy_size != __item.my_size.getGeneration()) {");
    gold.append("\n        __dmy_size.show(__item.my_size.get(), __obj.planField(\"my_size\"));");
    gold.append("\n        __gmy_size = __item.my_size.getGeneration();");
    gold.append("\n      }");
    gold.append("\n      if (__gmy_type != __item.my_type.getGeneration()) {");
    gold.append("\n        __dmy_type.show(__item.my_type.get(), __obj.planField(\"my_type\"));");
    gold.append("\n        __gmy_type = __item.my_type.getGeneration();");
    gold.append("\n      }");
    gold.append("\n      if (__gmy_valid != __item.my_valid.getGeneration()) {");
    gold.append("\n        __dmy_valid.show(__item.my_valid.get(), __obj.planField(\"my_valid\"));");
    gold.append("\n        __gmy_valid = __item.my_valid.getGeneration();");
    gold.append("\n      }");
    gold.append("\n      if (__obj.end()) {");
    gold.append("\n        __emitted = true;");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n    public void hide(PrivateLazyDeltaWriter __writer) {");
    gold.append("\n      if (__emitted) {");
    gold.append("\n        __emitted = false;");
    gold.append("\n        __writer.writeNull();");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public Set<String> __get_intern_strings() {");
    gold.append("\n    HashSet<String> __interns = new HashSet<>();");
    gold.append("\n    __interns.add(\"\");");
    gold.append("\n    __interns.add(\"?\");");
    gold.append("\n    return __interns;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public PrivateView __createPrivateView(NtClient __who, Perspective ___perspective, AssetIdEncoder __encoder) {");
    gold.append("\n    Attachment_1 __self = this;");
    gold.append("\n    DeltaAttachment_1 __state = new DeltaAttachment_1();");
    gold.append("\n    RTx__ViewerType __viewerState = new RTx__ViewerType();");
    gold.append("\n    return new PrivateView(__who, ___perspective, __encoder) {");
    gold.append("\n      @Override");
    gold.append("\n      public long memory() {");
    gold.append("\n        return __state.__memory();");
    gold.append("\n      }");
    gold.append("\n      @Override");
    gold.append("\n      public void dumpViewer(JsonStreamWriter __writer) {");
    gold.append("\n        __viewerState.__writeOut(__writer);");
    gold.append("\n      }");
    gold.append("\n      @Override");
    gold.append("\n      public void ingest(JsonStreamReader __reader) {");
    gold.append("\n        __viewerState.__ingest(__reader);");
    gold.append("\n      }");
    gold.append("\n      @Override");
    gold.append("\n      public void update(JsonStreamWriter __writer) {");
    gold.append("\n        __state.show(__self, PrivateLazyDeltaWriter.bind(__who, __writer, __viewerState, __encoder));");
    gold.append("\n      }");
    gold.append("\n    };");
    gold.append("\n  }");
    gold.append("\n  private static class RTx__ViewerType implements NtMessageBase {");
    gold.append("\n    private RTx__ViewerType(JsonStreamReader __reader) {");
    gold.append("\n      __ingest(__reader);");
    gold.append("\n    }");
    gold.append("\n    public int __DATA_GENERATION = 1;");
    gold.append("\n    private void __ingest(JsonStreamReader __reader) {");
    gold.append("\n      __reader.skipValue();");
    gold.append("\n    }");
    gold.append("\n    @Override");
    gold.append("\n    public void __writeOut(JsonStreamWriter __writer) {");
    gold.append("\n      __writer.beginObject();");
    gold.append("\n      __writer.endObject();");
    gold.append("\n    }");
    gold.append("\n    private RTx__ViewerType() {}");
    gold.append("\n  }");
    gold.append("\n  private class DeltaRTx__ViewerType implements DeltaNode {");
    gold.append("\n    private boolean __emitted;");
    gold.append("\n    private DeltaRTx__ViewerType() {");
    gold.append("\n      __emitted = false;");
    gold.append("\n    }");
    gold.append("\n    @Override");
    gold.append("\n    public long __memory() {");
    gold.append("\n      long __sum = 40;");
    gold.append("\n      return __sum;");
    gold.append("\n    }");
    gold.append("\n    public void show(RTx__ViewerType __item, PrivateLazyDeltaWriter __writer) {");
    gold.append("\n      PrivateLazyDeltaWriter __obj = __writer.planObject();");
    gold.append("\n      if (__obj.end()) {");
    gold.append("\n        __emitted = true;");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n    public void hide(PrivateLazyDeltaWriter __writer) {");
    gold.append("\n      if (__emitted) {");
    gold.append("\n        __emitted = false;");
    gold.append("\n        __writer.writeNull();");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected boolean __is_direct_channel(String channel) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __handle_direct(NtClient who, String channel, Object __message) throws AbortMessageException {");
    gold.append("\n    return;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __route(AsyncTask task) {");
    gold.append("\n    return;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected Object __parse_message(String channel, JsonStreamReader __reader) {");
    gold.append("\n    __reader.skipValue();");
    gold.append("\n    return NtMessageBase.NULL;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __reset_future_queues() {");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __invoke_label(String __new_state) {}");
    gold.append("\n  public boolean __onCanAssetAttached__0(NtClient who) {");
    gold.append("\n    __code_cost += 2;");
    gold.append("\n    __track(0);");
    gold.append("\n    return true;");
    gold.append("\n  }");
    gold.append("\n  public void __onAssetAttached__0(NtClient who, NtAsset file) {");
    gold.append("\n    __code_cost += 2;");
    gold.append("\n    __track(1);");
    gold.append("\n    latest.set(file);");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public boolean __onConnected(NtClient __cvalue) {");
    gold.append("\n    boolean __result = false;");
    gold.append("\n    return __result;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __onDisconnected(NtClient __cvalue) {}");
    gold.append("\n  @Override");
    gold.append("\n  public boolean __onCanAssetAttached(NtClient __cvalue) {");
    gold.append("\n    boolean __result = false;");
    gold.append("\n    if (__onCanAssetAttached__0(__cvalue)) __result = true;");
    gold.append("\n    return __result;");
    gold.append("\n  }");
    gold.append("\n  public static boolean __onCanCreate(CoreRequestContext __context) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  public static boolean __onCanInvent(CoreRequestContext __context) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  public static boolean __onCanSendWhileDisconnected(CoreRequestContext __context) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __onAssetAttached(NtClient __cvalue, NtAsset __asset) {");
    gold.append("\n    __onAssetAttached__0(__cvalue, __asset);");
    gold.append("\n  }");
    gold.append("\n  public static HashMap<String, Object> __config() {");
    gold.append("\n    HashMap<String, Object> __map = new HashMap<>();");
    gold.append("\n    return __map;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public String[] __getTests() {");
    gold.append("\n    return new String[] {};");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __test(TestReportBuilder report, String testName) {}");
    gold.append("\n  @Override");
    gold.append("\n  protected void __construct_intern(NtClient who, NtMessageBase message) {}");
    gold.append("\n  @Override");
    gold.append("\n  protected NtMessageBase __parse_construct_arg(JsonStreamReader __reader) {");
    gold.append("\n    __reader.skipValue();");
    gold.append("\n    return NtMessageBase.NULL;");
    gold.append("\n  }");
    gold.append("\n  /* end of file */");
    gold.append("\n}");
    gold.append("\n");
    gold.append("\n--JAVA COMPILE RESULTS-----------------------------");
    gold.append("\nBegin");
    gold.append("\nEnd");
    gold.append("\n--REFLECTION RESULTS-------------------------------------");
    gold.append("\n{\"types\":{\"#root\":{\"nature\":\"reactive_record\",\"name\":\"Root\",\"fields\":{\"latest\":{\"type\":{\"nature\":\"reactive_value\",\"type\":\"asset\"},\"privacy\":\"public\"},\"my_file\":{\"type\":{\"nature\":\"native_value\",\"type\":\"asset\"},\"privacy\":\"public\"},\"my_name\":{\"type\":{\"nature\":\"native_value\",\"type\":\"string\"},\"privacy\":\"public\"},\"my_id\":{\"type\":{\"nature\":\"native_value\",\"type\":\"string\"},\"privacy\":\"public\"},\"my_size\":{\"type\":{\"nature\":\"native_value\",\"type\":\"long\"},\"privacy\":\"public\"},\"my_type\":{\"type\":{\"nature\":\"native_value\",\"type\":\"string\"},\"privacy\":\"public\"},\"my_valid\":{\"type\":{\"nature\":\"native_value\",\"type\":\"bool\"},\"privacy\":\"public\"}}},\"__ViewerType\":{\"nature\":\"native_message\",\"name\":\"__ViewerType\",\"anonymous\":true,\"fields\":{}}},\"channels\":{},\"constructors\":[],\"labels\":[]}");
    gold.append("\n--JAVA RUNNING-------------------------------------");
    gold.append("\n{\"command\":\"construct\",\"timestamp\":\"0\",\"who\":{\"agent\":\"?\",\"authority\":\"?\"},\"arg\":{},\"entropy\":\"0\"}-->{\"__constructed\":true,\"__entropy\":\"-4962768465676381896\",\"__messages\":null,\"__seq\":1} need:false in:0");
    gold.append("\n{\"command\":\"invalidate\",\"timestamp\":\"25\"}-->{\"__messages\":null,\"__seq\":2,\"__entropy\":\"4804307197456638271\",\"__time\":\"25\"} need:false in:-25");
    gold.append("\nCPU:0");
    gold.append("\nMEMORY:776");
    gold.append("\n{\"command\":\"invalidate\",\"timestamp\":\"50\"}-->{\"__messages\":null,\"__seq\":3,\"__entropy\":\"-1034601897293430941\",\"__time\":\"50\"} need:false in:-50");
    gold.append("\nNO_ONE: CREATED PRIVATE VIEW");
    gold.append("\n+ NO_ONE DELTA:{\"data\":{\"my_name\":\"\",\"my_id\":\"\",\"my_size\":\"0\",\"my_type\":\"\",\"my_valid\":false},\"seq\":3}");
    gold.append("\nNO_ONE|FAILURE:184333");
    gold.append("\n{\"command\":\"invalidate\",\"timestamp\":\"75\"}-->{\"__messages\":null,\"__seq\":4,\"__entropy\":\"7848011421992302230\",\"__time\":\"75\"} need:false in:-75");
    gold.append("\nRANDO: CREATED PRIVATE VIEW");
    gold.append("\n+ NO_ONE DELTA:{\"seq\":4}");
    gold.append("\n+ RANDO DELTA:{\"data\":{\"my_name\":\"\",\"my_id\":\"\",\"my_size\":\"0\",\"my_type\":\"\",\"my_valid\":false},\"seq\":4}");
    gold.append("\nRANDO|FAILURE:184333");
    gold.append("\n{\"command\":\"invalidate\",\"timestamp\":\"100\"}-->{\"__messages\":null,\"__seq\":5,\"__entropy\":\"-8929183248358367000\",\"__time\":\"100\"} need:false in:-100");
    gold.append("\nRANDO|SUCCESS:5");
    gold.append("\n+ NO_ONE DELTA:{\"seq\":5}");
    gold.append("\n+ RANDO DELTA:{\"seq\":5}");
    gold.append("\nMEMORY:1614");
    gold.append("\n--JAVA RESULTS-------------------------------------");
    gold.append("\n{\"latest\":{\"id\":\"\",\"size\":\"0\",\"name\":\"\",\"type\":\"\",\"md5\":\"\",\"sha384\":\"\"},\"__state\":\"\",\"__constructed\":true,\"__next_time\":\"0\",\"__last_expire_time\":\"0\",\"__blocked\":false,\"__seq\":5,\"__entropy\":\"-8929183248358367000\",\"__auto_future_id\":0,\"__connection_id\":0,\"__message_id\":0,\"__time\":\"100\",\"__auto_table_row_id\":0}");
    gold.append("\n--DUMP RESULTS-------------------------------------");
    gold.append("\n{\"latest\":{\"id\":\"\",\"size\":\"0\",\"name\":\"\",\"type\":\"\",\"md5\":\"\",\"sha384\":\"\"},\"__state\":\"\",\"__constructed\":true,\"__next_time\":\"0\",\"__last_expire_time\":\"0\",\"__blocked\":false,\"__seq\":5,\"__entropy\":\"-8929183248358367000\",\"__auto_future_id\":0,\"__connection_id\":0,\"__message_id\":0,\"__time\":\"100\",\"__auto_table_row_id\":0}");
    gold.append("\n{\"latest\":{\"id\":\"\",\"size\":\"0\",\"name\":\"\",\"type\":\"\",\"md5\":\"\",\"sha384\":\"\"},\"__state\":\"\",\"__constructed\":true,\"__next_time\":\"0\",\"__last_expire_time\":\"0\",\"__blocked\":false,\"__seq\":5,\"__entropy\":\"-8929183248358367000\",\"__auto_future_id\":0,\"__connection_id\":0,\"__message_id\":0,\"__time\":\"100\",\"__auto_table_row_id\":0}");
    gold.append("\n--JAVA TEST RESULTS--------------------------------");
    gold.append("\n");
    gold.append("\nSuccess");
    assertStable(live, gold);
  }
  private String cached_CanAttach_2 = null;
  private String get_CanAttach_2() {
    if (cached_CanAttach_2 != null) {
      return cached_CanAttach_2;
    }
    cached_CanAttach_2 = generateTestOutput(false, "CanAttach_2", "./test_code/Assets_CanAttach_failure.a");
    return cached_CanAttach_2;
  }

  @Test
  public void testCanAttachFailure() {
    assertLiveFail(get_CanAttach_2());
  }

  @Test
  public void testCanAttachNotTerribleLineNumbers() {
    assertNotTerribleLineNumbers(get_CanAttach_2());
  }

  @Test
  public void testCanAttachExceptionFree() {
    assertExceptionFree(get_CanAttach_2());
  }

  @Test
  public void testCanAttachTODOFree() {
    assertTODOFree(get_CanAttach_2());
  }

  @Test
  public void stable_CanAttach_2() {
    String live = get_CanAttach_2();
    StringBuilder gold = new StringBuilder();
    gold.append("Path:Assets_CanAttach_failure.a");
    gold.append("\n--ISSUES-------------------------------------------");
    gold.append("\n[{\"range\":{\"start\":{\"line\":0,\"character\":17},\"end\":{\"line\":1,\"character\":1}},\"severity\":1,\"source\":\"error\",\"message\":\"The @can_attach handler must return a boolean (DocumentEvents)\"}]\"--JAVA---------------------------------------------");
    gold.append("\n");
    gold.append("\nFailedValidation");
    assertStable(live, gold);
  }
  private String cached_Failures_3 = null;
  private String get_Failures_3() {
    if (cached_Failures_3 != null) {
      return cached_Failures_3;
    }
    cached_Failures_3 = generateTestOutput(false, "Failures_3", "./test_code/Assets_Failures_failure.a");
    return cached_Failures_3;
  }

  @Test
  public void testFailuresFailure() {
    assertLiveFail(get_Failures_3());
  }

  @Test
  public void testFailuresNotTerribleLineNumbers() {
    assertNotTerribleLineNumbers(get_Failures_3());
  }

  @Test
  public void testFailuresExceptionFree() {
    assertExceptionFree(get_Failures_3());
  }

  @Test
  public void testFailuresTODOFree() {
    assertTODOFree(get_Failures_3());
  }

  @Test
  public void stable_Failures_3() {
    String live = get_Failures_3();
    StringBuilder gold = new StringBuilder();
    gold.append("Path:Assets_Failures_failure.a");
    gold.append("\n--ISSUES-------------------------------------------");
    gold.append("\n[{\"range\":{\"start\":{\"line\":4,\"character\":2},\"end\":{\"line\":4,\"character\":8}},\"severity\":1,\"source\":\"error\",\"message\":\"Type 'asset' lacks field 'food' (FieldLookup)\"},{\"range\":{\"start\":{\"line\":4,\"character\":2},\"end\":{\"line\":4,\"character\":8}},\"severity\":1,\"source\":\"error\",\"message\":\"Expression is not a function (FunctionInvoke)\"},{\"range\":{\"start\":{\"line\":5,\"character\":2},\"end\":{\"line\":5,\"character\":7}},\"severity\":1,\"source\":\"error\",\"message\":\"Type check failure: the type 'asset' is unable to store type 'int'. (TypeCheckReferences)\"}]\"--JAVA---------------------------------------------");
    gold.append("\n");
    gold.append("\nFailedValidation");
    assertStable(live, gold);
  }
  private String cached_Sanity_4 = null;
  private String get_Sanity_4() {
    if (cached_Sanity_4 != null) {
      return cached_Sanity_4;
    }
    cached_Sanity_4 = generateTestOutput(true, "Sanity_4", "./test_code/Assets_Sanity_success.a");
    return cached_Sanity_4;
  }

  @Test
  public void testSanityEmission() {
    assertEmissionGood(get_Sanity_4());
  }

  @Test
  public void testSanitySuccess() {
    assertLivePass(get_Sanity_4());
  }

  @Test
  public void testSanityGoodWillHappy() {
    assertGoodWillHappy(get_Sanity_4());
  }

  @Test
  public void testSanityExceptionFree() {
    assertExceptionFree(get_Sanity_4());
  }

  @Test
  public void testSanityTODOFree() {
    assertTODOFree(get_Sanity_4());
  }

  @Test
  public void stable_Sanity_4() {
    String live = get_Sanity_4();
    StringBuilder gold = new StringBuilder();
    gold.append("Path:Assets_Sanity_success.a");
    gold.append("\n--EMISSION-----------------------------------------");
    gold.append("\nEmission Success, Yay");
    gold.append("\n--ISSUES-------------------------------------------");
    gold.append("\n[]\"--JAVA---------------------------------------------");
    gold.append("\nimport org.adamalang.runtime.async.*;");
    gold.append("\nimport org.adamalang.runtime.contracts.*;");
    gold.append("\nimport org.adamalang.runtime.delta.*;");
    gold.append("\nimport org.adamalang.runtime.delta.secure.*;");
    gold.append("\nimport org.adamalang.runtime.exceptions.*;");
    gold.append("\nimport org.adamalang.runtime.index.*;");
    gold.append("\nimport org.adamalang.runtime.json.*;");
    gold.append("\nimport org.adamalang.runtime.natives.*;");
    gold.append("\nimport org.adamalang.runtime.natives.lists.*;");
    gold.append("\nimport org.adamalang.runtime.ops.*;");
    gold.append("\nimport org.adamalang.runtime.reactives.*;");
    gold.append("\nimport org.adamalang.runtime.stdlib.*;");
    gold.append("\nimport org.adamalang.runtime.sys.*;");
    gold.append("\nimport java.util.function.Consumer;");
    gold.append("\nimport java.util.function.Function;");
    gold.append("\nimport java.util.ArrayList;");
    gold.append("\nimport java.util.Comparator;");
    gold.append("\nimport java.util.HashMap;");
    gold.append("\nimport java.util.HashSet;");
    gold.append("\nimport java.util.Map;");
    gold.append("\nimport java.util.Set;");
    gold.append("\nimport java.lang.Math;");
    gold.append("\npublic class Sanity_4 extends LivingDocument {");
    gold.append("\n  private final RxAsset latest;");
    gold.append("\n  private final RxBoolean te;");
    gold.append("\n  @Override");
    gold.append("\n  public long __memory() {");
    gold.append("\n    long __sum = super.__memory();");
    gold.append("\n    __sum += latest.__memory();");
    gold.append("\n    __sum += te.__memory();");
    gold.append("\n    return __sum;");
    gold.append("\n  }");
    gold.append("\n  public Sanity_4(DocumentMonitor __monitor) {");
    gold.append("\n    super(__monitor);");
    gold.append("\n    latest = new RxAsset(this, NtAsset.NOTHING);");
    gold.append("\n    te = new RxBoolean(this, false);");
    gold.append("\n    __goodwillBudget = 100000;");
    gold.append("\n    __goodwillLimitOfBudget = 100000;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __insert(JsonStreamReader __reader) {");
    gold.append("\n    if (__reader.startObject()) {");
    gold.append("\n      while(__reader.notEndOfObject()) {");
    gold.append("\n        String __fieldName = __reader.fieldName();");
    gold.append("\n        switch (__fieldName) {");
    gold.append("\n          case \"latest\":");
    gold.append("\n            latest.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"te\":");
    gold.append("\n            te.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__state\":");
    gold.append("\n            __state.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__constructed\":");
    gold.append("\n            __constructed.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__next_time\":");
    gold.append("\n            __next_time.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__last_expire_time\":");
    gold.append("\n            __last_expire_time.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__blocked\":");
    gold.append("\n            __blocked.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__seq\":");
    gold.append("\n            __seq.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__entropy\":");
    gold.append("\n            __entropy.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__auto_future_id\":");
    gold.append("\n            __auto_future_id.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__connection_id\":");
    gold.append("\n            __connection_id.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__message_id\":");
    gold.append("\n            __message_id.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__time\":");
    gold.append("\n            __time.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__auto_table_row_id\":");
    gold.append("\n            __auto_table_row_id.__insert(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__dedupe\":");
    gold.append("\n            __hydrateDeduper(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__clients\":");
    gold.append("\n            __hydrateClients(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__messages\":");
    gold.append("\n            __hydrateMessages(__reader);");
    gold.append("\n            break;");
    gold.append("\n          default:");
    gold.append("\n            __reader.skipValue();");
    gold.append("\n        }");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __patch(JsonStreamReader __reader) {");
    gold.append("\n    if (__reader.startObject()) {");
    gold.append("\n      while(__reader.notEndOfObject()) {");
    gold.append("\n        String __fieldName = __reader.fieldName();");
    gold.append("\n        switch (__fieldName) {");
    gold.append("\n          case \"latest\":");
    gold.append("\n            latest.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"te\":");
    gold.append("\n            te.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__state\":");
    gold.append("\n            __state.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__constructed\":");
    gold.append("\n            __constructed.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__next_time\":");
    gold.append("\n            __next_time.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__last_expire_time\":");
    gold.append("\n            __last_expire_time.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__blocked\":");
    gold.append("\n            __blocked.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__seq\":");
    gold.append("\n            __seq.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__entropy\":");
    gold.append("\n            __entropy.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__auto_future_id\":");
    gold.append("\n            __auto_future_id.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__connection_id\":");
    gold.append("\n            __connection_id.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__message_id\":");
    gold.append("\n            __message_id.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__time\":");
    gold.append("\n            __time.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__auto_table_row_id\":");
    gold.append("\n            __auto_table_row_id.__patch(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__dedupe\":");
    gold.append("\n            __hydrateDeduper(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__clients\":");
    gold.append("\n            __hydrateClients(__reader);");
    gold.append("\n            break;");
    gold.append("\n          case \"__messages\":");
    gold.append("\n            __hydrateMessages(__reader);");
    gold.append("\n            break;");
    gold.append("\n          default:");
    gold.append("\n            __reader.skipValue();");
    gold.append("\n        }");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __dump(JsonStreamWriter __writer) {");
    gold.append("\n    __writer.beginObject();");
    gold.append("\n    __writer.writeObjectFieldIntro(\"latest\");");
    gold.append("\n    latest.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"te\");");
    gold.append("\n    te.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__state\");");
    gold.append("\n    __state.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__constructed\");");
    gold.append("\n    __constructed.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__next_time\");");
    gold.append("\n    __next_time.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__last_expire_time\");");
    gold.append("\n    __last_expire_time.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__blocked\");");
    gold.append("\n    __blocked.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__seq\");");
    gold.append("\n    __seq.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__entropy\");");
    gold.append("\n    __entropy.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__auto_future_id\");");
    gold.append("\n    __auto_future_id.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__connection_id\");");
    gold.append("\n    __connection_id.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__message_id\");");
    gold.append("\n    __message_id.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__time\");");
    gold.append("\n    __time.__dump(__writer);");
    gold.append("\n    __writer.writeObjectFieldIntro(\"__auto_table_row_id\");");
    gold.append("\n    __auto_table_row_id.__dump(__writer);");
    gold.append("\n    __dumpDeduper(__writer);");
    gold.append("\n    __dumpClients(__writer);");
    gold.append("\n    __dumpMessages(__writer);");
    gold.append("\n    __writer.endObject();");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __commit(String __name, JsonStreamWriter __forward, JsonStreamWriter __reverse) {");
    gold.append("\n    __state.__commit(\"__state\", __forward, __reverse);");
    gold.append("\n    __constructed.__commit(\"__constructed\", __forward, __reverse);");
    gold.append("\n    __next_time.__commit(\"__next_time\", __forward, __reverse);");
    gold.append("\n    __last_expire_time.__commit(\"__last_expire_time\", __forward, __reverse);");
    gold.append("\n    __blocked.__commit(\"__blocked\", __forward, __reverse);");
    gold.append("\n    __seq.__commit(\"__seq\", __forward, __reverse);");
    gold.append("\n    __entropy.__commit(\"__entropy\", __forward, __reverse);");
    gold.append("\n    __auto_future_id.__commit(\"__auto_future_id\", __forward, __reverse);");
    gold.append("\n    __connection_id.__commit(\"__connection_id\", __forward, __reverse);");
    gold.append("\n    __message_id.__commit(\"__message_id\", __forward, __reverse);");
    gold.append("\n    __time.__commit(\"__time\", __forward, __reverse);");
    gold.append("\n    __auto_table_row_id.__commit(\"__auto_table_row_id\", __forward, __reverse);");
    gold.append("\n    latest.__commit(\"latest\", __forward, __reverse);");
    gold.append("\n    te.__commit(\"te\", __forward, __reverse);");
    gold.append("\n    /* root */");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __revert() {");
    gold.append("\n    __state.__revert();");
    gold.append("\n    __constructed.__revert();");
    gold.append("\n    __next_time.__revert();");
    gold.append("\n    __last_expire_time.__revert();");
    gold.append("\n    __blocked.__revert();");
    gold.append("\n    __seq.__revert();");
    gold.append("\n    __entropy.__revert();");
    gold.append("\n    __auto_future_id.__revert();");
    gold.append("\n    __connection_id.__revert();");
    gold.append("\n    __message_id.__revert();");
    gold.append("\n    __time.__revert();");
    gold.append("\n    __auto_table_row_id.__revert();");
    gold.append("\n    latest.__revert();");
    gold.append("\n    te.__revert();");
    gold.append("\n    /* root */");
    gold.append("\n  }");
    gold.append("\n  private class DeltaSanity_4 implements DeltaNode {");
    gold.append("\n    private DAsset __dlatest;");
    gold.append("\n    private DBoolean __dte;");
    gold.append("\n    private boolean __emitted;");
    gold.append("\n    private DeltaSanity_4() {");
    gold.append("\n      __dlatest = new DAsset();");
    gold.append("\n      __dte = new DBoolean();");
    gold.append("\n      __emitted = false;");
    gold.append("\n    }");
    gold.append("\n    @Override");
    gold.append("\n    public long __memory() {");
    gold.append("\n      long __sum = 40;");
    gold.append("\n      __sum += __dlatest.__memory();");
    gold.append("\n      __sum += __dte.__memory();");
    gold.append("\n      return __sum;");
    gold.append("\n    }");
    gold.append("\n    public void show(Sanity_4 __item, PrivateLazyDeltaWriter __writer) {");
    gold.append("\n      __code_cost += 2;");
    gold.append("\n      PrivateLazyDeltaWriter __obj = __writer.planObject();");
    gold.append("\n      __obj.manifest();");
    gold.append("\n      __dlatest.show(__item.latest.get(), __obj.planField(\"latest\"));");
    gold.append("\n      __dte.show(__item.te.get(), __obj.planField(\"te\"));");
    gold.append("\n      if (__obj.end()) {");
    gold.append("\n        __emitted = true;");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n    public void hide(PrivateLazyDeltaWriter __writer) {");
    gold.append("\n      if (__emitted) {");
    gold.append("\n        __emitted = false;");
    gold.append("\n        __writer.writeNull();");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public Set<String> __get_intern_strings() {");
    gold.append("\n    HashSet<String> __interns = new HashSet<>();");
    gold.append("\n    __interns.add(\"\");");
    gold.append("\n    __interns.add(\"?\");");
    gold.append("\n    return __interns;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public PrivateView __createPrivateView(NtClient __who, Perspective ___perspective, AssetIdEncoder __encoder) {");
    gold.append("\n    Sanity_4 __self = this;");
    gold.append("\n    DeltaSanity_4 __state = new DeltaSanity_4();");
    gold.append("\n    RTx__ViewerType __viewerState = new RTx__ViewerType();");
    gold.append("\n    return new PrivateView(__who, ___perspective, __encoder) {");
    gold.append("\n      @Override");
    gold.append("\n      public long memory() {");
    gold.append("\n        return __state.__memory();");
    gold.append("\n      }");
    gold.append("\n      @Override");
    gold.append("\n      public void dumpViewer(JsonStreamWriter __writer) {");
    gold.append("\n        __viewerState.__writeOut(__writer);");
    gold.append("\n      }");
    gold.append("\n      @Override");
    gold.append("\n      public void ingest(JsonStreamReader __reader) {");
    gold.append("\n        __viewerState.__ingest(__reader);");
    gold.append("\n      }");
    gold.append("\n      @Override");
    gold.append("\n      public void update(JsonStreamWriter __writer) {");
    gold.append("\n        __state.show(__self, PrivateLazyDeltaWriter.bind(__who, __writer, __viewerState, __encoder));");
    gold.append("\n      }");
    gold.append("\n    };");
    gold.append("\n  }");
    gold.append("\n  private static class RTx__ViewerType implements NtMessageBase {");
    gold.append("\n    private RTx__ViewerType(JsonStreamReader __reader) {");
    gold.append("\n      __ingest(__reader);");
    gold.append("\n    }");
    gold.append("\n    public int __DATA_GENERATION = 1;");
    gold.append("\n    private void __ingest(JsonStreamReader __reader) {");
    gold.append("\n      __reader.skipValue();");
    gold.append("\n    }");
    gold.append("\n    @Override");
    gold.append("\n    public void __writeOut(JsonStreamWriter __writer) {");
    gold.append("\n      __writer.beginObject();");
    gold.append("\n      __writer.endObject();");
    gold.append("\n    }");
    gold.append("\n    private RTx__ViewerType() {}");
    gold.append("\n  }");
    gold.append("\n  private class DeltaRTx__ViewerType implements DeltaNode {");
    gold.append("\n    private boolean __emitted;");
    gold.append("\n    private DeltaRTx__ViewerType() {");
    gold.append("\n      __emitted = false;");
    gold.append("\n    }");
    gold.append("\n    @Override");
    gold.append("\n    public long __memory() {");
    gold.append("\n      long __sum = 40;");
    gold.append("\n      return __sum;");
    gold.append("\n    }");
    gold.append("\n    public void show(RTx__ViewerType __item, PrivateLazyDeltaWriter __writer) {");
    gold.append("\n      PrivateLazyDeltaWriter __obj = __writer.planObject();");
    gold.append("\n      if (__obj.end()) {");
    gold.append("\n        __emitted = true;");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n    public void hide(PrivateLazyDeltaWriter __writer) {");
    gold.append("\n      if (__emitted) {");
    gold.append("\n        __emitted = false;");
    gold.append("\n        __writer.writeNull();");
    gold.append("\n      }");
    gold.append("\n    }");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected boolean __is_direct_channel(String channel) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __handle_direct(NtClient who, String channel, Object __message) throws AbortMessageException {");
    gold.append("\n    return;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __route(AsyncTask task) {");
    gold.append("\n    return;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected Object __parse_message(String channel, JsonStreamReader __reader) {");
    gold.append("\n    __reader.skipValue();");
    gold.append("\n    return NtMessageBase.NULL;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __reset_future_queues() {");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __invoke_label(String __new_state) {}");
    gold.append("\n  @Override");
    gold.append("\n  public boolean __onConnected(NtClient __cvalue) {");
    gold.append("\n    boolean __result = false;");
    gold.append("\n    return __result;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __onDisconnected(NtClient __cvalue) {}");
    gold.append("\n  @Override");
    gold.append("\n  public boolean __onCanAssetAttached(NtClient __cvalue) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  public static boolean __onCanCreate(CoreRequestContext __context) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  public static boolean __onCanInvent(CoreRequestContext __context) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  public static boolean __onCanSendWhileDisconnected(CoreRequestContext __context) {");
    gold.append("\n    return false;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __onAssetAttached(NtClient __cvalue, NtAsset __asset) {}");
    gold.append("\n  public static HashMap<String, Object> __config() {");
    gold.append("\n    HashMap<String, Object> __map = new HashMap<>();");
    gold.append("\n    return __map;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public String[] __getTests() {");
    gold.append("\n    return new String[] {};");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  public void __test(TestReportBuilder report, String testName) {}");
    gold.append("\n  private void __construct_0(NtClient __who, NtMessageBase __object) {");
    gold.append("\n    __code_cost += 7;");
    gold.append("\n    __track(0);");
    gold.append("\n    NtAsset x = NtAsset.NOTHING;");
    gold.append("\n    __track(1);");
    gold.append("\n    latest.set(x);");
    gold.append("\n    __track(2);");
    gold.append("\n    latest.set(NtAsset.NOTHING);");
    gold.append("\n    __track(3);");
    gold.append("\n    NtAsset y = x;");
    gold.append("\n    __track(4);");
    gold.append("\n    final NtAsset z = y;");
    gold.append("\n    __track(5);");
    gold.append("\n    te.set((y).equals(z));");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected NtMessageBase __parse_construct_arg(JsonStreamReader __reader) {");
    gold.append("\n    __reader.skipValue();");
    gold.append("\n    return NtMessageBase.NULL;");
    gold.append("\n  }");
    gold.append("\n  @Override");
    gold.append("\n  protected void __construct_intern(NtClient __who, NtMessageBase __object) {");
    gold.append("\n    __construct_0(__who, __object);");
    gold.append("\n  }");
    gold.append("\n  /* end of file */");
    gold.append("\n}");
    gold.append("\n");
    gold.append("\n--JAVA COMPILE RESULTS-----------------------------");
    gold.append("\nBegin");
    gold.append("\nEnd");
    gold.append("\n--REFLECTION RESULTS-------------------------------------");
    gold.append("\n{\"types\":{\"#root\":{\"nature\":\"reactive_record\",\"name\":\"Root\",\"fields\":{\"latest\":{\"type\":{\"nature\":\"reactive_value\",\"type\":\"asset\"},\"privacy\":\"public\"},\"te\":{\"type\":{\"nature\":\"reactive_value\",\"type\":\"bool\"},\"privacy\":\"public\"}}},\"__ViewerType\":{\"nature\":\"native_message\",\"name\":\"__ViewerType\",\"anonymous\":true,\"fields\":{}}},\"channels\":{},\"constructors\":[],\"labels\":[]}");
    gold.append("\n--JAVA RUNNING-------------------------------------");
    gold.append("\n{\"command\":\"construct\",\"timestamp\":\"0\",\"who\":{\"agent\":\"?\",\"authority\":\"?\"},\"arg\":{},\"entropy\":\"0\"}-->{\"__constructed\":true,\"__entropy\":\"-4962768465676381896\",\"te\":true,\"__messages\":null,\"__seq\":1} need:false in:0");
    gold.append("\n{\"command\":\"invalidate\",\"timestamp\":\"25\"}-->{\"__messages\":null,\"__seq\":2,\"__entropy\":\"4804307197456638271\",\"__time\":\"25\"} need:false in:-25");
    gold.append("\nCPU:7");
    gold.append("\nMEMORY:578");
    gold.append("\n{\"command\":\"invalidate\",\"timestamp\":\"50\"}-->{\"__messages\":null,\"__seq\":3,\"__entropy\":\"-1034601897293430941\",\"__time\":\"50\"} need:false in:-50");
    gold.append("\nNO_ONE: CREATED PRIVATE VIEW");
    gold.append("\n+ NO_ONE DELTA:{\"data\":{\"te\":true},\"seq\":3}");
    gold.append("\nNO_ONE|FAILURE:184333");
    gold.append("\n{\"command\":\"invalidate\",\"timestamp\":\"75\"}-->{\"__messages\":null,\"__seq\":4,\"__entropy\":\"7848011421992302230\",\"__time\":\"75\"} need:false in:-75");
    gold.append("\nRANDO: CREATED PRIVATE VIEW");
    gold.append("\n+ NO_ONE DELTA:{\"seq\":4}");
    gold.append("\n+ RANDO DELTA:{\"data\":{\"te\":true},\"seq\":4}");
    gold.append("\nRANDO|FAILURE:184333");
    gold.append("\n{\"command\":\"invalidate\",\"timestamp\":\"100\"}-->{\"__messages\":null,\"__seq\":5,\"__entropy\":\"-8929183248358367000\",\"__time\":\"100\"} need:false in:-100");
    gold.append("\nRANDO|SUCCESS:5");
    gold.append("\n+ NO_ONE DELTA:{\"seq\":5}");
    gold.append("\n+ RANDO DELTA:{\"seq\":5}");
    gold.append("\nMEMORY:936");
    gold.append("\n--JAVA RESULTS-------------------------------------");
    gold.append("\n{\"latest\":{\"id\":\"\",\"size\":\"0\",\"name\":\"\",\"type\":\"\",\"md5\":\"\",\"sha384\":\"\"},\"te\":true,\"__state\":\"\",\"__constructed\":true,\"__next_time\":\"0\",\"__last_expire_time\":\"0\",\"__blocked\":false,\"__seq\":5,\"__entropy\":\"-8929183248358367000\",\"__auto_future_id\":0,\"__connection_id\":0,\"__message_id\":0,\"__time\":\"100\",\"__auto_table_row_id\":0}");
    gold.append("\n--DUMP RESULTS-------------------------------------");
    gold.append("\n{\"latest\":{\"id\":\"\",\"size\":\"0\",\"name\":\"\",\"type\":\"\",\"md5\":\"\",\"sha384\":\"\"},\"te\":true,\"__state\":\"\",\"__constructed\":true,\"__next_time\":\"0\",\"__last_expire_time\":\"0\",\"__blocked\":false,\"__seq\":5,\"__entropy\":\"-8929183248358367000\",\"__auto_future_id\":0,\"__connection_id\":0,\"__message_id\":0,\"__time\":\"100\",\"__auto_table_row_id\":0}");
    gold.append("\n{\"latest\":{\"id\":\"\",\"size\":\"0\",\"name\":\"\",\"type\":\"\",\"md5\":\"\",\"sha384\":\"\"},\"te\":true,\"__state\":\"\",\"__constructed\":true,\"__next_time\":\"0\",\"__last_expire_time\":\"0\",\"__blocked\":false,\"__seq\":5,\"__entropy\":\"-8929183248358367000\",\"__auto_future_id\":0,\"__connection_id\":0,\"__message_id\":0,\"__time\":\"100\",\"__auto_table_row_id\":0}");
    gold.append("\n--JAVA TEST RESULTS--------------------------------");
    gold.append("\n");
    gold.append("\nSuccess");
    assertStable(live, gold);
  }
  private String cached_SoloArg_5 = null;
  private String get_SoloArg_5() {
    if (cached_SoloArg_5 != null) {
      return cached_SoloArg_5;
    }
    cached_SoloArg_5 = generateTestOutput(false, "SoloArg_5", "./test_code/Assets_SoloArg_failure.a");
    return cached_SoloArg_5;
  }

  @Test
  public void testSoloArgFailure() {
    assertLiveFail(get_SoloArg_5());
  }

  @Test
  public void testSoloArgNotTerribleLineNumbers() {
    assertNotTerribleLineNumbers(get_SoloArg_5());
  }

  @Test
  public void testSoloArgExceptionFree() {
    assertExceptionFree(get_SoloArg_5());
  }

  @Test
  public void testSoloArgTODOFree() {
    assertTODOFree(get_SoloArg_5());
  }

  @Test
  public void stable_SoloArg_5() {
    String live = get_SoloArg_5();
    StringBuilder gold = new StringBuilder();
    gold.append("Path:Assets_SoloArg_failure.a");
    gold.append("\n--ISSUES-------------------------------------------");
    gold.append("\n[{\"range\":{\"start\":{\"line\":0,\"character\":15},\"end\":{\"line\":1,\"character\":1}},\"severity\":1,\"source\":\"error\",\"message\":\"The @attached requires two parameters @attached(who, what) (DocumentEvents)\"}]\"--JAVA---------------------------------------------");
    gold.append("\n");
    gold.append("\nFailedValidation");
    assertStable(live, gold);
  }
}
