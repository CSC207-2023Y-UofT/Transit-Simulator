package ui.staff;

import ui.PageController;
import ui.WelcomePage;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffHomePage extends JPanel {
    public StaffHomePage(PageController controller) {
        super(new GridLayout(0, 3));

        // Implementation of map

        // TODO


        // Log in button
        JButton loginButton = new RoundedButton("Log In");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(114, 217, 112));
        loginButton.setFont(new Font("Serif", Font.BOLD, 20));
        loginButton.addActionListener(e -> {
            new StaffSelectPage(controller);
            frame.dispose();
        });


        // Back button
        JButton backButton = new RoundedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.addActionListener(e -> {
            new WelcomePage();
            frame.dispose();
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
