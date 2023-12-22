package pl.amazingcode.threadscollider.multi;

import java.util.function.Consumer;
import pl.amazingcode.threadscollider.single.ThreadsCollider;

/** Intermediary builder for {@link MultiThreadsCollider}. */
public interface MultiOptionalBuilder {

  /**
   * Sets exception consumer for threads. This consumer will be called for each exception thrown by
   * threads. Consumer will be called in thread safe manner.
   *
   * @param threadsExceptionsConsumer - exception consumer for threads.
   * @return {@link MultiOptionalBuilder}
   */
  MultiOptionalBuilder withThreadsExceptionsConsumer(Consumer<Exception> threadsExceptionsConsumer);

  /**
   * Sets await termination timeout for executor service used by {@link ThreadsCollider}.
   *
   * @param timeout - await termination timeout for executor service used by {@link
   *     ThreadsCollider}.
   * @return {@link MultiTimeUnitBuilder}
   */
  MultiTimeUnitBuilder withAwaitTerminationTimeout(long timeout);

  /**
   * Builds {@link MultiThreadsCollider}.
   *
   * @return {@link MultiThreadsCollider}
   */
  MultiThreadsCollider build();
}
