package ui.staff;

import ui.MapDisplay;
import ui.UIController;
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
     * Constructs a new StaffHomePage with the given UIController.
     *
     * @param controller the UIController that is used to control the UI
     */
    public StaffHomePage(UIController controller) {
        super(new BorderLayout());

        // Log in button
        JButton loginButton = new ShadowedButton("Log In");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(0, 151, 8));
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.addActionListener(e -> controller.open(new LoginPage(controller)));

        JPanel panel = MapDisplay.createPageWithMapAndButtons(controller, loginButton, new Color(210, 207, 206));
        this.add(panel);
    }

}