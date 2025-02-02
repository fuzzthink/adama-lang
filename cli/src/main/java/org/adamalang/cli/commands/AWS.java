/*
 * This file is subject to the terms and conditions outlined in the file 'LICENSE' (hint: it's MIT); this file is located in the root directory near the README.md which you should also read.
 *
 * This file is part of the 'Adama' project which is a programming language and document store for board games; however, it can be so much more.
 *
 * See http://www.adama-lang.org/ for more information.
 *
 * (c) 2020 - 2022 by Jeffrey M. Barber (http://jeffrey.io)
 */
package org.adamalang.cli.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adamalang.cli.Config;
import org.adamalang.cli.Util;
import org.adamalang.common.ConfigObject;
import org.adamalang.common.jvm.MachineHeat;
import org.adamalang.common.metrics.NoOpMetricsFactory;
import org.adamalang.extern.aws.AWSConfig;
import org.adamalang.extern.aws.AWSMetrics;
import org.adamalang.extern.aws.S3;
import org.adamalang.extern.aws.SES;

import java.util.ArrayList;

public class AWS {
  public static void execute(Config config, String[] args) throws Exception {
    if (args.length == 0) {
      awsHelp();
      return;
    }
    String command = Util.normalize(args[0]);
    String[] next = Util.tail(args);
    switch (command) {
      case "setup":
        awsSetup(config);
        return;
      case "test-email":
        awsTestEmail(config);
        return;
      case "memory-test":
        awsMemoryTest();
        return;
      case "help":
        awsHelp();
        return;
    }
  }

  public static void awsMemoryTest() throws Exception {
    MachineHeat.install();
    ArrayList<byte[]> chunks = new ArrayList<>();
    int MB = 0;
    System.out.println("memory MB, ms, %");
    while (true) {
      long started = System.nanoTime();
      chunks.add(new byte[1024*1024]);
      double taken = (System.nanoTime() - started) / 1000000.0;
      MB ++;
      System.out.println(MB + ", " + taken + ", " + MachineHeat.memory());
    }
  }

  public static void awsHelp() {
    System.out.println(Util.prefix("Production AWS Support.", Util.ANSI.Green));
    System.out.println();
    System.out.println(Util.prefix("USAGE:", Util.ANSI.Yellow));
    System.out.println("    " + Util.prefix("adama aws", Util.ANSI.Green) + " " + Util.prefix("[AWSSUBCOMMAND]", Util.ANSI.Magenta));
    System.out.println();
    System.out.println(Util.prefix("FLAGS:", Util.ANSI.Yellow));
    System.out.println("    " + Util.prefix("--config", Util.ANSI.Green) + "          Supplies a config file path other than the default (~/.adama)");
    System.out.println();
    System.out.println(Util.prefix("AWSSUBCOMMAND:", Util.ANSI.Yellow));
    System.out.println("    " + Util.prefix("setup", Util.ANSI.Green) + "             Interactive setup for the config");
    System.out.println("    " + Util.prefix("test-email", Util.ANSI.Green) + "        Test Email via AWS");
    System.out.println("    " + Util.prefix("memory-test", Util.ANSI.Green) + "       Crash by allocating memory");
    System.out.println("    " + Util.prefix("release", Util.ANSI.Green) + "           Release the binary to the world");
  }

  public static void awsTestEmail(Config config) throws Exception {
    AWSConfig awsConfig = new AWSConfig(new ConfigObject(config.get_or_create_child("aws")));

    System.out.println();
    System.out.print(Util.prefix("To:", Util.ANSI.Yellow));
    String to = System.console().readLine();

    SES ses = new SES(awsConfig, new AWSMetrics(new NoOpMetricsFactory()));
    ses.sendCode(to, "TESTCODE");
  }

  public static void awsSetup(Config config) throws Exception {
    System.out.println();
    System.out.print(Util.prefix("AccessKey:", Util.ANSI.Yellow));
    String accessKey = System.console().readLine();

    System.out.println();
    System.out.print(Util.prefix("SecretKey:", Util.ANSI.Yellow));
    String secretKey = System.console().readLine();

    System.out.println();
    System.out.print(Util.prefix("Region:", Util.ANSI.Yellow));
    String region = System.console().readLine();

    System.out.println();
    System.out.print(Util.prefix("Init-From-Email:", Util.ANSI.Yellow));
    String fromEmailAddressForInit = System.console().readLine();

    System.out.println();
    System.out.print(Util.prefix("Init-ReplyTo-Email:", Util.ANSI.Yellow));
    String replyToEmailAddressForInit = System.console().readLine();

    System.out.println();
    System.out.print(Util.prefix("Bucket:", Util.ANSI.Yellow));
    String bucket = System.console().readLine();

    config.manipulate((node) -> {
      ObjectNode roleNode = node.putObject("aws");
      roleNode.put("access_key", accessKey);
      roleNode.put("secret_key", secretKey);
      roleNode.put("region", region);
      roleNode.put("init_from_email", fromEmailAddressForInit);
      roleNode.put("init_reply_email", replyToEmailAddressForInit);
      roleNode.put("bucket", bucket);
    });
  }
}
