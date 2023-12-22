package pl.amazingcode.threadscollider.fixtures;

public final class ThreadUnsafeCounter {

  private int counter = 0;

  public void increment() {
    counter++;
  }

  public void decrement() {
    counter--;
  }

  public int value() {
    return counter;
  }
}
