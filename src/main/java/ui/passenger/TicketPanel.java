package ui.passenger;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicketPanel extends JPanel {

    public TicketPanel() {

        // Title
        JLabel title = new JLabel("Train Ticket", SwingConstants.CENTER);
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
        JButton activateButton = new RoundedButton("Activate");
        activateButton.setBackground(new Color(215, 215, 215));
        activateButton.setFont(new Font("Serif", Font.PLAIN, 25));
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
                activateButton.setEnabled(false);
                activateButton.setBackground(new Color(255, 255, 255));
                activateButton.setText("Active");
            }
        });

        this.setLayout(new GridLayout(5, 1));
        this.setBackground(new Color(185, 151, 144));

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
