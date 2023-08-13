package interface_adapter.viewmodel;

import app_business.boundary.IStationInteractor;
import app_business.boundary.ITrainInteractor;
import app_business.dto.StationDTO;
import app_business.dto.TrainDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransitMapViewModelTest {

    private IStationInteractor stationInteractor;
    private ITrainInteractor trainInteractor;
    private TransitMapViewModel viewModel;

    @BeforeEach
    void setUp() {
        stationInteractor = mock(IStationInteractor.class);
        trainInteractor = mock(ITrainInteractor.class);
        viewModel = new TransitMapViewModel(stationInteractor, trainInteractor);
    }

    @Test
    void testPresent() {
        Graphics2D graphics = mock(Graphics2D.class);
        StationDTO station = new StationDTO("Station A", Arrays.asList(1), 1000, 1000); // Corrected here
        when(stationInteractor.getStations()).thenReturn(Arrays.asList(station));
        when(trainInteractor.getTrains()).thenReturn(Arrays.asList());
        viewModel.present(graphics, 1920, 1080);
        verify(stationInteractor, times(1)).getStations();
        verify(trainInteractor, times(1)).getTrains();
        // Additional graphics verifications can be added here
    }


    @Test
    void testGetArrivalsWithoutStation() {
        StationDTO station = new StationDTO("Station A", List.of(1), 1000, 1000); // Corrected instantiation
        when(stationInteractor.getStations()).thenReturn(List.of(station));
        viewModel.present(mock(Graphics2D.class), 1920, 1080); // Ensure the stations list is populated
        Optional<ArrivalsViewModel> viewModelOptional = viewModel.getArrivals(1500, 1500); // Using a random position
        assertFalse(viewModelOptional.isPresent());
    }


    @Test
    void testOnMouseMoveWithHighlighting() {
        StationDTO station = new StationDTO("Station A", Arrays.asList(1), 1000, 1000); // Corrected instantiation
        when(stationInteractor.getStations()).thenReturn(Arrays.asList(station));
        viewModel.present(mock(Graphics2D.class), 1920, 1080); // Ensure the stations list is populated
        viewModel.onMouseMove(1000, 1000); // Using the known station's position
        // An internal state is changed. For it to be validated, a method exposing the internal state would be needed.
    }


    @Test
    void testOnMouseMoveWithoutHighlighting() {
        StationDTO station = new StationDTO("Station A", Arrays.asList(1), 1000, 1000); // Corrected instantiation
        when(stationInteractor.getStations()).thenReturn(Arrays.asList(station));
        viewModel.present(mock(Graphics2D.class), 1920, 1080); // Ensure the stations list is populated
        viewModel.onMouseMove(1500, 1500); // Using a random position
        // An internal state is changed. For it to be validated, a method exposing the internal state would be needed.
    }

    // Additional tests can be added, especially if more functionalities are exposed in the ViewModel.
}
