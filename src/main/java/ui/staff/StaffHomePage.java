package ui.staff;

import interface_adapter.viewmodel.TransitMapViewModel;
import ui.UIController;
import ui.WelcomePage;
import ui.map.MapPanel;
import ui.util.ShadowPanel;
import ui.util.ShadowedButton;

import javax.swing.*;
import java.awt.*;

/**
 * StaffHomePage is a JPanel that displays the home page of a staff member.
 * It is used by the UIController to display the home page of a staff member.
 *
 * @see UIController
 */
public class StaffHomePage extends JPanel {

    /**
     * The UIController that is used to control the UI.
     */
    private final UIController controller;

    /**
     * The JPanel that displays the map.
     */
    private MapPanel mapPanel;

    /**
     * Constructs a new StaffHomePage with the given UIController.
     *
     * @param controller the UIController that is used to control the UI
     */
    public StaffHomePage(UIController controller) {
        super(new BorderLayout());

        this.controller = controller;

        // Log in button
        JButton loginButton = new ShadowedButton("Log In");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(0, 151, 8));
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.addActionListener(e -> {
            controller.open(new LoginPage(controller));
        });

        // Back button
        JButton backButton = new ShadowedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> {
            controller.open(new WelcomePage(controller));
        });

        // Add components to the panel

        this.setBackground(new Color(210, 207, 206));

        // Map
        TransitMapViewModel presenter = new TransitMapViewModel(
                controller.getInteractorPool().getStationInteractor(),
                controller.getInteractorPool().getTrainInteractor()
        );
        MapPanel mapPanel = new MapPanel(presenter);

        JPanel marginPanel = new JPanel(new BorderLayout());
        ShadowPanel shadowPanel = new ShadowPanel(new BorderLayout());
        shadowPanel.setThickness(10);
        marginPanel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        marginPanel.setBackground(new Color(230, 230, 230));
        shadowPanel.add(mapPanel, BorderLayout.CENTER);
        marginPanel.add(shadowPanel, BorderLayout.CENTER);
        this.add(marginPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(0, 3));
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(loginButton, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

}
