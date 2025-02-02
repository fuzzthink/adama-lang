/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.extern.aws;

import org.adamalang.common.ConfigObject;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

public class AWSConfig implements AwsCredentialsProvider, AwsCredentials {
  private final String accessKeyId;
  private final String secretKey;
  public final String fromEmailAddressForInit;
  public final String replyToEmailAddressForInit;
  public final String region;
  public final String bucketForAssets;

  public AWSConfig(ConfigObject config) throws Exception {
    this.accessKeyId = config.strOfButCrash("access_key", "AWS Access Key not found");
    this.secretKey = config.strOfButCrash("secret_key", "AWS Secret Key not found");
    this.region = config.strOfButCrash("region", "AWS Region");
    this.fromEmailAddressForInit = config.strOfButCrash("init_from_email", "No sender email address set for init");
    this.replyToEmailAddressForInit = config.strOfButCrash("init_reply_email", "No reply email address set for init");
    this.bucketForAssets = config.strOfButCrash("bucket", "No bucket for assets");
  }

  @Override
  public String accessKeyId() {
    return accessKeyId;
  }

  @Override
  public String secretAccessKey() {
    return secretKey;
  }

  @Override
  public AwsCredentials resolveCredentials() {
    return this;
  }
}
