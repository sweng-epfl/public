package homework02;

import grading.GradedCategory;
import grading.GradedTest;
import homework02.util.InventoryObserver;
import homework02.util.Manager;
import homework02.util.Movement;
import homework02.util.MovementType;
import homework02.util.TestInventoryObserver;
import homework02.util.TestInventoryObserverBis;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GradedCategory("Part 2.1: InventoryManager")
public final class InventoryManagerTest {

    private TestInventoryObserver observer;
    private Manager manager;

    @BeforeEach
    public void init() {
        observer = new TestInventoryObserver();
        manager = new InventoryManager(new InventoryDatabase());

    }

    // Tests without observers

    @GradedTest("The constructor should throw an `IllegalArgumentException` if passed a null database")
    public void test00managerThrowsOnConstructionWithNullDatabase() {
        assertThrows(IllegalArgumentException.class, () -> new InventoryManager(null));
    }

    @GradedTest("`add` should throw an `IllegalArgumentException` if passed a null `ItemKind`")
    public void test01addThrowsOnNullItemKind() {
        assertThrows(IllegalArgumentException.class, () -> manager.add(null));
    }

    @GradedTest("`add` should add the element to the database if it is not already there")
    public void test02addNewElementIsCorrect() {
        manager.add(ItemKind.IPHONE);
        assertThat(manager.getQuantity(ItemKind.IPHONE), is(1));
    }

    @GradedTest("`add` should add two items to the inventory database if it is called twice with the same element")
    public void test03addExistingElementIsCorrect() {
        manager.add(ItemKind.IPHONE);
        manager.add(ItemKind.IPHONE);
        assertThat(manager.getQuantity(ItemKind.IPHONE), is(2));
    }

    @GradedTest("`getQuantity` should return 0 if the item is not in the inventory")
    public void test04getQuantityForNonExistingItemKindIsCorrect() {
        assertThat(manager.getQuantity(ItemKind.JEANS), is(0));
    }

    @GradedTest("`getQuantity` should return 3 if the item is 3 times in the inventory")
    public void test05getQuantityIsCorrect() {
        manager.add(ItemKind.JEANS);
        manager.add(ItemKind.JEANS);
        manager.add(ItemKind.JEANS);
        assertThat(manager.getQuantity(ItemKind.JEANS), is(3));
    }

    @GradedTest("`getQuantity` should throw an `IllegalArgumentException` if passed a null `ItemKind`")
    public void test06getQuantityThrowsOnNullItemKind() {
        assertThrows(IllegalArgumentException.class, () -> manager.getQuantity(null));
    }

    @GradedTest("`take` should return false if the item is not in the inventory")
    public void test07takeNonExistingItemIsCorrect() {
        assertThat(manager.take(ItemKind.SUPER_MARIO), is(false));
    }

    @GradedTest("`take` should return false if the item is exhausted")
    public void test08takeExhaustedItemIsCorrect() {
        manager.add(ItemKind.SUPER_MARIO);
        manager.take(ItemKind.SUPER_MARIO);
        assertThat(manager.take(ItemKind.SUPER_MARIO), is(false));
    }

    @GradedTest("`take` should return true and remove one item from the inventory if the item is indeed in the inventory")
    public void test09takeItemIsCorrect() {
        manager.add(ItemKind.SUPER_MARIO);
        manager.add(ItemKind.SUPER_MARIO);
        assertThat(manager.take(ItemKind.SUPER_MARIO), is(true));
        assertThat(manager.getQuantity(ItemKind.SUPER_MARIO), is(1));
    }

    @GradedTest("`take` should throw an `IllegalArgumentException` if passed a null `ItemKind`")
    public void test10takeThrowsOnNullItemKind() {
        assertThrows(IllegalArgumentException.class, () -> manager.take(null));
    }

    // Tests with observers

    @GradedTest("`addObserver` should throw an `IllegalArgumentException` if passed a null observer")
    public void test11addObserverThrowsOnNullObserver() {
        assertThrows(IllegalArgumentException.class, () -> manager.addObserver(null));
    }

    @GradedTest("`add` should correctly notify the observers")
    public void test12addCorrectlyNotifiesObservers() {
        manager.addObserver(observer);
        
        manager.add(ItemKind.SHIRT);
        assertThat(observer.getMovement(), is(new Movement(ItemKind.SHIRT, MovementType.ADD)));
    }

    @GradedTest("`take` should correctly notify the observers")
    public void test13takeCorrectlyNotifiesObservers() {
        manager.addObserver(observer);

        manager.add(ItemKind.SHIRT);
        manager.take(ItemKind.SHIRT);
        assertThat(observer.getMovement(), is(new Movement(ItemKind.SHIRT, MovementType.REMOVE)));
    }

    @GradedTest("`addObserver` should not add the observer again if it was previously added")
    public void test14addObserverDoesNotAddTwice(){
        TestInventoryObserverBis obs = new TestInventoryObserverBis();
        manager.addObserver(obs);
        manager.addObserver(obs);
        manager.add(ItemKind.IPHONE);
        assertThat(obs.getUpdateCounter(), is(1));
    }
}
