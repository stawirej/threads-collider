package pl.amazingcode.threadscollider.single;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;
import static pl.amazingcode.threadscollider.fixtures.AppleExamples.RED_DELICIOUS;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import pl.amazingcode.threadscollider.Processors;
import pl.amazingcode.threadscollider.ThreadsCollider;
import pl.amazingcode.threadscollider.fixtures.UniqueApples;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ThreadsCollider_Scenarios {

  @RepeatedTest(10)
  void Build_threads_collider_with_user_defined_threads_count() {
    // Given
    UniqueApples uniqueApples = UniqueApples.newInstance();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider().withAction(() -> uniqueApples.add(RED_DELICIOUS)).times(10).build()) {

      threadsCollider.collide();
    }

    // Then
    then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
  }

  @RepeatedTest(10)
  void Build_threads_collider_with_available_processors() {
    // Given
    UniqueApples uniqueApples = UniqueApples.newInstance();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withAction(() -> uniqueApples.add(RED_DELICIOUS))
            .times(Processors.ALL)
            .build()) {
      threadsCollider.collide();
    }

    // Then
    then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
  }

  @Nested
  class Build_threads_collider_with_custom_executor_await_termination_timeout {

    @RepeatedTest(10)
    void as_nanoseconds() {
      // Given
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.add(RED_DELICIOUS))
              .times(Processors.ALL)
              .withAwaitTerminationTimeout(1_000_000)
              .asNanoseconds()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(10)
    void as_microseconds() {
      // Given
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.add(RED_DELICIOUS))
              .times(Processors.ALL)
              .withAwaitTerminationTimeout(1_000)
              .asMicroseconds()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(10)
    void as_milliseconds() {
      // Given
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.add(RED_DELICIOUS))
              .times(Processors.ALL)
              .withAwaitTerminationTimeout(1)
              .asMilliseconds()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(10)
    void as_seconds() {
      // Given
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.add(RED_DELICIOUS))
              .times(Processors.ALL)
              .withAwaitTerminationTimeout(1)
              .asSeconds()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(10)
    void as_minutes() {
      // Given
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.add(RED_DELICIOUS))
              .times(Processors.ALL)
              .withAwaitTerminationTimeout(1)
              .asMinutes()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(10)
    void as_hours() {
      // Given
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.add(RED_DELICIOUS))
              .times(Processors.ALL)
              .withAwaitTerminationTimeout(1)
              .asHours()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }

    @RepeatedTest(10)
    void as_days() {
      // Given
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.add(RED_DELICIOUS))
              .times(Processors.ALL)
              .withAwaitTerminationTimeout(1)
              .asDays()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
    }
  }
}
