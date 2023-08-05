package ui.passenger;

import presenter.TransitMapPassengerPresenter;
import ui.UIController;
import ui.WelcomePage;
import ui.map.MapPanel;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassengerHomePage extends JPanel {
    private final UIController controller;
    private JButton buyButton, backButton;
    private MapPanel mapPanel;

    public PassengerHomePage(UIController controller) {
        super(new GridLayout(0, 1));

        this.controller = controller;

        // Buy
        buyButton = new RoundedButton("Buy Ticket");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setBackground(new Color(114, 217, 112));
        buyButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        buyButton.setFont(new Font("Serif", Font.BOLD, 20));
        buyButton.addActionListener(e -> controller.open(new PurchaseTicketPage(controller)));

        // Back button
        backButton = new RoundedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.addActionListener(e -> controller.open(new WelcomePage(controller)));


        // Add components to the panel

        this.setBackground(new Color(210, 207, 206));

        // Map
        TransitMapPassengerPresenter presenter = new TransitMapPassengerPresenter(
                controller.getInteractorPool().getStationInteractor(),
                controller.getInteractorPool().getTrainInteractor()
        );
        mapPanel = new MapPanel(presenter);
        mapPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(mapPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 3));

        for (int i = 0; i < 6; i++) {
            bottomPanel.add(new JLabel(""));
        }
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(new JLabel(""));
        bottomPanel.add(buyButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

}
