package ui.passenger;

import ui.util.ShadowBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicketPanel extends JPanel {

    private final JPanel innerPanel = new JPanel(new GridLayout(6, 1));

    public TicketPanel() {
        super(new GridLayout(1, 1));

        setBackground(Color.ORANGE);

        // Margin
        setBorder(new EmptyBorder(20, 20, 20, 20));
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBorder(new ShadowBorder());

        JLabel title = new JLabel("Train Ticket", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));

        // Message
        JLabel message = new JLabel("Do not close this tab. You will lose your ticket.", SwingConstants.CENTER);
        message.setFont(new Font("Serif", Font.PLAIN, 25));

        // Ticket ID
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        JLabel ticketId = new JLabel("Ticket ID: " + randomId, SwingConstants.CENTER);
        ticketId.setFont(new Font("Serif", Font.PLAIN, 25));

        // Valid Time
        JLabel validTime = new JLabel("", SwingConstants.CENTER);
        validTime.setFont(new Font("Serif", Font.PLAIN, 25));

        // Ticket Type
        String ticketType = "Single";   // temporary
        JLabel typeLabel = new JLabel("Ticket Type: " + ticketType, SwingConstants.CENTER);
        typeLabel.setFont(new Font("Serif", Font.PLAIN, 25));

        // Timer code: DO NOT MODIFY!!
        Timer timer = new Timer(1000, new ActionListener() {
            int remaining = 2 * 60 * 60; // 2 hours in seconds

            @Override
            public void actionPerformed(ActionEvent e) {
                remaining--;
                if (remaining < 0) {
                    ((Timer) e.getSource()).stop();
                    validTime.setText("Ticket has expired");
                } else {
                    int hours = remaining / 3600;
                    int minutes = (remaining % 3600) / 60;
                    int seconds = remaining % 60;
                    validTime.setText(String.format("Ticket Validity: %02d:%02d:%02d", hours, minutes, seconds));
                }
            }
        });
        timer.start();


        // Add components to panel
        innerPanel.add(title);
        innerPanel.add(message);
        innerPanel.add(ticketId);
        innerPanel.add(typeLabel);
        innerPanel.add(validTime);

        add(innerPanel);
    }
}
