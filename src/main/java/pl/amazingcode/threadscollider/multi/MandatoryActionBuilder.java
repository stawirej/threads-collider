package pl.amazingcode.threadscollider.multi;

/** Intermediary builder for {@link MultiThreadsCollider}. */
public interface MandatoryActionBuilder {

  /**
   * Set action to be executed.
   *
   * @param action action to be executed
   * @return {@link TimesBuilder}
   */
  TimesBuilder withAction(Runnable action);

  /**
   * Set action to be executed.
   *
   * @param action action to be executed
   * @param actionName description of action used when reporting deadlocked threads.
   * @return {@link TimesBuilder}
   */
  TimesBuilder withAction(Runnable action, String actionName);
}
