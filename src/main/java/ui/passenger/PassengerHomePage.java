package ui.passenger;

import bootstrap.InteractorPool;
import interactor.station.StationInteractor;
import presenter.TransitMapPassengerPresenter;
import ui.WelcomePage;
import ui.map.MapPanel;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassengerHomePage extends JPanel {
    private JButton buyButton, backButton;
    private MapPanel mapPanel;

    public PassengerHomePage(InteractorPool pool) {
        super(new GridLayout(0, 3));


        // Buy
        buyButton = new RoundedButton("Buy Ticket");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setBackground(new Color(114, 217, 112));
        buyButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        buyButton.setFont(new Font("Serif", Font.BOLD, 20));
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new PurchaseTicketPage(); TODO
//                frame.dispose();
            }
        });

        // Back button
        backButton = new RoundedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new WelcomePage(); TODO
//                frame.dispose();
            }
        });

        // Map
        TransitMapPassengerPresenter presenter = new TransitMapPassengerPresenter(pool.getStationInteractor());
        mapPanel = new MapPanel(presenter);
        mapPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel

        this.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 12; i++) {
            this.add(new JLabel("  "));
        }


        this.add(backButton);
        this.add(new JLabel("  "));
        this.add(buyButton);

    }

}
