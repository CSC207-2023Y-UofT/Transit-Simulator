package ui.map;

import controller.map.ArrivalsViewModel;
import model.Direction;
import ui.UIController;
import ui.staff.StaffHomePage;
import ui.util.ShadowedButton;
import ui.util.SuppliedLabel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class StationPanel extends JPanel {
    private final JPanel bottomPanel;
    private final Timer timer = new Timer(100, e -> this.update());
    private final ArrivalsViewModel viewModel;

    public StationPanel(ArrivalsViewModel viewModel) {

        this.viewModel = viewModel;
        viewModel.update();

        bottomPanel = new JPanel(new GridLayout(0, 1));

        String stationName = viewModel.getStation().getName();
        JLabel stationLabel = new JLabel(stationName + " Station", SwingConstants.CENTER);
        stationLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel subtitleLabel = new JLabel("Train Schedule");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        List<JLabel> arrivalsLabels = new ArrayList<>();
        var lines = viewModel.getStation().getLines();
        for (int line : lines) {
            Map<Direction, Long> arrivals = viewModel.getNextArrivals().get(line);
            if (arrivals == null) continue;

            arrivals.forEach((dir, arrival) -> {
                Supplier<String> supplier = () -> {
                    Map<Direction, Long> updated = viewModel.getNextArrivals()
                            .get(line);
                    if (updated == null) return "N/A";
                    Long updatedArrival = updated.get(dir);
                    if (updatedArrival == null) return "N/A";

                    return String.format("Line %d, %s: %ds", line, dir, arrival / 1000);
                };

                JLabel label = new SuppliedLabel(supplier);
                label.setFont(new Font("Arial", Font.PLAIN, 18));
                arrivalsLabels.add(label);
            });
        }

        bottomPanel.add(stationLabel);
        bottomPanel.add(new JLabel(" "));
        bottomPanel.add(subtitleLabel);
        bottomPanel.add(new JLabel(" "));
        arrivalsLabels.forEach(bottomPanel::add);
        this.add(bottomPanel, BorderLayout.CENTER);

    }

    private void update() {
        viewModel.update();
        System.out.println(viewModel.getNextArrivals());
        repaint();
    }

    @Override
    public void addNotify() {
        timer.start();
    }

    @Override
    public void removeNotify() {
        timer.stop();
    }
}
