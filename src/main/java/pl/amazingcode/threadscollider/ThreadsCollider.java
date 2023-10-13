package pl.amazingcode.threadscollider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadsCollider implements AutoCloseable {

  private static final long DEFAULT_TIMEOUT = 60;
  private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
  private final ExecutorService executor;
  private final long threadCount;
  private final AtomicLong startedThreadsCount = new AtomicLong(0);
  private final AtomicBoolean spinLock;
  private final long timeout;
  private final TimeUnit timeUnit;

  private ThreadsCollider(int threadCount, long timeout, TimeUnit timeUnit) {

    this.executor = Executors.newFixedThreadPool(threadCount);
    this.spinLock = new AtomicBoolean(true);
    this.threadCount = threadCount;
    this.timeout = timeout;
    this.timeUnit = timeUnit;
  }

  public void collide(Runnable runnable) {

    for (var i = 0; i < threadCount; i++) {
      executor.execute(() -> decorate(runnable));
    }

    while (startedThreadsCount.get() != threadCount)
      ;
    spinLock.set(false);
  }

  private void decorate(Runnable runnable) {

    startedThreadsCount.incrementAndGet();

    while (startedThreadsCount.get() != threadCount) {
      while (spinLock.get())
        ;
      runnable.run();
    }
  }

  @Override
  public void close() {
    try {
      executor.shutdownNow();
      executor.awaitTermination(timeout, timeUnit);
    } catch (Exception e) {
      // Intentionally left blank
    }
  }

  public static class ThreadsColliderBuilder
      implements ThreadsCountBuilder, TimeoutBuilder, TimeUnitBuilder, Builder {

    private int threadCount;
    private long timeout = DEFAULT_TIMEOUT;
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;

    public static ThreadsCountBuilder threadsCollider() {

      return new ThreadsColliderBuilder();
    }

    public TimeoutBuilder withThreadsCount(int threadCount) {

      this.threadCount = threadCount;
      return this;
    }

    public TimeoutBuilder withAvailableProcessors() {

      this.threadCount = Runtime.getRuntime().availableProcessors();
      return this;
    }

    public TimeUnitBuilder withAwaitTerminationTimeout(long timeout) {

      this.timeout = timeout;
      return this;
    }

    @Override
    public Builder asNanoseconds() {

      this.timeUnit = TimeUnit.NANOSECONDS;
      return this;
    }

    @Override
    public Builder asMicroseconds() {

      this.timeUnit = TimeUnit.MICROSECONDS;
      return this;
    }

    @Override
    public Builder asMilliseconds() {

      this.timeUnit = TimeUnit.MILLISECONDS;
      return this;
    }

    @Override
    public Builder asSeconds() {

      this.timeUnit = TimeUnit.SECONDS;
      return this;
    }

    @Override
    public Builder asMinutes() {

      this.timeUnit = TimeUnit.MINUTES;
      return this;
    }

    @Override
    public Builder asHours() {

      this.timeUnit = TimeUnit.HOURS;
      return this;
    }

    @Override
    public Builder asDays() {

      this.timeUnit = TimeUnit.DAYS;
      return this;
    }

    public ThreadsCollider build() {

      return new ThreadsCollider(threadCount, timeout, timeUnit);
    }
  }
}
