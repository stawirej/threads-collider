package pl.amazingcode.threadscollider;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface TimeoutBuilder {

  /**
   * Sets await termination timeout for executor service used by {@link ThreadsCollider}.
   *
   * @param timeout - await termination timeout for executor service used by {@link
   *     ThreadsCollider}.
   * @return {@link TimeUnitBuilder}
   */
  TimeUnitBuilder withAwaitTerminationTimeout(long timeout);

  /**
   * Builds {@link ThreadsCollider}.
   *
   * @return {@link ThreadsCollider}
   */
  ThreadsCollider build();
}
