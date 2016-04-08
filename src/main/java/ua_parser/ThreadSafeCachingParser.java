package ua_parser;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * When doing webanalytics (with for example PIG) the main pattern is to process
 * weblogs in clickstreams. A basic fact about common clickstreams is that in
 * general the same browser will do multiple requests in sequence. This has the
 * effect that the same useragent will appear in the logfiles and we will see
 * the need to parse the same useragent over and over again.
 *
 * This class introduces a very simple LRU cache to reduce the number of times
 * the parsing is actually done.
 *
 * @author Niels Basjes
 *
 */
public class ThreadSafeCachingParser extends Parser {

  public static final int CACHE_SIZE = 500_000;

  private static final Comparator<BasePattern> PATTERN_COMPARATOR = (o1, o2) -> o2.getHits() - o1.getHits();

  private Cache<String, Client> cache = CacheBuilder.newBuilder().maximumSize(CACHE_SIZE).expireAfterAccess(24, TimeUnit.HOURS).build();

  // ------------------------------------------

  public ThreadSafeCachingParser() throws IOException {
    super();
  }

  public ThreadSafeCachingParser(InputStream regexYaml) {
    super(regexYaml);
  }

  // ------------------------------------------

  @SuppressWarnings("unchecked")
  @Override
  public Client parse(String agentString) {
    if (agentString == null) {
      return null;
    }
    Client client = cache.getIfPresent(agentString);
    if (client != null) {
      return client;
    }
    client = super.parse(agentString);
    cache.put(agentString, client);
    return client;
  }

  /**
   * Optimizes rules order
   */
  public void optimize() {
    deviceParser.setPatterns(optimize(deviceParser.getPatterns()));
    osParser.setPatterns(optimize(osParser.getPatterns()));
    uaParser.setPatterns(optimize(uaParser.getPatterns()));
  }

  /**
   * For testing purposes only
   */
  public void resetCache() {
    CacheBuilder.newBuilder().maximumSize(CACHE_SIZE).expireAfterAccess(24, TimeUnit.HOURS).build();
  }

  private <V extends BasePattern> List<V> optimize(List<V> patterns) {
    List<V> optimizedPatterns = new ArrayList<>(patterns);
    Collections.sort(optimizedPatterns, PATTERN_COMPARATOR);
    return optimizedPatterns;
  }

}
