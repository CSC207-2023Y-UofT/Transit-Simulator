package ui.passenger;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TrainTicketPage {

    private JFrame frame;
    private JPanel panel;
    private JLabel title, ticketId, validTime, typeLabel;
    private JButton homeButton;

    public TrainTicketPage() {

        // Create the frame and panel
        frame = new JFrame("Train Ticket");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(new Color(185, 151, 144));

        title = new JLabel("Train Ticket", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));

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

        // Home Button
        homeButton = new RoundedButton("Home");
        homeButton.setPreferredSize(new Dimension(200, 50));
        homeButton.setFont(new Font("Serif", Font.BOLD, 25));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PassengerHomePage();
            }
        });

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
        panel.add(new JLabel("  "));
        panel.add(title);
        panel.add(ticketId);
        panel.add(typeLabel);
        panel.add(validTime);
        panel.add(new JLabel("  "));
        panel.add(homeButton);
        panel.add(new JLabel("  "));


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new TrainTicketPage();
    }

}
