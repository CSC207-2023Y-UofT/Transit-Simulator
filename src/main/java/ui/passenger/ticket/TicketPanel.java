package ui.passenger.ticket;

import ui.util.ShadowedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicketPanel extends JPanel {

    public TicketPanel() {

        // Title
        String type = "Adult"; // TODO: Replace with actual ticket type
        JLabel title = new JLabel(type + " Ticket", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));

        // Ticket ID
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        JLabel ticketId = new JLabel("Ticket ID: " + randomId, SwingConstants.CENTER);
        ticketId.setFont(new Font("Serif", Font.PLAIN, 25));

        // Valid Time
        JLabel validTime = new JLabel("", SwingConstants.CENTER);
        validTime.setFont(new Font("Serif", Font.PLAIN, 25));

        // Message
        JLabel messageLabel = new JLabel("Activate before boarding", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Serif", Font.PLAIN, 25));

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
        
        // Activate button
        JButton activateButton = new ShadowedButton("Activate");
        activateButton.setBackground(new Color(238, 238, 238));
        activateButton.setFont(new Font("Serif", Font.PLAIN, 25));
        activateButton.addActionListener(e -> {
            timer.start();
            activateButton.setEnabled(false);
            activateButton.setBackground(new Color(255, 255, 255));
            activateButton.setText("Active");
            this.setBackground(new Color(255, 255, 255));
        });

        this.setLayout(new GridLayout(5, 1));
        this.setBackground(new Color(206, 184, 180));

        this.add(title);
        this.add(ticketId);
        this.add(messageLabel);
        this.add(validTime);
        this.add(activateButton);
    }

    public static void main(String[] args) {
        new TicketPanel();
    }

}
