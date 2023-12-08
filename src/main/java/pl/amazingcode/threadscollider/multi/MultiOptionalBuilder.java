package pl.amazingcode.threadscollider.multi;

import java.util.function.Consumer;
import pl.amazingcode.threadscollider.single.OptionalBuilder;
import pl.amazingcode.threadscollider.single.ThreadsCollider;
import pl.amazingcode.threadscollider.single.TimeUnitBuilder;

/** Intermediary builder for {@link ThreadsCollider}. */
public interface MultiOptionalBuilder {

  /**
   * Sets exception consumer for threads. This consumer will be called for each exception thrown by
   * threads. Consumer will be called in thread safe manner.
   *
   * @param threadsExceptionsConsumer - exception consumer for threads.
   * @return {@link OptionalBuilder}
   */
  MultiOptionalBuilder withThreadsExceptionsConsumer(Consumer<Exception> threadsExceptionsConsumer);

  /**
   * Sets await termination timeout for executor service used by {@link ThreadsCollider}.
   *
   * @param timeout - await termination timeout for executor service used by {@link
   *     ThreadsCollider}.
   * @return {@link TimeUnitBuilder}
   */
  MultiTimeUnitBuilder withAwaitTerminationTimeout(long timeout);

  /**
   * Builds {@link ThreadsCollider}.
   *
   * @return {@link ThreadsCollider}
   */
  MultiThreadsCollider build();
}
