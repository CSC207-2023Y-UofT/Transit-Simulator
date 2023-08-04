package ui.staff;

import ui.PageController;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffSelectPage extends JPanel {
    private JLabel staffTypeLabel;
    private JButton adminButton, trainEngineerButton, trainOperatorButton, backButton;

    public StaffSelectPage(PageController controller) {
        super(new GridLayout(0, 3));

        staffTypeLabel = new JLabel("Please select your staff type.", SwingConstants.CENTER);
        staffTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        staffTypeLabel.setFont(new Font("Serif", Font.BOLD, 25));

        // Admin button
        adminButton = new RoundedButton("Admin");
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminButton.setBackground(new Color(112, 170, 255));
        adminButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        adminButton.setFont(new Font("Serif", Font.BOLD, 20));
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage(controller);
                frame.dispose();
            }
        });

        // Train Operator button
        trainOperatorButton = new RoundedButton("Train Operator");
        trainOperatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        trainOperatorButton.setBackground(new Color(112, 170, 255));
        trainOperatorButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        trainOperatorButton.setFont(new Font("Serif", Font.BOLD, 20));
        trainOperatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OperatorLoginPage(controller);
                frame.dispose();
            }
        });

        // Train Engineer button
        trainEngineerButton = new RoundedButton("Train Engineer");
        trainEngineerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        trainEngineerButton.setBackground(new Color(112, 170, 255));
        trainEngineerButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        trainEngineerButton.setFont(new Font("Serif", Font.BOLD, 20));
        trainEngineerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EngineerLoginPage(controller);
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
                new StaffHomePage(controller);
                frame.dispose();
            }
        });


        // Add components to the panel

        this.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(staffTypeLabel);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(adminButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(trainOperatorButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(trainEngineerButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 6; i++) {
            this.add(new JLabel("  "));
        }

        this.add(backButton);
        this.add(new JLabel("  "));
        this.add(new JLabel("  "));
    }

}
