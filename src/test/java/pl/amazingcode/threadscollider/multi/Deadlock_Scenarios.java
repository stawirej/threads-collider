package pl.amazingcode.threadscollider.multi;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.multi.MultiThreadsCollider.MultiThreadsColliderBuilder.multiThreadsCollider;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Deadlock_Scenarios {

  private static final int ACTION_THREADS_COUNT = Runtime.getRuntime().availableProcessors() / 2;

  @RepeatedTest(1)
  void Detect_deadlock() {
    // Given
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    List<Exception> exceptions = new ArrayList<>();

    Runnable r1 =
        () -> {
          synchronized (list1) {
            list1.add(1);
            synchronized (list2) {
              list2.add(1);
            }
          }
        };

    Runnable r2 =
        () -> {
          synchronized (list2) {
            list2.add(1);
            synchronized (list1) {
              list1.add(1);
            }
          }
        };

    try (MultiThreadsCollider collider =
        multiThreadsCollider()
            .withAction(r1)
            .times(ACTION_THREADS_COUNT)
            .withAction(r2)
            .times(ACTION_THREADS_COUNT)
            .withThreadsExceptionsConsumer(exceptions::add)
            .withAwaitTerminationTimeout(1)
            .asSeconds()
            .build()) {

      collider.collide();
    }

    // Then
    then(exceptions).isEmpty();
  }
}
