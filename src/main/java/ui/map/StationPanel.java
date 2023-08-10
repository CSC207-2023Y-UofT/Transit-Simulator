package ui.map;

import controller.map.ArrivalsViewModel;
import ui.util.SuppliedLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Represents a panel that visualizes station information, including line and arrival details.
 */
public class StationPanel extends JPanel {

    /** The main panel that hosts all the station details. */
    private final JPanel panel;

    /**
     * A timer that schedules periodic updates to the panel.
     * This ensures that station information remains current.
     */
    private final Timer timer = new Timer(50, e -> this.update());

    /** The view model containing data for station arrivals. */
    private final ArrivalsViewModel viewModel;

    /**
     * Constructor for the StationPanel. Initializes the panel with the provided
     * viewModel to display station details, lines, and next arrivals.
     *
     * @param viewModel The view model containing data for station arrivals.
     */
    public StationPanel(ArrivalsViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.update();

        panel = new JPanel(new GridLayout(0, 1));
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        String stationName = viewModel.getStation().getName();
        JLabel stationLabel = new JLabel(stationName + " Station", SwingConstants.CENTER);
        stationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        stationLabel.setOpaque(true);
        stationLabel.setBackground(Color.DARK_GRAY);
        stationLabel.setForeground(Color.WHITE);
        stationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(stationLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        Map<Integer, Color> lineColors = Map.of(
                1, new Color(255, 153, 0),
                2, new Color(67, 161, 67),
                3, new Color(5, 142, 203),
                4, new Color(136, 8, 136)
        );

        for (int line : viewModel.getStation().getLines()) {
            Map<String, Long> arrivals = viewModel.getNextArrivals().get(line);
            if (arrivals == null) continue;

            Color lineColor = lineColors.getOrDefault(line, Color.GRAY);
            JLabel lineLabel = new JLabel("Line " + line);
            lineLabel.setOpaque(true);
            lineLabel.setBackground(lineColor);
            lineLabel.setForeground(Color.WHITE);
            lineLabel.setFont(new Font("Arial", Font.BOLD, 18));
            lineLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            panel.add(lineLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));

            arrivals.forEach((dir, arrival) -> {
                Supplier<String> supplier = () -> {
                    Map<String, Long> updated = viewModel.getNextArrivals().get(line);
                    if (updated == null) return "N/A";
                    Long updatedArrival = updated.get(dir);
                    if (updatedArrival == null) return "N/A";

                    return String.format("%ds", updatedArrival / 1000);
                };

                SuppliedLabel label = new SuppliedLabel(supplier);
                label.setFont(new Font("Arial", Font.PLAIN, 16));
                label.setForeground(Color.BLACK);
                label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, lineColor));
                label.setHorizontalAlignment(SwingConstants.CENTER);

                JLabel directionLabel = new JLabel("Towards " + dir);
                directionLabel.setFont(new Font("Arial", Font.BOLD, 16));
                directionLabel.setForeground(Color.BLACK);
                directionLabel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

                panel.add(directionLabel);
                panel.add(label);
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            });
        }
    }

    /**
     * Updates the panel by fetching the latest data from the viewModel and repainting.
     */
    private void update() {
        viewModel.update();
        repaint();
    }

    /**
     * Called when this component is added to a container and starts the timer
     * for periodic updates.
     */
    @Override
    public void addNotify() {
        super.addNotify();
        timer.start();
    }

    /**
     * Called when this component is removed from a container and stops the timer.
     */
    @Override
    public void removeNotify() {
        super.removeNotify();
        timer.stop();
    }

}
