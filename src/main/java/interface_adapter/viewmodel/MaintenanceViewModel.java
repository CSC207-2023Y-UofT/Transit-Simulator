package interface_adapter.viewmodel;

import app_business.dto.TrainDTO;
import entity.model.train.TrainStatus;
import interface_adapter.controller.TrainController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaintenanceViewModel {
    private final TrainController controller;

    private final Map<String, Boolean> maintenanceStatuses = new HashMap<>();

    public MaintenanceViewModel(TrainController controller) {
        this.controller = controller;
    }

    public void update() {
        List<TrainDTO> dtoList = controller.findAll();
        for (TrainDTO dto : dtoList) {
            boolean needsMaintenance = dto.getStatus() == TrainStatus.UNDER_MAINTENANCE;
            maintenanceStatuses.put(dto.getName(), needsMaintenance);
        }
    }

    public Object[][] getMaintenanceTable() {
        return maintenanceStatuses.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                .toArray(Object[][]::new);
    }

    public void setNeedsMaintenance(String trainName, boolean needsMaintenance) {
        controller.setNeedsMaintenance(trainName, needsMaintenance);
    }
}
