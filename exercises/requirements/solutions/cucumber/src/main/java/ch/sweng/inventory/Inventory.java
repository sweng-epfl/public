package ch.sweng.inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Product, Integer> inventory;

    public Inventory() {
        this.inventory = new HashMap<>();
    }
    public void add(Product product){
        this.inventory.putIfAbsent(product, 0);
        this.inventory.replace(product, this.inventory.get(product) + 1);
    }

    public int getProductQuantity(Product product){
        this.inventory.putIfAbsent(product, 0);
        return this.inventory.get(product);
    }

    /**
     * Remove one product from the inventory. If removing more than current quantity, the quantity is just 0.
     * @param product
     */
    public void remove(Product product) {
        this.inventory.putIfAbsent(product, 0);

        this.inventory.replace(product, Math.max(this.inventory.get(product) - 1, 0));
    }

    public void returnAndRefund(Product product){
        this.add(product);
    }
}

