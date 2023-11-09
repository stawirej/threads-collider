package pl.amazingcode.threadscollider;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.amazingcode.threadscollider.MultiThreadsCollider.MultiThreadsColliderBuilder.multiThreadsCollider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class MultiThreadsCollider_Scenarios {

  @RepeatedTest(10)
  void Build_multi_threads_collider_list() {
    // Given
    List<String> list = Collections.synchronizedList(new ArrayList<>());
    List<Exception> exceptions = new ArrayList<>();
    int threadsCount = Runtime.getRuntime().availableProcessors();
    int addThreadsCount = threadsCount / 2;
    int removeThreadsCount = threadsCount / 2;

    for (int i = 0; i < threadsCount; i++) {
      list.add("text");
    }

    // When
    try (MultiThreadsCollider threadsCollider =
        multiThreadsCollider()
            .withAction(() -> list.add("text"))
            .times(addThreadsCount)
            .withAction(() -> list.remove("text"))
            .times(removeThreadsCount)
            .withThreadsExceptionsConsumer(exceptions::add)
            .build()) {

      threadsCollider.collide();
    }

    // Then
    then(exceptions).isEmpty();
    then(list).hasSize(threadsCount).containsOnly("text");
  }
}
