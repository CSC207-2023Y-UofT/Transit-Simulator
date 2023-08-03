package ui.staff.admin;

import employee.Admin;
import ui.WelcomePage;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomePage {

    private JFrame frame;
    private JPanel panel;
    private JButton buyButton, viewButton, mapButton, homeButton;

    public AdminHomePage() {

        // Create the frame and panel
        frame = new JFrame("Admin Home Page");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 3));

        // Home button
        homeButton = new RoundedButton("Back");
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.setBackground(new Color(255, 255, 255));
        homeButton.setFont(new Font("Serif", Font.BOLD, 20));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomePage();
                frame.dispose();
            }
        });


//
//
//
//        // Buy
//        buyButton = new RoundedButton("Buy Ticket");
//        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        buyButton.setBackground(new Color(112,170, 255));
//        buyButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
//        buyButton.setFont(new Font("Serif", Font.BOLD, 20));
//        buyButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new PurchaseTicketPage();
//                frame.dispose();
//            }
//        });
//
//        // View
//        viewButton = new RoundedButton("View Ticket");
//        viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        viewButton.setBackground(new Color(112,170, 255));
//        viewButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
//        viewButton.setFont(new Font("Serif", Font.BOLD, 20));
//        viewButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                // if there is a ticket:
//                new TrainTicketPage();
//
//                // if there is no ticket:
//                new NoTicketPage();
//
//                frame.dispose();
//            }
//        });
//
//        // Map
//        mapButton = new RoundedButton("Open Map");
//        mapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mapButton.setBackground(new Color(112,170, 255));
//        mapButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
//        mapButton.setFont(new Font("Serif", Font.BOLD, 20));
//        mapButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // new PassengerMapPage();
//                frame.dispose();
//            }
//        });
//
//





        // Add components to the panel

        panel.setBackground(new Color(210, 207, 206));

        panel.add(homeButton);

        for (int i = 0; i < 5; i++) {
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

        panel.add(homeButton);
        panel.add(new JLabel("  "));
        panel.add(new JLabel("  "));

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new AdminHomePage();
    }

}
