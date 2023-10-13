package pl.amazingcode.threadscollider;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;
import static pl.amazingcode.threadscollider.fixtures.AppleExamples.RED_DELICIOUS;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import pl.amazingcode.threadscollider.fixtures.UniqueApples;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ThreadsCollider_Scenarios {

  @RepeatedTest(100)
  void Build_threads_collider_with_user_defined_threads_count() {
    // Given
    var uniqueApples = UniqueApples.newInstance();

    // When
    try (var threadsCollider = threadsCollider().withThreadsCount(10).build()) {
      threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
    }

    // Then
    then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
  }

  @RepeatedTest(100)
  void Build_threads_collider_with_available_processors() {
    // Given
    var uniqueApples = UniqueApples.newInstance();

    // When
    try (var threadsCollider = threadsCollider().withAvailableProcessors().build()) {
      threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
    }

    // Then
    then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
  }

  @Nested
  class Build_threads_collider_with_custom_executor_await_termination_timeout {

    @RepeatedTest(100)
    void as_nanoseconds() {
      // Given
      var uniqueApples = UniqueApples.newInstance();

      // When
      try (var threadsCollider =
          threadsCollider()
              .withAvailableProcessors()
              .withAwaitTerminationTimeout(1_000_000)
              .asNanoseconds()
              .build()) {
        threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(100)
    void as_microseconds() {
      // Given
      var uniqueApples = UniqueApples.newInstance();

      // When
      try (var threadsCollider =
          threadsCollider()
              .withAvailableProcessors()
              .withAwaitTerminationTimeout(1_000)
              .asMicroseconds()
              .build()) {
        threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(100)
    void as_milliseconds() {
      // Given
      var uniqueApples = UniqueApples.newInstance();

      // When
      try (var threadsCollider =
          threadsCollider()
              .withAvailableProcessors()
              .withAwaitTerminationTimeout(1)
              .asMilliseconds()
              .build()) {
        threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(100)
    void as_seconds() {
      // Given
      var uniqueApples = UniqueApples.newInstance();

      // When
      try (var threadsCollider =
          threadsCollider()
              .withAvailableProcessors()
              .withAwaitTerminationTimeout(1)
              .asSeconds()
              .build()) {
        threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(100)
    void as_minutes() {
      // Given
      var uniqueApples = UniqueApples.newInstance();

      // When
      try (var threadsCollider =
          threadsCollider()
              .withAvailableProcessors()
              .withAwaitTerminationTimeout(1)
              .asMinutes()
              .build()) {
        threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(100)
    void as_hours() {
      // Given
      var uniqueApples = UniqueApples.newInstance();

      // When
      try (var threadsCollider =
          threadsCollider()
              .withAvailableProcessors()
              .withAwaitTerminationTimeout(1)
              .asHours()
              .build()) {
        threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(100)
    void as_days() {
      // Given
      var uniqueApples = UniqueApples.newInstance();

      // When
      try (var threadsCollider =
          threadsCollider()
              .withAvailableProcessors()
              .withAwaitTerminationTimeout(1)
              .asDays()
              .build()) {
        threadsCollider.collide(() -> uniqueApples.add(RED_DELICIOUS));
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }
  }
}
