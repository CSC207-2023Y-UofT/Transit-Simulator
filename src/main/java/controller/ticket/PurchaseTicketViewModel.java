package controller.ticket;

import ticket.TicketType;

import java.util.ArrayList;
import java.util.List;

public class PurchaseTicketViewModel {
    private final List<TicketType> ticketTypesList = new ArrayList<>();

    public void addTicket(TicketType type) {
        if (ticketTypesList.size() >= 12) return;
        ticketTypesList.add(type);
    }

    public void removeTicket(TicketType type) {
        ticketTypesList.remove(type);
    }

    public List<TicketType> getTicketTypesList() {
        return ticketTypesList;
    }

    public void reset() {
        ticketTypesList.clear();
    }

    public int count(TicketType type) {
        int count = 0;
        for (TicketType ticketType : ticketTypesList) {
            if (ticketType.equals(type)) {
                count++;
            }
        }
        return count;
    }

    public double getTotalCost() {
        double totalPrice = 0;
        for (TicketType ticketType : ticketTypesList) {
            totalPrice += ticketType.getPrice();
        }
        return totalPrice;
    }

}
