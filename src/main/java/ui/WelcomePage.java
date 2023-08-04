package ui;

import ui.passenger.PassengerHomePage;
import ui.round.RoundedButton;
import ui.staff.StaffSelectPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage {
    private JFrame frame;
    private JPanel panel;
    private JLabel titleLabel, userTypeLabel;
    private JButton passengerButton, staffButton;


    public WelcomePage() {

        // Create the frame and panel
        frame = new JFrame("Ticket Buying Page");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 3));

        // Title
        titleLabel = new JLabel("Welcome to the TTC", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));

        // Prompt
        userTypeLabel = new JLabel("Please select your user type.", SwingConstants.CENTER);
        userTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userTypeLabel.setFont(new Font("Serif", Font.BOLD, 25));

        // Passenger button
        passengerButton = new RoundedButton("Passenger");
        passengerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        passengerButton.setFont(new Font("Serif", Font.BOLD, 25));
        passengerButton.setPreferredSize(new Dimension(200, 50));
        passengerButton.setBackground(new Color(112, 170, 255));
        passengerButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        passengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PassengerHomePage();
                frame.dispose();
            }
        });

        // Staff button
        staffButton = new RoundedButton("Staff");
        staffButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        staffButton.setFont(new Font("Serif", Font.BOLD, 25));
        staffButton.setPreferredSize(new Dimension(200, 50));
        staffButton.setBackground(new Color(112,170, 255));
        staffButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StaffSelectPage();
                frame.dispose();
            }
        });


        // Add components to the panel

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(titleLabel);
        panel.add(new JLabel("  "));

        panel.add(new JLabel("  "));
        panel.add(userTypeLabel);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(passengerButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(staffButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        // Make background color light gray
        panel.setBackground(new Color(220,220,220));

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new WelcomePage();
    }

}
