/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.runtime.delta.secure;

import javax.crypto.SecretKey;

/** encode assets for secure usage by clients */
public class AssetIdEncoder {
  private final SecretKey key;

  public AssetIdEncoder(String keyHeader) {
    this.key = SecureAssetUtil.secretKeyOf(keyHeader);
  }

  /** encrypt the id per the client's given header */
  public String encrypt(String id) {
    return SecureAssetUtil.encryptToBase64(key, id);
  }
}
