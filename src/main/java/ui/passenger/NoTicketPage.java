package ui.passenger;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NoTicketPage {

    private JFrame frame;
    private JPanel panel;
    private JLabel title;
    private JButton homeButton;

    public NoTicketPage() {

        // Create the frame and panel
        frame = new JFrame("No Ticket");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(new Color(185, 151, 144));

        title = new JLabel("There are no active tickets.", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));

        // Home Button
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


        // Add components to panel
        panel.add(new JLabel("  "));
        panel.add(title);
        panel.add(new JLabel("  "));
        panel.add(homeButton);
        panel.add(new JLabel("  "));


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new NoTicketPage();
    }

}
