package ui.passenger;

import interactor.station.StationInteractor;
import presenter.TransitMapPassengerPresenter;
import ui.WelcomePage;
import ui.map.MapPanel;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassengerHomePage {

    private JFrame frame;
    private JPanel panel;
    private JButton buyButton, backButton;
    private MapPanel mapPanel;

    public PassengerHomePage() {

        // Create the frame and panel
        frame = new JFrame("Passenger Home Page");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 3));


        // TODO: Map implementation


        // Buy
        buyButton = new RoundedButton("Buy Ticket");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setBackground(new Color(114, 217, 112));
        buyButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        buyButton.setFont(new Font("Serif", Font.BOLD, 20));
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PurchaseTicketPage();
                frame.dispose();
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
                new WelcomePage();
                frame.dispose();
            }
        });

//        mapPanel = new MapPanel(new TransitMapPassengerPresenter(stationInteractor));
//        mapPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel

        panel.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 12; i++) {
            panel.add(new JLabel("  "));
        }


        panel.add(backButton);
        panel.add(new JLabel("  "));
        panel.add(buyButton);

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new PassengerHomePage();
    }

}
