import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public final class WorkLog {
    private final Map<Person, Integer> hoursWorked;
    private final Map<Person, ZonedDateTime> workStart;

    public WorkLog() {
        hoursWorked = new HashMap<>();
        workStart = new HashMap<>();
    }

    public void startWorking(Person person) {
        if (person.isMinor() || person.isElderly()) {
            throw new IllegalArgumentException("This person is not allowed to log hours for work!");
        }
        if (workStart.containsKey(person)) {
            throw new IllegalStateException("This person has already started working!");
        }

        workStart.put(person, ZonedDateTime.now());
    }

    public void stopWorking(Person person) {
        ZonedDateTime start = workStart.getOrDefault(person, null);
        if (start == null) {
            throw new IllegalStateException("This person hasn't started working!");
        }

        ZonedDateTime stop = ZonedDateTime.now();

        int hours = 0;
        if (start.toLocalDate().equals(stop.toLocalDate())) {
            // Easy case, it's the same day
            hours = stop.getHour() - start.getHour();
        } else {
            // A bit more complicated
            hours += 24 - start.getHour();
            hours += stop.getDayOfYear() - start.getDayOfYear();
            hours += stop.getHour();
        }

        hoursWorked.merge(person, hours, Integer::sum);
        workStart.remove(person);
    }

    public int getHoursWorked(Person person) {
        return hoursWorked.get(person);
    }
}
