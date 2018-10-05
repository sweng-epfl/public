package ch.epfl.sweng.testing.solutions.part2;

import org.junit.Before;
import org.junit.Test;

import static ch.epfl.sweng.testing.solutions.part2.Category.NORMAL;
import static org.junit.Assert.assertEquals;

public class TicketTest {
    private Ticket ticket;

    @Before
    public void setUp() {
        ticket = new Ticket(NORMAL, "Geneva", "Maroon 5");
    }

    @Test
    public void ticketCategoryTest() {
        assertEquals(ticket.getCategory(), NORMAL);
    }

    @Test
    public void ticketLocationTest() {
        assertEquals(ticket.getLocation(), "Geneva");
    }

    @Test
    public void ticketGroupTest() {
        assertEquals(ticket.getLocation(), "Maroon 5");
    }
}
