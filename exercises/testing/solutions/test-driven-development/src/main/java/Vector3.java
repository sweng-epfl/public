import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A `Vector3` is a 3-component vector of 32-bit floating point numbers.
 *
 * - Creating a vector using three values `x`, `y` and `z` assigns them to the corresponding
 * components in Euclidean space.
 * - The `zero` vector is defined as the vector with all components equal to zero.
 * - Copying a vector creates a new object with the same component values.
 * - Computing the dot product between two non-null vectors returns the sum over the product of each individual components.
 * - The length of a vector is defined as the square root of the dot product of the vector with itself.
 * - The cross product between two non-null vectors `a` and `b` is defined as the vector with first component
 *   `x = a.y * b.z - a.z * b.y`, second component `y = a.z * b.x - a.x * b.z` and third component `z = a.x * b.y - a.y * b.x`.
 * - The normalized value of a vector is such that every component is divided by the vector's length; it is not defined
 *   if the length is close to zero.
 * - Scaling a vector multiplies each component of the vector with a scalar value.
 * - Two vectors are considered equal their three individual components are strictly equal.
 */
public final class Vector3 {

    private float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3 zero() {
        return new Vector3(0.0f, 0.0f, 0.0f);
    }

    public Vector3 copy() {
        return new Vector3(this.x, this.y, this.z);
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    public boolean isZero() {
        return isAll(f -> f == 0.0f);
    }

    public float dot(Vector3 other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public float length() {
        return (float)Math.sqrt(dot(this));
    }

    public Vector3 cross(Vector3 other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }
        return new Vector3(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }

    public Vector3 normalized() {
        float length = length();
        if (length <= 1e-5) {
            return copy();
        }
        return scaled(1.0f / length);
    }

    public Vector3 scaled(float scalar) {
        return copy().apply(f -> f * scalar);
    }

    @Override
    public String toString() {
        String format;
        if (isOne(f -> f < 1e-2)) {
            format = "%e";
        } else {
            format = "%f";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        builder.append(String.format(format, this.x));
        builder.append(" ");
        builder.append(String.format(format, this.y));
        builder.append(" ");
        builder.append(String.format(format, this.z));
        builder.append(" ]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Float.compare(vector3.x, x) == 0 && Float.compare(vector3.y, y) == 0 && Float.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    private boolean isOne(Predicate<Float> predicate) {
        return predicate.test(this.x) || predicate.test(this.y) || predicate.test(this.z);
    }

    private boolean isAll(Predicate<Float> predicate) {
        return predicate.test(this.x) && predicate.test(this.y) && predicate.test(this.z);
    }

    private Vector3 apply(Function<Float, Float> function) {
        this.x = function.apply(this.x);
        this.y = function.apply(this.y);
        this.z = function.apply(this.z);
        return this;
    }
}
