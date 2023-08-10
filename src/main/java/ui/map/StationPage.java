package ui.map;

import controller.map.ArrivalsViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a window (JFrame) that displays information about a station.
 * The station information is visualized using the {@link StationPanel}.
 */
public class StationPage extends JFrame {

    /**
     * Constructor for the StationPage. Initializes the frame with the provided
     * viewModel to display station information.
     *
     * @param viewModel The view model containing data for station arrivals.
     */
    public StationPage(ArrivalsViewModel viewModel) {

        // Set the window title
        super("Station Time");

        // Set preferred size for the window
        this.setPreferredSize(new Dimension(370, 430));

        // Set the close operation for the window
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Make the window visible
        this.setVisible(true);

        // Create and set the content pane for this frame using StationPanel
        JPanel panel = new StationPanel(viewModel);
        this.setContentPane(panel);

        // Adjust the frame size to its content
        this.pack();

        // Refresh the layout and visual representation
        this.revalidate();
        this.repaint();
    }
}
