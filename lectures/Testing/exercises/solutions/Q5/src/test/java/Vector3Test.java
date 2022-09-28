import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

final class Vector3Test {

    @Test
    public void constructorAssignsCorrectComponents() {
        float x = 1.0f;
        float y = 2.0f;
        float z = 3.0f;
        Vector3 v = new Vector3(x, y, z);

        assertThat(v.x, is(x));
        assertThat(v.y, is(y));
        assertThat(v.z, is(z));
    }

    @Test
    public void zeroIsZeroVector() {
        Vector3 v = Vector3.zero();

        assertThat(v.x, is(0.0f));
        assertThat(v.y, is(0.0f));
        assertThat(v.z, is(0.0f));
    }

    @Test
    public void copyReturnsIdenticalCopy() {
        float x = 1.0f;
        float y = 2.0f;
        float z = 3.0f;
        Vector3 v = new Vector3(x, y, z);
        Vector3 vc = v.copy();

        assertThat(vc.x, is(1.0f));
        assertThat(vc.y, is(2.0f));
        assertThat(vc.z, is(3.0f));
    }

    @Test
    public void isZeroReturnsTrueForZeroVector() {
        Vector3 v = Vector3.zero();

        assertThat(v.isZero(), is(true));
    }

    @Test
    public void isZeroReturnsFalseForNonzeroVector() {
        Vector3 v = new Vector3(1.0f, 2.0f, 3.0f);

        assertThat(v.isZero(), is(false));
    }

    @Test
    public void isZeroReturnsFalseForAlmostZeroVector() {
        Vector3 v = new Vector3(0.0f, 0.0f, Float.MIN_NORMAL);
    }

    @Test
    public void dotFailsWithNullVector() {
        Vector3 v = new Vector3(1.0f, 2.0f, 3.0f);
        assertThrows(IllegalArgumentException.class, () -> v.dot(null));
    }

    @Test
    public void dotWithZeroIsZero() {
        Vector3 v = new Vector3(1.0f, 2.0f, 3.0f);

        assertThat(v.dot(Vector3.zero()), is(0.0f));
    }

    @Test
    public void dotWithTwoNonzeroVectorIsCorrect() {
        float dot = 2.0f;
        Vector3 v1 = new Vector3(1.0f, 2.0f, 3.0f);
        Vector3 v2 = new Vector3(-1.0f, 0.0f, 1.0f);

        assertThat(v1.dot(v2), is(dot));
        assertThat(v2.dot(v1), is(dot));
    }

    @Test
    public void lengthOfZeroIsZero() {
        assertThat(Vector3.zero().length(), is(0.0f));
    }

    @Test
    public void lengthOfVectorIsCorrect() {
        float length = 5.0f;
        Vector3 v = new Vector3(0.0f, 3.0f, 4.0f);

        assertThat(v.length(), is(length));
    }

    @Test
    public void crossFailsWithNullVector() {
        Vector3 v = new Vector3(1.0f, 2.0f, 3.0f);

        assertThrows(IllegalArgumentException.class, () -> v.cross(null));
    }

    @Test
    public void crossWithSelfIsZero() {
        Vector3 v = new Vector3(1.0f, 2.0f, 3.0f);

        assertThat(v.cross(v).isZero(), is(true));
    }

    @Test
    public void crossWithUnitAxisIsCorrect() {
        Vector3 x = new Vector3(1.0f, 0.0f, 0.0f);
        Vector3 y = new Vector3(0.0f, 1.0f, 0.0f);
        Vector3 z = new Vector3(0.0f, 0.0f, 1.0f);

        assertThat(x.cross(y), is(z));
    }

    @Test
    public void dotWithCrossProductResultIsZero() {
        Vector3 v1 = new Vector3(1.0f, 2.0f, 3.0f);
        Vector3 v2 = new Vector3(-1.0f, 1.0f, 2.0f);
        Vector3 c = v1.cross(v2);

        assertThat(c.dot(v1), is(0.0f));
        assertThat(c.dot(v2), is(0.0f));
    }

    @Test
    public void normalizedOfUnitVectorIsSelf() {
        Vector3 x = new Vector3(1.0f, 0.0f, 0.0f);

        assertThat(x.normalized(), is(x));
    }

    @Test
    public void normalizedOfLongVectorIsUnitLength() {
        Vector3 v = new Vector3(1.0f, 2.0f, 3.0f);

        assertThat((double) v.normalized().length(), closeTo(1.0, 1e-5));
    }

    @Test
    public void normalizedOfAlmostZeroVectorIsSelf() {
        Vector3 v = new Vector3(0.0f, 0.0f, Float.MIN_NORMAL);

        assertThat(v.normalized(), is(v));
    }

    @Test
    public void scaledVectorIsCorrect() {
        Vector3 v1 = new Vector3(1.0f, 2.0f, 3.0f);
        Vector3 v2 = new Vector3(2.0f, 4.0f, 6.0f);

        assertThat(v1.scaled(2.0f), is(v2));
    }

    @Test
    public void scaledVectorByZeroIsZero() {
        Vector3 v = new Vector3(1.0f, 2.0f, 3.0f);

        assertThat(v.scaled(0.0f).isZero(), is(true));
    }

    @Test
    public void zeroIsNotEqualToNull() {
        Vector3 v = Vector3.zero();

        assertThat(v.equals(null), is(false));
    }

    @Test
    public void zeroIsEqualToItself() {
        Vector3 v = Vector3.zero();

        assertThat(v.equals(v), is(true));
    }

    @Test
    public void zeroHasDeterministicHashcode() {
        Vector3 v1 = Vector3.zero();
        Vector3 v2 = Vector3.zero();

        assertThat(v1.hashCode(), is(v2.hashCode()));
    }

    @Test
    public void zeroToStringUsesScientificNotation() {
        Vector3 v = Vector3.zero();

        assertThat(v.toString(), containsStringIgnoringCase("e+00"));
    }

    @Test
    public void oneToStringUsesRegularNotation() {
        Vector3 v = new Vector3(1.0f, 1.0f, 1.0f);

        assertThat(v.toString(), not(containsStringIgnoringCase("e")));
    }
}
