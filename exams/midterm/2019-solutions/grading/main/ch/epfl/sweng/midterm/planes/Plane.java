package ch.epfl.sweng.midterm.planes;

// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

import java.util.function.Function;

public interface Plane {
    void unload();

    void load(int passengersWeightKg, int luggageWeightKg);

    /**
     * Computes the amount of fuel the company will need to buy to allow the plane
     * to go to its next destination.<br>
     * Legally, the plane has to have 10% more fuel that what is actually needed to
     * reach its destination before taking off (safety reasons)<br>
     * As the company needs to pay for the fuel, we want this function to return the
     * minimum number possible!
     *
     * @param routeLength      the length of the route to the next destination, in
     *                         kilometers
     * @param routeConsumption the consumption of the plane, kilometer per kilometer
     *                         (that is, `routeConsumption.apply(10)` returns the
     *                         average amount of fuel consumed by the plane to go
     *                         from km 10 to km 11 on its route)
     * @return the amount of fuel to add to the plane to allow it to reach its
     *         destination safely
     */
    double additionalFuelNeeded(int routeLength, Function<Integer, Double> routeConsumption);

    double computePlaneWeight();

    Function<Integer, Double> computePlaneSpeedFunction(int routeLength);

    /**
     * Add some fuel to the plane
     *
     * @param quantity the quantity (> 0) of fuel to add to the plane
     */
    void addFuel(double quantity);

    /**
     * Get the quantity of fuel currently in the plane
     *
     * @return the quantity of fuel remaining in the plane
     */
    double getRemainingFuelLiters();
}
