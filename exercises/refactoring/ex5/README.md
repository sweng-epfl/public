# More refactoring

Refactor the following code and make it as clean as possible !

The `Room` class will be your main focus. It has a name, a location and it's occupancies are represented by a `Map` that links a `TimeSlot` to a `Course`. The latter are not fully implemented here but you can assume that they have correct attributes and working methods.

```java
public class Room {
    private RoomName name;
    private double locationLatitude;
    private double locationLongitude;
    private int floor;
    private Map<TimeSlot, Course> occupancies;
    
    public Room(RoomName name, double locationLatitude, double locationLongitude,
                int floor, Map<TimeSlot, Course> occupancies) {
        // initialize attributes
    }
    
    public boolean isAvailable() {
        TimeSlot now = TimeSlot.now(); // returns the TimeSlot that we're currently in
        for (TimeSlot s : occupancies.keys()) {
            if (s == now) {
                return false;
            }
        }
    }
    
    public boolean isAvailableAt(TimeSlot slot) {
        boolean temp = true;
        for (TimeSlot s : occupancies.keys()) {
            if (s == slot) {
                temp = false;
            }
        }
        return temp;
    }
    
    public void getName() {
        return name.getName();
    }
    
    // distance in meters from other to this room's location
    public double distanceFrom(double otherLatitude, double otherLongitude, int otherFloor) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(locationLatitude - otherLatitude);
        double lonDistance = Math.toRadians(locationLongitude - otherLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(otherLatitude)) * Math.cos(Math.toRadians(locationLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        // approximate elevations between 2 floors to be 10m 
        double elevationDiff = otherFloor * 10 - floor * 10;

        distance = Math.pow(distance, 2) + Math.pow(elevationDiff, 2);

        return Math.sqrt(distance);
    }
    
    public Course.TYPE mostCommonCourseType() {
        int nbMath, nbArt, nbEnglish, nbHistory, nbGeography;
        Course.TYPE currentBest = null;
        int currentMax = -1;
        for (Course c : occupancies.values()) {
            switch(c.getType()) {
                case MATH:
                    nbMath++;
                    if(nbMath > currentMax) {
                        currentMax = nbMath;
                        currentBest = MATH;
                    }
                    break;
                case ART:
                    nbArt++;
                    if(nbArt > currentMax) {
                        currentMax = nbArt;
                        currentBest = ART;
                    }
                    break;
                case ENGLISH:
                    nbEnglish++;
                    if(nbEnglish > currentMax) {
                        currentMax = nbEnglish;
                        currentBest = ENGLISH;
                    }
                    break;
                case HISTORY:
                    nbHistory++;
                    if(nbHistory > currentMax) {
                        currentMax = nbHistory;
                        currentBest = HISTORY;
                    }
                    break;
                case GEOGRAPHY:
                    nbGeography++;
                    if(nbGeography > currentMax) {
                        currentMax = nbGeography;
                        currentBest = GEOGRAPHY;
                    }
                    break;
                default:
                    throw new Error("Undefined course type !");
            }
        }
        return currentBest;
    }
}

public class RoomName {
    private String name;
    
    public RoomName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

public class Course {
    // ...
    
    public enum TYPE {
        MATH, ART, ENGLISH, HISTORY, GEOGRAPHY
    };
}

public class TimeSlot {
    // ...
}
```