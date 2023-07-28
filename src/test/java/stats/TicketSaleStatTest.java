package stats;

import stats.event.TicketSaleStat;
import ticket.AdultTicket;
import ticket.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test for the TicketSaleStat class.
 */
public class TicketSaleStatTest {

    private TicketSaleStat ticketSaleStat;
    private Ticket testTicket;
    private final double delta = 0.001;  // a small delta to compare double values

    /**
     * Setup before each test case.
     */
    @BeforeEach
    public void setUp() {
        testTicket = new AdultTicket();
        ticketSaleStat = new TicketSaleStat(testTicket);
    }

    /**
     * Test to check if the getTicket method is working correctly.
     */
    @Test
    public void testGetTicket() {
        assertSame(testTicket, ticketSaleStat.getTicket(),
                "The ticket returned by getTicket() is not the same as the expected ticket.");
    }

    /**
     * Test to check if the getRevenue method is working correctly.
     */
    @Test
    public void testGetRevenue() {
        assertEquals(testTicket.getPrice(), ticketSaleStat.getRevenue(), delta,
                "The revenue returned by getRevenue() did not match the expected revenue.");
    }
}
