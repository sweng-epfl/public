package homework02;

import grading.GradedCategory;
import grading.GradedTest;
import homework02.util.Manager;
import homework02.util.TestInventoryLogger;
import homework02.util.TestInventoryManager;
import homework02.util.TestInventorySupplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GradedCategory("Part 2.5: UserInterfaceSingleton")
public final class UserInterfaceSingletonTest {

    private static Manager manager = new TestInventoryManager(new InventoryDatabase());

    @GradedTest("`getInstance` should always return the same instance")
    public void test01getInstanceReturnsTheSameInstantiatedInstance() {
        manager = new TestInventoryManager(new InventoryDatabase());
        var logger = new TestInventoryLogger(manager);
        var supplier = new TestInventorySupplier(manager);
        UserInterfaceSingleton.init(manager, logger, supplier);
        var instance = UserInterfaceSingleton.getInstance();
        assertThat(UserInterfaceSingleton.getInstance(), is(instance));
    }

    @GradedTest("`getInstance` shoud return null if `init` was not called previously")
    public void test00getInstanceReturnsNullIfInitWasNotCalledBefore() {
        assertThat(UserInterfaceSingleton.getInstance(), is(nullValue()));
    }

    @GradedTest("`init` should replace the instance with the new one if called a second time")
    public void test02invokingInitTwiceReplacesTheInstance(){
        manager = new TestInventoryManager(new InventoryDatabase());
        var logger = new TestInventoryLogger(manager);
        var supplier = new TestInventorySupplier(manager);
        UserInterfaceSingleton.init(manager, logger, supplier);
        var instance1 = UserInterfaceSingleton.getInstance();

        var manager2 = new TestInventoryManager(new InventoryDatabase());
        var logger2 = new TestInventoryLogger(manager);
        var supplier2 = new TestInventorySupplier(manager);

        UserInterfaceSingleton.init(manager2, logger2, supplier2);
        var instance2 = UserInterfaceSingleton.getInstance();
        assertThat(instance1 == instance2, is(false));
    }

    @GradedTest("`init` should throw an `IllegalArgumentException` if passed a null manager")
    public void test03userInterfaceThrowsOnConstructionWithNullManager() {
        assertThrows(IllegalArgumentException.class, () ->
                UserInterfaceSingleton.init(null, new TestInventoryLogger(manager), new TestInventorySupplier(manager)));
    }

    @GradedTest("`init` should throw an `IllegalArgumentException` if passed a null logger")
    public void test05userInterfaceThrowsOnConstructionWithNullLogger() {
        assertThrows(IllegalArgumentException.class, () ->
                UserInterfaceSingleton.init(manager, null, new TestInventorySupplier(manager)));
    }

    @GradedTest("`init` should throw an `IllegalArgumentException` if passed a null supplier")
    public void test06userInterfaceThrowsOnConstructionWithNullSupplier() {
        assertThrows(IllegalArgumentException.class, () ->
                UserInterfaceSingleton.init(manager, new TestInventoryLogger(manager), null));
    }
}
