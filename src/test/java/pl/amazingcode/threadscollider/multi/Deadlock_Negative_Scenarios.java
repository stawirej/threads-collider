package pl.amazingcode.threadscollider.multi;

import static pl.amazingcode.threadscollider.multi.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import pl.amazingcode.threadscollider.fixtures.assertobject.CollisionsAssert;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Deadlock_Negative_Scenarios {

  private static final int ACTION_THREADS_COUNT = Runtime.getRuntime().availableProcessors() / 2;
  private static final int TEST_REPETITIONS = 10;
  private static final CollisionsAssert collisionsAssert =
      CollisionsAssert.newInstance(TEST_REPETITIONS);
  private List<Integer> list1;
  private List<Integer> list2;

  @AfterAll
  static void afterAll() {

    collisionsAssert.report();
  }

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

  @RepeatedTest(TEST_REPETITIONS)
  void Detect_deadlock(TestInfo testInfo) {
    // Given
    String testName = testInfo.getTestMethod().get().getName();
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (ThreadsCollider collider =
        threadsCollider()
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
    boolean containsDeadlock =
        !exceptions.isEmpty() && exceptions.get(0).getMessage().contains("BLOCKED");
    collisionsAssert.assertTrue(testName, containsDeadlock);
  }
}
