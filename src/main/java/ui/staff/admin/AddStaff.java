package ui.staff.admin;

import ui.UIController;
import ui.util.ShadowedButton;

import javax.swing.*;
import java.awt.*;

public class AddStaff extends JPanel {

    private UIController controller;
    private JComboBox<String> employeeTypeDropdown;
    private JTextField nameField;
    private JTextField staffNumberField;
    private JTextField assignedTrainField;
    private Management managementPanel;

    public AddStaff(UIController controller, Management managementPanel) {

        this.controller = controller;
        this.managementPanel = managementPanel;

        setLayout(new GridBagLayout());  // Switching to GridBagLayout for more control
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);  // Margins around components
        c.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Serif", Font.PLAIN, 16);  // Setting a common font

        // Employee Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 18));
        nameField = new JTextField(20);
        nameField.setFont(new Font("Serif", Font.PLAIN, 18));

        GridBagConstraints cName = (GridBagConstraints) c.clone(); // clone to keep properties
        cName.gridx = 0;
        cName.gridy = 0;
        add(nameLabel, cName);

        GridBagConstraints cNameField = (GridBagConstraints) c.clone(); // clone to keep properties
        cNameField.gridx = 1;
        cNameField.gridy = 0;
        add(nameField, cNameField);

        // Employee Type
        JLabel employeeTypeLabel = new JLabel("Employee Type:");
        employeeTypeLabel.setFont(font);
        c.gridx = 0;
        c.gridy = 1;
        add(employeeTypeLabel, c);

        String[] employeeTypes = {"Train Engineer", "Train Operator"};
        employeeTypeDropdown = new JComboBox<>(employeeTypes);
        employeeTypeDropdown.setFont(font);
        c.gridx = 1;
        c.gridy = 1;
        add(employeeTypeDropdown, c);

        // Staff Number
        JLabel staffNumberLabel = new JLabel("Staff Number:");
        staffNumberLabel.setFont(font);
        c.gridx = 0;
        c.gridy = 2;
        add(staffNumberLabel, c);

        staffNumberField = new JTextField(20);
        staffNumberField.setFont(font);
        c.gridx = 1;
        c.gridy = 2;
        add(staffNumberField, c);

        // Assigned Train
        JLabel assignedTrainLabel = new JLabel("Assigned Train:");
        assignedTrainLabel.setFont(font);
        c.gridx = 0;
        c.gridy = 3;
        add(assignedTrainLabel, c);

        assignedTrainField = new JTextField(20);
        assignedTrainField.setFont(font);
        c.gridx = 1;
        c.gridy = 3;
        add(assignedTrainField, c);

        // Add Button
        JButton addButton = new ShadowedButton("Add");
        addButton.setBackground(new Color(189, 87, 231));
        addButton.setFont(new Font("Serif", Font.BOLD, 18));  // Making button font slightly bolder
        addButton.addActionListener(e -> addStaffToManagement());
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;  // Spanning the button across 2 columns
        add(addButton, c);

        // Cancel Button
        JButton cancelButton = new ShadowedButton("Cancel");
        cancelButton.setBackground(new Color(255, 255, 255));
        cancelButton.setFont(new Font("Serif", Font.BOLD, 18));
        cancelButton.addActionListener(e -> controller.open(managementPanel));

        GridBagConstraints cCancel = (GridBagConstraints) c.clone(); // clone to keep properties
        cCancel.gridx = 0;
        cCancel.gridy = 5;
        cCancel.gridwidth = 2;
        add(cancelButton, cCancel);
    }

    private void addStaffToManagement() {

        String employeeName = nameField.getText();
        String employeeType = (String) employeeTypeDropdown.getSelectedItem();
        String staffNumber = staffNumberField.getText();
        String assignedTrain = assignedTrainField.getText();

        // Validate input
        if (employeeName.trim().isEmpty() || employeeType == null || staffNumber.trim().isEmpty() || assignedTrain.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        managementPanel.addStaffRow(new Object[]{employeeName, employeeType, staffNumber, assignedTrain});
        staffNumberField.setText("");
        assignedTrainField.setText("");

        controller.open(managementPanel);
    }

}
