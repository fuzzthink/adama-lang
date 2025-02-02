/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.translator.tree.types;

import org.adamalang.runtime.json.JsonStreamWriter;
import org.adamalang.translator.env.Environment;
import org.adamalang.translator.parser.token.Token;
import org.adamalang.translator.tree.common.DocumentPosition;
import org.adamalang.translator.tree.common.TokenizedItem;

import java.util.function.Consumer;

public abstract class TyType extends DocumentPosition {
  public final TypeBehavior behavior;
  private TypeAnnotation annotation;

  public TyType(final TypeBehavior behavior) {
    this.behavior = behavior;
    this.annotation = null;
  }

  public abstract void emitInternal(Consumer<Token> yielder);

  public void emit(Consumer<Token> yielder) {
    emitInternal(yielder);
    if (annotation != null) {
      annotation.emit(yielder);
    }
  }

  public abstract String getAdamaType();

  public abstract String getJavaBoxType(Environment environment);

  public abstract String getJavaConcreteType(Environment environment);

  public abstract TyType makeCopyWithNewPositionInternal(DocumentPosition position, TypeBehavior newBehavior);

  public TyType makeCopyWithNewPosition(DocumentPosition position, TypeBehavior newBehavior) {
    TyType copy = makeCopyWithNewPositionInternal(position, newBehavior);
    if (annotation != null) {
      copy.annotation = annotation;
    }
    return copy;
  }

  public abstract void typing(Environment environment);

  public void writeAnnotations(JsonStreamWriter writer) {
    if (annotation != null) {
      writer.writeObjectFieldIntro("annotations");
      writer.beginArray();
      for (TokenizedItem<Token> token : annotation.annotations) {
        writer.writeString(token.item.text);
      }
      writer.endArray();
    }
  }

  public abstract void writeTypeReflectionJson(JsonStreamWriter writer);

  public TyType withPosition(final DocumentPosition position) {
    reset();
    ingest(position);
    return this;
  }

  public void annotate(TypeAnnotation annotation) {
    this.annotation = annotation;
  }
}
