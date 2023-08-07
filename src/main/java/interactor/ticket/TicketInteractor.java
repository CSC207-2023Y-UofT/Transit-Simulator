package interactor.ticket;

import stats.entry.impl.TicketSaleStat;
import stats.persistence.StatDataController;
import ticket.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class serves as an intermediary between the system and ticket-related operations,
 * facilitating actions such as buying and activating tickets.
 */
public class TicketInteractor implements ITicketInteractor {

    /** Controller for recording statistics about ticket sales. */
    private final StatDataController stats;

    /** Data store to manage and store ticket information. */
    private final TicketDataStore dataStore;

    /**
     * Constructs a new TicketInteractor with a specified ticket data store and stats controller.
     *
     * @param dataStore The data store for ticket-related data.
     * @param stats     The stats controller to record ticket statistics.
     */
    public TicketInteractor(TicketDataStore dataStore, StatDataController stats) {
        this.dataStore = dataStore;
        this.stats = stats;
    }

    /**
     * Buys tickets for the given ticket types.
     *
     * @param ticketTypes List of ticket types to be bought.
     * @return List of bought tickets.
     */
    public List<BoughtTicket> buyTickets(List<TicketType> ticketTypes) {

        List<Ticket> tickets = new ArrayList<>();

        for (TicketType ticketType : ticketTypes) {
            Ticket ticket = new Ticket(ticketType);
            tickets.add(ticket);

            dataStore.saveTicket(ticket);
        }

        for (Ticket ticket : tickets) {
            TicketSaleStat saleStat = new TicketSaleStat(ticket);
            stats.record(saleStat);
        }

        List<BoughtTicket> response = new ArrayList<>();

        for (Ticket ticket : tickets) {
            BoughtTicket boughtTicket = convertTicket(ticket);
            response.add(boughtTicket);
        }

        return response;
    }

    /**
     * Retrieves the information of a bought ticket by its ID.
     *
     * @param ticketId ID of the ticket to be retrieved.
     * @return An optional containing the bought ticket information if found, empty otherwise.
     */
    @Override
    public Optional<BoughtTicket> getTicket(int ticketId) {
        Ticket ticket = dataStore.getTicket(ticketId).orElse(null);
        if (ticket == null) return Optional.empty();
        return Optional.of(convertTicket(ticket));
    }

    /**
     * Activates a ticket based on its ID and returns its information.
     *
     * @param ticketId ID of the ticket to be activated.
     * @return An optional containing the activated ticket information if found and activated, empty otherwise.
     */
    @Override
    public Optional<BoughtTicket> activateTicket(int ticketId) {
        Ticket ticket = dataStore.getTicket(ticketId).orElse(null);
        if (ticket == null) return Optional.empty();
        if (ticket.isActivated()) return Optional.of(convertTicket(ticket));
        ticket.activate();
        dataStore.saveTicket(ticket);
        return Optional.of(new BoughtTicket(ticket.getPrice(),
                ticket.getType(),
                ticket.getId(),
                ticket.isActivated(),
                ticket.getExpiry()));
    }

    /**
     * Converts a Ticket object to a BoughtTicket object.
     *
     * @param ticket The Ticket object to be converted.
     * @return The converted BoughtTicket object.
     */
    private BoughtTicket convertTicket(Ticket ticket) {
        return new BoughtTicket(ticket.getPrice(),
                ticket.getType(),
                ticket.getId(),
                ticket.isActivated(),
                ticket.getExpiry());
    }
}
