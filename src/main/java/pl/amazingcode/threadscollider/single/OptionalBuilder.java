package pl.amazingcode.threadscollider.single;

import java.util.function.Consumer;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface OptionalBuilder {

  /**
   * Sets exception consumer for threads. This consumer will be called for each exception thrown by
   * threads. Consumer will be called in thread safe manner.
   *
   * @param threadsExceptionsConsumer - exception consumer for threads.
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder withThreadsExceptionsConsumer(Consumer<Exception> threadsExceptionsConsumer);

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
