package pl.amazingcode.threadscollider;

import java.util.Optional;

class Action {

  private final Runnable runnable;

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private final Optional<String> actionName;

  private final int times;

  private Action(Runnable runnable, Optional<String> actionName, int times) {

    this.runnable = runnable;
    this.actionName = actionName;
    this.times = times;
  }

  static Action of(Runnable runnable, String actionName, int times) {

    return new Action(runnable, Optional.ofNullable(actionName), times);
  }

  Runnable runnable() {

    return runnable;
  }

  Optional<String> actionName() {

    return actionName;
  }

  int times() {

    return times;
  }
}
