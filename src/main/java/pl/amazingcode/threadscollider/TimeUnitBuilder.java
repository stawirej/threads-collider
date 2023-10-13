package pl.amazingcode.threadscollider;

public interface TimeUnitBuilder {

  Builder asNanoseconds();

  Builder asMicroseconds();

  Builder asMilliseconds();

  Builder asSeconds();

  Builder asMinutes();

  Builder asHours();

  Builder asDays();
}
