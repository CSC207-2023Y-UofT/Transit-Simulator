package ui;

import interface_adapter.viewmodel.TransitMapViewModel;
import ui.map.MapPanel;
import ui.util.ShadowPanel;
import ui.util.ShadowedButton;

import javax.swing.*;
import java.awt.*;

public class MapDisplay {

    public static JPanel createPageWithMapAndButtons(UIController controller, JButton actionButton, Color pageBackgroundColor) {
        JPanel panel = new JPanel(new BorderLayout());

        // Back button
        JButton backButton = new ShadowedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> controller.open(new WelcomePage(controller)));

        // Add components to the panel

        panel.setBackground(pageBackgroundColor);

        // Map
        JPanel marginPanel = getMapPanel(controller);
        panel.add(marginPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(0, 3));
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(actionButton, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel getMapPanel(UIController controller) {
        TransitMapViewModel presenter = new TransitMapViewModel(
                controller.getInteractorPool().getStationInteractor(),
                controller.getInteractorPool().getTrainInteractor()
        );

        // The JPanel that displays the map.
        MapPanel mapPanel = new MapPanel(presenter);
        JPanel marginPanel = new JPanel(new BorderLayout());
        ShadowPanel shadowPanel = new ShadowPanel(new BorderLayout());
        shadowPanel.setThickness(10);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        marginPanel.setBackground(new Color(230, 230, 230));
        shadowPanel.add(mapPanel, BorderLayout.CENTER);
        marginPanel.add(shadowPanel, BorderLayout.CENTER);
        return marginPanel;
    }
}
