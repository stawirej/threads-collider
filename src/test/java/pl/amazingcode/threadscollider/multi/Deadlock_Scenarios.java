package pl.amazingcode.threadscollider.multi;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.multi.MultiThreadsCollider.MultiThreadsColliderBuilder.multiThreadsCollider;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Deadlock_Scenarios {

  private static final int ACTION_THREADS_COUNT = Runtime.getRuntime().availableProcessors() / 2;
  private List<Integer> list1;
  private List<Integer> list2;

  private void update1(List<Integer> list1, List<Integer> list2) {

    synchronized (list1) {
      list1.add(1);
      synchronized (list2) {
        list2.add(1);
      }
    }
  }

  private void update2(List<Integer> list2, List<Integer> list1) {

    synchronized (list2) {
      list2.add(1);
      synchronized (list1) {
        list1.add(1);
      }
    }
  }

  @BeforeEach
  void setUp() {
    list1 = new ArrayList<>();
    list2 = new ArrayList<>();
  }

  @RepeatedTest(10)
  void Detect_deadlock() {
    // Given
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (MultiThreadsCollider collider =
        multiThreadsCollider()
            .withAction(() -> update1(list1, list2))
            .times(ACTION_THREADS_COUNT)
            .withAction(() -> update2(list2, list1))
            .times(ACTION_THREADS_COUNT)
            .withThreadsExceptionsConsumer(exceptions::add)
            .withAwaitTerminationTimeout(100)
            .asMilliseconds()
            .build()) {

      collider.collide();
    }

    // Then
    then(exceptions).isEmpty();
  }
}
