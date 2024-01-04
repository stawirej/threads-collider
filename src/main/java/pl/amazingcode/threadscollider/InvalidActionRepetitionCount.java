package pl.amazingcode.threadscollider;

import static java.lang.String.format;

/** Thrown when invalid action repetition count. */
public final class InvalidActionRepetitionCount extends RuntimeException {

  private InvalidActionRepetitionCount(String message) {
    super(message);
  }

  /**
   * Creates new instance of {@link InvalidActionRepetitionCount}.
   *
   * @param times action repetition count
   * @return new instance of {@link InvalidActionRepetitionCount}
   */
  public static InvalidActionRepetitionCount of(int times) {

    return new InvalidActionRepetitionCount(
        format("Action has to be repeated at least once, but was %d times.", times));
  }
}
