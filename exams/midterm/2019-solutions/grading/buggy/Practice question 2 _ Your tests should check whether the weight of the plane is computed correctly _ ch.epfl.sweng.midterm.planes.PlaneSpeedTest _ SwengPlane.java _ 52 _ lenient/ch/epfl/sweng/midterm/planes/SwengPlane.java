package ch.epfl.sweng.midterm.planes;

// !!!!!!!!!!!!!!!!!!!!!!
// You can edit this file
// !!!!!!!!!!!!!!!!!!!!!!

import com.google.common.base.Preconditions;

import java.util.function.Function;

public class SwengPlane implements Plane {
    private static final double FUEL_WEIGHT_KG_PER_L = 0.8;
    private static final int TAKE_OFF_DISTANCE_KM = 30;
    private static final int LANDING_DISTANCE_KM = 20;
    private static final int WEIGHT_KG_AT_MAX_SPEED = 60_000;
    private static final int WEIGHT_KG_AT_MIN_SPEED = 75_000;
    private static final int EMPTY_WEIGHT_KG = 46_000;
    private static final int MAX_SPEED = 940;
    private static final int MIN_SPEED = 740;

    private double remainingFuelLiters;
    private int luggageWeightKg;
    private int passengersWeightKg;

    @Override
    public void unload() {
        this.luggageWeightKg = 0;
        this.passengersWeightKg = 0;
    }

    @Override
    public void load(int passengersWeightKg, int luggageWeightKg) {
        this.passengersWeightKg = passengersWeightKg;
        this.luggageWeightKg = luggageWeightKg;
    }

    @Override
    public double additionalFuelNeeded(int routeLength, Function<Integer, Double> routeConsumption) {
        Preconditions.checkArgument(routeLength > 0, "negative or null routeLength");

        double consumption = 0;

        for (int i = 0; i < routeLength; ++i) {
            consumption += routeConsumption.apply(i);
        }

        return (consumption * 1.1) - remainingFuelLiters;
    }

    @Override
    public double computePlaneWeight() {
        return remainingFuelLiters + EMPTY_WEIGHT_KG + passengersWeightKg + luggageWeightKg;
    }

    @Override
    public Function<Integer, Double> computePlaneSpeedFunction(int routeLength) {
        Preconditions.checkArgument(routeLength > TAKE_OFF_DISTANCE_KM + LANDING_DISTANCE_KM, "too short routeLength");

        double weightFactor = Math.max(0D,
                Math.min(computePlaneWeight(), WEIGHT_KG_AT_MIN_SPEED) - WEIGHT_KG_AT_MAX_SPEED)
                / (WEIGHT_KG_AT_MAX_SPEED - WEIGHT_KG_AT_MIN_SPEED);
        double cruiseSpeed = MAX_SPEED - ((MAX_SPEED - MIN_SPEED) * weightFactor);

        return currentDistance -> {
            double speed = cruiseSpeed;

            if (currentDistance <= TAKE_OFF_DISTANCE_KM) {
                double incrFactor = cruiseSpeed / TAKE_OFF_DISTANCE_KM;
                speed = currentDistance * incrFactor;
            } else if (currentDistance >= routeLength - LANDING_DISTANCE_KM) {
                double decrFactor = cruiseSpeed / LANDING_DISTANCE_KM;
                speed = (routeLength - currentDistance) * decrFactor;
            }

            return speed;
        };
    }

    @Override
    public void addFuel(double quantity) {
        Preconditions.checkArgument(quantity > 0, "negative or null fuel quantity");

        this.remainingFuelLiters += quantity;
    }

    @Override
    public double getRemainingFuelLiters() {
        return remainingFuelLiters;
    }
}
