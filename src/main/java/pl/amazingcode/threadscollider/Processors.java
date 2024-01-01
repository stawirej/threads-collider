package pl.amazingcode.threadscollider;

/** Number of processors available to the Java virtual machine. */
public final class Processors {

  /** Use all available processors. */
  public static final int ALL = Runtime.getRuntime().availableProcessors();

  /** Use half of available processors. */
  public static final int HALF = ALL / 2;

  /** Use one third of available processors. */
  public static final int ONE_THIRD = ALL / 3;

  /** Use one quarter of available processors. */
  public static final int ONE_QUARTER = ALL / 4;
}
