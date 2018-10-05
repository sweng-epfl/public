package ch.epfl.sweng.testing.solutions.part2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ch.epfl.sweng.testing.solutions.part2.Category.VIP;

public class Shop {
    private List<Ticket> tickets;
    private List<String> promoCodes;

    public Shop() {
        tickets = new ArrayList<>();
        promoCodes = new ArrayList<>();

        promoCodes.add("VIP2018");
        promoCodes.add("PARTNER");
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Ticket buyTicket(String location) throws IllegalArgumentException{
        Iterator<Ticket> iterator = tickets.iterator();

        while(iterator.hasNext()) {
            Ticket t = iterator.next();

            if (t.getLocation().equals(location)) {
                iterator.remove();
                return t;
            }
        }

        throw new IllegalArgumentException("No Ticket with this location available");
    }

    public Ticket improveTicket(Ticket ticket, String promoCode) throws IllegalArgumentException {
        if (promoCodes.contains(promoCode)) {
            return new Ticket(VIP, ticket.getLocation(), ticket.getGroup());
        }

        throw new IllegalArgumentException("Invalid promo code");
    }

    public Ticket changeLocation(Ticket ticket, String location) throws IllegalArgumentException {
        Iterator<Ticket> iterator = tickets.iterator();

        Ticket t;

        while(iterator.hasNext()) {
            t = iterator.next();

            if (t.getGroup().equals(ticket.getGroup()) && t.getCategory() == ticket.getCategory() && t.getLocation().equals(location)) {
                iterator.remove();

                tickets.add(ticket);

                return t;
            }
        }

        throw new IllegalArgumentException("No equivalent ticket available");
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Tickets currently available: ");
        s.append(System.lineSeparator());
        s.append("----------------------------");
        s.append(System.lineSeparator());

        for(int i = 0; i < tickets.size(); i++) {
            s.append(String.format("Ticket %d : " + tickets.get(i).getCategory() + ", " + tickets.get(i).getLocation() + ", " + tickets.get(i).getGroup(), i));
            s.append(System.lineSeparator());
        }

        return s.toString();

    }


}
