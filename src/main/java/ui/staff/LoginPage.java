package ui.staff;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel {

    public LoginPage() {
        super(new GridLayout(0, 3));

        // Personnel Number
        JLabel label = new JLabel("Personnel Number: ", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 25));

        JTextField personnelNumberField = new JPasswordField();
        personnelNumberField.setFont(new Font("Serif", Font.PLAIN, 20));

        // Sign In button
        JButton signInButton = new RoundedButton("Sign In");
        signInButton.setBackground(new Color(112, 170, 255));
        signInButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        signInButton.setFont(new Font("Serif", Font.BOLD, 20));
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform sign in here, possibly by passing personnelNumberField.getText() to a method that handles sign in.


                // TODO: Add code to check if personnel number is valid

                // We should know what option they picked earlier and direct them there

                // new AdminHomePage();
                // new EngineerHomePage();
                // new OperatorHomePage();

                frame.dispose();
            }
        });

        // Back button
        JButton backButton = new RoundedButton("Back");
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StaffSelectPage();
                frame.dispose();
            }
        });

        // Add components to the panel

        this.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 6; i++) {
            this.add(new JLabel("  "));
        }

        this.add(label);
        this.add(personnelNumberField);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(signInButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 6; i++) {
            this.add(new JLabel("  "));
        }

        this.add(backButton);
        this.add(new JLabel("  "));
        this.add(new JLabel("  "));
    }

    public static void main(String[] args) {
        new LoginPage();
    }

}