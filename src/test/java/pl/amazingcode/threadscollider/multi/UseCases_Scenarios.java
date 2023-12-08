package pl.amazingcode.threadscollider.multi;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.multi.MultiThreadsCollider.MultiThreadsColliderBuilder.multiThreadsCollider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import pl.amazingcode.threadscollider.fixtures.Counter;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class UseCases_Scenarios {

  @RepeatedTest(10)
  void Thread_safe_counter() {
    // Given
    Counter counter = new Counter();

    // When
    try (MultiThreadsCollider threadsCollider =
        multiThreadsCollider()
            .withAction(counter::increment)
            .times(4)
            .withAction(counter::decrement)
            .times(4)
            .build()) {

      threadsCollider.collide();
    }

    // Then
    then(counter.value()).isZero();
  }
}
