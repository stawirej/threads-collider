package pl.amazingcode.threadscollider;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import pl.amazingcode.threadscollider.fixtures.assertobject.CollisionsAssert;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class UseCases_Negative_Scenarios {

  private static final int TEST_REPETITIONS = 10;
  private static final CollisionsAssert collisionsAssert =
      CollisionsAssert.newInstance(TEST_REPETITIONS);

  @AfterAll
  static void afterAll() {

    collisionsAssert.report();
  }

  @RepeatedTest(TEST_REPETITIONS)
  void Adding_to_set(TestInfo testInfo) {
    // Given
    String testName = testInfo.getTestMethod().get().getName();
    Set<String> set = new HashSet<>();

    // When
    try (ThreadsCollider threadsCollider = threadsCollider().withAvailableProcessors().build()) {
      threadsCollider.collide(() -> set.add("foo"));
    }

    // Then
    collisionsAssert.assertTrue(testName, set.size() != 1);
  }

  @RepeatedTest(TEST_REPETITIONS)
  void Adding_to_list(TestInfo testInfo) {
    // Given
    String testName = testInfo.getTestMethod().get().getName();
    List<String> list = new ArrayList<>();
    int threadsCount = Runtime.getRuntime().availableProcessors();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider().withThreadsCount(threadsCount).build()) {
      threadsCollider.collide(() -> list.add("bar"));
    }

    // Then
    collisionsAssert.assertTrue(testName, list.size() != threadsCount);
  }

  @RepeatedTest(TEST_REPETITIONS)
  void Thread_safe_adding_to_map(TestInfo testInfo) {
    // Given
    String testName = testInfo.getTestMethod().get().getName();
    Map<String, String> map = new HashMap<>();

    // When
    try (ThreadsCollider threadsCollider = threadsCollider().withAvailableProcessors().build()) {
      threadsCollider.collide(() -> map.put("foo", "bar"));
    }

    // Then
    collisionsAssert.assertTrue(testName, map.size() != 1);
  }

  @RepeatedTest(TEST_REPETITIONS)
  void Adding_to_map_when_absent(TestInfo testInfo) {
    // Given
    String testName = testInfo.getTestMethod().get().getName();
    Map<String, String> map = new HashMap<>();

    // When
    try (ThreadsCollider threadsCollider = threadsCollider().withAvailableProcessors().build()) {
      threadsCollider.collide(() -> map.putIfAbsent("foo", "bar"));
    }

    // Then
    collisionsAssert.assertTrue(testName, map.size() != 1);
  }

  @RepeatedTest(TEST_REPETITIONS)
  void Log_failed_threads_exceptions() {
    // Given
    Runnable failingRunnable =
        () -> {
          throw new IllegalStateException("message");
        };
    int threadsCount = 10;

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider().withThreadsCount(threadsCount).build()) {

      threadsCollider.collide(failingRunnable);

      // Then
      then(threadsCollider.exceptions()).hasSize(threadsCount);
    }
  }
}
