package ui.passenger;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThankYouPage {

    private JFrame frame;
    private JPanel panel;
    private JLabel blank1, blank2;
    private JButton activateButton, homeButton;

    public ThankYouPage() {

        // Create the frame and panel
        frame = new JFrame("Thank You Page");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(5, 1));

        blank1 = new JLabel("  ");
        blank1.setOpaque(true);
        blank1.setBackground(Color.lightGray);
        panel.add(blank1);

        JLabel label = new JLabel("<html><div style='text-align: center;'>Thank you for your purchase.<br/>Your ticket is valid for 2 hours upon activation.</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 28));
        label.setOpaque(true);
        label.setBackground(Color.lightGray);
        panel.add(label);

        activateButton = new RoundedButton("Activate Ticket");
        activateButton.setPreferredSize(new Dimension(200, 50));
        activateButton.setFont(new Font("Serif", Font.BOLD, 25));
        activateButton.setBackground(new Color(0, 151, 8));
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrainTicketPage();
                frame.dispose();
            }
        });
        blank2 = new JLabel("  ");
        blank2.setOpaque(true);
        blank2.setBackground(Color.lightGray);
        panel.add(blank2);
        panel.add(activateButton);


        homeButton = new RoundedButton("Home");
        homeButton.setPreferredSize(new Dimension(200, 50));
        homeButton.setFont(new Font("Serif", Font.BOLD, 25));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PassengerHomePage();
                frame.dispose();
            }
        });
        panel.add(homeButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 600);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new ThankYouPage();
    }

}
