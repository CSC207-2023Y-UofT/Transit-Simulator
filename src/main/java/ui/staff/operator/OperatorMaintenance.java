package ui.staff.operator;

import ui.PageController;
import ui.round.RoundedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorMaintenance extends JPanel {
    public OperatorMaintenance(PageController controller) {
        super(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new GridLayout(0, 2));

        // Home button
        JButton homeButton = new RoundedButton("Home");
        homeButton.setBackground(new Color(210, 207, 206));
        homeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        homeButton.setFont(new Font("Serif", Font.BOLD, 20));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.open(new StaffHomePage(controller));
            }
        });

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

        // Create a table model - make the second column Boolean type
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Boolean.class : super.getColumnClass(columnIndex);
            }
        };

        // Create table with our table model
        JTable table = new JTable(model);

        // Set table properties
        table.setFont(new Font("Serif", Font.PLAIN, 20));
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
        JButton routeButton = new RoundedButton("Route");
        routeButton.setBackground(new Color(112, 170, 255));
        routeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        routeButton.setFont(new Font("Serif", Font.BOLD, 20));
        routeButton.addActionListener(e -> {
            controller.open(new OperatorRoute(controller));
        });

        // maintenance button: does nothing since already on this page
        JButton maintenanceButton = new RoundedButton("Maintenance");
        maintenanceButton.setBackground(new Color(112, 170, 255));
        maintenanceButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        maintenanceButton.setFont(new Font("Serif", Font.BOLD, 20));

        bottomPanel.add(routeButton);
        bottomPanel.add(maintenanceButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

}
