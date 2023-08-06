package ui.staff.admin;

import ui.UIController;

import javax.swing.*;
import java.awt.*;

public class AddStaff extends JPanel {

    private UIController controller;
    private JComboBox<String> employeeTypeDropdown;
    private JTextField staffNumberField;
    private JTextField assignedTrainField;
    private Management managementPanel;

    public AddStaff(UIController controller, Management managementPanel) {

        this.controller = controller;
        this.managementPanel = managementPanel;

        setLayout(new GridLayout(4, 2));

        // Create and add form components
        JLabel employeeTypeLabel = new JLabel("Employee Type:");

        // Dropdown menu for employee types
        String[] employeeTypes = { "Train Engineer", "Train Operator" };
        employeeTypeDropdown = new JComboBox<>(employeeTypes);

        JLabel staffNumberLabel = new JLabel("Staff Number:");
        staffNumberField = new JTextField(20);

        JLabel assignedTrainLabel = new JLabel("Assigned Train:");
        assignedTrainField = new JTextField(20);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addStaffToManagement());

        add(employeeTypeLabel);
        add(employeeTypeDropdown);
        add(staffNumberLabel);
        add(staffNumberField);
        add(assignedTrainLabel);
        add(assignedTrainField);
        add(new JLabel()); // Empty label for alignment
        add(addButton);
    }

    private void addStaffToManagement() {
        // Get values from fields
        String employeeType = (String) employeeTypeDropdown.getSelectedItem();
        String staffNumber = staffNumberField.getText();
        String assignedTrain = assignedTrainField.getText();

        // Assuming managementPanel contains a method to add this data to its table
        managementPanel.addStaffRow(new Object[]{employeeType, staffNumber, assignedTrain});

        // Optionally, clear the fields for another input
        staffNumberField.setText("");
        assignedTrainField.setText("");

        controller.open(managementPanel);
    }
}
