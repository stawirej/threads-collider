package pl.amazingcode.threadscollider;

final class ThreadsColliderFailure extends RuntimeException {

  private ThreadsColliderFailure(Throwable cause) {

    super(cause);
  }

  public static ThreadsColliderFailure from(Throwable cause) {

    return new ThreadsColliderFailure(cause);
  }
}