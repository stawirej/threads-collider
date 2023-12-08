package pl.amazingcode.threadscollider.fixtures;

import java.util.concurrent.atomic.AtomicInteger;

public final class Counter {

  private final AtomicInteger counter = new AtomicInteger(0);

  public void increment() {
    counter.incrementAndGet();
  }

  public void decrement() {
    counter.decrementAndGet();
  }

  public int value() {
    return counter.get();
  }
}
