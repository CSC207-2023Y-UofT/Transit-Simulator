package interface_adapter.viewmodel;

import app_business.dto.TicketDTO;
import entity.ticket.TicketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketViewModelTest {

    private TicketDTO mockTicketDTO;
    private TicketViewModel ticketViewModel;

    @BeforeEach
    void setUp() {
        mockTicketDTO = mock(TicketDTO.class);
        ticketViewModel = new TicketViewModel(mockTicketDTO);
    }

    @Test
    void constructorShouldInitializeWithGivenTicket() {
        assertEquals(mockTicketDTO, ticketViewModel.getTicket());
    }

    @Test
    void getterShouldRetrieveCorrectTicketDTO() {
        TicketDTO anotherTicketDTO = new TicketDTO(100.0, TicketType.ADULT, 123, true, 100000L);
        TicketViewModel anotherTicketViewModel = new TicketViewModel(anotherTicketDTO);
        assertEquals(anotherTicketDTO, anotherTicketViewModel.getTicket());
    }

    @Test
    void setterShouldSetNewTicketDTO() {
        TicketDTO newTicketDTO = new TicketDTO(200.0, TicketType.STUDENT, 456, false, 200000L);
        ticketViewModel.setTicket(newTicketDTO);
        assertEquals(newTicketDTO, ticketViewModel.getTicket());
    }

    @Test
    void shouldHandleNullTicketDTO() {
        ticketViewModel.setTicket(null);
        assertNull(ticketViewModel.getTicket());
    }

}
