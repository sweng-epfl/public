package ch.epfl.sweng.midterm.planes;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN edit everything in this file
// You CAN delete this file
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class PlaneFuelTest {
    @Test
    void addFuelShouldAddFuel() {
        Plane plane = new SwengPlane();

        assertEquals(0, plane.getRemainingFuelLiters(), 0);
        plane.addFuel(100);
        assertEquals(100, plane.getRemainingFuelLiters(), 0);
    }

    @Test
    void negativeAddFuelShouldThrow() {
        Plane plane = new SwengPlane();

        assertThrows(IllegalArgumentException.class, () -> plane.addFuel(-1));
        assertThrows(IllegalArgumentException.class, () -> plane.addFuel(0));
    }
}
