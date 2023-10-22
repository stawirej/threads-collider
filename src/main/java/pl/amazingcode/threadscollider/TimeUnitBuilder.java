package pl.amazingcode.threadscollider;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface TimeUnitBuilder {

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as nanoseconds.
   *
   * @return {@link Builder}
   */
  OptionalBuilder asNanoseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as microseconds.
   *
   * @return {@link Builder}
   */
  OptionalBuilder asMicroseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as milliseconds.
   *
   * @return {@link Builder}
   */
  OptionalBuilder asMilliseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as seconds.
   *
   * @return {@link Builder}
   */
  OptionalBuilder asSeconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as minutes.
   *
   * @return {@link Builder}
   */
  OptionalBuilder asMinutes();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as hours.
   *
   * @return {@link Builder}
   */
  OptionalBuilder asHours();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as days.
   *
   * @return {@link Builder}
   */
  OptionalBuilder asDays();
}
