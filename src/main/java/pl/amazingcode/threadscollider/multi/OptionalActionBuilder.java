package pl.amazingcode.threadscollider.multi;

import java.util.function.Consumer;

/** Intermediary builder for {@link MultiThreadsCollider}. */
public interface OptionalActionBuilder {

  /**
   * Sets action to be executed by {@link MultiThreadsCollider}.
   *
   * @param action action to be executed by {@link MultiThreadsCollider}
   * @return {@link TimesBuilder}
   */
  TimesBuilder withAction(Runnable action);

  /**
   * Sets await termination timeout for executor service used by {@link MultiThreadsCollider}.
   *
   * @param timeout await termination timeout for executor service used by {@link
   *     MultiThreadsCollider}
   * @return {@link MultiOptionalBuilder}
   */
  MultiTimeUnitBuilder withAwaitTerminationTimeout(long timeout);

  /**
   * Sets exception consumer for threads. This consumer will be called for each exception thrown by
   * threads. Consumer will be called in thread safe manner.
   *
   * @param threadsExceptionsConsumer - exception consumer for threads.
   * @return {@link MultiOptionalBuilder}
   */
  MultiOptionalBuilder withThreadsExceptionsConsumer(Consumer<Exception> threadsExceptionsConsumer);

  /**
   * Builds {@link MultiThreadsCollider}.
   *
   * @return {@link MultiThreadsCollider}
   */
  MultiThreadsCollider build();
}
