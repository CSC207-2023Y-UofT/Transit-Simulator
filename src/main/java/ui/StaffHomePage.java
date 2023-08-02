package ui;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffHomePage {
    private JFrame frame;
    private JPanel panel;
    private JLabel staffTypeLabel;
    private JButton adminButton, trainEngineerButton, trainOperatorButton, backButton;


    public StaffHomePage() {

        // Create the frame and panel
        frame = new JFrame("Staff Select Page");
        panel = new JPanel(new GridLayout(0, 3));

        // Add the "Please select your user type." title
        staffTypeLabel = new JLabel("Please select your staff type.", SwingConstants.CENTER);
        staffTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the label

        // Admin button
        adminButton = new RoundedButton("Admin");
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the button
        adminButton.setBackground(new Color(135,156,210));
        adminButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new AdminPage();
                frame.dispose();
            }
        });

        // Train Engineer button
        trainEngineerButton = new RoundedButton("Train Engineer");
        trainEngineerButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the button
        trainEngineerButton.setBackground(new Color(135,156,210));
        trainEngineerButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        trainEngineerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new TrainEngineerPage();
                frame.dispose();
            }
        });

        // Train Operator button
        trainOperatorButton = new RoundedButton("Train Operator");
        trainOperatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        trainOperatorButton.setBackground(new Color(135,156,210));
        trainOperatorButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        trainOperatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new TrainOperatorPage();
                frame.dispose();
            }
        });

        // Back button
        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserTypePage();
                frame.dispose();
            }
        });


        // Add components to the panel

        panel.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(staffTypeLabel);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(adminButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(trainEngineerButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(trainOperatorButton);
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
        new StaffHomePage();
    }
}
