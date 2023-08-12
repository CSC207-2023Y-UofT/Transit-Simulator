package ui.staff.engineer;

import app_business.dto.EmployeeDTO;
import interface_adapter.viewmodel.RouteViewModel;
import ui.UIController;
import ui.util.ShadowedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import java.awt.*;

/**
 * OperatorRoute is a JPanel that displays the route that the operator is assigned to.
 * It is used by the UIController.
 *
 * @see UIController
 */
public class EngineerRoutePage extends JPanel {

    /**
     * Constructs a new OperatorRoute object.
     * @param controller the controller used to switch panels
     */
    public EngineerRoutePage(UIController controller, EmployeeDTO employeeDTO) {
        super(new BorderLayout());

        RouteViewModel routeViewModel = new RouteViewModel(employeeDTO,
                controller.getControllerPool().getEmployeeController());

        // Top panel
        JPanel topPanel = new JPanel(new GridLayout(0, 2));

        // Home button
        JButton homeButton = new ShadowedButton("Home");
        homeButton.setBackground(new Color(210, 207, 206));
        homeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        homeButton.addActionListener(e -> controller.open(new StaffHomePage(controller)));

        // id label
        int id = employeeDTO.getStaffNumber();
        JLabel idLabel = new JLabel("Engineer " + id, SwingConstants.CENTER);
        idLabel.setFont(new Font("Arial", Font.BOLD, 25));
        idLabel.setOpaque(true);
        idLabel.setBackground(new Color(255, 255, 255));

        topPanel.add(homeButton);
        topPanel.add(idLabel);
        this.add(topPanel, BorderLayout.NORTH);

        // Middle panel
        JPanel middlePanel = new JPanel(new GridLayout(0, 1));

        // route label
        JLabel routeLabel = new JLabel(routeViewModel.getWhatToSay());
        routeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        routeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        routeLabel.setOpaque(true);
        routeLabel.setBackground(new Color(255, 255, 255));
        middlePanel.add(routeLabel);

        this.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(0, 2));

        // route button: does nothing since already on this page
        JButton routeButton = new ShadowedButton("Assigned Route");
        routeButton.setBackground(new Color(57, 210, 190));
        routeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        routeButton.setFont(new Font("Arial", Font.BOLD, 20));

        // maintenance button
        JButton maintenanceButton = new ShadowedButton("Maintenance");
        maintenanceButton.setBackground(new Color(136, 203, 194));
        maintenanceButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        maintenanceButton.setFont(new Font("Arial", Font.BOLD, 20));
        maintenanceButton.addActionListener(e -> controller.open(new EngineerMaintenance(controller, employeeDTO)));

        bottomPanel.add(routeButton);
        bottomPanel.add(maintenanceButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

}
