package ui.staff.operator;

import ui.round.RoundedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorMaintenance {
    private final JFrame frame;
    private final JPanel topPanel;
    private final JPanel middlePanel;
    private final JPanel bottomPanel;
    private final int id;
    private int line;
    private int train;
    private String operator, engineer;
    private final JLabel idLabel;
    private JLabel routeLabel;
    private JLabel operatorLabel;
    private JLabel engineerLabel;
    private final JButton homeButton;
    private final JButton routeButton;
    private final JButton maintenanceButton;
    private final JTable table;

    public OperatorMaintenance() {

        // Create the frame
        frame = new JFrame("Operator Maintenance");
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
        middlePanel = new JPanel();
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
        table = new JTable(model);

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
        frame.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel
        bottomPanel = new JPanel(new GridLayout(0, 2));

        // route button
        routeButton = new RoundedButton("Route");
        routeButton.setBackground(new Color(112, 170, 255));
        routeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        routeButton.setFont(new Font("Serif", Font.BOLD, 20));
        routeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OperatorRoute();
                frame.dispose();
            }
        });

        // maintenance button: does nothing since already on this page
        maintenanceButton = new RoundedButton("Maintenance");
        maintenanceButton.setBackground(new Color(112, 170, 255));
        maintenanceButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        maintenanceButton.setFont(new Font("Serif", Font.BOLD, 20));

        bottomPanel.add(routeButton);
        bottomPanel.add(maintenanceButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new OperatorMaintenance();
    }

}
