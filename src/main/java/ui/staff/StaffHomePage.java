package ui.staff;

import controller.transit_map.TransitMapPagePresenter;
import ui.UIController;
import ui.WelcomePage;
import ui.map.MapPanel;
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

    private final UIController controller;
    private JButton loginButton, backButton;
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
        loginButton = new ShadowedButton("Log In");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(0, 151, 8));
        loginButton.setFont(new Font("Serif", Font.BOLD, 20));
        loginButton.addActionListener(e -> {
            controller.open(new StaffSelectPage(controller));
        });


        // Back button
        backButton = new ShadowedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.addActionListener(e -> {
            controller.open(new WelcomePage(controller));
        });


        // Add components to the panel

        this.setBackground(new Color(210, 207, 206));

        // Map
        TransitMapPagePresenter presenter = new TransitMapPagePresenter(
                controller.getInteractorPool().getStationInteractor(),
                controller.getInteractorPool().getTrainInteractor()
        );
        mapPanel = new MapPanel(presenter);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(mapPanel);

        this.add(topPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(0, 3));

        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(loginButton, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);




    }

}
