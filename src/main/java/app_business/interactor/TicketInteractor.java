package app_business.interactor;

import app_business.boundary.ITicketInteractor;
import app_business.dto.TicketDTO;
import stats.StatTracker;
import stats.entry.impl.revenue.TicketSaleStat;
import entity.ticket.Ticket;
import persistence.boundary.TicketDataStore;
import entity.ticket.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Interactor class for ticket operations.
 * Implements the {@link ITicketInteractor} interface for ticket-related actions.
 */
public class TicketInteractor implements ITicketInteractor {

    /** Controller for statistics data. */
    private final StatTracker stats;

    /** Data storage for tickets. */
    private final TicketDataStore dataStore;

    /**
     * Constructs a TicketInteractor instance.
     *
     * @param dataStore The ticket data store.
     * @param stats The statistics data controller.
     */
    public TicketInteractor(TicketDataStore dataStore, StatTracker stats) {
        this.dataStore = dataStore;
        this.stats = stats;
    }

    /**
     * Purchase a list of tickets based on given ticket types.
     * Records the sale of each ticket for statistical purposes.
     *
     * @param ticketTypes The list of ticket types to purchase.
     * @return A list of {@link TicketDTO} objects representing purchased tickets.
     */
    public List<TicketDTO> buyTickets(List<TicketType> ticketTypes) {

        List<Ticket> tickets = new ArrayList<>();

        for (TicketType ticketType : ticketTypes) {
            Ticket ticket = new Ticket(ticketType);
            tickets.add(ticket);

            dataStore.save(ticket);
        }

        for (Ticket ticket : tickets) {
            TicketSaleStat saleStat = new TicketSaleStat(ticket);
            stats.record(saleStat);
        }

        List<TicketDTO> response = new ArrayList<>();

        for (Ticket ticket : tickets) {
            TicketDTO ticketDTO = toDTO(ticket);
            response.add(ticketDTO);
        }

        return response;
    }

    /**
     * Fetches the details of a specific ticket by its ID.
     *
     * @param ticketId The ID of the ticket to retrieve.
     * @return An optional {@link TicketDTO} containing ticket details, if found.
     */
    @Override
    public Optional<TicketDTO> getTicket(int ticketId) {
        Ticket ticket = dataStore.find(ticketId).orElse(null);
        if (ticket == null) return Optional.empty();
        return Optional.of(toDTO(ticket));
    }

    /**
     * Activates a specific ticket by its ID.
     *
     * @param ticketId The ID of the ticket to activate.
     */
    @Override
    public void activateTicket(int ticketId) {
        Ticket ticket = dataStore.find(ticketId).orElse(null);
        if (ticket == null) return;
        if (ticket.isActivated()) return;
        ticket.activate();
        dataStore.save(ticket);
        new TicketDTO(ticket.getPrice(),
                ticket.getType(),
                ticket.getId(),
                ticket.isActivated(),
                ticket.getExpiry());
    }

    /**
     * Cleans expired tickets from the data store.
     */
    @Override
    public void cleanTickets() {
        dataStore.cleanExpiredTickets();
    }

    /**
     * Converts a Ticket object into its corresponding DTO format.
     *
     * @param ticket The ticket to convert.
     * @return The {@link TicketDTO} representation of the ticket.
     */
    private TicketDTO toDTO(Ticket ticket) {
        return new TicketDTO(ticket.getPrice(),
                ticket.getType(),
                ticket.getId(),
                ticket.isActivated(),
                ticket.getExpiry());
    }

}
