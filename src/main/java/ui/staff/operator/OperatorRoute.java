package ui.staff.operator;

import ui.UIController;
import ui.round.RoundedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import java.awt.*;

/**
 * OperatorRoute is a JPanel that displays the route of a train.
 * It is used by the UIController to display the route of a train.
 *
 * @see UIController
 */
public class OperatorRoute extends JPanel {
    /**
     * Constructs a new OperatorRoute object.
     * @param controller the controller used to switch panels
     */
    public OperatorRoute(UIController controller) {

        // Top panel
        JPanel topPanel = new JPanel(new GridLayout(0, 2));

        // Home button
        JButton homeButton = new RoundedButton("Home");
        homeButton.setBackground(new Color(210, 207, 206));
        homeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        homeButton.setFont(new Font("Serif", Font.BOLD, 20));
        homeButton.addActionListener(e -> controller.open(new StaffHomePage(controller)));

        // id label
        int id = 322; // TODO: should be .getId()
        JLabel idLabel = new JLabel("Operator " + id, SwingConstants.CENTER);
        idLabel.setFont(new Font("Serif", Font.BOLD, 25));
        idLabel.setOpaque(true);
        idLabel.setBackground(new Color(255, 255, 255));

        topPanel.add(homeButton);
        topPanel.add(idLabel);
        this.add(topPanel, BorderLayout.NORTH);

        // Middle panel
        JPanel middlePanel = new JPanel(new GridLayout(0, 5));

        // route label
        int line = 1;
        int train = 1; // TODO: should be .getLine() and .getTrain()
        JLabel routeLabel = new JLabel("Line " + line + " Train " + train);
        routeLabel.setFont(new Font("Serif", Font.BOLD, 25));

        // operator label
        String operator = "grace"; // TODO: should be .getOperator()
        JLabel operatorLabel = new JLabel("Operator: " + operator);
        operatorLabel.setFont(new Font("Serif", Font.ITALIC, 25));

        // engineer label
        String engineer = "zoey"; // TODO: should be .getEngineer()
        JLabel engineerLabel = new JLabel("Engineer: " + engineer);
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

        this.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(0, 2));

        // route button: does nothing since already on this page
        JButton routeButton = new RoundedButton("Assigned Route");
        routeButton.setBackground(new Color(80, 148, 255));
        routeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        routeButton.setFont(new Font("Serif", Font.BOLD, 20));

        // maintenance button
        JButton maintenanceButton = new RoundedButton("Maintenance");
        maintenanceButton.setBackground(new Color(128, 155, 197));
        maintenanceButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        maintenanceButton.setFont(new Font("Serif", Font.BOLD, 20));
        maintenanceButton.addActionListener(e -> controller.open(new OperatorMaintenance(controller)));

        bottomPanel.add(routeButton);
        bottomPanel.add(maintenanceButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

}
