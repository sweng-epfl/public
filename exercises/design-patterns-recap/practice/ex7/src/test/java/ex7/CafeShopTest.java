package ex7;

import org.junit.jupiter.api.Test;

// import static org.junit.Assert.assertEquals;

public class CafeShopTest {

    @Test
    public void testFunctionality() {
        Beverage espr = new Espresso();

// TODO: uncomment the code below, make it compile and pass the tests

//        assertEquals("Espresso $1.99", espr.getDescription() + " $" + espr.cost());
//        // add some condiments
//        espr = new Soy(espr);
//        espr = new Mocha(espr);
//        espr = new SteamedMilk(espr);
//        espr = new Whip(espr);
//        assertEquals("Espresso, Soy, Mocha, Steamed Milk, Whipped Milk $2.5400000000000005",
//                espr.getDescription() + " $" + espr.cost());
//
//
//        Beverage decaf = new Decaf();
//        assertEquals("Decaf $1.05", decaf.getDescription() + " $" + decaf.cost());
//        decaf = new Soy(new Whip(new Whip( new SteamedMilk(decaf))));
//        assertEquals("Decaf, Steamed Milk, Whipped Milk, Whipped Milk, Soy $1.5000000000000002",
//            decaf.getDescription() + " $" + decaf.cost());
    }
}
