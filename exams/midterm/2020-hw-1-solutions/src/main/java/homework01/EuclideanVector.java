package homework01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class EuclideanVector implements Iterable<Integer> {
    private final List<Integer> values;


    public EuclideanVector(final int... values) {
        // Just because the array is usually passed in using special syntax doesn't mean it can't be null :-)
        if (values == null) {
            throw new IllegalArgumentException("Values should not be null.");
        }

        // Using an array as the internal representation and assigning it directly without cloning
        // allows one to create a vector with an array then change the array's values,
        // and the vector changes too, which breaks this class' immutability contract.

        // Passing the number of values to the ArrayList constructor ensures that
        // it will not have to resize, which could cause out-of-memory errors
        // if there are extremely many values.
        final List<Integer> mutableValues = new ArrayList<>(values.length);
        for (final int value : values) {
            mutableValues.add(value);
        }

        this.values = Collections.unmodifiableList(mutableValues);
    }


    public int value(final int index) {
        // The list will throw IndexOutOfBoundsException if the index is invalid,
        // so there is no need for an explicit check.
        return values.get(index - 1);
    }

    public int dimension() {
        return values.size();
    }

    public double norm() {
        return Math.sqrt(dotProduct(this));
    }

    public boolean isOrthogonalTo(final EuclideanVector other) {
        if (other == null) {
            throw new IllegalArgumentException("'other' cannot be null.");
        }
        if (dimension() != other.dimension()) {
            throw new IllegalArgumentException("'other' must be of the same dimension.");
        }

        return dotProduct(other) == 0.0;
    }

    @Override
    public Iterator<Integer> iterator() {
        // It is important to use an immutable list internally (see the constructor),
        // rather than just any List such as an ArrayList, as otherwise the list can be mutated
        // by using its iterator's remove() method.
        return values.iterator();
    }

    public EuclideanVector plus(final EuclideanVector other) {
        if (other == null) {
            throw new IllegalArgumentException("'other' cannot be null.");
        }
        if (dimension() != other.dimension()) {
            throw new IllegalArgumentException("'other' must be of the same dimension.");
        }

        int[] additionResults = new int[dimension()];
        for (int n = 1; n < dimension() + 1; n++) {
            additionResults[n - 1] = value(n) + other.value(n);
        }

        return new EuclideanVector(additionResults);
    }

    public EuclideanVector scalarDivide(final double scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("'scalar' cannot be zero.");
        }
        int[] divisionResults = new int[dimension()];
        for (int n = 1; n < dimension() + 1; n++) {
            divisionResults[n - 1] = (int) Math.round(value(n) / scalar);
        }

        return new EuclideanVector(divisionResults);
    }

    public EuclideanVector crossProduct(final EuclideanVector other) {
        if (other == null) {
            throw new IllegalArgumentException("'other' cannot be null.");
        }
        if (dimension() != other.dimension()) {
            throw new IllegalArgumentException("'other' must be of the same dimension.");
        }
        if (dimension() != 3) {
            throw new IllegalArgumentException("The vector must be of dimension 3.");
        }

        int d1 = value(2) * other.value(3) - value(3) * other.value(2);
        int d2 = value(3) * other.value(1) - value(1) * other.value(3);
        int d3 = value(1) * other.value(2) - value(2) * other.value(1);

        return new EuclideanVector(d1, d2, d3);
    }

    // The equality contract was not required for this class,
    // but it simplifies the implementation of PointSpace.

    @Override
    public boolean equals(final Object other) {
        if (other == null) return false;
        if (other.getClass() != EuclideanVector.class) return false;
        return values.equals(((EuclideanVector) other).values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }


    // Helper method to compute the dot product of two vectors,
    // used for both the length and the orthogonality check.
    // Assumes that the given vector is non-null and of the correct dimension.
    // Returns a double to make sure there are no overflows.
    private double dotProduct(final EuclideanVector other) {
        double result = 0;
        for (int n = 1; n < dimension() + 1; n++) {
            // Casting to double here is required to avoid overflows;
            // otherwise, the multiplication will be performed on ints,
            // which will overflow (and then be added as a double to result).
            result += (double) value(n) * other.value(n);
        }
        return result;
    }
}
