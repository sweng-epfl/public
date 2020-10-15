package ch.sweng.inventory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class StepDefinitions {
    private Inventory inventory;

    @Given("there are {int} sweaters in the inventory")
    public void withXSweatersInInventory(int nSweaters){
        this.inventory = new Inventory();
        for (int i = 0; i < nSweaters; i++) {
            this.inventory.add(Product.SWEATER);
        }
    }

    @Given("there are {int} t-shirts in the inventory")
    public void withXTshirtsInInventory(int ntshirts){
        this.inventory = new Inventory();
        for (int i = 0; i < ntshirts; i++) {
            this.inventory.add(Product.TSHIRT);
        }
    }

    @When("I add {int} sweaters to the inventory")
    public void addXSweatersToInventory(int nSweaters){
        for (int i = 0; i < nSweaters; i++) {
            this.inventory.add(Product.SWEATER);
        }
    }

    @When("I remove {int} t-shirts from the inventory")
    public void addXTshirtsToInventory(int ntshirts){
        for (int i = 0; i < ntshirts; i++) {
            this.inventory.remove(Product.TSHIRT);
        }
    }

    @When("I refund a client that bought a t-shirt")
    public void refundTshirt(){
        this.inventory.returnAndRefund(Product.TSHIRT);
    }

    @Then("the inventory contains {int} t-shirts")
    public void checkInventoryContainsXTshirts(int nTshirts){
        assertEquals(nTshirts, this.inventory.getProductQuantity(Product.TSHIRT));
    }

    @Then("the inventory contains {int} sweaters")
    public void checkInventoryContainsXSweaters(int nSweaters){
        assertEquals(nSweaters, this.inventory.getProductQuantity(Product.SWEATER));
    }





}
