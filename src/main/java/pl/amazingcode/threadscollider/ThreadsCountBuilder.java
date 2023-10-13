package pl.amazingcode.threadscollider;

public interface ThreadsCountBuilder {

  TimeoutBuilder withThreadsCount(int threadCount);

  TimeoutBuilder withAvailableProcessors();
}
