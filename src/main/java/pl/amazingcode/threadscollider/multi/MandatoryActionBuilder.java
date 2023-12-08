package pl.amazingcode.threadscollider.multi;

/** Builder for mandatory action. */
public interface MandatoryActionBuilder {

  /**
   * Set action to be executed.
   *
   * @param action action to be executed
   * @return builder
   */
  TimesBuilder withAction(Runnable action);
}
