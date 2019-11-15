package ch.epfl.sweng.midterm.planes;

// !!!!!!!!!!!!!!!!!!!!!!
// You can edit this file
// !!!!!!!!!!!!!!!!!!!!!!

import com.google.common.base.Preconditions;

import java.util.function.Function;

public class SwengPlane implements Plane {
    @Override
    public void unload() {
    }

    @Override
    public void load(int passengersWeightKg, int luggageWeightKg) {
    }

    @Override
    public double additionalFuelNeeded(int routeLength, Function<Integer, Double> routeConsumption) {
        return 0.0;
    }

    @Override
    public double computePlaneWeight() {
        return 0.0;
    }

    @Override
    public Function<Integer, Double> computePlaneSpeedFunction(int routeLength) {
        return x -> 0D;
    }

    @Override
    public void addFuel(double quantity) {
    }

    @Override
    public double getRemainingFuelLiters() {
        return 0D;
    }
}
