package interface_adapter.viewmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.ticket.TicketType;
public class PurchaseTicketViewModelTest {

    private PurchaseTicketViewModel viewModel;

    @BeforeEach
    public void setup() {
        viewModel = new PurchaseTicketViewModel();
    }

    @Test
    public void testAddSingleTicket() {
        viewModel.addTicket(TicketType.ADULT);
        assertEquals(1, viewModel.count(TicketType.ADULT));
    }

    @Test
    public void testAddMultipleSameTickets() {
        viewModel.addTicket(TicketType.ADULT);
        viewModel.addTicket(TicketType.ADULT);
        assertEquals(2, viewModel.count(TicketType.ADULT));
    }

    @Test
    public void testAddDifferentTicketTypes() {
        viewModel.addTicket(TicketType.ADULT);
        viewModel.addTicket(TicketType.CHILD);
        assertEquals(1, viewModel.count(TicketType.ADULT));
        assertEquals(1, viewModel.count(TicketType.CHILD));
    }

    @Test
    public void testAddMoreThanCapacity() {
        for (int i = 0; i < 13; i++) {
            viewModel.addTicket(TicketType.STUDENT);
        }
        assertEquals(12, viewModel.count(TicketType.STUDENT));
    }

    @Test
    public void testRemoveExistingTicket() {
        viewModel.addTicket(TicketType.ADULT);
        viewModel.removeTicket(TicketType.ADULT);
        assertEquals(0, viewModel.count(TicketType.ADULT));
    }

    @Test
    public void testRemoveNonExistingTicket() {
        viewModel.removeTicket(TicketType.ADULT);
        assertEquals(0, viewModel.count(TicketType.ADULT));
    }

    @Test
    public void testRemoveFromMultipleSameTickets() {
        viewModel.addTicket(TicketType.ADULT);
        viewModel.addTicket(TicketType.ADULT);
        viewModel.removeTicket(TicketType.ADULT);
        assertEquals(1, viewModel.count(TicketType.ADULT));
    }

    @Test
    public void testRemoveFromEmptyList() {
        viewModel.removeTicket(TicketType.ADULT);
        assertEquals(0, viewModel.count(TicketType.ADULT));
    }

    @Test
    public void testGetTicketTypesList() {
        viewModel.addTicket(TicketType.ADULT);
        assertTrue(viewModel.getTicketTypesList().contains(TicketType.ADULT));
    }

    @Test
    public void testReset() {
        viewModel.addTicket(TicketType.ADULT);
        viewModel.reset();
        assertEquals(0, viewModel.count(TicketType.ADULT));
    }

    @Test
    public void testTotalCost() {
        viewModel.addTicket(TicketType.ADULT);
        viewModel.addTicket(TicketType.CHILD);
        double expectedCost = TicketType.ADULT.getPrice() + TicketType.CHILD.getPrice();
        assertEquals(expectedCost, viewModel.getTotalCost());
    }

    @Test
    public void testAddNullTicket() {
        assertThrows(IllegalArgumentException.class, () -> {
        viewModel.addTicket(null);
        }, "type cannot be null");
    }

    @Test
    public void testRemoveNullTicket() {
        assertDoesNotThrow(() -> viewModel.removeTicket(null));
        assertEquals(0, viewModel.getTicketTypesList().size());
    }
}
