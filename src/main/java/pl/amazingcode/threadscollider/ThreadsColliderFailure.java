package pl.amazingcode.threadscollider;

/** Exception thrown when {@link ThreadsCollider} or {@link ThreadsCollider} fails. */
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
