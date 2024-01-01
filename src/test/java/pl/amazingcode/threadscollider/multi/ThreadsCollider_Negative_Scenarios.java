package pl.amazingcode.threadscollider.multi;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import pl.amazingcode.threadscollider.Processors;
import pl.amazingcode.threadscollider.ThreadsCollider;
import pl.amazingcode.threadscollider.fixtures.ThreadUnsafeCounter;
import pl.amazingcode.threadscollider.fixtures.assertobject.CollisionsAssert;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ThreadsCollider_Negative_Scenarios {

  private static final int TEST_REPETITIONS = 200;

  private static final CollisionsAssert collisionsAssert =
      CollisionsAssert.newInstance(TEST_REPETITIONS);

  @AfterAll
  static void afterAll() {

    collisionsAssert.report();
  }

  @RepeatedTest(TEST_REPETITIONS)
  void Build_multi_threads_collider(TestInfo testInfo) {
    // Given
    String testName = testInfo.getTestMethod().get().getName();
    ThreadUnsafeCounter counter = new ThreadUnsafeCounter();

    // When
    try (ThreadsCollider threadsCollider =
        ThreadsCollider.ThreadsColliderBuilder.threadsCollider()
            .withAction(counter::increment)
            .times(Processors.HALF)
            .withAction(counter::decrement)
            .times(Processors.HALF)
            .build()) {

      threadsCollider.collide();
    }

    // Then
    collisionsAssert.assertTrue(testName, counter.value() != 0);
  }

  @Nested
  class Build_threads_collider_with_custom_executor_await_termination_timeout {

    @RepeatedTest(TEST_REPETITIONS)
    void as_nanoseconds(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      ThreadUnsafeCounter counter = new ThreadUnsafeCounter();

      // When
      try (ThreadsCollider threadsCollider =
          ThreadsCollider.ThreadsColliderBuilder.threadsCollider()
              .withAction(counter::increment)
              .times(Processors.HALF)
              .withAction(counter::decrement)
              .times(Processors.HALF)
              .withAwaitTerminationTimeout(1_000_000)
              .asNanoseconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, counter.value() != 0);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_microseconds(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      ThreadUnsafeCounter counter = new ThreadUnsafeCounter();

      // When
      try (ThreadsCollider threadsCollider =
          ThreadsCollider.ThreadsColliderBuilder.threadsCollider()
              .withAction(counter::increment)
              .times(Processors.HALF)
              .withAction(counter::decrement)
              .times(Processors.HALF)
              .withAwaitTerminationTimeout(1_000)
              .asMicroseconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, counter.value() != 0);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_milliseconds(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      ThreadUnsafeCounter counter = new ThreadUnsafeCounter();

      // When
      try (ThreadsCollider threadsCollider =
          ThreadsCollider.ThreadsColliderBuilder.threadsCollider()
              .withAction(counter::increment)
              .times(Processors.HALF)
              .withAction(counter::decrement)
              .times(Processors.HALF)
              .withAwaitTerminationTimeout(1)
              .asMilliseconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, counter.value() != 0);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_seconds(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      ThreadUnsafeCounter counter = new ThreadUnsafeCounter();

      // When
      try (ThreadsCollider threadsCollider =
          ThreadsCollider.ThreadsColliderBuilder.threadsCollider()
              .withAction(counter::increment)
              .times(Processors.HALF)
              .withAction(counter::decrement)
              .times(Processors.HALF)
              .withAwaitTerminationTimeout(1)
              .asSeconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, counter.value() != 0);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_minutes(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      ThreadUnsafeCounter counter = new ThreadUnsafeCounter();

      // When
      try (ThreadsCollider threadsCollider =
          ThreadsCollider.ThreadsColliderBuilder.threadsCollider()
              .withAction(counter::increment)
              .times(Processors.HALF)
              .withAction(counter::decrement)
              .times(Processors.HALF)
              .withAwaitTerminationTimeout(1)
              .asMinutes()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, counter.value() != 0);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_hours(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      ThreadUnsafeCounter counter = new ThreadUnsafeCounter();

      // When
      try (ThreadsCollider threadsCollider =
          ThreadsCollider.ThreadsColliderBuilder.threadsCollider()
              .withAction(counter::increment)
              .times(Processors.HALF)
              .withAction(counter::decrement)
              .times(Processors.HALF)
              .withAwaitTerminationTimeout(1)
              .asHours()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, counter.value() != 0);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_days(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      ThreadUnsafeCounter counter = new ThreadUnsafeCounter();

      // When
      try (ThreadsCollider threadsCollider =
          ThreadsCollider.ThreadsColliderBuilder.threadsCollider()
              .withAction(counter::increment)
              .times(Processors.HALF)
              .withAction(counter::decrement)
              .times(Processors.HALF)
              .withAwaitTerminationTimeout(1)
              .asDays()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, counter.value() != 0);
    }
  }
}
