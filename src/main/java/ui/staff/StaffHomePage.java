package ui.staff;

import ui.WelcomePage;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffHomePage {
    private JFrame frame;
    private JPanel panel;
    private JButton backButton, loginButton;

    public StaffHomePage() {

        // Create the frame and panel
        frame = new JFrame("Staff Home Page");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 3));

        // Implementation of map

        // TODO


        // Log in button
        loginButton = new RoundedButton("Log In");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(114, 217, 112));
        loginButton.setFont(new Font("Serif", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StaffSelectPage();
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

        for (int i = 0; i < 12; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(backButton);
        panel.add(new JLabel("  "));
        panel.add(loginButton);

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new StaffHomePage();
    }

}
