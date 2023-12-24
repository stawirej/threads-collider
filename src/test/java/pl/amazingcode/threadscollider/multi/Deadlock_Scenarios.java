package pl.amazingcode.threadscollider.multi;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.multi.MultiThreadsCollider.MultiThreadsColliderBuilder.multiThreadsCollider;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Deadlock_Scenarios {

  private static final int ACTION_THREADS_COUNT = Runtime.getRuntime().availableProcessors() / 2;

  private static String dump;

  private static String threadDump(boolean lockedMonitors, boolean lockedSynchronizers) {
    StringBuffer threadDump = new StringBuffer(System.lineSeparator());
    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    for (long threadId : threadMXBean.findDeadlockedThreads()) {
      ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
      if (threadInfo != null) {
        threadDump.append(threadInfo);
        threadDump.append(System.lineSeparator());
        if (lockedMonitors) {
          for (MonitorInfo monitorInfo : threadInfo.getLockedMonitors()) {
            threadDump.append(monitorInfo.toString());
            threadDump.append(System.lineSeparator());
          }
        }
        if (lockedSynchronizers) {
          for (LockInfo lockInfo : threadInfo.getLockedSynchronizers()) {
            threadDump.append(lockInfo.toString());
            threadDump.append(System.lineSeparator());
          }
        }
      }
    }
    return threadDump.toString();
  }

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

    //    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    //    executor.schedule(
    //        () -> {
    //          dump = threadDump(true, true);
    //          System.out.println(dump);
    //        },
    //        1,
    //        TimeUnit.SECONDS);

    // When
    //    assertTimeoutPreemptively(
    //        Duration.ofSeconds(3),
    //        () -> {
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
    //        });

    // Then
    exceptions.forEach(e -> System.out.println(e.toString()));
    //    then(list1).hasSize(ACTION_THREADS_COUNT);
    //    then(list2).hasSize(ACTION_THREADS_COUNT);
    then(exceptions).isEmpty();
  }
}
