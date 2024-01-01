package pl.amazingcode.threadscollider;

final class ThreadFactory {

  static final java.util.concurrent.ThreadFactory THREAD_FACTORY =
      runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setName("collider-pool-" + thread.getName().toLowerCase());
        return thread;
      };
}
