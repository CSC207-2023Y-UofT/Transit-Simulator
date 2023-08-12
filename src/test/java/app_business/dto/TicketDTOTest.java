package app_business.dto;

import entity.ticket.TicketType;

import org.junit.jupiter.api.*;
public class TicketDTOTest {
    static double cost;
    static TicketType type;
    static int ticketId;
    static boolean activated;
    static long expiry;
    static TicketDTO ticketDTO;

    @DisplayName("TicketDTOTest Class Setup")
    @BeforeAll
    static void setup() {
        cost = 99.99;
        type = TicketType.ADULT;
        ticketId = 99;
        activated = true;
        expiry = 999999999;
        ticketDTO = new TicketDTO(cost, type, ticketId, activated, expiry);  // convenient test constructor
    }

    @Test
    void testGetCost() {
        Assertions.assertEquals(cost, ticketDTO.getCost());
    }

    @Test
    void testGetType() {
        Assertions.assertSame(type, ticketDTO.getType());
    }

    @Test
    void testGetTicketId() {
        Assertions.assertEquals(ticketId, ticketDTO.getTicketId());
    }

    @Test
    void testIsActivated() {
        Assertions.assertEquals(activated, ticketDTO.isActivated());
    }

    @Test
    void testGetExpiry() {
        Assertions.assertEquals(expiry, ticketDTO.getExpiry());
    }

    @DisplayName("TicketDTOTest Class Teardown")
    @AfterAll
    static void teardown() {
        cost = 0;
        type = null;
        ticketId = 0;
        activated = false;
        expiry = 0;
        ticketDTO = null;
    }
}
