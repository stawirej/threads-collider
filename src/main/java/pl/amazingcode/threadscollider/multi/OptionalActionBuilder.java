package pl.amazingcode.threadscollider.multi;

import java.util.function.Consumer;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface OptionalActionBuilder {

  /**
   * Sets action to be executed by {@link ThreadsCollider}.
   *
   * @param action action to be executed by {@link ThreadsCollider}
   * @return {@link TimesBuilder}
   */
  TimesBuilder withAction(Runnable action);

  /**
   * Sets action to be executed by {@link ThreadsCollider}.
   *
   * @param action action to be executed by {@link ThreadsCollider}
   * @param actionName description of action used when reporting deadlocked threads.
   * @return {@link TimesBuilder}
   */
  TimesBuilder withAction(Runnable action, String actionName);

  /**
   * Sets await termination timeout for executor service used by {@link ThreadsCollider}.
   *
   * @param timeout await termination timeout for executor service used by {@link ThreadsCollider}
   * @return {@link OptionalBuilder}
   */
  TimeUnitBuilder withAwaitTerminationTimeout(long timeout);

  /**
   * Sets exception consumer for threads. This consumer will be called for each exception thrown by
   * threads. Consumer will be called in thread safe manner.
   *
   * @param threadsExceptionsConsumer - exception consumer for threads.
   * @return {@link OptionalBuilder}
   */
  OptionalBuilder withThreadsExceptionsConsumer(Consumer<Exception> threadsExceptionsConsumer);

  /**
   * Builds {@link ThreadsCollider}.
   *
   * @return {@link ThreadsCollider}
   */
  ThreadsCollider build();
}
