package interface_adapter.viewmodel;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import app_business.boundary.IStationInteractor;
import app_business.dto.StationDTO;
import entity.model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ArrivalsViewModelTest {

    @Mock
    private IStationInteractor stationInteractor;

    @InjectMocks
    private ArrivalsViewModel viewModel;

    private StationDTO sampleStation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        sampleStation = new StationDTO("Station 99", List.of(1, 2), 1, 2);
        viewModel = new ArrivalsViewModel(sampleStation, stationInteractor);
    }

    @Test
    void testUpdate() {
        // Given: Mocking stationInteractor to return specific values
        when(stationInteractor.getNextStation(eq("Station 99"), eq(1), eq(Direction.FORWARD)))
                .thenReturn(Optional.of(new StationDTO("Forward Next Station", List.of(1), 3, 4)));
        when(stationInteractor.getNextStation(eq("Station 99"), eq(1), eq(Direction.BACKWARD)))
                .thenReturn(Optional.of(new StationDTO("Backward Next Station", List.of(1), 3, 4)));
        when(stationInteractor.getTimeTillNextArrival(eq("Station 99"), eq(1), eq(Direction.FORWARD)))
                .thenReturn(Optional.of(5L));
        when(stationInteractor.getTimeTillNextArrival(eq("Station 99"), eq(1), eq(Direction.BACKWARD)))
                .thenReturn(Optional.of(10L));

        // When: Calling update on the viewModel
        viewModel.update();

        // Then: Assertions to ensure correctness of the update function
        var arrivals = viewModel.getNextArrivals();
        assertFalse(arrivals.isEmpty());

        var lineOneArrivals = arrivals.get(1);
        assertNotNull(lineOneArrivals);
        assertEquals(5L, lineOneArrivals.get("Forward Next Station"));
        assertEquals(10L, lineOneArrivals.get("Backward Next Station"));

        // Verifying interactor calls
        verify(stationInteractor, times(2)).getNextStation(eq("Station 99"), eq(1), any(Direction.class));
        verify(stationInteractor, times(2)).getTimeTillNextArrival(eq("Station 99"), eq(1), any(Direction.class));
    }


    @Test
    public void testNoNextStation() {
        when(stationInteractor.getTimeTillNextArrival(anyString(), anyInt(), eq(Direction.FORWARD)))
                .thenReturn(Optional.of(10L));
        when(stationInteractor.getNextStation(anyString(), anyInt(), eq(Direction.FORWARD)))
                .thenReturn(Optional.empty()); // This represents no next station

        ArrivalsViewModel viewModel = new ArrivalsViewModel(sampleStation, stationInteractor);
        viewModel.update();

        Map<Integer, Map<String, Long>> arrivals = viewModel.getNextArrivals();
        assertTrue(arrivals.get(1).isEmpty()); // Assuming 1 is the line number in mockStation
    }


    @Test
    void testGetStation() {
        assertEquals(sampleStation, viewModel.getStation());
    }
}
