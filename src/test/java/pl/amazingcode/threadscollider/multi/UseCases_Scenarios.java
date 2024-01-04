package pl.amazingcode.threadscollider.multi;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import pl.amazingcode.threadscollider.Processors;
import pl.amazingcode.threadscollider.ThreadsCollider;
import pl.amazingcode.threadscollider.fixtures.Counter;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class UseCases_Scenarios {

  @RepeatedTest(10)
  void Thread_safe_counter() {
    // Given
    Counter counter = new Counter();
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withAction(counter::increment)
            .times(Processors.HALF)
            .withAction(counter::decrement)
            .times(Processors.HALF)
            .withThreadsExceptionsConsumer(exceptions::add)
            .build()) {

      threadsCollider.collide();
    }

    // Then
    then(counter.value()).isZero();
    then(exceptions).isEmpty();
  }
}
