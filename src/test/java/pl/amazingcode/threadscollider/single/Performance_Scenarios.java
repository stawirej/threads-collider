package pl.amazingcode.threadscollider.single;

import static java.lang.System.nanoTime;
import static java.util.stream.Collectors.toList;
import static pl.amazingcode.threadscollider.ThreadsCollider.ThreadsColliderBuilder.threadsCollider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import pl.amazingcode.threadscollider.Processors;
import pl.amazingcode.threadscollider.ThreadsCollider;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class Performance_Stats_Scenarios {

  @RepeatedTest(100)
  void Threads_start_times_diff() {
    // Given
    List<Long> startTimes = Collections.synchronizedList(new ArrayList<>());
    Runnable runnable =
        () -> {
          long startTime = nanoTime();
          startTimes.add(startTime);
        };

    // When
    try (ThreadsCollider threadsCollider =
        threadsCollider()
            .withAction(runnable)
            .times(Processors.HALF)
            .withAwaitTerminationTimeout(100)
            .asMilliseconds()
            .build()) {

      threadsCollider.collide();
    }

    // Then
    List<Long> sortedStartTimes = startTimes.stream().sorted().collect(toList());
    //    for (int i = 0; i < sortedStartTimes.size() - 1; i++) {
    //        long diff = sortedStartTimes.get(i + 1) - sortedStartTimes.get(i);
    //        long diffMicroseconds = diff / 1000;
    //        System.out.println("Threads start times diff: " + diff + " nanoseconds");
    //        System.out.println("Threads start times diff: " + diffMicroseconds + " microseconds");
    //    }

    long diff =
        (sortedStartTimes.get(sortedStartTimes.size() - 1) - sortedStartTimes.get(0)) / 1000;
    if (diff == 0) {
      long diffNs = sortedStartTimes.get(sortedStartTimes.size() - 1) - sortedStartTimes.get(0);
      System.out.println("Threads start times diff: " + diffNs + " nanoseconds");
    } else {
      System.out.println("Threads start times diff: " + diff + " microseconds");
    }
  }
}
