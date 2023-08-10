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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class StationPanel extends JPanel {
    private final JPanel bottomPanel;
    private final Timer timer = new Timer(50, e -> this.update());
    private final ArrivalsViewModel viewModel;

    private final List<SuppliedLabel> arrivalsLabels = new ArrayList<>();

    public StationPanel(ArrivalsViewModel viewModel) {

        this.viewModel = viewModel;
        viewModel.update();

        bottomPanel = new JPanel(new GridLayout(0, 1));
        this.add(bottomPanel, BorderLayout.CENTER);

        String stationName = viewModel.getStation().getName();
        JLabel stationLabel = new JLabel(stationName + " Station", SwingConstants.CENTER);
        stationLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel subtitleLabel = new JLabel("Train Schedule");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        var lines = viewModel.getStation().getLines();
        for (int line : lines) {
            Map<String, Long> arrivals = viewModel.getNextArrivals().get(line);
            if (arrivals == null) continue;

            arrivals.forEach((dir, arrival) -> {
                Supplier<String> supplier = () -> {
                    Map<String, Long> updated = viewModel.getNextArrivals()
                            .get(line);
                    if (updated == null) return "N/A";
                    Long updatedArrival = updated.get(dir);
                    if (updatedArrival == null) return "N/A";

                    return String.format("Line %d, Towards %s: %ds", line, dir, updatedArrival / 1000);
                };

                SuppliedLabel label = new SuppliedLabel(supplier);
                label.setFont(new Font("Arial", Font.PLAIN, 18));
                arrivalsLabels.add(label);
            });
        }

        bottomPanel.add(stationLabel);
        bottomPanel.add(new JLabel(" "));
        bottomPanel.add(subtitleLabel);
        bottomPanel.add(new JLabel(" "));
        arrivalsLabels.forEach(bottomPanel::add);
    }

    private void update() {
        viewModel.update();
        repaint();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        timer.start();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        timer.stop();
    }

}
