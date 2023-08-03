package ui.staff;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuccessfulAuthenticationPage {

    private JFrame frame;
    private JPanel panel;
    private JLabel label, successLabel;
    private JTextField personnelNumberField;
    private JButton signInButton, backButton;

    public SuccessfulAuthenticationPage() {

        // Create the frame and panel
        frame = new JFrame("Staff Sign In");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 3));

        // Personnel Number
        label = new JLabel("Personnel Number: ", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 25));

        // Success Message
        successLabel = new JLabel("Success. Logging in.", SwingConstants.CENTER);
        successLabel.setFont(new Font("Serif", Font.BOLD, 20));
        successLabel.setForeground(new Color(29, 220, 37));


        // Input field
        personnelNumberField = new JTextField();
        personnelNumberField.setFont(new Font("Serif", Font.PLAIN , 20));
        personnelNumberField.setBorder(BorderFactory.createLineBorder(new Color(29, 229, 37), 3));

        // Add components to the panel

        panel.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(successLabel);
        panel.add(new JLabel("  "));


        panel.add(label);
        panel.add(personnelNumberField);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 9; i++) {
            panel.add(new JLabel("  "));
        }


        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SuccessfulAuthenticationPage();
    }

}
