package interface_adapter.viewmodel;

import app_business.dto.TrainDTO;
import entity.model.train.TrainStatus;
import interface_adapter.controller.TrainController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                .sorted(Comparator.comparingInt(entry -> {
                    Pattern pattern = Pattern.compile("\\d+");
                    String trainName = entry.getKey();
                    Matcher matcher = pattern.matcher(trainName);
                    if (matcher.find()) {
                        return Integer.parseInt(matcher.group());
                    }
                    return 0;
                }))
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                .toArray(Object[][]::new);
    }

    public void setNeedsMaintenance(String trainName, boolean needsMaintenance) {
        controller.setNeedsMaintenance(trainName, needsMaintenance);
    }
}
