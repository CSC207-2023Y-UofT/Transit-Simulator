package ui.map;

import controller.transit_map.TransitMapPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MapPanel is a JPanel that displays the map of the transit system.
 * It is used by the TransitMapPresenter to display the map.
 *
 * @see TransitMapPresenter
 */
public class MapPanel extends JPanel {

    /**
     * The TransitMapPresenter that is used to present the map.
     */
    private final TransitMapPresenter presenter;

    /**
     * Constructs a new MapPanel object with the given TransitMapPresenter.
     *
     * @param presenter the TransitMapPresenter that is used to present the map
     */
    public MapPanel(TransitMapPresenter presenter) {
        this.presenter = presenter;

        setLayout(new BorderLayout());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                presenter.onClick(e.getX(), e.getY());
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                presenter.onMouseMove(e.getX(), e.getY());
                repaint();
            }
        });
    }

    /**
     * Paints the panel.
     * @param g the graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        presenter.present((Graphics2D) g, getWidth(), getHeight());
        if (this.getParent() != null) {
            repaint(10);
        }
    }
}
