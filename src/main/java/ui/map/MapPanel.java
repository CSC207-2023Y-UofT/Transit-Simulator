package ui.map;

import interface_adapter.map.TransitMapViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MapPanel is a JPanel that displays the map of the transit system.
 * It is used by the TransitMapViewModel to display the map.
 *
 * @see TransitMapViewModel
 */
public class MapPanel extends JPanel {

    /**
     * The TransitMapViewModel that is used to present the map.
     */
    private final TransitMapViewModel viewModel;

    /**
     * The StationPage that is currently being displayed.
     */
    private volatile StationPage currentStationPage = null;

    /**
     * The timer that is used to repaint the panel.
     */
    private final Timer timer = new Timer(10, e -> this.repaint());

    /**
     * Constructs a new MapPanel object with the given TransitMapViewModel.
     *
     * @param viewModel the TransitMapPresenter that is used to present the map
     */
    public MapPanel(TransitMapViewModel viewModel) {
        this.viewModel = viewModel;

        setLayout(new BorderLayout());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
                var optArrivals = viewModel.getArrivals(e.getX(), e.getY());
                optArrivals.ifPresent(model -> SwingUtilities.invokeLater(() -> {
                    if (currentStationPage != null) currentStationPage.dispose();
                    currentStationPage = new StationPage(model);
                }));

            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                viewModel.onMouseMove(e.getX(), e.getY());
                repaint();
            }
        });
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
        if (currentStationPage != null) currentStationPage.dispose();
    }

    /**
     * Paints the panel.
     *
     * @param g the graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        viewModel.present((Graphics2D) g, getWidth(), getHeight());
    }

}
