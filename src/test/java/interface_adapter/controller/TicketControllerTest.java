package interface_adapter.controller;

import app_business.boundary.ITicketInteractor;
import app_business.dto.TicketDTO;
import entity.ticket.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;


/**
 * Tests for the TicketController class. Take note of the mocking of the ITicketInteractor interface; this is because
 * we want to isolate the behavior of the interactor from the controller, since we are testing only the controller.
 * The mock class basically assumes that the interactor is working as intended, and that it does return the expected
 * values; that's why we skip the logic inside the interactor and assume it gives valid results.
 */
public class TicketControllerTest {
    TicketController ticketController;
    MockTicketInteractor mockTicketInteractor;

    @DisplayName("TicketControllerTest Class Setup")
    @BeforeEach
    public void setup() {
        mockTicketInteractor = new MockTicketInteractor();
        ticketController = new TicketController(mockTicketInteractor);
    }

    @Test
    public void testBuyTickets() {
        List<TicketDTO> tickets = ticketController.buyTickets(List.of(TicketType.CHILD, TicketType.ADULT));  // Whether it's ADULT or another enum type, neither the number of arguments, we don't care; it should not affect the logic inside StatsController.

        Assertions.assertEquals(2, tickets.size());
    }

    @Test
    public void testActivateTicket() {
        ticketController.activateTicket(123);

        Assertions.assertTrue(mockTicketInteractor.wasTicketActivated(123));
    }

    @Test
    public void testGetTicket() {
        Optional<TicketDTO> ticket = ticketController.getTicket(123);

        Assertions.assertTrue(ticket.isPresent());
        Assertions.assertEquals(123, ticket.get().getTicketId());
    }


    private static class MockTicketInteractor implements ITicketInteractor {
        final List<Integer> wasActivated = new ArrayList<>();
        @Override
        public List<TicketDTO> buyTickets(List<TicketType> ticketTypes) {
            return List.of(
                    new TicketDTO(1, TicketType.CHILD, 10, false, -1),
                    new TicketDTO(2, TicketType.ADULT, 11, false, -1)
            );
        }

        @Override
        public Optional<TicketDTO> activateTicket(int ticketId) {
            wasActivated.add(ticketId);  // record that the ticket with id ticketId was activated
            return Optional.of(new TicketDTO(5, TicketType.ADULT, ticketId, true, 7200000));
        }

        @Override
        public Optional<TicketDTO> getTicket(int ticketId) {
            return Optional.of(new TicketDTO(6, TicketType.ADULT, ticketId, false, -1));
        }

        @Override
        public void cleanTickets() {
            // Do nothing
        }

        public boolean wasTicketActivated(int ticketId) {
            return wasActivated.contains(ticketId);
        }
    }

}
