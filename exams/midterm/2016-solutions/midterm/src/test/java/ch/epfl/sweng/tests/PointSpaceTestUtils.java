package ch.epfl.sweng.tests;

import ch.epfl.sweng.PointSpace;

final class PointSpaceTestUtils {

    private PointSpaceTestUtils() {
    }

    static final double EPSILON = 0.0001;
    static final int[] ORIGIN_ZERO = {0, 0, 0};
    static final int[] ORIGIN_ONE = {1, 1, 1};

    static PointSpace createPointSpace(int[] origin) {
        return new PointSpace(origin[0], origin[1], origin[2]);
    }

    static void addPoint(PointSpace ps, int[] point) {
        ps.addPoint(point[0], point[1], point[2]);
    }

    private static double distance(int[] p1, int[] p2) {
        double result = 0.0;
        double tmp = (double) p2[0] - p1[0];
        tmp *= tmp;
        result += tmp;
        tmp = (double) p2[1] - p1[1];
        tmp *= tmp;
        result += tmp;
        tmp = (double) p2[2] - p1[1];
        tmp *= tmp;
        result += tmp;
        return Math.sqrt(result);
    }

    static double calculateClosestDistance(int[] origin, int[][] points) {
        double min = Double.MAX_VALUE;
        for (int[] point : points) {
            double d = distance(origin, point);
            if (d < min) min = d;
        }
        return min;
    }

    static double calculateFarthestDistance(int[] origin, int[][] points) {
        double max = 0;
        for (int[] point : points) {
            double d = distance(origin, point);
            if (d > max) max = d;
        }
        return max;
    }
}
