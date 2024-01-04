## About

`Threads Collider` attempts to execute a desired action on multiple threads at the "exactly" same moment to increase the
chances of manifesting issues caused by a race condition.

## Overview

```java

@RepeatedTest(10)
void Thread_safe_adding_to_list() {
    // Given
    List<String> list = new ArrayList<>();    // <-- NOT thread safe

    // When
    try (ThreadsCollider threadsCollider =
             threadsCollider()
                 .withAction(() -> list.add("bar"))
                 .times(Processors.ALL)   // run action on all available processors
                 .build()) {

        threadsCollider.collide();
    }

    // Then
    then(list).hasSize(Processors.ALL).containsOnly("bar");
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
    List<String> list = Collections.synchronizedList(new ArrayList<>());  // <-- thread safe

    // When
    try (ThreadsCollider threadsCollider =
             threadsCollider()
                 .withAction(() -> list.add("bar"))
                 .times(Processors.ALL)   // run action on all available processors
                 .build()) {

        threadsCollider.collide();
    }

    // Then
    then(list).hasSize(Processors.ALL).containsOnly("bar");
}
```

### Usage

#### Single action

- You can create `ThreadsCollider` using `ThreadsColliderBuilder`.
- Use `junit5` `@RepeatedTest` annotation to run test multiple times to increase chance of manifesting concurrency issues.

```java

@RepeatedTest(10)
    // run test multiple times to increase chance of manifesting concurrency issues
void Thread_safe_adding_to_set() {
    // Given
    Set<String> set = Collections.synchronizedSet(new HashSet<>());
    List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());

    // When
    try (ThreadsCollider threadsCollider =    // use try-with-resources to automatically shutdown threads collider
             threadsCollider()
                 .withAction(() -> set.add("foo"))    // action to be executed simultaneously
                 .times(Processors.ALL)   // run action on all available processors
                 .withThreadsExceptionsConsumer(exceptions::add)  // save threads exceptions, default consumer do nothing
                 .withAwaitTerminationTimeout(
                     100)    // threads collider will wait 100 milliseconds for threads to finish, default 60 seconds
                 .asMilliseconds()
                 .build()) {  // build threads collider

        threadsCollider.collide();  // code to be executed simultaneously at "exactly" same moment
    }

    // Then
    then(set).hasSize(1).containsExactly("foo");
}
```

#### Multiple actions

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

> **_NOTE:_** Change `counter` field to `int` to manifest concurrency issues.

```java

@RepeatedTest(10)
    // run test multiple times to increase chance of manifesting concurrency issues
void Thread_safe_counter() {
    // Given
    Counter counter = new Counter();
    List<Exception> exceptions = new ArrayList<>();

    // When
    try (ThreadsCollider threadsCollider =
             threadsCollider()
                 .withAction(counter::increment)    // first  action to be executed simultaneously
                 .times(Processors.HALF)            // run on half of available processors
                 .withAction(counter::decrement)    // second action to be executed simultaneously
                 .times(Processors.HALF)            // run on half of available processors
                 .withThreadsExceptionsConsumer(exceptions::add)    // save threads exceptions
                 .build()) {

        threadsCollider.collide();
    }

    // Then
    then(counter.value()).isZero();   // counter should be zero after all threads finish
    then(exceptions).isEmpty();       // no exceptions should be thrown during threads execution
}
```

> **_NOTE:_**  It is recommended to set threads exceptions consumer using `withThreadsExceptionsConsumer()` method to be sure
> that no exceptions were thrown during threads execution.

#### Detailed examples:

- Single action
    - [UseCases_Scenarios.java](src%2Ftest%2Fjava%2Fpl%2Famazingcode%2Fthreadscollider%2Fsingle%2FUseCases_Scenarios.java)
    - [ThreadsCollider_Scenarios.java](src%2Ftest%2Fjava%2Fpl%2Famazingcode%2Fthreadscollider%2Fsingle%2FThreadsCollider_Scenarios.java)

- Multiple actions
    - [UseCases_Scenarios.java](src%2Ftest%2Fjava%2Fpl%2Famazingcode%2Fthreadscollider%2Fmulti%2FUseCases_Scenarios.java)
    - [ThreadsCollider_Scenarios.java](src%2Ftest%2Fjava%2Fpl%2Famazingcode%2Fthreadscollider%2Fmulti%2FThreadsCollider_Scenarios.java)

- Deadlocks
    - [Deadlock_Scenarios.java](src%2Ftest%2Fjava%2Fpl%2Famazingcode%2Fthreadscollider%2Fmulti%2FDeadlock_Scenarios.java)

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
