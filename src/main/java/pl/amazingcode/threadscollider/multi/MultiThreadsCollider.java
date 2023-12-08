package pl.amazingcode.threadscollider.multi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import pl.amazingcode.threadscollider.exceptions.ThreadsColliderFailure;

/** Allows to execute multiple actions by all threads at the "same time". */
public final class MultiThreadsCollider implements AutoCloseable {

  private static final long DEFAULT_TIMEOUT = 60;
  private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
  private final List<Runnable> runnables;
  private final List<Integer> times;
  private final ExecutorService executor;
  private final int threadsCount;
  private final AtomicInteger startedThreadsCount;
  private final AtomicBoolean spinLock;
  private final CountDownLatch runningThreadsLatch;
  private final long timeout;
  private final TimeUnit timeUnit;
  private final Consumer<Exception> threadsExceptionsConsumer;

  private MultiThreadsCollider(
      List<Runnable> runnables,
      List<Integer> times,
      int threadsCount,
      long timeout,
      TimeUnit timeUnit,
      Consumer<Exception> threadsExceptionsConsumer) {

    this.runnables = runnables;
    this.times = times;
    this.threadsCount = threadsCount;
    this.executor = Executors.newFixedThreadPool(threadsCount);
    this.spinLock = new AtomicBoolean(true);
    this.startedThreadsCount = new AtomicInteger(0);
    this.runningThreadsLatch = new CountDownLatch(threadsCount);
    this.timeout = timeout;
    this.timeUnit = timeUnit;
    this.threadsExceptionsConsumer = threadsExceptionsConsumer;
  }

  /**
   * Tries to execute multiple actions by all threads at the "same time".
   *
   * @throws ThreadsColliderFailure if any exception occurs during execution. This not includes
   *     exceptions thrown by threads.
   */
  public void collide() {

    try {
      Iterator<Runnable> runnableIterator = runnables.iterator();
      Iterator<Integer> timesIterator = times.iterator();

      while (runnableIterator.hasNext()) {
        Runnable runnable = runnableIterator.next();
        int times = timesIterator.next();
        for (int i = 0; i < times; i++) {
          executor.execute(() -> decorate(runnable));
        }
      }

      while (startedThreadsCount.get() < threadsCount)
        ;

      spinLock.set(false);
      runningThreadsLatch.await();
    } catch (InterruptedException exception) {
      throw ThreadsColliderFailure.from(exception);
    }
  }

  private void decorate(Runnable runnable) {

    try {
      startedThreadsCount.incrementAndGet();

      while (startedThreadsCount.get() < threadsCount)
        ;

      while (spinLock.get())
        ;

      runnable.run();
    } catch (Exception exception) {
      consumeException(exception);
    } finally {
      runningThreadsLatch.countDown();
    }
  }

  /** Shuts down the executor service and waits for all threads to finish by given timeout. */
  @Override
  public void close() {
    try {
      executor.shutdown();
      if (!executor.awaitTermination(timeout, timeUnit)) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
    }
  }

  private synchronized void consumeException(Exception exception) {

    threadsExceptionsConsumer.accept(exception);
  }

  /** Builder for {@link MultiThreadsCollider}. */
  public static class MultiThreadsColliderBuilder
      implements MandatoryActionBuilder,
          OptionalActionBuilder,
          TimesBuilder,
          MultiTimeUnitBuilder,
          MultiOptionalBuilder {

    private final List<Runnable> runnables;
    private final List<Integer> times;
    private long timeout = DEFAULT_TIMEOUT;
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;
    private Consumer<Exception> threadsExceptionsConsumer = (exception) -> {};

    private MultiThreadsColliderBuilder() {

      this.runnables = new ArrayList<>();
      this.times = new ArrayList<>();
    }

    public static MandatoryActionBuilder multiThreadsCollider() {

      return new MultiThreadsColliderBuilder();
    }

    @Override
    public TimesBuilder withAction(Runnable runnable) {

      this.runnables.add(runnable);
      return this;
    }

    @Override
    public MultiThreadsColliderBuilder times(int times) {

      this.times.add(times);
      return this;
    }

    @Override
    public MultiTimeUnitBuilder withAwaitTerminationTimeout(long timeout) {

      this.timeout = timeout;
      return this;
    }

    @Override
    public MultiOptionalBuilder withThreadsExceptionsConsumer(
        Consumer<Exception> threadsExceptionsConsumer) {

      this.threadsExceptionsConsumer = threadsExceptionsConsumer;
      return this;
    }

    @Override
    public MultiOptionalBuilder asNanoseconds() {

      this.timeUnit = TimeUnit.NANOSECONDS;
      return this;
    }

    @Override
    public MultiThreadsColliderBuilder asMicroseconds() {

      this.timeUnit = TimeUnit.MICROSECONDS;
      return this;
    }

    @Override
    public MultiThreadsColliderBuilder asMilliseconds() {

      this.timeUnit = TimeUnit.MILLISECONDS;
      return this;
    }

    @Override
    public MultiThreadsColliderBuilder asSeconds() {

      this.timeUnit = TimeUnit.SECONDS;
      return this;
    }

    @Override
    public MultiThreadsColliderBuilder asMinutes() {

      this.timeUnit = TimeUnit.MINUTES;
      return this;
    }

    @Override
    public MultiThreadsColliderBuilder asHours() {

      this.timeUnit = TimeUnit.HOURS;
      return this;
    }

    @Override
    public MultiThreadsColliderBuilder asDays() {

      this.timeUnit = TimeUnit.DAYS;
      return this;
    }

    @Override
    public MultiThreadsCollider build() {

      int threadsCount = times.stream().mapToInt(Integer::intValue).sum();

      return new MultiThreadsCollider(
          runnables, times, threadsCount, timeout, timeUnit, threadsExceptionsConsumer);
    }
  }
}
