package homework02;

import grading.GradedCategory;
import grading.GradedTest;
import homework02.util.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GradedCategory("Part 2.2: InventoryLogger")
public final class InventoryLoggerTest {

    private Logger logger;
    private Manager manager;

    @BeforeEach
    public void init() {
        manager = new TestInventoryManager(new InventoryDatabase());
        logger = new InventoryLogger(manager);
    }

    @GradedTest("The constructor should throw an `IllegalArgumentException` if passed a null manager")
    public void test00loggerThrowsOnConstructionWithNullManager() {
        assertThrows(IllegalArgumentException.class, () -> new InventoryLogger(null));
    }

    @GradedTest("The logger should be notified on item addition, and it should add the movement to the list")
    public void test01loggerIsNotifiedOnItemAddition() {
        manager.add(ItemKind.T_SHIRT);
        var result = List.of(new Movement(ItemKind.T_SHIRT, MovementType.ADD).toString());
        assertThat(logger.getMovements(), is(result));
    }

    @GradedTest("The logger should be notified on multiple updates, and it should add all the movements to the list")
    public void test02loggerIsNotifiedOnMultipleUpdates() {
        manager.add(ItemKind.T_SHIRT);
        manager.take(ItemKind.T_SHIRT);
        var result = new String[] { new Movement(ItemKind.T_SHIRT, MovementType.ADD).toString(),
                new Movement(ItemKind.T_SHIRT, MovementType.REMOVE).toString() };
        assertThat(logger.getMovements(), containsInAnyOrder(result));
    }

    @GradedTest("`getMovements` should return a copy of the list")
    public void test03getMovementsReturnsACopy() {
        var movements = logger.getMovements();
        var movementsSize = movements.size();
        movements.add("A test!");
        assertThat(logger.getMovements().size(), is(movementsSize));
    }

    @GradedTest("`update` should throw an `IllegalArgumentException` if passed a null movement")
    public void test04updateThrowsOnNullMovement() {
        assertThrows(IllegalArgumentException.class, () -> logger.update(null));
    }

    @GradedTest("`empty` should empty the logger's list of movements")
    public void test05emptyIsCorrect() {
        manager.add(ItemKind.T_SHIRT);
        logger.empty();
        assertThat(logger.getMovements().isEmpty(), is(true));
    }
}
