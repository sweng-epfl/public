package ch.epfl.sweng.midterm_grading.planes;

import ch.epfl.sweng.midterm.planes.Plane;
import ch.epfl.sweng.midterm.planes.SwengPlane;
import grading.GradedCategory;
import grading.GradedTest;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GradedCategory("Practice question 2")
class PlaneFuelTest {
    Plane plane;

    @BeforeEach
    void setup() {
        this.plane = new SwengPlane();
    }

    @GradedTest("The additional fuel needed for a route that costs nothing should be 0")
    void t01_set_to_tank_at_start() {
        assertEquals(0D, plane.additionalFuelNeeded(10, x -> 0D));

        plane.addFuel(500);

        double result = plane.additionalFuelNeeded(10, x -> 0D);
        if (result < 0) {
            assertEquals(-500D, result);
        } else {
            assertEquals(0D, result);
        }
    }


    @GradedTest("The additional fuel is provisioned exactly for the route length")
    void t02_bug2_lte_fixed() {
        // Function returns 100 only at km100, which should never be reached since i < 100
        assertEquals(0, plane.additionalFuelNeeded(100, x -> x == 100D ? 100 : 0D));
    }


    @GradedTest("The additional fuel is provisioned exactly according to the route consumption function")
    void t03_but3_mult_fixed() {
        // Function returns 1 only at km50, bugs 1 and 4 ignored (empty tank) and bug 2 ignored (nothing at km 100)
        assertEquals(1.1, plane.additionalFuelNeeded(100, x -> x == 50 ? 1D : 0D));
    }


    @GradedTest("The additional fuel is provisioned exactly according to the remaining fuel")
    void t04() {
        plane.addFuel(200D);

        // Function returns 500 only at KM1, ignores bugs 2 and 3 ; bug 1 is hard to ignore as it is very dependent
        assertEquals(350D, plane.additionalFuelNeeded(100, x -> x == 1 ? 500D : 0D));
    }
}
