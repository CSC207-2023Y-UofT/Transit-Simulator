package ui.map;

import presenter.TransitMapPassengerPresenter;
import presenter.TransitMapPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanel extends JPanel {

    private final TransitMapPresenter presenter;

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

    @Override
    protected void paintComponent(Graphics g) {
        presenter.present((Graphics2D) g, getWidth(), getHeight());
    }
}
