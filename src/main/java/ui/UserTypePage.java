package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTypePage {
    private JFrame frame;
    private JPanel panel;
    private JLabel userTypeLabel;
    private JButton passengerButton, staffButton;

    public UserTypePage() {
        frame = new JFrame("User Type Page");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        userTypeLabel = new JLabel("Please select your user type.", SwingConstants.CENTER);
        userTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the label

        passengerButton = new JButton("Passenger");
        passengerButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the button
        passengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the ticket buying page and dispose of the current frame
                new TicketBuyingPage();
                frame.dispose();
            }
        });

        staffButton = new JButton("Staff");
        staffButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the button
        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display a message saying "Not implemented yet"
                JOptionPane.showMessageDialog(frame, "Not implemented yet.");
            }
        });

        // Add components to the panel
        panel.add(userTypeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // Add a 20-pixel tall space
        panel.add(passengerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add a 10-pixel tall space
        panel.add(staffButton);

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new UserTypePage();
    }
}
