package ui.staff.operator;

import ui.round.RoundedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorRoute {
    private final JFrame frame;
    private final JPanel topPanel;
    private final JPanel middlePanel;
    private final JPanel bottomPanel;
    private final int id;
    private final int line;
    private final int train;
    private final String operator;
    private final String engineer;
    private final JLabel idLabel;
    private final JLabel routeLabel;
    private final JLabel operatorLabel;
    private final JLabel engineerLabel;
    private final JButton homeButton;
    private final JButton routeButton;
    private final JButton maintenanceButton;

    public OperatorRoute() {

        // Create the frame
        frame = new JFrame("Operator Route");
        frame.setPreferredSize(new Dimension(900, 600));

        // Top panel
        topPanel = new JPanel(new GridLayout(0, 2));

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
        idLabel = new JLabel("Operator " + id, SwingConstants.CENTER);
        idLabel.setFont(new Font("Serif", Font.BOLD, 25));
        idLabel.setOpaque(true);
        idLabel.setBackground(new Color(255, 255, 255));

        topPanel.add(homeButton);
        topPanel.add(idLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // Middle panel
        middlePanel = new JPanel(new GridLayout(0, 5));

        // route label
        line = 1;
        train = 1; // TODO: should be .getLine() and .getTrain()
        routeLabel = new JLabel("Line " + line + " Train " + train);
        routeLabel.setFont(new Font("Serif", Font.BOLD, 25));

        // operator label
        operator = "grace"; // TODO: should be .getOperator()
        operatorLabel = new JLabel("Operator: " + operator);
        operatorLabel.setFont(new Font("Serif", Font.ITALIC, 25));

        // engineer label
        engineer = "zoey"; // TODO: should be .getEngineer()
        engineerLabel = new JLabel("Engineer: " + engineer);
        engineerLabel.setFont(new Font("Serif", Font.PLAIN, 25));

        for (int i = 0; i < 5; i++) {
            middlePanel.add(new JLabel(""));
        }

        middlePanel.add(new JLabel(""));
        middlePanel.add(new JLabel(""));
        middlePanel.add(routeLabel);
        middlePanel.add(new JLabel(""));
        middlePanel.add(new JLabel(""));

        middlePanel.add(new JLabel(""));
        middlePanel.add(new JLabel(""));
        middlePanel.add(operatorLabel);
        middlePanel.add(new JLabel(""));
        middlePanel.add(new JLabel(""));

        middlePanel.add(new JLabel(""));
        middlePanel.add(new JLabel(""));
        middlePanel.add(engineerLabel);
        middlePanel.add(new JLabel(""));
        middlePanel.add(new JLabel(""));

        for (int i = 0; i < 5; i++) {
            middlePanel.add(new JLabel(""));
        }

        frame.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel
        bottomPanel = new JPanel(new GridLayout(0, 2));

        // route button: does nothing since already on this page
        routeButton = new RoundedButton("Route");
        routeButton.setBackground(new Color(112, 170, 255));
        routeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        routeButton.setFont(new Font("Serif", Font.BOLD, 20));

        // maintenance button
        maintenanceButton = new RoundedButton("Maintenance");
        maintenanceButton.setBackground(new Color(112, 170, 255));
        maintenanceButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        maintenanceButton.setFont(new Font("Serif", Font.BOLD, 20));
        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OperatorMaintenance();
                frame.dispose();
            }
        });

        bottomPanel.add(routeButton);
        bottomPanel.add(maintenanceButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new OperatorRoute();
    }

}
