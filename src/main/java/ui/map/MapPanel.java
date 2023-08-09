package ui.map;

import controller.map.ArrivalsViewModel;
import controller.map.TransitMapViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MapPanel is a JPanel that displays the map of the transit system.
 * It is used by the TransitMapPresenter to display the map.
 *
 * @see TransitMapViewModel
 */
public class MapPanel extends JPanel {

    /**
     * The TransitMapPresenter that is used to present the map.
     */
    private final TransitMapViewModel viewModel;

    private volatile StationPage currentStationPage = null;

    /**
     * Constructs a new MapPanel object with the given TransitMapPresenter.
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

    private final Timer timer = new Timer(10, e -> this.repaint());

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

    /**
     * Paints the panel.
     * @param g the graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        viewModel.present((Graphics2D) g, getWidth(), getHeight());
    }
}
