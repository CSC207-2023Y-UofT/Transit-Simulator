package interface_adapter.viewmodel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.aggregate.SingletonAggregate;
import ui.UIController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class SingletonStatViewModelTest {

    private SingletonStatViewModel viewModel;

    @BeforeEach
    void setUp() {
        viewModel = new SingletonStatViewModel();
    }

    @Test
    void testDefaultGraphColour() {
        assertEquals(SingletonStatViewModel.GraphColour.BLUE, viewModel.getGraphColour());
    }

    @Test
    void testSetAndGetGraphColour() {
        viewModel.setGraphColour(SingletonStatViewModel.GraphColour.RED);
        assertEquals(SingletonStatViewModel.GraphColour.RED, viewModel.getGraphColour());
    }

    @Test
    void testSetAndGetAggregates() {
        // Mock SingletonAggregate objects
        SingletonAggregate<Number> aggregate1 = mock(SingletonAggregate.class);
        SingletonAggregate<Number> aggregate2 = mock(SingletonAggregate.class);

        when(aggregate1.getValue()).thenReturn(100);
        when(aggregate2.getValue()).thenReturn(150);

        // Set and test aggregates
        viewModel.setAggregates(Arrays.asList(aggregate1, aggregate2));
        assertEquals(2, viewModel.getAggregates().size());
        assertEquals(100, viewModel.getAggregates().get(0).getValue());
        assertEquals(150, viewModel.getAggregates().get(1).getValue());
    }

    // matt pls help
    @Test
    void testDrawMethod() {
        Graphics2D graphics = mock(Graphics2D.class);
        UIController controller = mock(UIController.class);

        // Assuming you have a public or protected method or field to set or get aggregates
        // Initialize aggregates list
        List<SingletonAggregate<? extends Number>> aggregatesList = new ArrayList<>();
        // Add some SingletonAggregate objects to the list if necessary
        // For example:
        // aggregatesList.add(new SingletonAggregate<>(someValue));
        viewModel.setAggregates(aggregatesList); // assuming you have a setter for it
        assertNotNull(viewModel);
        viewModel.setGraphColour(SingletonStatViewModel.GraphColour.GREEN);

        // Calling the draw method
        viewModel.draw(controller, "Test Display", graphics, 100, 100);

        // Check that the setColor method of Graphics2D was called (for simplicity, we'll check it for any color)
        verify(graphics, atLeastOnce()).setColor(any(Color.class));
    }
}


