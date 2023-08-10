package ui.staff.operator;

import app_business.dto.EmployeeDTO;
import ui.UIController;
import ui.util.ShadowedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * OperatorMaintenance is a JPanel that displays the maintenance status of all trains.
 * It is used by the UIController to display the maintenance status of all trains.
 *
 * @see UIController
 */
public class OperatorMaintenance extends JPanel {

    private final EmployeeDTO employeeDTO;

    /**
     * Constructs a new OperatorMaintenance object.
     *
     * @param controller the controller used to switch panels
     */
    public OperatorMaintenance(UIController controller, EmployeeDTO employeeDTO) {
        super(new BorderLayout());

        this.employeeDTO = employeeDTO;

        // Top panel
        JPanel topPanel = new JPanel(new GridLayout(0, 2));

        // Home button
        JButton homeButton = new ShadowedButton("Home");
        homeButton.setBackground(new Color(210, 207, 206));
        homeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        homeButton.addActionListener(e -> controller.open(new StaffHomePage(controller)));

        // id label
        int id = 322; // TODO: should be .getId()
        JLabel idLabel = new JLabel("Operator " + id, SwingConstants.CENTER);
        idLabel.setFont(new Font("Arial", Font.BOLD, 25));
        idLabel.setOpaque(true);
        idLabel.setBackground(new Color(255, 255, 255));

        topPanel.add(homeButton);
        topPanel.add(idLabel);
        this.add(topPanel, BorderLayout.NORTH);

        // Middle panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Create column names
        String[] columnNames = {"Train", "Needs Maintenance?"};

        // Create data
        Object[][] data = {
                {"Train 1", Boolean.TRUE},
                {"Train 2", Boolean.TRUE},
                {"Train 3", Boolean.TRUE},
                {"Train 4", Boolean.TRUE},
                {"Train 5", Boolean.TRUE}
        };

        // Create a table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Boolean.class : super.getColumnClass(columnIndex);
            }
        };

        // Create table with our table model and set table properties
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(30);
        table.setGridColor(Color.DARK_GRAY);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(String.class, centerRenderer);

        // Ensure only checkboxes can be edited
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setPreferredSize(new Dimension(500, 300)); // Adjust to desired size

        middlePanel.add(scrollPane, BorderLayout.SOUTH);
        this.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(0, 2));

        // route button
        JButton routeButton = new ShadowedButton("Assigned Route");
        routeButton.setBackground(new Color(222, 175, 119));
        routeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        routeButton.setFont(new Font("Arial", Font.BOLD, 20));
        routeButton.addActionListener(e -> {
            controller.open(new OperatorRoute(controller));
        });

        // maintenance button: does nothing since already on this page
        JButton maintenanceButton = new ShadowedButton("Maintenance");
        maintenanceButton.setBackground(new Color(222, 144, 53));
        maintenanceButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        maintenanceButton.setFont(new Font("Arial", Font.BOLD, 20));

        bottomPanel.add(routeButton);
        bottomPanel.add(maintenanceButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

}
