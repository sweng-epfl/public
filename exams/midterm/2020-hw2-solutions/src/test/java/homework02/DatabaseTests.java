package homework02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class DatabaseTests {

    private InventoryDatabase database;

    @BeforeEach
    public void init() {
        database = new InventoryDatabase();
    }

    @Test
    public void databaseThrowsOnConstructionWithNullDatabase() {
        assertThrows(IllegalArgumentException.class, () -> new InventoryDatabase(null));
    }

    @Test
    public void updateThrowsOnNullItemKind() {
        assertThrows(IllegalArgumentException.class, () -> database.update(null, 2));
    }

    @Test
    public void updateThrowsOnNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> database.update(ItemKind.SUPER_MARIO, -10));
    }

    @Test
    public void getThrowsOnNullItemKind() {
        assertThrows(IllegalArgumentException.class, () -> database.get(null));
    }

    @Test
    public void updateAndGetAreCorrect() {
        database.update(ItemKind.APPLE_WATCH, 2);
        assertThat(database.get(ItemKind.APPLE_WATCH), is(2));
    }

    @Test
    public void getReturnsZeroOnNonExistentItem() {
        assertThat(database.get(ItemKind.JEANS), is(0));
    }
}
