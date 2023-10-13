package pl.amazingcode.threadscollider;

public interface TimeoutBuilder {

  TimeUnitBuilder withAwaitTerminationTimeout(long timeout);

  ThreadsCollider build();
}
