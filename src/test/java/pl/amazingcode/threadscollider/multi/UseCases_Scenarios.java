package pl.amazingcode.threadscollider.multi;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.multi.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import pl.amazingcode.threadscollider.fixtures.Counter;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class UseCases_Scenarios {

  private static final int ACTION_THREADS_COUNT = Runtime.getRuntime().availableProcessors() / 2;

  @RepeatedTest(10)
  void Thread_safe_counter() {
    // Given
    Counter counter = new Counter();
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withAction(counter::increment)
            .times(ACTION_THREADS_COUNT)
            .withAction(counter::decrement)
            .times(ACTION_THREADS_COUNT)
            .withThreadsExceptionsConsumer(exceptions::add)
            .build()) {

      threadsCollider.collide();
    }

    // Then
    then(counter.value()).isZero();
    then(exceptions).isEmpty();
  }
}
