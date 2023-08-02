package ui;

import ui.passenger.PurchaseTicketPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    private JButton buyTicketButton;
    private JButton viewTicketButton;
    private JButton exitButton;

    public HomePage() {
        setTitle("Subway Ticket System");
        setSize(900, 600);
        setLayout(new GridLayout(4, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a label for the title of the application
        JLabel title = new JLabel("Welcome to the Subway Ticket System", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));

        // Create the buy ticket button
        buyTicketButton = new JButton("Buy Ticket");
        buyTicketButton.setFont(new Font("Arial", Font.PLAIN, 20));
        buyTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PurchaseTicketPage();
                dispose();
            }
        });

        // Create the view ticket button
        viewTicketButton = new JButton("View Ticket");
        viewTicketButton.setFont(new Font("Arial", Font.PLAIN, 20));
        viewTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PurchaseTicketPage();
                dispose();
            }
        });

        // Create the exit button
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add components to the frame
        add(title);
        add(buyTicketButton);
        add(viewTicketButton);
        add(exitButton);
    }

    public static void main(String[] args) {
        new HomePage().setVisible(true);
    }

}
