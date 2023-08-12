package interface_adapter.viewmodel;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import app_business.boundary.ITrainInteractor;
import app_business.dto.TrainDTO;
import entity.model.train.TrainStatus;
import interface_adapter.controller.TrainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class MaintenanceViewModelTest {

    @Mock
    private ITrainInteractor trainInteractor;

    @InjectMocks
    private TrainController trainController;

    private MaintenanceViewModel viewModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new MaintenanceViewModel(trainController);
    }

    @Test
    void testUpdateForTrainUnderMaintenance() {
        List<TrainDTO> trains = Arrays.asList(
                new TrainDTO("Train A", 100, 50, null, null, null, TrainStatus.UNDER_MAINTENANCE),
                new TrainDTO("Train B", 100, 75, null, null, null, TrainStatus.IN_SERVICE)
        );

        when(trainInteractor.getTrains()).thenReturn(trains);

        viewModel.update();

        assertTrue((Boolean) viewModel.getMaintenanceTable()[0][1]);
        assertFalse((Boolean) viewModel.getMaintenanceTable()[1][1]); // Train B is not under maintenance
    }

    @Test
    void testUpdateForAllTrainsInService() {
        List<TrainDTO> trains = Arrays.asList(
                new TrainDTO("Train 1", 100, 50, null, null, null, TrainStatus.IN_SERVICE),
                new TrainDTO("Train 2", 100, 75, null, null, null, TrainStatus.IN_SERVICE)
        );

        when(trainInteractor.getTrains()).thenReturn(trains);

        viewModel.update();

        assertFalse((Boolean)viewModel.getMaintenanceTable()[0][1]); // Train A is not under maintenance
        assertFalse((Boolean)viewModel.getMaintenanceTable()[1][1]); // Train B is not under maintenance
    }

    @Test
    void testSetTrainUnderMaintenance() {
        viewModel.setNeedsMaintenance("Train 1", true);
        verify(trainInteractor, times(1)).setNeedsMaintenance("Train 1", true);
    }

    @Test
    void testSetTrainOutOfMaintenance() {
        viewModel.setNeedsMaintenance("Train 1", false);
        verify(trainInteractor, times(1)).setNeedsMaintenance("Train 1", false);
    }

    @Test
    void testGetMaintenanceTableSorting() {
        List<TrainDTO> trains = Arrays.asList(
                new TrainDTO("Train 2", 100, 75, null, null, null, TrainStatus.IN_SERVICE),
                new TrainDTO("Train 1", 100, 50, null, null, null, TrainStatus.UNDER_MAINTENANCE)
        );

        when(trainInteractor.getTrains()).thenReturn(trains);

        viewModel.update();

        assertEquals("Train 1", viewModel.getMaintenanceTable()[0][0]);
        assertEquals("Train 2", viewModel.getMaintenanceTable()[1][0]);
    }
}
