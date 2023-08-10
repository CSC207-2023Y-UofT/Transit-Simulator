package interface_adapter.viewmodel;

import entity.model.train.TrainStatus;
import interface_adapter.controller.TrainController;

import java.util.HashMap;
import java.util.Map;

public class MaintenanceViewModel {
    private final TrainController controller;

    private Map<String, Boolean> maintenanceStatuses = new HashMap<>();

    public MaintenanceViewModel(TrainController controller) {
        this.controller = controller;
    }

    public void update() {
        controller.findAll().forEach(train -> {
            maintenanceStatuses.put(train.getName(), train.getStatus() == TrainStatus.NEEDS_MAINTENANCE);
        });
    }
}
