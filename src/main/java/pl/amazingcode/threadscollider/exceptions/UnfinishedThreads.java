package pl.amazingcode.threadscollider.exceptions;

import static java.lang.String.format;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

public final class UnfinishedThreads extends RuntimeException {

  private static final String MESSAGE =
      System.lineSeparator()
          + "There are threads that have not completed within the specified timeout: %s %s"
          + System.lineSeparator()
          + "Check if there are any deadlocks and fix them. "
          + System.lineSeparator()
          + "If there are no deadlocks, increase timeout."
          + System.lineSeparator()
          + "Deadlocked threads: [%s]";

  private UnfinishedThreads(String message) {
    super(message);
  }

  public static UnfinishedThreads becauseTimeoutExceeded(long timeout, TimeUnit timeUnit) {

    return new UnfinishedThreads(format(MESSAGE, timeout, timeUnit, threadDump()));
  }

  private static String threadDump() {

    StringBuilder threadDump = new StringBuilder(System.lineSeparator());
    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    for (long threadId : threadMXBean.findDeadlockedThreads()) {
      ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);

      if (threadInfo != null) {
        threadDump.append(threadInfo);

        for (MonitorInfo monitorInfo : threadInfo.getLockedMonitors()) {
          threadDump.append(monitorInfo);
        }

        for (LockInfo lockInfo : threadInfo.getLockedSynchronizers()) {
          threadDump.append(lockInfo);
        }
      }
    }
    return threadDump.toString();
  }
}
