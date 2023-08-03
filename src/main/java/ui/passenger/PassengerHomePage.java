package ui.passenger;

import ui.WelcomePage;
import ui.round.RoundedButton;
import ui.ticket.TrainTicketPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassengerHomePage {

    private JFrame frame;
    private JPanel panel;
    private JButton buyButton, viewButton, mapButton, backButton;

    public PassengerHomePage() {

        // Create the frame and panel
        frame = new JFrame("Passenger Home Page");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 3));

        // Buy
        buyButton = new RoundedButton("Buy Tickets");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setBackground(new Color(112,170, 255));
        buyButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        buyButton.setFont(new Font("Serif", Font.BOLD, 20));
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PurchaseTicketPage();
                frame.dispose();
            }
        });

        // View
        viewButton = new RoundedButton("View Tickets");
        viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewButton.setBackground(new Color(112,170, 255));
        viewButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        viewButton.setFont(new Font("Serif", Font.BOLD, 20));
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrainTicketPage();
                frame.dispose();
            }
        });

        // Map
        mapButton = new RoundedButton("Open Map");
        mapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mapButton.setBackground(new Color(112,170, 255));
        mapButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        mapButton.setFont(new Font("Serif", Font.BOLD, 20));
        mapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new PassengerMapPage();
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


        // Add components to the panel

        panel.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(buyButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(viewButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(mapButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(backButton);
        panel.add(new JLabel("  "));
        panel.add(new JLabel("  "));

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
