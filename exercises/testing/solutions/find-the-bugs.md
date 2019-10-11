# Solutions: Find the bugs

## People and their age

The bug is that `Person` checks for an age below 17 instead of 18 in the `isMinor` method.

You can demonstrate this bug using a test such as the following:

```java
@Test
void seventeenYearOldPeopleAreMinors() {
    assertThat(new Person("Carmen", "Sandiego", 17).isMinor(), is(true));
}
```


## The address book

The bug is that `Person`'s equality and hash code methods do not take the last name into account, meaning two people with the same first name and age are considered equal,
even if their last names are different.

You can demonstrate this bug using a test such as the following:

```java
@Test
void differentLastNamesButSameFirstNamesAndAgesAreNotConfused() {
    AddressBook book = new AddressBook();
    book.setAddress(new Person("Alan", "Turing", 99), "Secret Enigma-cracking lab");
    book.setAddress(new Person("Alan", "Rickman", 99), "Potions-brewing room");

    assertThat(book.toString(),
           both(containsString("Alan Turing: Secret Enigma-cracking lab"))
          .and(containsString("Alan Rickman: Potions-brewing room"))
    );
}
```


## The work log

The `WorkLog` class uses `ZonedDateTime.now()`, which depends on the real system clock.
This is a problem for tests because they could pass or fail depending on the date or time!

Instead, the `WorkLog` class needs to take a `Clock` class in its constructor, which should be stored as a private field and then passed to the `now` method, which has an overload accepting a `Clock`.

This way, the tests can use a fake clock that can be set to any instant:

```java
private final class FakeClock extends Clock {
    private Instant instant;

    void setInstant(Instant instant) {
        this.instant = instant;
    }

    @Override
    public ZoneId getZone() {
        return ZoneId.of("UTC");
    }

    @Override
    public Clock withZone(ZoneId zoneId) {
        throw new RuntimeException("This method should not be called");
    }

    @Override
    public Instant instant() {
        return instant;
    }
}
```

One can then discover that the "hours worked" calculation is wrong when the start and end are not in the same day:

```java
@Test
void dayDifferencesAreHandledInWorkHoursComputation() {
    FakeClock clock = new FakeClock();
    WorkLog log = new WorkLog(clock);
    Person person = new Person("Arsène", "Lupin", 30);

    clock.setInstant(ZonedDateTime.of(2019, 10, 11, 18, 0, 0, 0, ZoneId.of("UTC")).toInstant());
    log.startWorking(person);

    clock.setInstant(ZonedDateTime.of(2019, 10, 12, 10, 0, 0, 0, ZoneId.of("UTC")).toInstant());
    log.stopWorking(person);

    assertThat(log.getHoursWorked(person), is(16));
}
```

To fix it, replace the hand-written logic by a simple call to the Java standard library:

```java
int hours = (int) start.until(stop, ChronoUnit.HOURS);
```
