package pl.amazingcode.threadscollider;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class UseCases_Scenarios {

  @RepeatedTest(10)
  void Thread_safe_adding_to_set() {
    // Given
    Set<String> set = Collections.synchronizedSet(new HashSet<>());

    // When
    try (ThreadsCollider threadsCollider = threadsCollider().withAvailableProcessors().build()) {
      threadsCollider.collide(() -> set.add("foo"));
    }

    // Then
    then(set).hasSize(1).containsExactly("foo");
  }

  @RepeatedTest(10)
  void Thread_safe_adding_to_list() {
    // Given
    List<String> list = Collections.synchronizedList(new ArrayList<>());
    int threadsCount = Runtime.getRuntime().availableProcessors();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider().withThreadsCount(threadsCount).build()) {
      threadsCollider.collide(() -> list.add("bar"));
    }

    // Then
    then(list).hasSize(threadsCount).containsOnly("bar");
  }

  @RepeatedTest(10)
  void Thread_safe_adding_to_map() {
    // Given
    Map<String, String> map = new ConcurrentHashMap<>();

    // When
    try (ThreadsCollider threadsCollider = threadsCollider().withAvailableProcessors().build()) {
      threadsCollider.collide(() -> map.put("foo", "bar"));
    }

    // Then
    then(map).hasSize(1).containsEntry("foo", "bar");
  }

  @RepeatedTest(10)
  void Thread_safe_adding_to_map_when_absent() {
    // Given
    Map<String, String> map = new ConcurrentHashMap<>();

    // When
    try (ThreadsCollider threadsCollider = threadsCollider().withAvailableProcessors().build()) {
      threadsCollider.collide(() -> map.putIfAbsent("foo", "bar"));
    }

    // Then
    then(map).hasSize(1).containsEntry("foo", "bar");
  }

  @RepeatedTest(10)
  void Log_failed_threads_exceptions() {
    // Given
    Runnable failingRunnable =
        () -> {
          throw new IllegalStateException("message");
        };
    List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());
    int threadsCount = 10;

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withThreadsCount(threadsCount)
            .withThreadsExceptionsConsumer(exceptions::add)
            .build()) {

      threadsCollider.collide(failingRunnable);
    }

    // Then
    then(exceptions).hasSize(threadsCount).map(Exception::getMessage).containsOnly("message");
  }

  @RepeatedTest(10)
  void Log_failed_threads_exceptions_in_thread_safe_manner() {
    // Given
    Runnable failingRunnable =
        () -> {
          throw new IllegalStateException("message");
        };
    List<Exception> exceptions = new ArrayList<>();
    int threadsCount = 10;

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withThreadsCount(threadsCount)
            .withThreadsExceptionsConsumer(exceptions::add)
            .build()) {

      threadsCollider.collide(failingRunnable);
    }

    // Then
    then(exceptions).hasSize(threadsCount).map(Exception::getMessage).containsOnly("message");
  }
}
