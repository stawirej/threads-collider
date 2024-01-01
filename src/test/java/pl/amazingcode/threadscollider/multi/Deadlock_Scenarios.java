package pl.amazingcode.threadscollider.multi;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.multi.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.condition.EnabledIf;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Deadlock_Scenarios {

  private static final int ACTION_THREADS_COUNT = Runtime.getRuntime().availableProcessors() / 2;
  private List<Integer> list1;
  private List<Integer> list2;
  private Lock lock1;
  private Lock lock2;

  private static boolean enoughProcessorCores() {

    return Runtime.getRuntime().availableProcessors() >= 8;
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

  private void update1WithLocks(List<Integer> list1, List<Integer> list2) {

    lock1.lock();
    try {
      list1.add(1);
      lock2.lock();
      try {
        list2.add(1);
      } finally {
        lock2.unlock();
      }
    } finally {
      lock1.unlock();
    }
  }

  private void update2WithLocks(List<Integer> list2, List<Integer> list1) {

    lock2.lock();
    try {
      list2.add(1);
      lock1.lock();
      try {
        list1.add(1);
      } finally {
        lock1.unlock();
      }
    } finally {
      lock2.unlock();
    }
  }

  @BeforeEach
  void setUp() {
    list1 = new ArrayList<>();
    list2 = new ArrayList<>();
    lock1 = new ReentrantLock();
    lock2 = new ReentrantLock();
  }

  @Disabled
  @RepeatedTest(10)
  @EnabledIf("enoughProcessorCores")
  void Detect_deadlock() {
    // Given
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (ThreadsCollider collider =
        threadsCollider()
            .withAction(() -> update1(list1, list2), "update1")
            .times(ACTION_THREADS_COUNT)
            .withAction(() -> update2(list2, list1), "update2")
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

  @Disabled
  @RepeatedTest(10)
  @EnabledIf("enoughProcessorCores")
  void Detect_deadlock_with_locks() {
    // Given
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (ThreadsCollider collider =
        threadsCollider()
            .withAction(() -> update1WithLocks(list1, list2), "update1WithLocks")
            .times(ACTION_THREADS_COUNT)
            .withAction(() -> update2WithLocks(list2, list1), "update2WithLocks")
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
