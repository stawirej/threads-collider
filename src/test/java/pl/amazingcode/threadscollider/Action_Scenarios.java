package pl.amazingcode.threadscollider;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Action_Scenarios {

  @Test
  void Report_error_on_times_smaller_than_1() {

    // When
    Throwable throwable = catchThrowable(() -> Action.of(() -> {}, "action", 0));

    // Then
    then(throwable)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Repeat action at least once.");
  }
}
