package pl.amazingcode.threadscollider.multi;

/** Intermediary builder for {@link MultiThreadsCollider}. */
public interface TimesBuilder {

  /**
   * Sets number of times to repeat action.
   *
   * @param times number of times to repeat action
   * @return {@link OptionalActionBuilder}
   */
  OptionalActionBuilder times(int times);
}
