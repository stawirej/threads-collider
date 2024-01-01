package pl.amazingcode.threadscollider;

import static pl.amazingcode.threadscollider.ThreadFactory.THREAD_FACTORY;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/** Allows to execute multiple actions by all threads at the "same time". */
public final class ThreadsCollider implements AutoCloseable {

  private static final long DEFAULT_TIMEOUT = 60;
  private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

  private final List<Action> actions;
  private final ExecutorService executor;
  private final int threadsCount;
  private final AtomicInteger startedThreadsCount;
  private final AtomicBoolean spinLock;
  private final CountDownLatch runningThreadsLatch;
  private final long timeout;
  private final TimeUnit timeUnit;
  private final Consumer<Exception> threadsExceptionsConsumer;

  private ThreadsCollider(
      List<Action> actions,
      long timeout,
      TimeUnit timeUnit,
      Consumer<Exception> threadsExceptionsConsumer) {

    this.actions = actions;
    this.threadsCount = actions.stream().mapToInt(Action::times).sum();
    this.executor = Executors.newFixedThreadPool(threadsCount, THREAD_FACTORY);
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

      for (Action action : actions) {
        for (int i = 0; i < action.times(); i++) {
          executor.execute(() -> decorate(action));
        }
      }

      while (startedThreadsCount.get() < threadsCount)
        ;

      spinLock.set(false);

      if (!runningThreadsLatch.await(timeout, timeUnit)) {
        consumeException(UnfinishedThreads.becauseTimeoutExceeded(timeout, timeUnit));
      }

    } catch (InterruptedException exception) {
      throw ThreadsColliderFailure.from(exception);
    }
  }

  private void decorate(Action action) {

    try {
      setThreadName(action.actionName());

      startedThreadsCount.incrementAndGet();

      while (startedThreadsCount.get() < threadsCount)
        ;

      while (spinLock.get())
        ;

      action.runnable().run();
    } catch (Exception exception) {
      consumeException(exception);
    } finally {
      runningThreadsLatch.countDown();
    }
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private void setThreadName(Optional<String> actionName) {

    actionName
        .map(name -> Thread.currentThread().getName() + " [" + name + "]")
        .ifPresent(Thread.currentThread()::setName);
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

  /** Builder for {@link ThreadsCollider}. */
  public static class ThreadsColliderBuilder
      implements MandatoryActionBuilder,
          OptionalActionBuilder,
          TimesBuilder,
          TimeUnitBuilder,
          OptionalBuilder {

    private final List<Action> actions;
    private Runnable runnable;
    private String actionName;
    private long timeout = DEFAULT_TIMEOUT;
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;
    private Consumer<Exception> threadsExceptionsConsumer = (exception) -> {};

    private ThreadsColliderBuilder() {

      this.actions = new ArrayList<>();
    }

    /**
     * Creates new instance of {@link ThreadsColliderBuilder}.
     *
     * @return {@link ThreadsColliderBuilder}
     */
    public static MandatoryActionBuilder threadsCollider() {

      return new ThreadsColliderBuilder();
    }

    @Override
    public TimesBuilder withAction(Runnable runnable) {

      this.runnable = runnable;
      this.actionName = null;
      return this;
    }

    @Override
    public TimesBuilder withAction(Runnable runnable, String actionName) {

      this.runnable = runnable;
      this.actionName = actionName;
      return this;
    }

    @Override
    public ThreadsColliderBuilder times(int times) {

      Action action = Action.of(runnable, actionName, times);
      this.actions.add(action);
      return this;
    }

    @Override
    public TimeUnitBuilder withAwaitTerminationTimeout(long timeout) {

      this.timeout = timeout;
      return this;
    }

    @Override
    public OptionalBuilder withThreadsExceptionsConsumer(
        Consumer<Exception> threadsExceptionsConsumer) {

      this.threadsExceptionsConsumer = threadsExceptionsConsumer;
      return this;
    }

    @Override
    public OptionalBuilder asNanoseconds() {

      this.timeUnit = TimeUnit.NANOSECONDS;
      return this;
    }

    @Override
    public ThreadsColliderBuilder asMicroseconds() {

      this.timeUnit = TimeUnit.MICROSECONDS;
      return this;
    }

    @Override
    public ThreadsColliderBuilder asMilliseconds() {

      this.timeUnit = TimeUnit.MILLISECONDS;
      return this;
    }

    @Override
    public ThreadsColliderBuilder asSeconds() {

      this.timeUnit = TimeUnit.SECONDS;
      return this;
    }

    @Override
    public ThreadsColliderBuilder asMinutes() {

      this.timeUnit = TimeUnit.MINUTES;
      return this;
    }

    @Override
    public ThreadsColliderBuilder asHours() {

      this.timeUnit = TimeUnit.HOURS;
      return this;
    }

    @Override
    public ThreadsColliderBuilder asDays() {

      this.timeUnit = TimeUnit.DAYS;
      return this;
    }

    @Override
    public ThreadsCollider build() {

      return new ThreadsCollider(actions, timeout, timeUnit, threadsExceptionsConsumer);
    }
  }
}
