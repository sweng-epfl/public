package ch.epfl.sweng.dp1.ex6;

/**
 * This class represents an Army for a House. An Army consists of many members of the House.
 */
public class Army {
    public Army(House house){
        Member warden = house.createMember();
        warden.sayMotto();
    }
}
