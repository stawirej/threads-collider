package pl.amazingcode.threadscollider.exceptions;

/**
 * Exception thrown when {@link pl.amazingcode.threadscollider.single.ThreadsCollider} or {@link
 * pl.amazingcode.threadscollider.multi.MultiThreadsCollider} fails.
 */
public final class ThreadsColliderFailure extends RuntimeException {

  private ThreadsColliderFailure(Throwable cause) {

    super(cause);
  }

  /**
   * Creates new instance of {@link ThreadsColliderFailure}.
   *
   * @param cause cause of failure
   * @return new instance of {@link ThreadsColliderFailure}
   */
  public static ThreadsColliderFailure from(Throwable cause) {

    return new ThreadsColliderFailure(cause);
  }
}
