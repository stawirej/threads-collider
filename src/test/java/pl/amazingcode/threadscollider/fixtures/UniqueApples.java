package pl.amazingcode.threadscollider.fixtures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class UniqueApples implements Iterable<Apple> {

  private final List<Apple> uniqueApples;

  private UniqueApples() {

    this.uniqueApples = new ArrayList<>();
  }

  public static UniqueApples newInstance() {

    return new UniqueApples();
  }

  public synchronized void add(Apple apple) {

    if (!uniqueApples.contains(apple)) {
      uniqueApples.add(apple);
    }
  }

  public void addUnSynchronized(Apple apple) {

    if (!uniqueApples.contains(apple)) {
      uniqueApples.add(apple);
    }
  }

  public int size() {

    return uniqueApples.size();
  }

  @Override
  public Iterator<Apple> iterator() {

    return uniqueApples.iterator();
  }
}
