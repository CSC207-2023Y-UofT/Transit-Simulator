package ui.passenger;

import controller.ticket.PurchaseTicketViewModel;
import controller.transit_map.TransitMapPagePresenter;
import ui.UIController;
import ui.WelcomePage;
import ui.map.MapPanel;
import ui.util.ShadowPanel;
import ui.util.ShadowedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * PassengerHomePage is a JPanel that displays the passenger home page.
 * It is used by the UIController to display the passenger home page.
 *
 * @see UIController
 */
public class PassengerHomePage extends JPanel {

    /**
     * The UIController that is used to control the UI.
     */
    private final UIController controller;
    /**
     * The buttons on the panel.
     */
    private JButton buyButton, backButton;

    /**
     * Constructs a new PassengerHomePage with the given UIController.
     *
     * @param controller the UIController that is used to control the UI
     */
    public PassengerHomePage(UIController controller) {
        super(new BorderLayout());

        this.controller = controller;

        // Buy
        buyButton = new ShadowedButton("Buy Tickets");
        buyButton.setForeground(Color.WHITE);
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setBackground(new Color(0, 151, 8));
        buyButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        buyButton.setFont(new Font("Serif", Font.BOLD, 20));
        buyButton.addActionListener(e -> controller.open(new PurchaseTicketPage(controller, new PurchaseTicketViewModel())));

        // Back button
        backButton = new ShadowedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.addActionListener(e -> controller.open(new WelcomePage(controller)));


        // Add components to the panel

        this.setBackground(new Color(230, 230, 230));

        // Map
        TransitMapPagePresenter presenter = new TransitMapPagePresenter(
                controller.getInteractorPool().getStationInteractor(),
                controller.getInteractorPool().getTrainInteractor()
        );
        /**
         * The map panel on the panel.
         */
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
        bottomPanel.add(buyButton, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

}
