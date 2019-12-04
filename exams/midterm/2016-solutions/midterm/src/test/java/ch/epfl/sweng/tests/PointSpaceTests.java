package ch.epfl.sweng.tests;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.PointSpace;
import ch.epfl.sweng.grading.GradedCategory;
import ch.epfl.sweng.grading.GradedTest;
import ch.epfl.sweng.grading.JUnitGradeSheetTestRunner;
import ch.epfl.sweng.grading.models.GradingPoints;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Functionality",
        group = "`PointSpace`",
        pointsFormat = GradingPoints.PointsFormat.PARSABLE)
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


    @Test
    @GradedTest(name = "An empty `PointSpace` should have `size` 0",
            points = 1)
    public void checkSizeOfEmptyPointSpace() {
        assertThat(PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO).size(), is(0));
    }

    @Test
    @GradedTest(name = "A `PointSpace` containing its origin should have `size` 1",
            points = 0)
    public void checkSizeOfPointSpaceContainingOrigin() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        PointSpaceTestUtils.addPoint(ps, PointSpaceTestUtils.ORIGIN_ZERO);
        assertThat(ps.size(), is(1));
    }

    @Test
    @GradedTest(name = "The `distanceToClosestPoint` for a `PointSpace` containing only its origin should be 0",
            points = 1)
    public void checkDistanceToClosestPointForOrigin() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        PointSpaceTestUtils.addPoint(ps, PointSpaceTestUtils.ORIGIN_ZERO);
        assertThat(ps.distanceToClosestPoint(), is(0.0));
    }

    @Test
    @GradedTest(name = "The `distanceToFarthestPoint` for a `PointSpace` containing only its origin should be 0",
            points = 1)
    public void checkDistanceToFarthestPointForOrigin() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        PointSpaceTestUtils.addPoint(ps, PointSpaceTestUtils.ORIGIN_ZERO);
        assertThat(ps.distanceToFarthestPoint(), is(0.0));
    }

    @Test
    @GradedTest(name = "The `size` of a `PointSpace` should be correct when there are no duplicates",
            points = 1)
    public void checkSizeOfPointSpace() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        assertThat(ps.size(), is(POINT_SET_1.length));
    }

    @Test
    @GradedTest(name = "The `size` of a `PointSpace` should be correct when there are duplicates",
            points = 1)
    public void checkSizeOfPointSpaceWithDupes() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        assertThat(ps.size(), is(POINT_SET_1.length));
    }

    @Test
    @GradedTest(name = "`distanceToClosestPoint` should be correct",
            points = 1)
    public void checkDistanceToClosestPoint() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ONE);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        assertThat(ps.distanceToClosestPoint(),
                Matchers.is(Matchers.closeTo(PointSpaceTestUtils.calculateClosestDistance(PointSpaceTestUtils.ORIGIN_ONE, POINT_SET_1), PointSpaceTestUtils.EPSILON)));
    }

    @Test
    @GradedTest(name = "`distanceToFarthestPoint` should be correct",
            points = 1)
    public void checkDistanceToFarthestPoint() {
        PointSpace ps = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ONE);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps, point);
        assertThat(ps.distanceToFarthestPoint(),
                Matchers.is(Matchers.closeTo(PointSpaceTestUtils.calculateFarthestDistance(PointSpaceTestUtils.ORIGIN_ONE, POINT_SET_1), PointSpaceTestUtils.EPSILON)));
    }

    @Test
    @GradedTest(name = "Combining empty `PointSpace`s should lead to a `size` of 0",
            points = 0)
    public void checkCombineEmptySizeIsCorrect() {
        assertThat(PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO).combine(PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO)).size(), is(0));
    }

    @Test
    @GradedTest(name = "Combining `PointSpace`s (with duplicates) should lead to a correct size",
            points = 1)
    public void checkCombineSizeIsCorrect() {
        PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps1, point);
        PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_2) PointSpaceTestUtils.addPoint(ps2, point);
        // Overlap is 1 point.
        assertThat(ps1.combine(ps2).size(), is(POINT_SET_1.length + POINT_SET_2.length - 1));
    }

    @Test
    @GradedTest(name = "Combining `PointSpace`s should lead to a correct `distanceToClosestPoint`",
            points = 1)
    public void checkCombineDistanceToClosestPointIsCorrect() {
        PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps1, point);
        PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_2) PointSpaceTestUtils.addPoint(ps2, point);
        assertThat(ps1.combine(ps2).distanceToClosestPoint(),
                Matchers.is(Matchers.closeTo(Math.min(
                        PointSpaceTestUtils.calculateClosestDistance(PointSpaceTestUtils.ORIGIN_ZERO, POINT_SET_1),
                        PointSpaceTestUtils.calculateClosestDistance(PointSpaceTestUtils.ORIGIN_ZERO, POINT_SET_2))
                        , PointSpaceTestUtils.EPSILON)));
    }

    @Test
    @GradedTest(name = "Combining `PointSpace`s should lead to a correct `distanceToFarthestPoint`",
            points = 1)
    public void checkCombineDistanceToFarthestPointIsCorrect() {
        PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps1, point);
        PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_2) PointSpaceTestUtils.addPoint(ps2, point);
        assertThat(ps1.combine(ps2).distanceToFarthestPoint(),
                Matchers.is(Matchers.closeTo(Math.max(
                        PointSpaceTestUtils.calculateFarthestDistance(PointSpaceTestUtils.ORIGIN_ZERO, POINT_SET_1),
                        PointSpaceTestUtils.calculateFarthestDistance(PointSpaceTestUtils.ORIGIN_ZERO, POINT_SET_2))
                        , PointSpaceTestUtils.EPSILON)));
    }


    @Test
    @GradedTest(name = "Combining `PointSpace`s should not change either of them",
            points = 1)
    public void combineDoesNotChangeAnything() {
        PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_1) PointSpaceTestUtils.addPoint(ps1, point);
        PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
        for (int[] point : POINT_SET_2) PointSpaceTestUtils.addPoint(ps2, point);

        ps1.combine(ps2);

        assertThat(ps1.size(), is(POINT_SET_1.length));
        assertThat(ps2.size(), is(POINT_SET_2.length));
    }


    @RunWith(JUnitGradeSheetTestRunner.class)
    @GradedCategory(name = "Corner cases",
            description = "Tests to make sure your implementation handles the main corner cases.",
            group = "`PointSpace`",
            pointsFormat = GradingPoints.PointsFormat.PARSABLE)
    public static final class CornerCases {
        @Test(timeout = 3000)
        @GradedTest(name = "`combine` should work with two 100,000-point spaces",
                points = 0)
        public void combineTwoPointSpacesOfAHundredThousandPointsEach() {
            PointSpace ps1 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
            PointSpace ps2 = PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO);
            for (int i = 0; i < 100000; ++i) {
                ps1.addPoint(i, i, i);
                ps2.addPoint(-i, -i, -i);
            }
            Assert.assertThat(ps1.combine(ps2).size(), is(200000 - 1));
        }
    }


    @RunWith(JUnitGradeSheetTestRunner.class)
    @GradedCategory(name = "Invalid arguments", description =
            "Tests that pass invalid arguments to the methods, and expect them to throw the right kind of exception.",
            group = "`PointSpace`",
            pointsFormat = GradingPoints.PointsFormat.PARSABLE)
    public static final class InvalidArguments {
        @Test(expected = IllegalStateException.class)
        @GradedTest(name = "`distanceToClosestPoint` on an empty space should throw an `IllegalStateException`",
                points = 1)
        public void distanceToClosestPointThrowsExceptionIfPointSpaceEmpty() {
            PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO).distanceToClosestPoint();
        }

        @Test(expected = IllegalStateException.class)
        @GradedTest(name = "`distanceToFarthestPoint` on an empty space should throw an `IllegalStateException`",
                points = 1)
        public void distanceToFarthestPointThrowsExceptionIfPointSpaceEmpty() {
            PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO).distanceToFarthestPoint();
        }

        @Test(expected = IllegalArgumentException.class)
        @GradedTest(name = "A null space for `combine` should throw an `IllegalArgumentException`",
                points = 1)
        public void combineWithNullThrowsException() {
            PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO).combine(null);
        }

        @Test(expected = IllegalArgumentException.class)
        @GradedTest(name = "A space with a different origin for `combine` should throw an `IllegalArgumentException`",
                points = 1)
        public void combineWithDifferentOriginPointSpaceThrowsException() {
            PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ZERO).combine(PointSpaceTestUtils.createPointSpace(PointSpaceTestUtils.ORIGIN_ONE));
        }
    }
}
