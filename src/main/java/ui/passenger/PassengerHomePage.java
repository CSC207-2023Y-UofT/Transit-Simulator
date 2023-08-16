package ui.passenger;

import interface_adapter.viewmodel.PurchaseTicketViewModel;
import ui.MapDisplay;
import ui.UIController;
import ui.WelcomePage;
import ui.util.ShadowedButton;

import javax.swing.*;
import java.awt.*;

/**
 * PassengerHomePage is a JPanel that displays the passenger home page.
 * It is used by the UIController to display the passenger home page.
 *
 * @see UIController
 */
public class PassengerHomePage extends JPanel {

    /**
     * Constructs a new PassengerHomePage with the given UIController.
     *
     * @param controller the UIController that is used to control the UI
     */
    public PassengerHomePage(UIController controller) {

        super(new BorderLayout());

        // Buy tickets button
        JButton buyButton = new ShadowedButton("Buy Tickets");
        buyButton.setForeground(Color.WHITE);
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setBackground(new Color(0, 151, 8));
        buyButton.setFont(new Font("Arial", Font.BOLD, 20));
        buyButton.addActionListener(e -> controller.open(new PurchaseTicketPage(controller, new PurchaseTicketViewModel())));

        // Back button
        JButton backButton = new ShadowedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> controller.open(new WelcomePage(controller)));


        JPanel panel = MapDisplay.createPageWithMapAndButtons(controller, buyButton, new Color(230, 230, 230));
        this.add(panel);

    }
}