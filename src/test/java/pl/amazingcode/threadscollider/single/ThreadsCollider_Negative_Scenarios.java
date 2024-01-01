package pl.amazingcode.threadscollider.single;

import static pl.amazingcode.threadscollider.fixtures.AppleExamples.RED_DELICIOUS;
import static pl.amazingcode.threadscollider.multi.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import pl.amazingcode.threadscollider.fixtures.UniqueApples;
import pl.amazingcode.threadscollider.fixtures.assertobject.CollisionsAssert;
import pl.amazingcode.threadscollider.multi.ThreadsCollider;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ThreadsCollider_Negative_Scenarios {

  private static final int TEST_REPETITIONS = 200;
  private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

  private static final CollisionsAssert collisionsAssert =
      CollisionsAssert.newInstance(TEST_REPETITIONS);

  @AfterAll
  static void afterAll() {

    collisionsAssert.report();
  }

  @RepeatedTest(TEST_REPETITIONS)
  void Build_threads_collider_with_user_defined_threads_count(TestInfo testInfo) {
    // Given
    String testName = testInfo.getTestMethod().get().getName();
    UniqueApples uniqueApples = UniqueApples.newInstance();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
            .times(10)
            .build()) {

      threadsCollider.collide();
    }

    // Then
    collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
  }

  @RepeatedTest(TEST_REPETITIONS)
  void Build_threads_collider_with_available_processors(TestInfo testInfo) {
    // Given
    String testName = testInfo.getTestMethod().get().getName();
    UniqueApples uniqueApples = UniqueApples.newInstance();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
            .times(AVAILABLE_PROCESSORS)
            .build()) {
      threadsCollider.collide();
    }

    // Then
    collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
  }

  @Nested
  class Build_threads_collider_with_custom_executor_await_termination_timeout {

    @RepeatedTest(TEST_REPETITIONS)
    void as_nanoseconds(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
              .times(AVAILABLE_PROCESSORS)
              .withAwaitTerminationTimeout(1_000_000)
              .asNanoseconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_microseconds(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
              .times(AVAILABLE_PROCESSORS)
              .withAwaitTerminationTimeout(1_000)
              .asMicroseconds()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_milliseconds(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
              .times(AVAILABLE_PROCESSORS)
              .withAwaitTerminationTimeout(1)
              .asMilliseconds()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_seconds(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
              .times(AVAILABLE_PROCESSORS)
              .withAwaitTerminationTimeout(1)
              .asSeconds()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_minutes(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
              .times(AVAILABLE_PROCESSORS)
              .withAwaitTerminationTimeout(1)
              .asMinutes()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_hours(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
              .times(AVAILABLE_PROCESSORS)
              .withAwaitTerminationTimeout(1)
              .asHours()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    void as_days(TestInfo testInfo) {
      // Given
      String testName = testInfo.getTestMethod().get().getName();
      UniqueApples uniqueApples = UniqueApples.newInstance();

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> uniqueApples.addUnSynchronized(RED_DELICIOUS))
              .times(AVAILABLE_PROCESSORS)
              .withAwaitTerminationTimeout(1)
              .asDays()
              .build()) {
        threadsCollider.collide();
      }

      // Then
      collisionsAssert.assertTrue(testName, uniqueApples.size() != 1);
    }
  }
}
