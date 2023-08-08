package ui.map;

import ui.UIController;
import ui.staff.StaffHomePage;
import ui.util.ShadowedButton;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StationPanel extends JPanel {

    private final JPanel topPanel;
    private final JPanel bottomPanel;

    public StationPanel() {

        topPanel = new JPanel(new GridLayout(0, 6));
        JButton closeButton = new ShadowedButton("Close");
        topPanel.add(closeButton, BorderLayout.EAST);
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        // closeButton.addActionListener(e -> close the frame));

        bottomPanel = new JPanel(new GridLayout(0, 1));

        String station = "Bloor-Younge";    // TODO: should be get station
        JLabel stationLabel = new JLabel(station + " Station", SwingConstants.CENTER);
        stationLabel.setFont(new Font("Arial", Font.BOLD, 20));

        int line = 1;   // TODO: should be getLine
        JLabel lineLabel = new JLabel("Line " + line);
        lineLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel subtitleLabel = new JLabel("Train Schedule");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel messageLabel = new JLabel("Trains run every 2-3 minutes during the \n" +
                "rush hours (from 6 a.m. to 9 a.m. and from \n" +
                "3 p.m. to 7 p.m.) and every 4-5 minutes \n" +
                "during off-peak hours.");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JTable table = new JTable();
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

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setPreferredSize(new Dimension(500, 300)); // Adjust to desired size

        // idk what u want on the table

        bottomPanel.add(stationLabel);
        bottomPanel.add(lineLabel);
        bottomPanel.add(new JLabel(" "));
        bottomPanel.add(subtitleLabel);
        bottomPanel.add(messageLabel);
        bottomPanel.add(new JLabel(" "));
        bottomPanel.add(table);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);

    }

}
