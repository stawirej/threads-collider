package pl.amazingcode.threadscollider.multi;

import java.util.Optional;

class Action {

  private final Runnable runnable;

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private final Optional<String> actionName;

  private Action(Runnable runnable, Optional<String> actionName) {

    this.runnable = runnable;
    this.actionName = actionName;
  }

  static Action of(Runnable runnable, String actionName) {

    return new Action(runnable, Optional.ofNullable(actionName));
  }

  static Action of(Runnable runnable) {

    return new Action(runnable, Optional.empty());
  }

  Runnable runnable() {

    return runnable;
  }

  Optional<String> actionName() {

    return actionName;
  }
}
