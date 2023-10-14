package pl.amazingcode.threadscollider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/** Allows to execute given code by all threads at the "same time". */
public final class ThreadsCollider implements AutoCloseable {

  private static final long DEFAULT_TIMEOUT = 60;
  private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
  private final ExecutorService executor;
  private final long threadsCount;
  private final AtomicLong startedThreadsCount = new AtomicLong(0);
  private final AtomicBoolean spinLock;
  private final long timeout;
  private final TimeUnit timeUnit;

  private ThreadsCollider(int threadsCount, long timeout, TimeUnit timeUnit) {

    this.executor = Executors.newFixedThreadPool(threadsCount);
    this.spinLock = new AtomicBoolean(true);
    this.threadsCount = threadsCount;
    this.timeout = timeout;
    this.timeUnit = timeUnit;
  }

  /**
   * Tries to execute given code by all threads at the "same time".
   *
   * @param runnable - code to be executed by each thread.
   */
  public void collide(Runnable runnable) {

    for (long i = 0; i < threadsCount; i++) {
      executor.execute(() -> decorate(runnable));
    }

    while (startedThreadsCount.get() != threadsCount)
      ;
    spinLock.set(false);
  }

  private void decorate(Runnable runnable) {

    startedThreadsCount.incrementAndGet();

    while (startedThreadsCount.get() != threadsCount) {
      while (spinLock.get())
        ;
      runnable.run();
    }
  }

  /** Shuts down the executor service and waits for all threads to finish by given timeout. */
  @Override
  public void close() {
    try {
      executor.shutdownNow();
      executor.awaitTermination(timeout, timeUnit);
    } catch (Exception e) {
      // Intentionally left blank
    }
  }

  /** Builder for {@link ThreadsCollider}. */
  public static class ThreadsColliderBuilder
      implements ThreadsCountBuilder, TimeoutBuilder, TimeUnitBuilder, Builder {

    private int threadsCount;
    private long timeout = DEFAULT_TIMEOUT;
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;

    private ThreadsColliderBuilder() {}

    /**
     * Creates new instance of {@link ThreadsColliderBuilder} with default values.
     *
     * @return new instance of {@link ThreadsColliderBuilder}
     */
    public static ThreadsCountBuilder threadsCollider() {

      return new ThreadsColliderBuilder();
    }

    public TimeoutBuilder withThreadsCount(int threadsCount) {

      this.threadsCount = threadsCount;
      return this;
    }

    public TimeoutBuilder withAvailableProcessors() {

      this.threadsCount = Runtime.getRuntime().availableProcessors();
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

      return new ThreadsCollider(threadsCount, timeout, timeUnit);
    }
  }
}
