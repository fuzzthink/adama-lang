/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.translator.tree.common;

import org.adamalang.translator.parser.token.Token;

import java.util.ArrayList;
import java.util.function.Consumer;

/** this allows us to wrap anything with tokens either before the item or after. */
public class TokenizedItem<T> {
  public final ArrayList<Token> after;
  public final ArrayList<Token> before;
  public final T item;

  public TokenizedItem(final T item) {
    this.before = new ArrayList<>();
    this.item = item;
    this.after = new ArrayList<>();
  }

  public void after(final Token token) {
    this.after.add(token);
  }

  public void before(final Token token) {
    this.before.add(token);
  }

  public void emitAfter(final Consumer<Token> yielder) {
    for (final Token b : after) {
      yielder.accept(b);
    }
  }

  public void emitBefore(final Consumer<Token> yielder) {
    for (final Token b : before) {
      yielder.accept(b);
    }
  }
}
