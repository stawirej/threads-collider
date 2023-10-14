package pl.amazingcode.threadscollider.fixtures;

import java.util.Objects;

public class Apple {

  private final String name;

  public Apple(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Apple apple = (Apple) o;
    return Objects.equals(name, apple.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
