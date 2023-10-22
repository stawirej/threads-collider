package pl.amazingcode.threadscollider;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface ThreadsCountBuilder {

  /**
   * Sets number of threads to be used by {@link ThreadsCollider}.
   *
   * @param threadsCount - number of threads to be used by {@link ThreadsCollider}.
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder withThreadsCount(int threadsCount);

  /**
   * Sets the number of threads to be used by {@link ThreadsCollider} to number of available
   * processors to the virtual machine. Never smaller than one.
   *
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder withAvailableProcessors();
}
