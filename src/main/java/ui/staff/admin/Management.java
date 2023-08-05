package ui.staff.admin;

import ui.UIController;
import ui.round.RoundedButton;
import ui.staff.StaffHomePage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Management is a JPanel that displays the management page.
 * It is used by the UIController to display the management page.
 *
 * @see UIController
 */
public class Management extends JPanel {

    /**
     * Constructs a new Management object.
     *
     * @param controller the controller used to switch panels
     */
    public Management(UIController controller) {
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
        JLabel idLabel = new JLabel("Admin " + id, SwingConstants.CENTER);
        idLabel.setFont(new Font("Serif", Font.BOLD, 25));
        idLabel.setOpaque(true);
        idLabel.setBackground(new Color(255, 255, 255));

        topPanel.add(homeButton);
        topPanel.add(idLabel);
        this.add(topPanel, BorderLayout.NORTH);


        // Middle panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Column Names
        String[] columns = {
                "Employee Type", "Staff number", "Assigned Train"
        };

        // Row Data
        Object[][] data = {
                {"Train Operator", 110, 1},
                {"Train Engineer", 242, 1},
                {"Train Operator", 550, 2},
                {"Train Engineer", 666, 2}
        };

        // Create Table Model
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        // Centering content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);


        // Setting the center alignment to all columns
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adjusting the table look
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setPreferredSize(new Dimension(500, 300)); // Adjust to desired size

        middlePanel.add(scrollPane, BorderLayout.SOUTH);
        this.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(0, 2));

        // management button: does nothing since already on this page
        JButton managementButton = new RoundedButton("Manage");
        managementButton.setBackground(new Color(112, 170, 255));
        managementButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        managementButton.setFont(new Font("Serif", Font.BOLD, 20));

        // stats button
        JButton statButton = new RoundedButton("Statistics");
        statButton.setBackground(new Color(112, 170, 255));
        statButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        statButton.setFont(new Font("Serif", Font.BOLD, 20));
        statButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // controller.open(new (controller));
            }
        });

        bottomPanel.add(managementButton);
        bottomPanel.add(statButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

}
