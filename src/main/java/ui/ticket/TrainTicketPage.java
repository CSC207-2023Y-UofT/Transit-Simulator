package ui.ticket;

import ui.passenger.PassengerHomePage;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TrainTicketPage extends JFrame {

    private JLabel ticketId, validTime, blank1, blank2;
    private RoundedButton homeButton;

    public TrainTicketPage() {

        setTitle("Train Ticket");
        setSize(900, 600);
        setLayout(new GridLayout(6,1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create components
        JLabel title = new JLabel("TRAIN TICKET", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));

        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        ticketId = new JLabel("Ticket ID: " + randomId, SwingConstants.CENTER);
        ticketId.setFont(new Font("Serif", Font.PLAIN, 25));

        validTime = new JLabel("", SwingConstants.CENTER);
        validTime.setFont(new Font("Serif", Font.PLAIN, 25));

        blank1 = new JLabel("    ");
        blank2 = new JLabel("    ");

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


        // change the background color of the labels to brown (like a paper color)
        Color paperColor = new Color(173, 133, 124);
        title.setOpaque(true);
        title.setBackground(paperColor);
        ticketId.setOpaque(true);
        ticketId.setBackground(paperColor);
        validTime.setOpaque(true);
        validTime.setBackground(paperColor);
        blank1.setOpaque(true);
        blank1.setBackground(paperColor);
        blank2.setOpaque(true);
        blank2.setBackground(paperColor);

        // add components to frame
        add(title);
        add(ticketId);
        add(validTime);
        add(blank1);
        add(blank2);
        add(homeButton);

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
        new TrainTicketPage().setVisible(true);
    }

}
