package pl.amazingcode.threadscollider;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Action_Scenarios {

  @ParameterizedTest
  @CsvSource({"0", "-1"})
  void Report_error_on_invalid_action_repetition_count(int times) {

    // When
    Throwable throwable = catchThrowable(() -> Action.of(() -> {}, "action", times));

    // Then
    then(throwable)
        .isInstanceOf(InvalidActionRepetitionCount.class)
        .hasMessage("Action has to be repeated at least once, but was %d times.", times);
  }

  @Test
  void Report_error_on_null_runnable() {
    // When
    Throwable throwable = catchThrowable(() -> Action.of(null, "action", 1));

    // Then
    then(throwable)
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Action runnable cannot be null.");
  }
}
