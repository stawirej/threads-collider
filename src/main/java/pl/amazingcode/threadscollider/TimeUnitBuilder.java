package pl.amazingcode.threadscollider;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface TimeUnitBuilder {

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as nanoseconds.
   *
   * @return {@link Builder}
   */
  Builder asNanoseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as microseconds.
   *
   * @return {@link Builder}
   */
  Builder asMicroseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as milliseconds.
   *
   * @return {@link Builder}
   */
  Builder asMilliseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as seconds.
   *
   * @return {@link Builder}
   */
  Builder asSeconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as minutes.
   *
   * @return {@link Builder}
   */
  Builder asMinutes();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as hours.
   *
   * @return {@link Builder}
   */
  Builder asHours();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as days.
   *
   * @return {@link Builder}
   */
  Builder asDays();
}
