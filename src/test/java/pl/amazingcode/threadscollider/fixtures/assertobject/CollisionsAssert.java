package pl.amazingcode.threadscollider.fixtures.assertobject;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CollisionsAssert {

  private final Map<String, Integer> testFailures;
  private final Map<String, Integer> testRuns;
  private final int testRepetitions;

  private CollisionsAssert(int testRepetitions) {

    this.testFailures = new ConcurrentHashMap<>();
    this.testRuns = new ConcurrentHashMap<>();
    this.testRepetitions = testRepetitions;
  }

  public static CollisionsAssert newInstance(int testRepetitions) {

    return new CollisionsAssert(testRepetitions);
  }

  public void assertTrue(String testName, boolean condition) {

    testRuns.putIfAbsent(testName, 0);
    testFailures.putIfAbsent(testName, 0);

    testRuns.compute(testName, (key, runsCount) -> runsCount + 1);
    testFailures.compute(
        testName, (key, failuresCount) -> condition ? failuresCount + 1 : failuresCount);

    assumeTrue(testRuns.get(testName) == testRepetitions);
    then(testFailures.get(testName)).as("Expect to detect concurrency issues").isGreaterThan(0);
  }

  public void report() {

    System.out.println("RAPORT");
    System.out.println("TEST REPETITIONS : " + testRepetitions);
    System.out.println("[test name : failures count]:");
    testFailures.forEach(
        (testName, failuresCount) -> {
          System.out.println(testName + " : " + failuresCount);
        });
  }
}
