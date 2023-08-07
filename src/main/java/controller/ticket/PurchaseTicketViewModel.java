package controller.ticket;

import ticket.TicketType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a view model for managing a list of ticket purchases.
 * It allows addition, removal, and querying of tickets.
 * The maximum number of ticket types that can be added to this model is limited to 12.
 */
public class PurchaseTicketViewModel {

    /** List of ticket types representing the user's selection. */
    private final List<TicketType> ticketTypesList = new ArrayList<>();

    /**
     * Adds a new ticket of the specified type to the list.
     * If the list already contains 12 ticket types, no more tickets will be added.
     *
     * @param type The type of ticket to add.
     */
    public void addTicket(TicketType type) {
        if (ticketTypesList.size() >= 12) return;
        ticketTypesList.add(type);
    }

    /**
     * Removes a ticket of the specified type from the list.
     * Only the first occurrence of the specified ticket type will be removed.
     *
     * @param type The type of ticket to remove.
     */
    public void removeTicket(TicketType type) {
        ticketTypesList.remove(type);
    }

    /**
     * Returns the list of ticket types added to this model.
     *
     * @return A list of {@link TicketType}.
     */
    public List<TicketType> getTicketTypesList() {
        return ticketTypesList;
    }

    /**
     * Clears the list of ticket types, effectively resetting the model.
     */
    public void reset() {
        ticketTypesList.clear();
    }

    /**
     * Counts the number of tickets of the specified type in the list.
     *
     * @param type The type of ticket to count.
     * @return The number of tickets of the specified type.
     */
    public int count(TicketType type) {
        int count = 0;
        for (TicketType ticketType : ticketTypesList) {
            if (ticketType.equals(type)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates and returns the total cost of all tickets in the list.
     *
     * @return The total cost of all tickets.
     */
    public double getTotalCost() {
        double totalPrice = 0;
        for (TicketType ticketType : ticketTypesList) {
            totalPrice += ticketType.getPrice();
        }
        return totalPrice;
    }

}
