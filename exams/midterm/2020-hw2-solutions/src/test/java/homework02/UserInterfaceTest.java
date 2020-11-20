package homework02;

import grading.GradedCategory;
import grading.GradedTest;
import homework02.util.*;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GradedCategory("Part 2.4: UserInterface")
public final class UserInterfaceTest {

    private Manager manager;
    private UserInterface userInterface;

    @BeforeEach
    public void init() {
        manager = new TestInventoryManager(new InventoryDatabase());
        var logger = new TestInventoryLogger(manager);
        var supplier = new TestInventorySupplier(manager);
        userInterface = new UserInterface(manager, logger, supplier);
    }

    @GradedTest("The constructor should throw an `IllegalArgumentException` if passed a null manager")
    public void test00userInterfaceThrowsOnConstructionWithNullManager() {
        assertThrows(IllegalArgumentException.class,
                () -> new UserInterface(null, new InventoryLogger(manager), new InventorySupplier(manager)));
    }

    @GradedTest("The constructor should throw an `IllegalArgumentException` if passed a null logger")
    public void test01userInterfaceThrowsOnConstructionWithNullLogger() {
        assertThrows(IllegalArgumentException.class,
                () -> new UserInterface(manager, null, new InventorySupplier(manager)));
    }

    @GradedTest("The constructor should throw an `IllegalArgumentException` if passed a null supplier")
    public void test02userInterfaceThrowsOnConstructionWithNullSupplier() {
        assertThrows(IllegalArgumentException.class,
                () -> new UserInterface(manager, new InventoryLogger(manager), null));
    }

    @GradedTest("`take` should return false if the item is not in the inventory")
    public void test03takeIsCorrectAndReturnsFalseOnNonExistentItem() {
        assertThat(userInterface.take(ItemKind.NINTENDO_SWITCH), is(false));
    }

    @GradedTest("`take` should succeed after calling `add` with the same element")
    public void test04addAndGetAreCorrect() {
        userInterface.add(ItemKind.NINTENDO_SWITCH);
        assertThat(userInterface.take(ItemKind.NINTENDO_SWITCH), is(true));
    }

    @GradedTest("`getLog` should return a string composed of all movements' strings, separated by a newline character, in the order in which they were produced")
    public void test05getLogIsCorrect() {
        userInterface.add(ItemKind.NINTENDO_SWITCH);
        userInterface.add(ItemKind.SUPER_MARIO);
        var movement1 = new Movement(ItemKind.NINTENDO_SWITCH, MovementType.ADD);
        var movement2 = new Movement(ItemKind.SUPER_MARIO, MovementType.ADD);

        String res = userInterface.getLog();
        res = res.trim();
        var resArray = res.split("\n");

        StringBuilder builder = new StringBuilder();
        for (String s : resArray) {
            builder.append(s.trim());
            builder.append('\n');
        }
        assertThat(builder.toString().trim(), is(movement1.toString()+ "\n" + movement2.toString()));
    }

    @GradedTest("`getItems` should return a string composed of one line for each item kind value")
    public void test06getItemsIsCorrect() {
        userInterface.add(ItemKind.NINTENDO_SWITCH);
        userInterface.add(ItemKind.NINTENDO_SWITCH);

        String[] results = new String[7];
        results[0] = "ItemKind=\tT_SHIRT\tQuantity=\t0";
        results[1] = "ItemKind=\tSHIRT\tQuantity=\t0";
        results[2] = "ItemKind=\tJEANS\tQuantity=\t0";
        results[3] = "ItemKind=\tNINTENDO_SWITCH\tQuantity=\t2";
        results[4] = "ItemKind=\tAPPLE_WATCH\tQuantity=\t0";
        results[5] = "ItemKind=\tIPHONE\tQuantity=\t0";
        results[6] = "ItemKind=\tSUPER_MARIO\tQuantity=\t0";

        var itemsListToTest = userInterface.getItems();
        assertThat(itemsListToTest.contains(results[0]), is(true));
        assertThat(itemsListToTest.contains(results[1]), is(true));
        assertThat(itemsListToTest.contains(results[2]), is(true));
        assertThat(itemsListToTest.contains(results[3]), is(true));
        assertThat(itemsListToTest.contains(results[4]), is(true));
        assertThat(itemsListToTest.contains(results[5]), is(true));
        assertThat(itemsListToTest.contains(results[6]), is(true));
    }

    @GradedTest("`setThreshold` should set the supplier's threshold")
    public void test07setThresholdIsCorrect() {
        userInterface.add(ItemKind.NINTENDO_SWITCH);
        userInterface.add(ItemKind.NINTENDO_SWITCH);
        userInterface.setSupplyQuantity(1);
        userInterface.setThreshold(2);
        userInterface.take(ItemKind.NINTENDO_SWITCH);
        assertThat(manager.getQuantity(ItemKind.NINTENDO_SWITCH), is(2));
    }

    @GradedTest("`setSupplyQuantity` should set the supplier's supply quantity")
    public void test08setSupplyQuantityIsCorrect() {
        userInterface.add(ItemKind.NINTENDO_SWITCH);
        userInterface.setSupplyQuantity(1);
        userInterface.setThreshold(1);
        userInterface.take(ItemKind.NINTENDO_SWITCH);
        assertThat(manager.getQuantity(ItemKind.NINTENDO_SWITCH), is(1));
    }

    @GradedTest("`add` should throw an `IllegalArgumentException` if passed a null item ")
    public void test09addWithNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> userInterface.add(null));
    }

    @GradedTest("`take` should throw an `IllegalArgumentException` if passed a null item")
    public void test10stakeWithNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> userInterface.take(null));
    }
}
