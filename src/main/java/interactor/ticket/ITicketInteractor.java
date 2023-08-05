package interactor.ticket;

import java.util.List;

/**
 * Input boundary for ticket interactor
 */
public interface ITicketInteractor {
    double getChildTicketPrice();
    double getAdultTicketPrice();
    double getSeniorTicketPrice();
    double getStudentTicketPrice();

    List<BoughtTicket> buyTickets(int childTickets,
                                  int adultTickets,
                                  int seniorTickets,
                                  int studentTickets);

    BoughtTicket getTicket(int ticketId);
}
