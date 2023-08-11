package ui.staff.operator;

import app_business.dto.EmployeeDTO;
import interface_adapter.viewmodel.MaintenanceViewModel;
import ui.UIController;
import ui.util.ShadowedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
    private final MaintenanceViewModel maintenanceViewModel;
    private final JTable table;
    private final DefaultTableModel model;

    /**
     * Constructs a new OperatorMaintenance object.
     *
     * @param controller the controller used to switch panels
     */
    public OperatorMaintenance(UIController controller, EmployeeDTO employeeDTO) {
        super(new BorderLayout());

        this.employeeDTO = employeeDTO;
        this.maintenanceViewModel = new MaintenanceViewModel(controller.getControllerPool().getTrainController());

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

        maintenanceViewModel.update();

        // Create data
        Object[][] data = maintenanceViewModel.getMaintenanceTable();

        // Create a table model
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Boolean.class : super.getColumnClass(columnIndex);
            }
        };

        // Create table with our table model and set table properties
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(30);
        table.setGridColor(Color.DARK_GRAY);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(String.class, centerRenderer);

        // Ensure only checkboxes can be edited
        table.setDefaultEditor(Object.class, null);

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 1) {
                    String trainName = (String) model.getValueAt(row, 0);
                    boolean needsMaintenance = (boolean) model.getValueAt(row, 1);
                    maintenanceViewModel.setNeedsMaintenance(trainName, needsMaintenance);
                    updateTable();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setPreferredSize(new Dimension(500, 300)); // Adjust to desired size

        middlePanel.add(scrollPane, BorderLayout.CENTER);
        this.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(0, 2));

        // route button
        JButton routeButton = new ShadowedButton("Assigned Route");
        routeButton.setBackground(new Color(222, 175, 119));
        routeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        routeButton.setFont(new Font("Arial", Font.BOLD, 20));
        routeButton.addActionListener(e -> controller.open(new OperatorRoutePage(controller, employeeDTO)));

        // maintenance button: does nothing since already on this page
        JButton maintenanceButton = new ShadowedButton("Maintenance");
        maintenanceButton.setBackground(new Color(222, 144, 53));
        maintenanceButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        maintenanceButton.setFont(new Font("Arial", Font.BOLD, 20));

        bottomPanel.add(routeButton);
        bottomPanel.add(maintenanceButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateTable() {
        maintenanceViewModel.update();
        Object[][] data = maintenanceViewModel.getMaintenanceTable();
        model.setDataVector(data, new String[]{"Train", "Needs Maintenance?"});
        model.fireTableDataChanged();
        repaint();
    }

}
