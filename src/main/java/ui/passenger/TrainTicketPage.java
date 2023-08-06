package ui.passenger;

import ui.UIController;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class TrainTicketPage {

    private JFrame frame;
    private JPanel panel;
    private JLabel title, message, ticketId, typeLabel;


    /**
     * The JLabel that displays the valid time of the ticket.
     */
    private final JLabel validTime;


    public TrainTicketPage() {

        frame = new JFrame("Train Ticket");
        frame.setPreferredSize(new Dimension(1500, 900));
        panel = new JPanel(new GridLayout(6, 1));
        panel.setBackground(new Color(185, 151, 144));

        title = new JLabel("Train Ticket", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));

        // Message
        message = new JLabel("Do not close this tab. You will lose your ticket.", SwingConstants.CENTER);
        message.setFont(new Font("Serif", Font.PLAIN, 25));

        // Ticket ID
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        ticketId = new JLabel("Ticket ID: " + randomId, SwingConstants.CENTER);
        ticketId.setFont(new Font("Serif", Font.PLAIN, 25));

        // Valid Time
        validTime = new JLabel("", SwingConstants.CENTER);
        validTime.setFont(new Font("Serif", Font.PLAIN, 25));

        // Ticket Type
        String ticketType = "Single";   // temporary
        typeLabel = new JLabel("Ticket Type: " + ticketType, SwingConstants.CENTER);
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
        panel.add(title);
        panel.add(message);
        panel.add(ticketId);
        panel.add(typeLabel);
        panel.add(validTime);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new TrainTicketPage();
    }
}
