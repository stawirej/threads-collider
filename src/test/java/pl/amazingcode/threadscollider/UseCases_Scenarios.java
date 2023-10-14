package pl.amazingcode.threadscollider;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class UseCases_Scenarios {

  @RepeatedTest(10)
  void Synchronize_adding_to_set() {
    // Given
    Set<String> set = Collections.synchronizedSet(new HashSet<>());

    // When
    try (ThreadsCollider threadsCollider = threadsCollider().withAvailableProcessors().build()) {
      threadsCollider.collide(() -> set.add("foo"));
    }

    // Then
    then(set).hasSize(1).containsExactly("foo");
  }

  @RepeatedTest(10)
  void Synchronize_adding_to_list() {
    // Given
    List<String> list = Collections.synchronizedList(new ArrayList<>());
    int threadsCount = Runtime.getRuntime().availableProcessors();

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider().withThreadsCount(threadsCount).build()) {
      threadsCollider.collide(() -> list.add("bar"));
    }

    // Then
    then(list).hasSize(threadsCount).containsOnly("bar");
  }
}
