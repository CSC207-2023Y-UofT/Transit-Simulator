package interface_adapter.ticket;

import entity.ticket.TicketType;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for purchasing tickets.
 */
public class PurchaseTicketViewModel {

    /**
     * The list of tickets to purchase
     */
    private final List<TicketType> ticketTypesList = new ArrayList<>();

    /**
     * Adds a ticket to the list of tickets to purchase
     */
    public void addTicket(TicketType type) {
        if (ticketTypesList.size() >= 12) return;
        ticketTypesList.add(type);
    }

    /**
     * Removes a ticket from the list of tickets to purchase
     *
     * @param type The type of ticket to remove
     */
    public void removeTicket(TicketType type) {
        ticketTypesList.remove(type);
    }

    /**
     * Gets the list of tickets to purchase
     */
    public List<TicketType> getTicketTypesList() {
        return ticketTypesList;
    }

    /**
     * Resets the list of tickets to purchase
     */
    public void reset() {
        ticketTypesList.clear();
    }

    /**
     * Gets the number of tickets of a given type
     *
     * @param type The type of ticket to count
     * @return The number of tickets of the given type
     */
    public int count(TicketType type) {
        return (int) ticketTypesList.stream()
                .filter(it -> it.equals(type))
                .count();
    }

    /**
     * Gets the total cost of all tickets to purchase
     */
    public double getTotalCost() {
        return ticketTypesList.stream()
                .mapToDouble(TicketType::getPrice)
                .sum();
    }

}
