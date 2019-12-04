package ch.epfl.sweng.tests;

import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import ch.epfl.sweng.EuclideanVector;
import ch.epfl.sweng.grading.GradedCategory;
import ch.epfl.sweng.grading.GradedTest;
import ch.epfl.sweng.grading.JUnitGradeSheetTestRunner;
import ch.epfl.sweng.grading.models.GradingPoints;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Functionality",
        group = "`EuclideanVector`",
        pointsFormat = GradingPoints.PointsFormat.PARSABLE)
public final class EuclideanVectorTests {
    private static final int[] TEN_ELEMENTS = {9, 2, 5, 0, 6, 1, 10, -5, -200, -1};

    private EuclideanVector singleElementEuclideanVector = new EuclideanVector(1);
    private int[] tenElementEuclideanVectorCtorArray = Arrays.copyOf(TEN_ELEMENTS, TEN_ELEMENTS.length);
    private EuclideanVector tenElementEuclideanVector = new EuclideanVector(tenElementEuclideanVectorCtorArray);


    // Basic functionality tests
    @Test
    @GradedTest(name = "The element of a single-element `EuclideanVector` should be accessible",
            points = 0)
    public void canGetFirstElementFromOneElementEuclideanVector() {
        assertThat(singleElementEuclideanVector.value(0), is(1));
    }

    @Test
    @GradedTest(name = "The `dimension` of a single-element `EuclideanVector` should be correct",
            points = 0)
    public void canGetDimensionOfOneElementEuclideanVector() {
        assertThat(singleElementEuclideanVector.dimension(), is(1));
    }

    @Test
    @GradedTest(name = "The `norm` of a single-element `EuclideanVector` should be correct",
            points = 0)
    public void canGetLengthOfOneElementEuclideanVector() {
        assertThat(singleElementEuclideanVector.norm(), is(1.0));
    }

    @Test(timeout = 3000)
    @GradedTest(name = "A single-element `EuclideanVector` should be iterable",
            points = 0)
    public void oneElementEuclideanVectorIsIterable() {
        assertThat(singleElementEuclideanVector, IsIterableWithSize.iterableWithSize(1));
    }

    @Test
    @GradedTest(name = "A single-element `EuclideanVector` should not be orthogonal to itself",
            points = 0)
    public void oneElementEuclideanVectorIsNotOrthogonalToItself() {
        assertThat(singleElementEuclideanVector.isOrthogonalTo(singleElementEuclideanVector), is(false));
    }

    @Test
    @GradedTest(name = "A single-element `EuclideanVector` should be orthogonal to a single-element vector of 0",
            points = 0)
    public void oneElementEuclideanVectorIsOrthogonalToAZeroLengthOneElementEuclideanVector() {
        assertThat(singleElementEuclideanVector.isOrthogonalTo(new EuclideanVector(0)), is(true));
    }

    @Test
    @GradedTest(name = "The first element of a 10-element `EuclideanVector` should be accessible",
            points = 1)
    public void canGetFirstElementFromTenElementEuclideanVector() {
        assertThat(tenElementEuclideanVector.value(0), is(TEN_ELEMENTS[0]));
    }

    @Test
    @GradedTest(name = "The last element of a 10-element `EuclideanVector` should be accessible",
            points = 0)
    public void canGetLastElementFromTenElementEuclideanVector() {
        assertThat(tenElementEuclideanVector.value(TEN_ELEMENTS.length - 1),
                is(TEN_ELEMENTS[TEN_ELEMENTS.length - 1]));
    }

    @Test
    @GradedTest(name = "The `dimension` of a 10-element `EuclideanVector` should be correct",
            points = 1)
    public void canGetDimensionOfTenElementEuclideanVector() {
        assertThat(tenElementEuclideanVector.dimension(), is(10));
    }

    @Test
    @GradedTest(name = "The `norm` of a 10-element `EuclideanVector` should be correct",
            points = 1)
    public void canGetLengthOfTenElementEuclideanVector() {
        double expectedLength = 0.0;
        for (Integer value : TEN_ELEMENTS) {
            expectedLength += value * value * 1.0;
        }
        expectedLength = Math.sqrt(expectedLength);
        assertThat(tenElementEuclideanVector.norm(), is(closeTo(expectedLength, 0.0001)));
    }

    @Test(timeout=3000)
    @GradedTest(name = "A 10-element `EuclideanVector` should be iterable",
            points = 0)
    public void tenElementEuclideanVectorIsIterable() {
        assertThat(tenElementEuclideanVector, IsIterableWithSize.iterableWithSize(10));
    }

    @Test
    @GradedTest(name = "A 10-element `EuclideanVector` should not be orthogonal to itself",
            points = 1)
    public void tenElementEuclideanVectorIsNotOrthogonalToItself() {
        assertThat(tenElementEuclideanVector.isOrthogonalTo(tenElementEuclideanVector), is(false));
    }

    @Test
    @GradedTest(name = "`isOrthogonalTo` should return true if the vectors are orthogonal",
            points = 1)
    public void tenElementEuclideanVectorsThatAreOrthogonal() {
        assertThat(new EuclideanVector(10, -20).isOrthogonalTo(new EuclideanVector(20, 10)), is(true));
    }

    // Immutability tests
    @Test
    @GradedTest(name = "`EuclideanVector` should be immutable",
            points = 1)
    public void euclideanVectorIsImmutable() {
        tenElementEuclideanVectorCtorArray[2] = 42;
        assertThat(tenElementEuclideanVector.value(2), is(TEN_ELEMENTS[2]));
    }

    // Iterator tests
    @Test
    @GradedTest(name = "The iterator of a single-element `EuclideanVector` should be correct",
            points = 0)
    public void oneElementIteratorIsCorrect() {
        Iterator<Integer> it = singleElementEuclideanVector.iterator();
        assertThat(it.next(), is(1));
    }

    @Test
    @GradedTest(name = "The iterator of an empty `EuclideanVector` should be correct",
            points = 1)
    public void emptyIteratorIsCorrect() {
        EuclideanVector v;
        try {
            v = new EuclideanVector();
        } catch (IllegalArgumentException e) {
            // pass
            return;
        }
        Iterator<Integer> it = v.iterator();
        assertThat(it.hasNext(), is(false));
    }

    @Test
    @GradedTest(name = "The iterator of a 10-element `EuclideanVector` should be correct",
            points = 1)
    public void tenElementIteratorIsCorrect() {
        Iterator<Integer> it = tenElementEuclideanVector.iterator();
        for (int element : TEN_ELEMENTS) {
            assertThat(it.next(), is(element));
        }
    }

    @RunWith(JUnitGradeSheetTestRunner.class)
    @GradedCategory(name = "Invalid arguments", description =
            "Tests that pass invalid arguments to the methods, and expect them to throw the right kind of exception.",
            group = "`EuclideanVector`",
            pointsFormat = GradingPoints.PointsFormat.PARSABLE)
    public static final class InvalidArguments {
        private EuclideanVector tenElementEuclideanVector = new EuclideanVector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


        @Test(expected = IllegalArgumentException.class)
        @GradedTest(name = "A null array in the constructor should throw an `IllegalArgumentException`",
                points = 1)
        @SuppressWarnings("ResultOfObjectAllocationIgnored")
        public void nullArrayInCtor() {
            new EuclideanVector((int[]) null);
        }

        @Test(expected = IndexOutOfBoundsException.class)
        @GradedTest(name = "A negative index for `value` should throw an `IndexOutOfBoundsException`",
                points = 1)
        public void negativeIndexForGetThrowsException() {
            tenElementEuclideanVector.value(-1);
        }

        @Test(expected = IndexOutOfBoundsException.class)
        @GradedTest(name = "An overflowing index for `value` should throw an `IndexOutOfBoundsException`",
                points = 1)
        public void indexLargerThanEuclideanVectorSizeThrowsException() {
            tenElementEuclideanVector.value(tenElementEuclideanVector.dimension());
        }

        @Test(expected = IllegalArgumentException.class)
        @GradedTest(name = "A null EuclideanVector for `isOrthogonal` should throw an `IllegalArgumentException`",
                points = 1)
        public void nullParameterToIsOrthogonalMethodThrowsException() {
            tenElementEuclideanVector.isOrthogonalTo(null);
        }

        @Test(expected = IllegalArgumentException.class)
        @GradedTest(name = "A vector of different dimension for `isOrthogonal` should throw an `IllegalArgumentException`",
                points = 1)
        public void differentSizedParameterToIsOrthogonalMethodThrowsException() {
            tenElementEuclideanVector.isOrthogonalTo(new EuclideanVector(1));
        }

        @Test(expected = NoSuchElementException.class)
        @GradedTest(name = "Calling next() on an empty `iterator` should throw a `NoSuchElementException`",
                points = 1)
        public void callingNextOnIteratorMoreTimesThanElementsInEuclideanVectorThrowsException() {
            Iterator<Integer> it = new EuclideanVector(1).iterator();
            it.next();
            it.next();
        }

        @Test(expected = UnsupportedOperationException.class)
        @GradedTest(name = "Calling remove() on an `iterator` should throw an `UnsupportedOperationException`",
                points = 1)
        public void callingRemoveOnIteratorOfImmutableObjectThrowsException() {
            Iterator<Integer> it = new EuclideanVector(1).iterator();
            it.next();
            it.remove();
        }
    }


    @RunWith(JUnitGradeSheetTestRunner.class)
    @GradedCategory(name = "Corner cases",
            description = "Tests to make sure your implementation handles the main corner cases.",
            group = "`EuclideanVector`",
            pointsFormat = GradingPoints.PointsFormat.PARSABLE)
    @SuppressWarnings("UnusedCatchParameter")
    public static final class CornerCases {
        private final int[] REALLY_LARGE_ARRAY = new int[Integer.MAX_VALUE >>> 3];
        private int[] thousandElementArrayOfMaxIntValue = new int[1000];


        @Before
        public void setUp() {
            Arrays.fill(thousandElementArrayOfMaxIntValue, Integer.MAX_VALUE);
        }


        @Test(timeout = 3000)
        @GradedTest(name = "Creating an `EuclideanVector` from a 1GB array should work",
                points = 0)
        public void createReallyBigEuclideanVector() {
            EuclideanVector v = new EuclideanVector(REALLY_LARGE_ARRAY);
            assertThat(v.value(REALLY_LARGE_ARRAY.length - 1), is(REALLY_LARGE_ARRAY[REALLY_LARGE_ARRAY.length - 1]));
        }

        @Test(timeout = 3000)
        @GradedTest(name = "The norm of a 10,000-element `EuclideanVector` of MAX_INTs should be correct",
                points = 0)
        public void normOfThousandElementEuclideanVectorOfIntMaxValueIsCorrect() {
            EuclideanVector v = new EuclideanVector(thousandElementArrayOfMaxIntValue);
            assertThat(v.norm(), is(closeTo(Integer.MAX_VALUE * Math.sqrt(1000.0), 0.0001)));
        }

        @Test(timeout = 3000)
        @GradedTest(name = "A 10,000-element `EuclideanVector` should not be orthogonal to itself",
                points = 0)
        public void thousandElementEuclideanVectorOfIntMaxValueIsNotOrthogonalToItself() {
            EuclideanVector v = new EuclideanVector(thousandElementArrayOfMaxIntValue);
            assertThat(v.isOrthogonalTo(v), is(false));
        }

        @Test
        @GradedTest(name = "Integer overflows in `isOrthogonalTo` should not cause problems",
                points = 0)
        public void integerOverflowIsHandledCorrectlyForOrthogonalCheck() {
            EuclideanVector v1 = new EuclideanVector(Integer.MAX_VALUE, 2);
            EuclideanVector v2 = new EuclideanVector(2, 1);
            assertThat(v1.isOrthogonalTo(v2), is(false));
        }

        // empty EuclideanVector tests
        @Test
        @GradedTest(name = "An empty `EuclideanVector` should have `dimension` 0, or throw an exception",
                points = 0)
        public void emptyEuclideanVectorHasDimensionZero() {
            EuclideanVector v;
            try {
                v = new EuclideanVector();
            } catch (final IllegalArgumentException e) {
                // pass
                return;
            }
            assertThat(v.dimension(), is(0));
        }

        @Test
        @GradedTest(name = "The values of an empty `EuclideanVector` should throw an `IndexOutOfBoundsException`",
                points = 0)
        @SuppressWarnings("ProhibitedExceptionCaught")
        public void emptyEuclideanVectorValueThrowsException() {
            try {
                new EuclideanVector().value(0);
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                // pass
                return;
            }

            fail();
        }

        @Test
        @GradedTest(name = "The norm of an empty `EuclideanVector` should return 0, or throw an exception",
                points = 0)
        public void emptyEuclideanVectorNormShouldReturnZeroOrThrowException() {
            double norm = 0.0;
            try {
                norm = (new EuclideanVector()).norm();
            } catch (final IllegalArgumentException | IllegalStateException e) {
                // pass
                return;
            }
            assertThat(norm, is(0.0));
        }

        @Test
        @GradedTest(name = "An empty `EuclideanVector` should  be orthogonal to everything, or throw exceptions.",
                points = 0)
        public void emptyEuclideanVectorAlwaysIsOrthogonalOrThrowsException() {
            EuclideanVector v1;
            EuclideanVector v2;
            boolean isOrthogonal;
            try {
                v1 = new EuclideanVector();
            } catch (final IllegalArgumentException e) {
                // pass
                return;
            }
            v2 = new EuclideanVector();

            try {
                isOrthogonal = v1.isOrthogonalTo(v2);
            } catch (IllegalArgumentException | IllegalStateException e) {
                // pass
                return;
            }

            assertThat(isOrthogonal, is(true));
        }

        @Test
        @GradedTest(name = "The `iterator` of an empty `EuclideanVector` should be empty, or throw exceptions.",
                points = 0)
        public void emptyEuclideanVectorIteratorNeverReturnsElements() {
            EuclideanVector v;
            Iterator<Integer> it;
            try {
                v = new EuclideanVector();
            } catch (final IllegalArgumentException e) {
                // pass
                return;
            }

            try {
                it = v.iterator();
            } catch (final IllegalStateException e) {
                // pass
                return;
            }

            assertThat(it.hasNext(), is(false));

            try {
                it.next();
            } catch (final NoSuchElementException e) {
                // pass
                return;
            }

            fail();
        }
    }
}
