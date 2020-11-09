package homework01;

import org.hamcrest.collection.IsIterableWithSize;
import org.junit.jupiter.api.BeforeAll;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import grading.GradedCategory;
import grading.GradedTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Euclidean Vector")
@SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions", "EqualsBetweenInconvertibleTypes"})
public final class EuclideanVectorTests {
    private static final int[] TEN_ELEMENTS_ARRAY = {9, 2, 5, 0, 6, 1, 10, -5, -200, -1};
    private static final int[] THOUSAND_ELEMENTS_ARRAY_OF_MAX_VALUE = new int[1000];
    private static final EuclideanVector SINGLE_ELEMENT_VECTOR = new EuclideanVector(1);
    private static final EuclideanVector TEN_ELEMENTS_VECTOR = new EuclideanVector(TEN_ELEMENTS_ARRAY.clone());


    @BeforeAll
    static void setUp() {
        Arrays.fill(THOUSAND_ELEMENTS_ARRAY_OF_MAX_VALUE, Integer.MAX_VALUE);
    }


    @GradedTest("The element of a single-element `EuclideanVector` should be accessible")
    public void canGetFirstElementFromOneElementEuclideanVector() {
        assertThat(SINGLE_ELEMENT_VECTOR.value(1), is(1));
    }

    @GradedTest("The `dimension` of a single-element `EuclideanVector` should be correct")
    public void canGetDimensionOfOneElementEuclideanVector() {
        assertThat(SINGLE_ELEMENT_VECTOR.dimension(), is(1));
    }

    @GradedTest("The `norm` of a single-element `EuclideanVector` should be correct")
    public void canGetLengthOfOneElementEuclideanVector() {
        assertThat(SINGLE_ELEMENT_VECTOR.norm(), is(1.0));
    }

    @GradedTest("A single-element `EuclideanVector` should be iterable")
    public void oneElementEuclideanVectorIsIterable() {
        assertThat(SINGLE_ELEMENT_VECTOR, IsIterableWithSize.iterableWithSize(1));
    }

    @GradedTest("A single-element `EuclideanVector` should be orthogonal to a single-element vector of 0")
    public void oneElementEuclideanVectorIsOrthogonalToAZeroLengthOneElementEuclideanVector() {
        assertThat(SINGLE_ELEMENT_VECTOR.isOrthogonalTo(new EuclideanVector(0)), is(true));
    }

    @GradedTest("The first element of a 10-element `EuclideanVector` should be accessible")
    public void canGetFirstElementFromTenElementEuclideanVector() {
        assertThat(TEN_ELEMENTS_VECTOR.value(1), is(TEN_ELEMENTS_ARRAY[0]));
    }

    @GradedTest("The last element of a 10-element `EuclideanVector` should be accessible")
    public void canGetLastElementFromTenElementEuclideanVector() {
        assertThat(TEN_ELEMENTS_VECTOR.value(TEN_ELEMENTS_ARRAY.length),
                is(TEN_ELEMENTS_ARRAY[TEN_ELEMENTS_ARRAY.length - 1]));
    }

    @GradedTest("The `dimension` of a 10-element `EuclideanVector` should be correct")
    public void canGetDimensionOfTenElementEuclideanVector() {
        assertThat(TEN_ELEMENTS_VECTOR.dimension(), is(10));
    }

    @GradedTest("The `norm` of a 10-element `EuclideanVector` should be correct")
    public void canGetLengthOfTenElementEuclideanVector() {
        double expectedLength = 0.0;
        for (Integer value : TEN_ELEMENTS_ARRAY) {
            expectedLength += value * value * 1.0;
        }
        expectedLength = Math.sqrt(expectedLength);
        assertThat(TEN_ELEMENTS_VECTOR.norm(), is(closeTo(expectedLength, 0.0001)));
    }

    @GradedTest("A 10-element `EuclideanVector` should be iterable")
    public void tenElementEuclideanVectorIsIterable() {
        assertThat(TEN_ELEMENTS_VECTOR, IsIterableWithSize.iterableWithSize(10));
    }


    @GradedTest("`isOrthogonalTo` should return true if the vectors are orthogonal")
    public void tenElementEuclideanVectorsThatAreOrthogonal() {
        assertThat(new EuclideanVector(10, -20).isOrthogonalTo(new EuclideanVector(20, 10)), is(true));
    }

    @GradedTest("`equals` should return true if the two vectors are indeed equal")
    public void sameEuclideanVectorsEqualsReturnsFalse() {
        EuclideanVector e1 = new EuclideanVector(1, 2, 3, 4, 5);
        EuclideanVector e2 = new EuclideanVector(1, 2, 3, 4, 5);
        assertThat(e1.equals(e2), is(true));
    }


    @GradedTest("`EuclideanVector` should be immutable")
    public void euclideanVectorIsImmutable() {
        int[] array = TEN_ELEMENTS_ARRAY.clone();
        EuclideanVector vector = new EuclideanVector(array);
        array[2] = 42;
        assertThat(vector.value(3), is(TEN_ELEMENTS_ARRAY[2]));
    }

    @GradedTest("The iterator of a single-element `EuclideanVector` should be correct")
    public void oneElementIteratorIsCorrect() {
        Iterator<Integer> it = SINGLE_ELEMENT_VECTOR.iterator();
        assertThat(it.next(), is(1));
    }

    @GradedTest("The iterator of an empty `EuclideanVector` should be correct")
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

    @GradedTest("The iterator of a 10-element `EuclideanVector` should be correct")
    public void tenElementIteratorIsCorrect() {
        Iterator<Integer> it = TEN_ELEMENTS_VECTOR.iterator();
        for (int element : TEN_ELEMENTS_ARRAY) {
            assertThat(it.next(), is(element));
        }
    }

    @GradedTest("A null array in the constructor should throw an `IllegalArgumentException`")
    public void nullArrayInCtor() {
        assertThrows(IllegalArgumentException.class, () -> new EuclideanVector((int[]) null));
    }

    @GradedTest("A negative index for `value` should throw an `IndexOutOfBoundsException`")
    public void negativeIndexForGetThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> TEN_ELEMENTS_VECTOR.value(-1));
    }

    @GradedTest("A index = 0 for `value` should throw an `IndexOutOfBoundsException`")
    public void zeroIndexForGetThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> TEN_ELEMENTS_VECTOR.value(0));
    }

    @GradedTest("An overflowing index for `value` should throw an `IndexOutOfBoundsException`")
    public void indexLargerThanEuclideanVectorSizeThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> TEN_ELEMENTS_VECTOR.value(TEN_ELEMENTS_VECTOR.dimension() + 1));
    }

    @GradedTest("A null EuclideanVector for `isOrthogonal` should throw an `IllegalArgumentException`")
    public void nullParameterToIsOrthogonalMethodThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> TEN_ELEMENTS_VECTOR.isOrthogonalTo(null));
    }

    @GradedTest("A vector of different dimension for `isOrthogonal` should throw an `IllegalArgumentException`")
    public void differentSizedParameterToIsOrthogonalMethodThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> TEN_ELEMENTS_VECTOR.isOrthogonalTo(new EuclideanVector(1)));
    }

    @GradedTest("The norm of a 10,000-element `EuclideanVector` of MAX_INTs should be correct")
    public void normOfThousandElementEuclideanVectorOfIntMaxValueIsCorrect() {
        EuclideanVector v = new EuclideanVector(THOUSAND_ELEMENTS_ARRAY_OF_MAX_VALUE);
        assertThat(v.norm(), is(closeTo(Integer.MAX_VALUE * Math.sqrt(1000.0), 0.0001)));
    }

    @GradedTest("The values of an empty `EuclideanVector` should throw an `IndexOutOfBoundsException`")
    public void emptyEuclideanVectorValueThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> new EuclideanVector().value(1));
    }


    @GradedTest("An empty `EuclideanVector` should  be orthogonal to everything, or throw exceptions.")
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

    @GradedTest("The `iterator` of an empty `EuclideanVector` should be empty, or throw exceptions.")
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
        assertThrows(NoSuchElementException.class, it::next);
    }

    @GradedTest("`plus` should throw IllegalArgumentException if the two vectors are not of the same dimension (5 vs 6)")
    public void differentDimensionEuclideanVectorsCannotBeAdded5vs6() {
        EuclideanVector e1 = new EuclideanVector(1, 2, 3, 4, 5);
        EuclideanVector e2 = new EuclideanVector(1, 2, 3, 4, 5, 6);
        assertThrows(IllegalArgumentException.class, () -> e1.plus(e2));
    }

    @GradedTest("`plus` should throw IllegalArgumentException if the two vectors are not of the same dimension (0 vs 3)")
    public void differentDimensionEuclideanVectorsCannotBeAdded0vs3() {
        EuclideanVector e1 = new EuclideanVector();
        EuclideanVector e2 = new EuclideanVector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> e1.plus(e2));
    }

    @GradedTest("`plus` should throw IllegalArgumentException if the vector given as argument is null")
    public void differentDimensionEuclideanVectorCannotBeAddedWithNull() {
        EuclideanVector e1 = new EuclideanVector();
        assertThrows(IllegalArgumentException.class, () -> e1.plus(null));
    }

    @GradedTest("`plus` should return the correct sum of two vectors if the vectors are of the same dimension")
    public void tenElementEuclideanVectorsSumIsCorrect() {
        EuclideanVector e1 = new EuclideanVector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        EuclideanVector e2 = new EuclideanVector(0, 3, 4, 7, 2, 6, 1, 54, 12, 7);
        assertThat(e1.plus(e2).equals(new EuclideanVector(1, 5, 7, 11, 7, 12, 8, 62, 21, 17)), is(true));
    }

    @GradedTest("`plus` should return an empty vector if the two vectors are empty")
    public void euclideanVectorsSumIsCorrectWithEmptyVectors() {
        EuclideanVector e1 = new EuclideanVector();
        EuclideanVector e2 = new EuclideanVector();
        assertThat(e1.plus(e2).equals(new EuclideanVector()), is(true));
    }

    @GradedTest("`scalarDivide` should throw `IllegalArgumentException` when the given scalar is 0")
    public void scalarDivideThrowsWhenCalledWithZero() {
        EuclideanVector e1 = new EuclideanVector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> e1.scalarDivide(0));
    }

    @GradedTest("`scalarDivide` should return an empty vector if the called on an empty vector")
    public void euclideanVectorsScalarDivideIsCorrectWithEmptyVector() {
        EuclideanVector e1 = new EuclideanVector();
        assertThat(e1.scalarDivide(3).equals(new EuclideanVector()), is(true));
    }

    @GradedTest("`scalarDivide` should return the correct result with one ten-elements vector and a valid scalar")
    public void tenElementEuclideanVectorsScalarDivideByThreeIsCorrect() {
        EuclideanVector e = new EuclideanVector(0, 3, 4, 7, 2, 6, 1, 54, 12, 7);
        assertThat(e.scalarDivide(3).equals(new EuclideanVector(0, 1, 1, 2, 1, 2, 0, 18, 4, 2)), is(true));
    }

    @GradedTest("`crossProduct` should throw `IllegalArgumentException` if the vector given as argument is null")
    public void crossProductThrowsWhenGivenNull() {
        EuclideanVector e1 = new EuclideanVector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> e1.crossProduct(null));
    }

    @GradedTest("`crossProduct` should throw `IllegalArgumentException` if the vector given as argument is not three-dimensional")
    public void crossProductThrowsWhenGivenVectorHasDimensionDifferentFromThree() {
        EuclideanVector e1 = new EuclideanVector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> e1.crossProduct(new EuclideanVector(0, 1, 1, 2, 1, 2, 0, 18, 4, 2)));
    }

    @GradedTest("`crossProduct` should throw `IllegalArgumentException` or `IllegalStateException` if the calling vector is not three-dimensional")
    public void crossProductThrowsWhenCallingVectorHasDimensionDifferentFromThree() {
        EuclideanVector e1 = new EuclideanVector(1, 2, 3, 4);
        try {
            e1.crossProduct(new EuclideanVector(0, 1, 1));
        } catch (IllegalArgumentException e) {
            return;
        } catch (IllegalStateException e){
            return;
        }
        fail("Expected IllegalArgumentException or IllegalStateException.");

    }

    @GradedTest("`crossProduct` should return the correct cross product if the vectors are of the same dimension")
    public void crossProductIsCorrect() {
        EuclideanVector e1 = new EuclideanVector(1, 2, 3);
        assertThat(e1.crossProduct(new EuclideanVector(100, 2, 7)), is(new EuclideanVector(8, 293, -198)));
    }
}
