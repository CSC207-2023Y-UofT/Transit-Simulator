package ui.staff.engineer;

import ui.round.RoundedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EngineerMaintenance {
    private JFrame frame;
    private JPanel panel;
    private int id, line, train;
    private String engineer, operator;
    private JLabel idLabel, routeLabel, engineerLabel, operatorLabel;
    private JButton homeButton, routeButton, maintenanceButton;

    public EngineerMaintenance() {

        // Create the frame and panel
        frame = new JFrame("Engineer Maintenance");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 2));

        // Home button
        homeButton = new RoundedButton("Home");
        homeButton.setBackground(new Color(210, 207, 206));
        homeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        homeButton.setFont(new Font("Serif", Font.BOLD, 20));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StaffHomePage();
                frame.dispose();
            }
        });

        // id label
        id = 322; // TODO: should be .getId()
        idLabel = new JLabel("Engineer " + id, SwingConstants.CENTER);
        idLabel.setFont(new Font("Serif", Font.BOLD, 25));
        idLabel.setOpaque(true);
        idLabel.setBackground(new Color(255, 255, 255));

        // route label
        line = 1;
        train = 1; // TODO: should be .getLine() and .getTrain()
        routeLabel = new JLabel("Line " + line + " Train " + train);
        routeLabel.setFont(new Font("Serif", Font.BOLD, 25));
        routeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // operator label
        operator = "grace"; // TODO: should be .getOperator()
        operatorLabel = new JLabel("Operator: " + operator);
        operatorLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        operatorLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // engineer label
        engineer = "zoey"; // TODO: should be .getEngineer()
        engineerLabel = new JLabel("Engineer: " + engineer);
        engineerLabel.setFont(new Font("Serif", Font.ITALIC, 25));
        engineerLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // route button: does nothing since already on this page
        routeButton = new RoundedButton("Route");
        routeButton.setBackground(new Color(112,170, 255));
        routeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        routeButton.setFont(new Font("Serif", Font.BOLD, 20));

        // maintenance button
        maintenanceButton = new RoundedButton("Maintenance");
        maintenanceButton.setBackground(new Color(112,170, 255));
        maintenanceButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        maintenanceButton.setFont(new Font("Serif", Font.BOLD, 20));
        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EngineerMaintenance();
                frame.dispose();
            }
        });


        // Add components to panel

        panel.add(homeButton);
        panel.add(idLabel);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        panel.add(routeLabel);
        panel.add(new JLabel(""));

        panel.add(operatorLabel);
        panel.add(new JLabel(""));

        panel.add(engineerLabel);
        panel.add(new JLabel(""));

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        panel.add(routeButton);
        panel.add(maintenanceButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new EngineerMaintenance();
    }

}
