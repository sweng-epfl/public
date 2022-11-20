import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListAdapterTest {
    // These two should be fields in real-world code,
    // but here we want to make sure other tests can still pass if the construction of either of these fails,
    // so that students can get partial points
    private ListAdapter singleElementListAdapter() {
        return new ListAdapter(Arrays.asList(4));
    }
    private ListAdapter manyElementsListAdapter() {
        return new ListAdapter(Arrays.asList(6, 9, 4, 2, 0, 4, 2));
    }

    @Test
    @Order(0)
    @DisplayName("The constructor should throw an `IllegalArgumentException` when given a `null` list")
    public void constructorThrowsExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ListAdapter(null);
        });
    }

    @Test
    @Order(1)
    @DisplayName("The constructor should throw an `IllegalArgumentException` when given an empty list")
    public void constructorThrowsExceptionWhenEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ListAdapter(new ArrayList<>());
        });
    }

    @Test
    @Order(2)
    @DisplayName("`getRoot` should return the lone element of a single-element list")
    public void getRootReturnsElementWhenListHasOneElement() {
        assertThat(singleElementListAdapter().getRoot(), is(4));
    }

    @Test
    @Order(3)
    @DisplayName("`getRoot` should return the first element of a list with many elements")
    public void getRootReturnsFirstElementWhenListHasManyElements() {
        assertThat(manyElementsListAdapter().getRoot(), is(6));
    }

    @Test
    @Order(4)
    @DisplayName("`getLeft` should return the left sub-tree")
    public void getLeftReturnsCorrectSubtree() {
        assertThat(manyElementsListAdapter().getLeft().getRoot(), is(9));
        assertThat(manyElementsListAdapter().getLeft().getLeft().getRoot(), is(2));
    }

    @Test
    @Order(5)
    @DisplayName("`getRight` should return the right sub-tree")
    public void getRightReturnsCorrectSubtree() {
        assertThat(manyElementsListAdapter().getRight().getRoot(), is(4));
        assertThat(manyElementsListAdapter().getRight().getRight().getRoot(), is(2));
    }

    @Test
    @Order(6)
    @DisplayName("`getLeft` should return `null` for a single-element list")
    public void getLeftReturnsNullOnSingleElementList() {
        assertThat(singleElementListAdapter().getLeft(), is(nullValue()));
    }

    @Test
    @Order(7)
    @DisplayName("`getLeft` should return `null` when the underlying list is empty")
    public void getLeftReturnsNullWhenEmpty() {
        assertThat(manyElementsListAdapter().getLeft().getLeft().getLeft(), is(nullValue()));
    }

    @Test
    @Order(8)
    @DisplayName("`getRight` should return `null` for a single-element list")
    public void getRightReturnsNullOnSingleElementList() {
        assertThat(singleElementListAdapter().getRight(), is(nullValue()));
    }

    @Test
    @Order(9)
    @DisplayName("`getRight` should return `null` when the underlying list is empty")
    public void getRightReturnsNullWhenEmpty() {
        assertThat(manyElementsListAdapter().getRight().getRight().getRight(), is(nullValue()));
    }
}
