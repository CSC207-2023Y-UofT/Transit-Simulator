package app_business.dto;

import entity.ticket.TicketType;

import org.junit.jupiter.api.*;
public class TicketDTOTest {
    static double cost;
    static TicketType type;
    static int ticketId;
    static long expiry;
    static TicketDTO ticketDTO;

    @DisplayName("TicketDTOTest Class Setup")
    @BeforeAll
    static void setup() {
        cost = 99.99;
        type = TicketType.ADULT;
        ticketId = 99;
        expiry = 999999999;
        ticketDTO = new TicketDTO(cost, type, ticketId, true, expiry);  // convenient test constructor
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
        Assertions.assertTrue(ticketDTO.isActivated());
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
        expiry = 0;
        ticketDTO = null;
    }
}
