# Exercise 4
There are various houses in Westeros such as House Stark, House Lannister, House Tyrell... Each `House` has many `Member`s and we can create a new member for the house:

```java
public interface House {
    public Member createMember();
}
```

Each member of the house can do something specific to that house only. For instance, saying its motto:

```java
public interface Member {
    public void sayMotto();
}
```

In this simple example, you want to create an [`Army`](src/main/java/ex4/Army.java) for each house. Currently, the army for each house only contains one member:

```java
public class Army {
    public Army(House house){
        Member warden = house.createMember();
        warden.sayMotto();
    }
}
```

Use the abstract factory pattern to change the current code and create two armies: one for the Starks, one for the Tyrells. 

Bonus exercise: a member of a house can also be a `Bastard`. 
```java

public interface House {
    public Member createMember();
    public Bastard createBastard();
}
```
Change the current code to be able to create `Bastard` members. 
