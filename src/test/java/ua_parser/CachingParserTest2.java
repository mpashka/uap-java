package ua_parser;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * These tests really only redo the same tests as in ParserTest but with a
 * different Parser subclass Also the same tests will be run several times on
 * the same user agents to validate the caching works correctly.
 *
 * @author niels
 *
 */
public class CachingParserTest2 {

  private Set<String> userAgents = new HashSet<>();
  private Set<String> oses = new HashSet<>();
  private Set<String> devices = new HashSet<>();

  @Test
  public void checkPerformance() throws Exception {
    // warm up JVM
    measureTime("Warm up", new ThreadSafeCachingParser());

    // Speed without optimizations
    ThreadSafeCachingParser parser = new ThreadSafeCachingParser();
    measureTime("No optimization", parser);

    // Using cache
    measureTime("Cache", parser);

    // Using cache + optimization
    parser.optimize();
    measureTime("Cache + optimization", parser);

    // Using optimization, not cache
    parser.resetCache();
    measureTime("Optimization, no cache", parser);

    System.out.println("Device:" + devices);
    System.out.println("OS:" + oses);
    System.out.println("UserAgents:" + userAgents);
  }

  private void measureTime(String msg, ThreadSafeCachingParser parser) throws Exception {
    long now = System.currentTimeMillis();
    int count = testOneTime(parser);
    long ms = (System.currentTimeMillis() - now) * 1000 / count;
    System.out.println(msg + " " + ms);
  }

  private int testOneTime(ThreadSafeCachingParser parser) throws Exception {
    BufferedReader in = new BufferedReader(new FileReader("/Public/Projects/PulsePoint/github/ad-serving-and-commons/ad-serving/ad-serving-commons/src/test/resources/com/contextweb/adserving/commons/useragent/IsMobileDetectorTest-user-agents.csv"));
    String inLine;
    int count = 0;
    int deviceFound = 0;
    int deviceNotFound = 0;
    while ((inLine = in.readLine()) != null) {
      if (inLine.charAt(0) != '2') {
        continue;
      }
      String fields[] = StringUtils.split(inLine, "\t");
      String ua = fields[6];
      Client parse = parser.parse(ua);
      userAgents.add(parse.userAgent.family);
      oses.add(parse.os.family);
      devices.add(parse.device.brand);


      String deviceName = parse.device.device;
      if (deviceName == null || deviceName.length() == 0 || deviceName.equals("Other")) {
        deviceNotFound++;
      } else {
        deviceFound++;
      }


      count++;
    }
    in.close();
    System.out.println("Device. Found: " + deviceFound + ", not: " + deviceNotFound + ", % found:" + NumberFormat.getPercentInstance().format(((double) deviceFound) / count));
    return count;
  }
}
