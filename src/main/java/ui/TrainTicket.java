package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TrainTicket extends JFrame {

    private JLabel ticketId;
    private JLabel validTime;

    public TrainTicket() {



        setTitle("Train Ticket");
        setSize(600, 300);
        setLayout(new GridLayout(5,1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create components
        JLabel title = new JLabel("TRAIN TICKET", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));

        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        ticketId = new JLabel("Ticket ID: " + randomId, SwingConstants.CENTER);
        ticketId.setFont(new Font("Serif", Font.PLAIN, 20));

        validTime = new JLabel("", SwingConstants.CENTER);
        validTime.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel passengerName = new JLabel("Passenger: John Doe", SwingConstants.CENTER);
        passengerName.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel destination = new JLabel("Destination: New York City", SwingConstants.CENTER);
        destination.setFont(new Font("Serif", Font.PLAIN, 20));

        // change the background color of the labels to brown (like a paper color)
        Color paperColor = new Color(210, 180, 140);
        title.setOpaque(true);
        title.setBackground(paperColor);
        ticketId.setOpaque(true);
        ticketId.setBackground(paperColor);
        validTime.setOpaque(true);
        validTime.setBackground(paperColor);
        passengerName.setOpaque(true);
        passengerName.setBackground(paperColor);
        destination.setOpaque(true);
        destination.setBackground(paperColor);

        // add components to frame
        add(title);
        add(ticketId);
        add(validTime);
        add(passengerName);
        add(destination);


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
    }

    public static void main(String[] args) {
        new TrainTicket().setVisible(true);
    }
}
