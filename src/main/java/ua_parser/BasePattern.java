package ua_parser;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Used to store hits number for rules optimization
 *
 * @author Pavel Moukhataev
 */
public class BasePattern {
    private AtomicInteger hits = new AtomicInteger();

    public void hit() {
        hits.incrementAndGet();
    }

    public int getHits() {
        return hits.get();
    }
}
