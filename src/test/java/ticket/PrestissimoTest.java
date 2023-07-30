package ticket;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class PrestissimoTest {

    @Test
    public void testLoading() {
        Prestissimo card = new Prestissimo(123);
        card.loading(100); // Load 100 units
        assertEquals(100, card.fund);
    }

    @Test
    public void testPurchaseSuccessful() {
        Prestissimo card = new Prestissimo(456);
        card.loading(100); // Load 100 units
        Optional<Boolean> result = card.purchase(50); // Purchase a ticket for 50 units
        assertTrue(result.isEmpty());
        assertEquals(50, card.fund); // Fund balance should be 50 after the purchase
    }

    @Test
    public void testPurchaseNotEnoughFunds() {
        Prestissimo card = new Prestissimo(789);
        card.loading(100); // Load 100 units
        Optional<Boolean> result = card.purchase(150); // Attempt to purchase a ticket for 150 units with only 100 units in balance
        assertTrue(result.isPresent());
        assertFalse(result.get()); // Purchase should be unsuccessful
        assertEquals(100, card.fund); // Fund balance should remain unchanged
    }
}
