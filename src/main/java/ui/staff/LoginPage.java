package ui.staff;

import interface_adapter.controller.EmployeeController;
import app_business.dto.EmployeeDTO;
import app_business.common.EmployeeType;
import ui.UIController;
import ui.staff.admin.Management;
import ui.staff.engineer.EngineerMaintenance;
import ui.staff.engineer.EngineerRoutePage;
import ui.staff.operator.OperatorMaintenance;
import ui.staff.operator.OperatorRoutePage;
import ui.util.ShadowedButton;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * LoginPage is a JPanel that displays the login page for staff.
 * It is used by the UIController to display the login page for staff.
 *
 * @see UIController
 */
public class LoginPage extends JPanel {

    /**
     * Constructs a new LoginPage object.
     *
     * @param controller the controller used to switch panels
     */
    public LoginPage(UIController controller) {
        super(new GridLayout(0, 3));

        // Personnel Number
        JLabel label = new JLabel("Personnel Number: ", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 25));

        JTextField personnelNumberField = new JPasswordField();
        personnelNumberField.setFont(new Font("Arial", Font.PLAIN, 20));

        // Sign In button
        JButton signInButton = new ShadowedButton("Sign In");
        signInButton.setBackground(new Color(0, 151, 8));
        signInButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        signInButton.setFont(new Font("Arial", Font.BOLD, 20));
        signInButton.addActionListener(e -> {

            int personnelNumber;
            try {
                personnelNumber = Integer.parseInt(personnelNumberField.getText());
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Please enter a valid personnel number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            EmployeeController employees = controller.getControllerPool().getEmployeeController();
            Optional<EmployeeDTO> loginRequest = employees.login(personnelNumber);
            if (loginRequest.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid personnel number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            EmployeeDTO employee = loginRequest.get();
            if (employee.getType() == EmployeeType.ADMINISTRATOR) {
                controller.open(new Management(controller, employee));
            } else if (employee.getType() == EmployeeType.ENGINEER) {
                controller.open(new EngineerRoutePage(controller, employee));
            } else if (employee.getType() == EmployeeType.OPERATOR) {
                controller.open(new OperatorRoutePage(controller, employee));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid personnel type: " +
                        employee.getType(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        });


        // Back button
        JButton backButton = new ShadowedButton("Back");
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> controller.open(new StaffHomePage(controller)));

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

}
