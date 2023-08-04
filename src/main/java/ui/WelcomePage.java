package ui;

import bootstrap.InteractorPool;
import ui.passenger.PassengerHomePage;
import ui.round.RoundedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel {
    private final InteractorPool interactors;
    private final PageController controller;
    private JLabel titleLabel, userTypeLabel;
    private JButton passengerButton, staffButton;


    public WelcomePage(PageController controller) {
        super(new GridLayout(0, 3));

        this.controller = controller;

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
        passengerButton.addActionListener(e -> {
            controller.open(new PassengerHomePage(controller));
        });

        // Staff button
        staffButton = new RoundedButton("Staff");
        staffButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        staffButton.setFont(new Font("Serif", Font.BOLD, 25));
        staffButton.setPreferredSize(new Dimension(200, 50));
        staffButton.setBackground(new Color(112, 170, 255));
        staffButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        staffButton.addActionListener(e -> {
            controller.open(new StaffHomePage(controller));
        });

        // Add components to the panel

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(titleLabel);
        this.add(new JLabel("  "));

        this.add(new JLabel("  "));
        this.add(userTypeLabel);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(passengerButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(staffButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        // Make background color light gray
        this.setBackground(new Color(220, 220, 220));

    }

}
