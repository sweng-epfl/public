package homework01;

import grading.GradedCategory;
import grading.GradedTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@GradedCategory("Point Space")
public final class PointSpaceTests {
    private static final int[][] POINT_SET_1 = {
            {-1, -1, -1},
            {1, 1, 1},
            {1, 2, 0},
            {-1, 3, 2},
            {-5, -10, 1},
            {20, 5, 2}
    };

    private static final int[][] POINT_SET_2 = {
            {-1, -1, -1},
            {0, 0, 0},
            {2, 2, 2},
            {1, 5, 3},
            {-8, -2, -5},
            {1, -1, 1},
            {20, 0, 0}
    };


    @GradedTest("A `PointSpace` containing its origin should have `size` 1")
    public void checkSizeOfPointSpaceContainingOrigin() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        PointSpaceTestUtils.addPoint(ps, PointSpaceTestUtils.ORIGIN_ZERO);
        assertThat(ps.size(), is(1));
    }

    @GradedTest("The `size` of a `PointSpace` should be correct when there are no duplicates")
    public void checkSizeOfPointSpace() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        assertThat(ps.size(), is(POINT_SET_1.length));
    }

    @GradedTest("The `size` of a `PointSpace` should be correct when there are duplicates")
    public void checkSizeOfPointSpaceWithDupes() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        assertThat(ps.size(), is(POINT_SET_1.length));
    }

    @GradedTest("`distanceToFarthestPoint` should be correct")
    public void checkDistanceToFarthestPoint() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ONE);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        assertThat(ps.distanceToFarthestPoint(),
                is(closeTo(PointSpaceTestUtils.calculateFarthestDistance(PointSpaceTestUtils.ORIGIN_ONE, POINT_SET_1), PointSpaceTestUtils.EPSILON)));
    }

    @GradedTest("Combining empty `PointSpace`s should lead to a `size` of 0")
    public void checkCombineEmptySizeIsCorrect() {
        assertThat(PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO).combine(PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO)).size(), is(0));
    }

    @GradedTest("Combining `PointSpace`s (with duplicates) should lead to a correct size")
    public void checkCombineSizeIsCorrect() {
        PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps1, point);
        PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_2) PointSpaceTestUtils.addPoint(ps2, point);
        // Overlap is 1 point.
        assertThat(ps1.combine(ps2).size(), is(POINT_SET_1.length + POINT_SET_2.length - 1));
    }

    @GradedTest("Combining `PointSpace`s should lead to a correct `distanceToClosestPoint`")
    public void checkCombineDistanceToClosestPointIsCorrect() {
        PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps1, point);
        PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_2) PointSpaceTestUtils.addPoint(ps2, point);
        assertThat(ps1.combine(ps2).distanceToClosestPoint(),
                is(closeTo(Math.min(
                        PointSpaceTestUtils.calculateClosestDistance(PointSpaceTestUtils.ORIGIN_ZERO, POINT_SET_1),
                        PointSpaceTestUtils.calculateClosestDistance(PointSpaceTestUtils.ORIGIN_ZERO, POINT_SET_2)),
                        PointSpaceTestUtils.EPSILON)));
    }

    @GradedTest("Combining `PointSpace`s should lead to a correct `distanceToFarthestPoint`")
    public void checkCombineDistanceToFarthestPointIsCorrect() {
        PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps1, point);
        PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_2) PointSpaceTestUtils.addPoint(ps2, point);
        assertThat(ps1.combine(ps2).distanceToFarthestPoint(),
                is(closeTo(Math.max(
                        PointSpaceTestUtils.calculateFarthestDistance(PointSpaceTestUtils.ORIGIN_ZERO, POINT_SET_1),
                        PointSpaceTestUtils.calculateFarthestDistance(PointSpaceTestUtils.ORIGIN_ZERO, POINT_SET_2)),
                        PointSpaceTestUtils.EPSILON)));
    }


    @GradedTest("Combining `PointSpace`s should not change either of them")
    public void combineDoesNotChangeAnything() {
        PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps1, point);
        PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_2) PointSpaceTestUtils.addPoint(ps2, point);

        ps1.combine(ps2);

        assertThat(ps1.size(), is(POINT_SET_1.length));
        assertThat(ps2.size(), is(POINT_SET_2.length));
    }

    @GradedTest("`distanceToClosestPoint` on an empty space should throw an `IllegalStateException`")
    public void distanceToClosestPointThrowsExceptionIfPointSpaceEmpty() {
        PointSpace space = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        assertThrows(IllegalStateException.class, space::distanceToClosestPoint);
    }

    @GradedTest("`distanceToFarthestPoint` on an empty space should throw an `IllegalStateException`")
    public void distanceToFarthestPointThrowsExceptionIfPointSpaceEmpty() {
        PointSpace space = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        assertThrows(IllegalStateException.class, space::distanceToFarthestPoint);
    }

    @GradedTest("A null space for `combine` should throw an `IllegalArgumentException`")
    public void combineWithNullThrowsException() {
        PointSpace space = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        assertThrows(IllegalArgumentException.class, () -> space.combine(null));
    }

    @GradedTest("A space with a different origin for `combine` should throw an `IllegalArgumentException`")
    public void combineWithDifferentOriginPointSpaceThrowsException() {
        PointSpace space1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        PointSpace space2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ONE);
        assertThrows(IllegalArgumentException.class, () -> space1.combine(space2));
    }

    @GradedTest("The `geometricCenter` for a `PointSpace` containing no points should throw `IllegalStateException`")
    public void geometricCenterWithNoPointsThrows() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        assertThrows(IllegalStateException.class, ps::geometricCenter);
    }

    @GradedTest("The `geometricCenter` for a `PointSpace` containing one point should be this point (with origin = {1,1,1})")
    public void geometricCenterWithOnePointIsThisPointOriginOne() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ONE);
        int[] point = {1, 2, 3};
        PointSpaceTestUtils.addPoint(ps, point);
        EuclideanVector geometricCenter = ps.geometricCenter();
        assertThat(geometricCenter.value(1), is(point[0]));
        assertThat(geometricCenter.value(2), is(point[1]));
        assertThat(geometricCenter.value(3), is(point[2]));
    }

    @GradedTest("The `geometricCenter` for a `PointSpace` containing one point should be this point (with origin = {0,0,0})")
    public void geometricCenterWithOnePointIsThisPointOriginZero() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        int[] point = {1, 2, 3};
        PointSpaceTestUtils.addPoint(ps, point);
        EuclideanVector geometricCenter = ps.geometricCenter();
        assertThat(geometricCenter.value(1), is(point[0]));
        assertThat(geometricCenter.value(2), is(point[1]));
        assertThat(geometricCenter.value(3), is(point[2]));
    }

    @GradedTest("The `geometricCenter` for a `PointSpace` containing a set of points should be the right value (with origin = {0,0,0})")
    public void geometricCenterWithASetOfPointOriginZero() {
        int[] origin = PointSpaceTestUtils.ORIGIN_ZERO;
        PointSpace ps = PointSpaceTestUtils.createPointSpace(origin);
        for (int[] point : POINT_SET_1) {
            PointSpaceTestUtils.addPoint(ps, point);
        }
        EuclideanVector geometricCenter = ps.geometricCenter();
        assertThat(geometricCenter.value(1), is(PointSpaceTestUtils.calculateGeometricCenter(POINT_SET_1)[0]));
        assertThat(geometricCenter.value(2), is(PointSpaceTestUtils.calculateGeometricCenter(POINT_SET_1)[1]));
        assertThat(geometricCenter.value(3), is(PointSpaceTestUtils.calculateGeometricCenter(POINT_SET_1)[2]));
    }

    @GradedTest("The `geometricCenter` for a `PointSpace` containing a set of points should be the right value (with origin = {1,1,1})")
    public void geometricCenterWithASetOfPointOriginONE() {
        int[] origin = PointSpaceTestUtils.ORIGIN_ONE;
        PointSpace ps = PointSpaceTestUtils.createPointSpace(origin);
        for (int[] point : POINT_SET_2) {
            PointSpaceTestUtils.addPoint(ps, point);
        }
        EuclideanVector geometricCenter = ps.geometricCenter();
        assertThat(geometricCenter.value(1), is(PointSpaceTestUtils.calculateGeometricCenter(POINT_SET_2)[0]));
        assertThat(geometricCenter.value(2), is(PointSpaceTestUtils.calculateGeometricCenter(POINT_SET_2)[1]));
        assertThat(geometricCenter.value(3), is(PointSpaceTestUtils.calculateGeometricCenter(POINT_SET_2)[2]));
    }

    static final class PointSpaceTestUtils {
        static final double EPSILON = 0.01;
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

        static int[] calculateGeometricCenter(int[][] points) {
            int[] res = {0, 0, 0};
            for (int[] p : points) {
                res[0] += p[0];
                res[1] += p[1];
                res[2] += p[2];
            }
            res[0] = (int) Math.round(res[0] * 1.0 / points.length);
            res[1] = (int) Math.round(res[1] * 1.0 / points.length);
            res[2] = (int) Math.round(res[2] * 1.0 / points.length);

            return res;
        }
    }
}