package pl.amazingcode.threadscollider.multi;

import pl.amazingcode.threadscollider.single.Builder;
import pl.amazingcode.threadscollider.single.ThreadsCollider;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface MultiTimeUnitBuilder {

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as nanoseconds.
   *
   * @return {@link Builder}
   */
  MultiOptionalBuilder asNanoseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as microseconds.
   *
   * @return {@link Builder}
   */
  MultiOptionalBuilder asMicroseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as milliseconds.
   *
   * @return {@link Builder}
   */
  MultiOptionalBuilder asMilliseconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as seconds.
   *
   * @return {@link Builder}
   */
  MultiOptionalBuilder asSeconds();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as minutes.
   *
   * @return {@link Builder}
   */
  MultiOptionalBuilder asMinutes();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as hours.
   *
   * @return {@link Builder}
   */
  MultiOptionalBuilder asHours();

  /**
   * Sets await termination timeout time unit for executor service used by {@link ThreadsCollider}
   * as days.
   *
   * @return {@link Builder}
   */
  MultiOptionalBuilder asDays();
}
