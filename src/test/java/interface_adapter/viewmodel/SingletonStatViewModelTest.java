package interface_adapter.viewmodel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.aggregate.RevenueAggregate;
import stats.aggregate.SingletonAggregate;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.BufferedImage;
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
        SingletonAggregate<? extends Number> aggregate1 = new RevenueAggregate(100);
        SingletonAggregate<? extends Number> aggregate2 = new RevenueAggregate(150);

        // Set and test aggregates
        viewModel.setAggregates(Arrays.asList(aggregate1, aggregate2));
        assertEquals(2, viewModel.getAggregates().size());
        assertEquals(100.0, viewModel.getAggregates().get(0).getValue());
        assertEquals(150.0, viewModel.getAggregates().get(1).getValue());
    }

    // matt pls help
    @Test
    void testDrawMethod() {
        Graphics2D graphics = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB).createGraphics();

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
        viewModel.draw("Test Display", graphics, 100, 100);
    }
}


