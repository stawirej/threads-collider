package pl.amazingcode.threadscollider.multi;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import pl.amazingcode.threadscollider.ThreadsCollider;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ThreadsCollider_Scenarios {

  @RepeatedTest(10)
  void Build_multi_threads_collider_list() {
    // Given
    List<String> list = Collections.synchronizedList(new ArrayList<>());
    List<Exception> exceptions = new ArrayList<>();
    int threadsCount = Runtime.getRuntime().availableProcessors();
    int addThreadsCount = threadsCount / 2;
    int removeThreadsCount = threadsCount / 2;

    for (int i = 0; i < threadsCount; i++) {
      list.add("text");
    }

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withAction(() -> list.add("text"))
            .times(addThreadsCount)
            .withAction(() -> list.remove("text"))
            .times(removeThreadsCount)
            .withThreadsExceptionsConsumer(exceptions::add)
            .build()) {

      threadsCollider.collide();
    }

    // Then
    then(exceptions).isEmpty();
    then(list).hasSize(threadsCount).containsOnly("text");
  }

  @Nested
  class Build_threads_collider_with_custom_executor_await_termination_timeout {

    @RepeatedTest(10)
    void as_nanoseconds() {
      // Given
      List<String> list = Collections.synchronizedList(new ArrayList<>());
      List<Exception> exceptions = new ArrayList<>();
      int threadsCount = Runtime.getRuntime().availableProcessors();
      int addThreadsCount = threadsCount / 2;
      int removeThreadsCount = threadsCount / 2;

      for (int i = 0; i < threadsCount; i++) {
        list.add("text");
      }

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> list.add("text"))
              .times(addThreadsCount)
              .withAction(() -> list.remove("text"))
              .times(removeThreadsCount)
              .withThreadsExceptionsConsumer(exceptions::add)
              .withAwaitTerminationTimeout(1_000_000)
              .asNanoseconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      then(exceptions).isEmpty();
      then(list).hasSize(threadsCount).containsOnly("text");
    }

    @RepeatedTest(10)
    void as_microseconds() {
      // Given
      List<String> list = Collections.synchronizedList(new ArrayList<>());
      List<Exception> exceptions = new ArrayList<>();
      int threadsCount = Runtime.getRuntime().availableProcessors();
      int addThreadsCount = threadsCount / 2;
      int removeThreadsCount = threadsCount / 2;

      for (int i = 0; i < threadsCount; i++) {
        list.add("text");
      }

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> list.add("text"))
              .times(addThreadsCount)
              .withAction(() -> list.remove("text"))
              .times(removeThreadsCount)
              .withThreadsExceptionsConsumer(exceptions::add)
              .withAwaitTerminationTimeout(1_000)
              .asMicroseconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      then(exceptions).isEmpty();
      then(list).hasSize(threadsCount).containsOnly("text");
    }

    @RepeatedTest(10)
    void as_milliseconds() {
      // Given
      List<String> list = Collections.synchronizedList(new ArrayList<>());
      List<Exception> exceptions = new ArrayList<>();
      int threadsCount = Runtime.getRuntime().availableProcessors();
      int addThreadsCount = threadsCount / 2;
      int removeThreadsCount = threadsCount / 2;

      for (int i = 0; i < threadsCount; i++) {
        list.add("text");
      }

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> list.add("text"))
              .times(addThreadsCount)
              .withAction(() -> list.remove("text"))
              .times(removeThreadsCount)
              .withThreadsExceptionsConsumer(exceptions::add)
              .withAwaitTerminationTimeout(1)
              .asMilliseconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      then(exceptions).isEmpty();
      then(list).hasSize(threadsCount).containsOnly("text");
    }

    @RepeatedTest(10)
    void as_seconds() {
      // Given
      List<String> list = Collections.synchronizedList(new ArrayList<>());
      List<Exception> exceptions = new ArrayList<>();
      int threadsCount = Runtime.getRuntime().availableProcessors();
      int addThreadsCount = threadsCount / 2;
      int removeThreadsCount = threadsCount / 2;

      for (int i = 0; i < threadsCount; i++) {
        list.add("text");
      }

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> list.add("text"))
              .times(addThreadsCount)
              .withAction(() -> list.remove("text"))
              .times(removeThreadsCount)
              .withThreadsExceptionsConsumer(exceptions::add)
              .withAwaitTerminationTimeout(1)
              .asSeconds()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      then(exceptions).isEmpty();
      then(list).hasSize(threadsCount).containsOnly("text");
    }

    @RepeatedTest(10)
    void as_minutes() {
      // Given
      List<String> list = Collections.synchronizedList(new ArrayList<>());
      List<Exception> exceptions = new ArrayList<>();
      int threadsCount = Runtime.getRuntime().availableProcessors();
      int addThreadsCount = threadsCount / 2;
      int removeThreadsCount = threadsCount / 2;

      for (int i = 0; i < threadsCount; i++) {
        list.add("text");
      }

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> list.add("text"))
              .times(addThreadsCount)
              .withAction(() -> list.remove("text"))
              .times(removeThreadsCount)
              .withThreadsExceptionsConsumer(exceptions::add)
              .withAwaitTerminationTimeout(1)
              .asMinutes()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      then(exceptions).isEmpty();
      then(list).hasSize(threadsCount).containsOnly("text");
    }

    @RepeatedTest(10)
    void as_hours() {
      // Given
      List<String> list = Collections.synchronizedList(new ArrayList<>());
      List<Exception> exceptions = new ArrayList<>();
      int threadsCount = Runtime.getRuntime().availableProcessors();
      int addThreadsCount = threadsCount / 2;
      int removeThreadsCount = threadsCount / 2;

      for (int i = 0; i < threadsCount; i++) {
        list.add("text");
      }

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> list.add("text"))
              .times(addThreadsCount)
              .withAction(() -> list.remove("text"))
              .times(removeThreadsCount)
              .withThreadsExceptionsConsumer(exceptions::add)
              .withAwaitTerminationTimeout(1)
              .asHours()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      then(exceptions).isEmpty();
      then(list).hasSize(threadsCount).containsOnly("text");
    }

    @RepeatedTest(10)
    void as_days() {
      // Given
      List<String> list = Collections.synchronizedList(new ArrayList<>());
      List<Exception> exceptions = new ArrayList<>();
      int threadsCount = Runtime.getRuntime().availableProcessors();
      int addThreadsCount = threadsCount / 2;
      int removeThreadsCount = threadsCount / 2;

      for (int i = 0; i < threadsCount; i++) {
        list.add("text");
      }

      // When
      try (ThreadsCollider threadsCollider =
          threadsCollider()
              .withAction(() -> list.add("text"))
              .times(addThreadsCount)
              .withAction(() -> list.remove("text"))
              .times(removeThreadsCount)
              .withThreadsExceptionsConsumer(exceptions::add)
              .withAwaitTerminationTimeout(1)
              .asDays()
              .build()) {

        threadsCollider.collide();
      }

      // Then
      then(exceptions).isEmpty();
      then(list).hasSize(threadsCount).containsOnly("text");
    }
  }
}
