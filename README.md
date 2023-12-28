## About

`Threads Collider` attempts to execute a desired action on multiple threads at the "exactly" same moment to increase the
chances of manifesting issues caused by a race condition.

## Overview

```java

@RepeatedTest(10)
void Thread_safe_adding_to_list() {
    // Given
    List<String> list = new ArrayList<>();    // <-- NOT thread safe
    int threadsCount = Runtime.getRuntime().availableProcessors();

    // When
    try (ThreadsCollider threadsCollider =
             threadsCollider().withThreadsCount(threadsCount).build()) {

        threadsCollider.collide(() -> list.add("bar")); // add "bar" to list multiple times simultaneously
    }

    // Then
    then(list).hasSize(threadsCount).containsOnly("bar");
}
```

#### Output

![img.png](png/img.png)

#### Example failure

```bash
java.lang.AssertionError: 
Expecting ArrayList:
  [null, null, null, null, null, null, "bar", "bar"]
to contain only:
  ["bar"]
but the following element(s) were unexpected:
  [null, null, null, null, null, null]
```

#### Fixed version

```java

@RepeatedTest(10)
void Thread_safe_adding_to_list() {
    // Given
    List<String> list = Collections.synchronizedList(new ArrayList<>()); // <-- thread safe
    int threadsCount = Runtime.getRuntime().availableProcessors();

    // When
    try (ThreadsCollider threadsCollider =
             threadsCollider().withThreadsCount(threadsCount).build()) {

        threadsCollider.collide(() -> list.add("bar"));
    }

    // Then
    then(list).hasSize(threadsCount).containsOnly("bar");
}
```

### Usage

#### Single action

- You can create `ThreadsCollider` using `ThreadsColliderBuilder`.
- Use `junit5` `@RepeatedTest` annotation to run test multiple times to increase chance of manifesting concurrency issues.

```java

@RepeatedTest(100)
    // run test multiple times to increase chance of manifesting concurrency issues
void Adding_unique_apples_is_thread_safe() {
    // Given
    UniqueApples uniqueApples = UniqueApples.newInstance();
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (ThreadsCollider threadsCollider =    // use try-with-resources to automatically shutdown threads collider
             threadsCollider()
                 .withAvailableProcessors()        // preferred; or withThreadsCount(CUSTOM_THREADS_COUNT)
                 .withThreadsExceptionsConsumer(exceptions::add) // optional threads exceptions consumer, default do nothing
                 .withAwaitTerminationTimeout(10)  // optional, default 60 seconds
                 .asSeconds()                      // optional - related only to "withAwaitTerminationTimeout()", default TimeUnit.SECONDS
                 .build()) {

        threadsCollider.collide(
            () -> uniqueApples.add(RED_DELICIOUS)); // <-- code to be executed simultaneously at "exactly" same moment
    }

    // Then
    then(uniqueApples).hasSize(1).containsExactlyInAnyOrder(RED_DELICIOUS);
}
```

#### Multiple actions

- When you need to execute multiple different actions simultaneously, you can use `MultiThreadsCollider` instead
  of `ThreadsCollider`.
- We have thread safe `Counter` class:

```java
public class Counter {

    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet();
    }

    public void decrement() {
        counter.decrementAndGet();
    }

    public int value() {
        return counter.get();
    }
}
```

```java
private static final int ACTION_THREADS_COUNT = Runtime.getRuntime().availableProcessors() / 2;

@RepeatedTest(10)   // run test multiple times to increase chance of manifesting concurrency issues
void Thread_safe_counter() {
    // Given
    Counter counter = new Counter();
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (MultiThreadsCollider threadsCollider =
             multiThreadsCollider()
                 .withAction(counter::increment)    // first  action to be executed simultaneously
                 .times(ACTION_THREADS_COUNT)       // set number of threads to execute first action
                 .withAction(counter::decrement)    // second action to be executed simultaneously
                 .times(ACTION_THREADS_COUNT)       // set number of threads to execute second action
                 .withThreadsExceptionsConsumer(exceptions::add)    // optional threads exceptions consumer, default do nothing
                 .build()) {

        threadsCollider.collide();
    }

    // Then
    then(counter.value()).isZero();
    then(exceptions).isEmpty();
}
```

> **_NOTE:_**  Although `withThreadsExceptionsConsumer()` method is optional, it is recommended to use it in assertions to be
> sure that no exceptions were thrown during threads execution.

#### Detailed examples:

- [ThreadsCollider_Scenarios.java](src%2Ftest%2Fjava%2Fpl%2Famazingcode%2Fthreadscollider%2FThreadsCollider_Scenarios.java)
- [UseCases_Scenarios.java](src%2Ftest%2Fjava%2Fpl%2Famazingcode%2Fthreadscollider%2FUseCases_Scenarios.java)
- [MultiThreadsCollider_Scenarios.java](src%2Ftest%2Fjava%2Fpl%2Famazingcode%2Fthreadscollider%2Fmulti%2FMultiThreadsCollider_Scenarios.java)

## Requirements

- Java 8+

## Dependencies

---

### Maven

```xml 

<dependency>
    <groupId>pl.amazingcode</groupId>
    <artifactId>threads-collider</artifactId>
    <version>1.0.2</version>
    <scope>test</scope>
</dependency>
```

### Gradle

```groovy
testImplementation group: 'pl.amazingcode', name: 'threads-collider', version: "1.0.2"
```

### TODO

- [ ] Add deadlock detection for ThreadCollider
- [ ] Update javadocs for timeout methods for ThreadsCollider
- [ ] Check negative times method arguments
- [ ] Check processors count
- [ ] Thread factory to commons
