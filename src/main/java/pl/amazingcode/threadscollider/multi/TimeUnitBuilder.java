package pl.amazingcode.threadscollider.multi;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface TimeUnitBuilder {

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as nanoseconds.
   *
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder asNanoseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as microseconds.
   *
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder asMicroseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as milliseconds.
   *
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder asMilliseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as seconds.
   *
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder asSeconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as minutes.
   *
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder asMinutes();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as hours.
   *
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder asHours();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as days.
   *
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder asDays();
}
