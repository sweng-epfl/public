import java.time.LocalDateTime;

public class TimeSlot {
    private int startHour;
    private int endHour;
    private String day;

    public TimeSlot(String day, int startHour, int endHour) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static TimeSlot now() {
        LocalDateTime now = LocalDateTime.now();
        return new TimeSlot(now.getDayOfWeek().name(), now.getHour(), now.getHour() + 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TimeSlot)) {
            return false;
        }
        TimeSlot that = (TimeSlot) other;
        return this.day.equals(that.day) &&
                this.startHour == that.startHour &&
                this.endHour == that.endHour;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + startHour;
        hash = 31 * hash + endHour;
        hash = 31 * hash + (day == null ? 0 : day.hashCode());
        return hash;
    }


}