package ui.staff.admin;

import interface_adapter.viewmodel.ManageEmployeesViewModel;
import interface_adapter.viewmodel.SingletonStatViewModel;
import app_business.dto.EmployeeDTO;
import app_business.common.EmployeeType;
import ui.UIController;
import ui.util.ShadowPanel;
import ui.util.ShadowedButton;
import ui.staff.StaffHomePage;

import javax.swing.table.JTableHeader;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Management is a JPanel that displays the management page.
 * It is used by the UIController to display the management page.
 *
 * @see UIController
 */
public class Management extends JPanel {

    /**
     * The JPanel that displays the stats.
     */
    private final JPanel statsHolderPanel;

    /**
     * The ManageEmployeesViewModel that handles the employee data.
     */
    private final ManageEmployeesViewModel manage;

    /**
     * The JPanel that displays the middle panel.
     */
    private final JPanel middlePanel;

    /**
     * The JTable that displays the staff.
     */
    private final JTable table;
    private final UIController controller;

    /**
     * Constructs a new Management object with the given UIController.
     *
     * @param controller the controller used to switch panels
     */
    public Management(UIController controller, EmployeeDTO employee) {
        super(new BorderLayout());
        this.controller = controller;

        this.manage = new ManageEmployeesViewModel(controller.getControllerPool().getEmployeeController());

        // Top panel
        JPanel topPanel = new JPanel(new GridLayout(0, 2));

        // Home button
        JButton homeButton = new ShadowedButton("Home");
        homeButton.setBackground(new Color(210, 207, 206));
        homeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        homeButton.addActionListener(e -> controller.open(new StaffHomePage(controller)));

        // id label
        int id = employee.getStaffNumber();
        JLabel idLabel = new JLabel("Admin " + id, SwingConstants.CENTER);
        idLabel.setFont(new Font("Arial", Font.BOLD, 25));
        idLabel.setOpaque(true);
        idLabel.setBackground(new Color(255, 255, 255));

        topPanel.add(homeButton);
        topPanel.add(idLabel);
        this.add(topPanel, BorderLayout.NORTH);

        // Middle panel
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Column Names
        String[] columns = {
                "Name", "Employee Type", "Staff number", "Assigned Train"
        };

        // Row Data
        Object[][] data = {};

        // Create Table Model
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Only one row can be selected at a time

        // Centering content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adjusting the table look
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        table.setFont(tableFont);
        table.setRowHeight(30);
        table.setGridColor(new Color(221, 221, 221));
        table.setShowGrid(true);
        Font headerFont = new Font("Arial", Font.BOLD, 18);
        JTableHeader header = table.getTableHeader();
        header.setFont(headerFont);

        updateTable();

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setPreferredSize(new Dimension(500, 300)); // Adjust to desired size

        // Add staff button
        JButton addStaffButton = new ShadowedButton("Add Staff");
        addStaffButton.setBackground(new Color(201, 153, 222));
        addStaffButton.setFont(new Font("Arial", Font.BOLD, 20));
        addStaffButton.addActionListener(e -> controller.open(new AddStaff(controller, this)));

        // Remove staff button
        JButton removeStaffButton = new ShadowedButton("Remove Staff");
        removeStaffButton.setBackground(new Color(201, 153, 222));
        removeStaffButton.setFont(new Font("Arial", Font.BOLD, 20));
        removeStaffButton.addActionListener(e -> removeSelectedStaff());

        middlePanel.add(new JLabel("    "), BorderLayout.NORTH);
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel editsPanel = new JPanel(new GridLayout(0, 2));
        editsPanel.add(addStaffButton, BorderLayout.WEST);
        editsPanel.add(removeStaffButton, BorderLayout.EAST);

        middlePanel.add(editsPanel, BorderLayout.SOUTH);
        this.add(middlePanel, BorderLayout.CENTER);

        // Middle panel but for stats
        statsHolderPanel = new JPanel(new GridLayout(0, 2));

        ShadowPanel statPanel1 = new ShadowPanel(new BorderLayout());
        statPanel1.setExtraInset(10);
        // The JPanel that displays the revenue stats.
        StatsPanel statsPanel1 = new StatsPanel(controller);
        statsPanel1.getViewModel().setGraphColour(SingletonStatViewModel.GraphColour.GREEN);
        statPanel1.add(statsPanel1);

        ShadowPanel statPanel2 = new ShadowPanel(new BorderLayout());
        statPanel2.setExtraInset(10);

        // The JPanel that displays the expense stats.
        StatsPanel statsPanel2 = new StatsPanel(controller);
        statsPanel2.getViewModel().setGraphColour(SingletonStatViewModel.GraphColour.RED);
        statsPanel2.setDisplay(StatsPanel.StatDisplay.EXPENSES);
        statPanel2.add(statsPanel2);

        statsHolderPanel.add(statPanel1);
        statsHolderPanel.add(statPanel2);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(0, 2));

        JButton managementButton = new JButton("Manage");
        managementButton.setBackground(new Color(189, 87, 231));
        managementButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        managementButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton statButton = new JButton("Statistics");
        statButton.setBackground(new Color(224, 224, 224));
        statButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        statButton.setFont(new Font("Arial", Font.BOLD, 20));

        managementButton.addActionListener(e -> {
            remove(statsHolderPanel);
            add(middlePanel, BorderLayout.CENTER);
            managementButton.setBackground(new Color(189, 87, 231));
            statButton.setBackground(new Color(224, 224, 224));
            revalidate();
            repaint();
        });

        statButton.addActionListener(e -> {
            remove(middlePanel);
            add(statsHolderPanel, BorderLayout.CENTER);
            statButton.setBackground(new Color(189, 87, 231));
            managementButton.setBackground(new Color(224, 224, 224));
            revalidate();
            repaint();
        });

        bottomPanel.add(managementButton);
        bottomPanel.add(statButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        Object[][] data = manage.getTableData();

        while (model.getRowCount() > 0) model.removeRow(0);

        for (Object[] datum : data) {
            model.addRow(datum);
        }

        model.fireTableDataChanged();
    }


    /**
     * Removes the selected staff from the table.
     */
    private void removeSelectedStaff() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) { // If a row is selected
            int staffNumber = (int) model.getValueAt(selectedRow, 2);

            var employees = controller.getControllerPool().getEmployeeController();

            var employee = employees.find(staffNumber)
                            .orElse(null);
            if (employee == null) return;

            if (employee.getType() == EmployeeType.ADMINISTRATOR) {
                JOptionPane.showMessageDialog(this, "Cannot remove an administrator.");
                return;
            }

            employees.removeEmployee(staffNumber);

            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a staff to remove.");
        }
    }



}
