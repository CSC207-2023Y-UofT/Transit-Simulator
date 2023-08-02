package ui;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthenticatePage {

    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JTextField personnelNumberField;
    private JButton signInButton, backButton;

    public AuthenticatePage() {

        // Create the frame and panel
        frame = new JFrame("Staff Sign In");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 3));

        // Personnel Number
        label = new JLabel("Personnel Number: ", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(20.0f));

        personnelNumberField = new JTextField();
        personnelNumberField.setFont(personnelNumberField.getFont().deriveFont(20.0f));

        // Sign In button
        signInButton = new RoundedButton("Sign In");
        signInButton.setBackground(new Color(135,156,210));
        signInButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        signInButton.setFont(signInButton.getFont().deriveFont(20.0f));
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform sign in here, possibly by passing personnelNumberField.getText() to a method that handles sign in.


                // TODO: Add code to check if personnel number is valid

                // We should know what option they picked earlier and direct them there

                frame.dispose();
            }
        });

        // Back button
        backButton = new RoundedButton("Back");
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(backButton.getFont().deriveFont(20.0f));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StaffHomePage();
                frame.dispose();
            }
        });

        // Add components to the panel

        panel.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 6; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(label);
        panel.add(personnelNumberField);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(signInButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 6; i++) {
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
        new AuthenticatePage();
    }

}
