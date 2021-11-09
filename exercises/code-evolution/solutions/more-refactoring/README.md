# More refactoring - Solutions

First, we realize that the `RoomName` class is pretty useless here. We can therefor do an **inline class** and let the `name` attribute be a simple `String` type.

```java
private String name;
```

Next, we see that we have 3 different attributes that together specify a location: `locationLatitude`, `locationLongitude` and `floor`. We can **extract class** and create a new class named `Location`...

```java
public class Location {
    private double latitude;
    private double longitude;
    private int floor;
    
    public Location(double latitude, double longitude, int room) {...}
}
```
... and change the `Room` class accordingly !

```java
private Location location;

public Room(String name, Location location, ...)
```

With that done, we can also take care of the `distanceFrom()` method. It makes a lot more sens for this method to be in the `Location` class we just created, so let's use **move method**. The `Location` class now looks like this:

```java
public class Location {
    private double latitude;
    private double longitude;
    private int floor;
    
    private static final double FLOOR_HEIGHT = 10;
    
    public Location(double latitude, double longitude, int room) {...}
    
    // distance in meters from other to this
    public double distanceFrom(Location other) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(this.latitude - other.latitude);
        double lonDistance = Math.toRadians(this.longitude - other.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(other.latitude)) * Math.cos(Math.toRadians(this.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        
        double elevationDiff = (other.floor - this.floor) * FLOOR_HEIGHT;

        distance = Math.pow(distance, 2) + Math.pow(elevationDiff, 2);

        return Math.sqrt(distance);
    }
}
```

Notice that the method now takes a `Location` as parameter. We also extracted the `FLOOR_HEIGHT` constant and made it a static attribute of the class, which makes the computation of elevationDiff much more understandable and doesn't need a comment anymore. If we wanted to go further, we could also **extract method** for the part that converts to meters.

Next, we see some duplication in the `isAvailable()` and `isAvailableAt()` methods (the names of the methods should have given you a hint). Also, in both methods, we are iterating over all keys until we find a match. This completely removes the point of having a map so we also **substitute algorithm** and the final version is:

```java
public boolean isAvailable() {
    return isAvailableAt(TimeSlot.now());
}
    
public boolean isAvailableAt(TimeSlot slot) {
    return occupancies.containsKey(slot);
}
```

Last but not least, we need to take care of the horrendous switch in `mostCommonCourseType()`. This code is clearly not scalable or easily maintainable. Imagine the `Course` class suddenly adds 5 new `Course.TYPE`s. To keep the code correct, one would have to manually add 5 new variables and 5 new copy-pasted blocks of code into the switch ! In a large project, one might forget to do this and the method would be broken. A much better solution is the following:

```java
public Course.TYPE mostCommonCourseType() {
    Integer[] counts = new Integer[Course.TYPE.values().length];
    Arrays.fill(counts, 0);
    for (Course c : occupancies.values())
        counts[c.getType().ordinal()]++;
    Integer max = Arrays.stream(counts).max(Integer::compareTo).get();
    int index = Arrays.asList(counts).indexOf(max);
    return Course.TYPE.values()[index];
}
```

Here we made use of java enum methods, such as [`ordinal()`](https://docs.oracle.com/javase/8/docs/api/java/lang/Enum.html#ordinal--) and [`values()`](https://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.9.2). This way, if the `Course.TYPE` enum were to be modified, the changes would automatically be reflected in the `mostCommonCourseType()` method.

The final version of our `Room` class looks like this:

```java
public class Room {
    private String name;
    private Location location;
    private Map<TimeSlot, Course> occupancies;
    
    public Room(String name, Location location, Map<TimeSlot, Course> occupancies) {
        // initialize attributes
    }
    
    public boolean isAvailable() {
        return isAvailableAt(TimeSlot.now());
    }
    
    public boolean isAvailableAt(TimeSlot slot) {
        return occupancies.containsKey(slot);
    }
    
    public String getName() {
        return name;
    }
    
    public double distanceFrom(Location other) {
        return location.distanceFrom(other);
    }
    
    public Course.TYPE mostCommonCourseType() {
        Integer[] counts = new Integer[Course.TYPE.values().length];
        Arrays.fill(counts, 0);
        for (Course c : occupancies.values())
            counts[c.getType().ordinal()]++;
        Integer max = Arrays.stream(counts).max(Integer::compareTo).get();
        int index = Arrays.asList(counts).indexOf(max);
        return Course.TYPE.values()[index];
    }
}
```
