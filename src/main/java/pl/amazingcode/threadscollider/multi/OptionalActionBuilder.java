package pl.amazingcode.threadscollider.multi;

import java.util.function.Consumer;

public interface OptionalActionBuilder {

  TimesBuilder withAction(Runnable action);

  MultiTimeUnitBuilder withAwaitTerminationTimeout(long timeout);

  MultiOptionalBuilder withThreadsExceptionsConsumer(Consumer<Exception> threadsExceptionsConsumer);

  MultiThreadsCollider build();
}
