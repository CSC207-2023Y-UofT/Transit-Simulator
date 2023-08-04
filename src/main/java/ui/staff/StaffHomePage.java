package ui.staff;

import ui.UIController;
import ui.WelcomePage;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;

public class StaffHomePage extends JPanel {
    public StaffHomePage(UIController controller) {
        super(new GridLayout(0, 3));

        // Implementation of map

        // TODO


        // Log in button
        JButton loginButton = new RoundedButton("Log In");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(114, 217, 112));
        loginButton.setFont(new Font("Serif", Font.BOLD, 20));
        loginButton.addActionListener(e -> {
            controller.open(new StaffSelectPage(controller));
        });


        // Back button
        JButton backButton = new RoundedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.addActionListener(e -> {
            controller.open(new WelcomePage(controller));
        });


        // Add components to the panel

        this.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 12; i++) {
            this.add(new JLabel("  "));
        }

        this.add(backButton);
        this.add(new JLabel("  "));
        this.add(loginButton);
    }

}
