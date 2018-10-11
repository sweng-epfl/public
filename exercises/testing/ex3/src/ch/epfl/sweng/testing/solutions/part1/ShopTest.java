package ch.epfl.sweng.testing.solutions.part2;

import org.junit.Before;
import org.junit.Test;

import static ch.epfl.sweng.testing.solutions.part2.Category.NORMAL;
import static ch.epfl.sweng.testing.solutions.part2.Category.STUDENT;
import static ch.epfl.sweng.testing.solutions.part2.Category.VIP;
import static org.junit.Assert.assertEquals;

public class ShopTest {
    private Shop shop;
    private Ticket t1 = new Ticket(NORMAL, "Geneva", "Maroon 5");;
    private Ticket t2 = new Ticket(STUDENT, "Zürich", "Linkin Park");
    private Ticket t3 = new Ticket(VIP, "Paris", "Muse");
    private Ticket t4 = new Ticket(NORMAL, "Berlin", "Maroon 5");
    private String correctPrint;
    private String addedPrint;
    private String boughtPrint;
    private String locationPrint;

    @Before
    public void setUp() {
        shop = new Shop();

        shop.addTicket(t1);
        shop.addTicket(t2);
        shop.addTicket(t3);

        String basePrint = "Tickets currently available: " + System.lineSeparator() + "----------------------------" + System.lineSeparator() ;

        correctPrint = basePrint + "Ticket 0 : NORMAL, Geneva, Maroon 5" + System.lineSeparator() + "Ticket 1 : STUDENT, Zürich, Linkin Park" + System.lineSeparator() + "Ticket 2 : VIP, Paris, Muse" + System.lineSeparator();
        addedPrint = basePrint + "Ticket 0 : NORMAL, Geneva, Maroon 5" + System.lineSeparator() + "Ticket 1 : STUDENT, Zürich, Linkin Park" + System.lineSeparator() + "Ticket 2 : VIP, Paris, Muse" + System.lineSeparator() + "Ticket 3 : NORMAL, Berlin, Maroon 5" + System.lineSeparator();
        boughtPrint = basePrint + "Ticket 0 : STUDENT, Zürich, Linkin Park" + System.lineSeparator() + "Ticket 1 : VIP, Paris, Muse" + System.lineSeparator();
        locationPrint = basePrint + "Ticket 0 : STUDENT, Zürich, Linkin Park" + System.lineSeparator() + "Ticket 1 : VIP, Paris, Muse" + System.lineSeparator() + "Ticket 2 : NORMAL, Berlin, Maroon 5" + System.lineSeparator();
    }

    @Test
    public void buyTicketTest() {
        Ticket t = shop.buyTicket("Paris");

        assertEquals(t.getLocation(), "Paris");
    }

    @Test(expected = IllegalArgumentException.class)
    public void buyTicketUnavailableTest() {
        shop.buyTicket("Lausanne");
    }

    @Test(expected = IllegalArgumentException.class)
    public void buyTicketTwiceTest() {
        Ticket t = shop.buyTicket("Geneva");

        assertEquals(t.getLocation(), "Geneva");

        shop.buyTicket("Geneva");
    }

    @Test
    public void improveTicketTest() {
        assertEquals(shop.improveTicket(t1, "VIP2018").getCategory(), VIP);
    }

    @Test(expected = IllegalArgumentException.class)
    public void improveTicketFakePromoCodeTest() {
        shop.improveTicket(t1, "VIP2016");
    }

    @Test
    public void changeLocationTest() {
        Ticket t = shop.changeLocation(t4, "Geneva");

        assertEquals(t.getLocation(), "Geneva");


    }

    @Test
    public void buyChangedTicketTest() {
        Ticket t = shop.changeLocation(t4, "Geneva");

        assertEquals(t.getLocation(), "Geneva");

        shop.buyTicket(t4.getLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLocationUnavailableTest() {
        shop.changeLocation(t1, "Berlin");
    }

    @Test
    public void printTest() {
        assertEquals(shop.toString(), correctPrint);
    }

    @Test
    public void printAddedTest() {
         shop.addTicket(t4);
        assertEquals(shop.toString(), addedPrint);
    }

    @Test
    public void printBoughtTest() {
        shop.buyTicket("Geneva");
        assertEquals(shop.toString(), boughtPrint);
    }

    @Test
    public void printLocationTest() {
         shop.changeLocation(t4, "Geneva");
         assertEquals(shop.toString(), locationPrint);
    }

}
