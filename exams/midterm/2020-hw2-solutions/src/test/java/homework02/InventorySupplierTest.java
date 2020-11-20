package homework02;

import grading.GradedCategory;
import grading.GradedTest;
import homework02.util.Manager;
import homework02.util.Supplier;
import homework02.util.TestInventoryManager;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GradedCategory("Part 2.3: InventorySupplier")
public final class InventorySupplierTest {

    private Supplier supplier;
    private Manager manager;

    @BeforeEach
    public void init() {
        manager = new TestInventoryManager(new InventoryDatabase());
        supplier = new InventorySupplier(manager);
    }

    @GradedTest("The constructor should throw an `IllegalArgumentException` if passed a null manager")
    public void test00supplierThrowsOnConstructionWithNullManager() {
        assertThrows(IllegalArgumentException.class, () -> new InventorySupplier(null));
    }

    @GradedTest("The supplier should be notified on item addition, and it should do nothing if the quantity is above the threshold")
    public void test01supplierIsNotifiedOnItemAdditionAndHasNothingToDo() {
        var quantity = manager.getQuantity(ItemKind.T_SHIRT);
        manager.add(ItemKind.T_SHIRT);
        manager.add(ItemKind.T_SHIRT);
        assertThat(manager.getQuantity(ItemKind.T_SHIRT), is(quantity + 2));
    }

    @GradedTest("The supplier should be notified on item deletion, and it should do nothing if the quantity is above the threshold")
    public void test02supplierIsNotifiedOnItemDeletionAndHasNothingToDo() {
        var quantity = manager.getQuantity(ItemKind.T_SHIRT);
        manager.add(ItemKind.T_SHIRT);
        manager.add(ItemKind.T_SHIRT);
        manager.take(ItemKind.T_SHIRT);
        assertThat(manager.getQuantity(ItemKind.T_SHIRT), is(quantity + 1));
    }

    @GradedTest("The supplier should be notified on item deletion, and it should do nothing if the quantity is above the threshold (with custom threshold)")
    public void test03supplierIsNotifiedOnItemDeletionAndHasNothingToDoWithCustomThreshold() {
        supplier.setThreshold(2);
        manager.add(ItemKind.T_SHIRT);
        manager.add(ItemKind.T_SHIRT);
        manager.take(ItemKind.T_SHIRT);
        assertThat(manager.getQuantity(ItemKind.T_SHIRT), is(1));
    }

    @GradedTest("The supplier should be notified on item deletion, and it should supply five additional items (with custom threshold and supply quantity)")
    public void test04supplierIsNotifiedOnItemDeletionAndSuppliesWithCustomThresholdAndSupplyQuantity() {
        supplier.setSupplyQuantity(5);
        supplier.setThreshold(2);
        manager.add(ItemKind.T_SHIRT);
        manager.add(ItemKind.T_SHIRT);
        manager.take(ItemKind.T_SHIRT);
        assertThat(manager.getQuantity(ItemKind.T_SHIRT), is(6));
    }

    @GradedTest("Setting the supply quantity to zero via `setSupplyQuantity` should set the threshold to zero as well")
    public void test05setThresholdToZeroSetsQuantityToZero() {
        manager.add(ItemKind.T_SHIRT);
        manager.add(ItemKind.T_SHIRT);
        manager.add(ItemKind.T_SHIRT);
        supplier.setThreshold(2);
        supplier.setSupplyQuantity(2);
        supplier.setSupplyQuantity(0);
        manager.take(ItemKind.T_SHIRT);
        supplier.setThreshold(2);
        assertThat(manager.getQuantity(ItemKind.T_SHIRT), is(2));
    }

    @GradedTest("Setting a negative threshold via `setThreshold` should throw an `IllegalArgumentException`")
    public void test06setThresholdThrowsOnNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> supplier.setThreshold(-1));
    }

    @GradedTest("Setting a negative supply quantity via `setSupplyQuantity` should throw an `IllegalArgumentException`")
    public void test07setSupplyQuantityThrowsOnNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> supplier.setSupplyQuantity(-1));
    }

    @GradedTest("`update` should throw an `IllegalArgumentException` if passed a null movement")
    public void test08updateThrowsOnNullMovement() {
        assertThrows(IllegalArgumentException.class, () -> supplier.update(null));
    }
}
