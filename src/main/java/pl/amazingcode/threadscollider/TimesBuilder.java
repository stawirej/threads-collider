package pl.amazingcode.threadscollider;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface TimesBuilder {

  /**
   * Sets number of times to repeat action.
   *
   * @param times number of times to repeat action
   * @return {@link OptionalActionBuilder}
   */
  OptionalActionBuilder times(int times);
}
