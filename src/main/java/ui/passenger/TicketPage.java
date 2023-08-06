package ui.passenger;

import ticket.Ticket;

import javax.swing.*;
import java.awt.*;

public class TicketPage {

    private JFrame frame;
    private JPanel panel;

    public TicketPage() {

        frame = new JFrame("Train Ticket");
        frame.setLayout(new GridLayout(3, 4));
        frame.setPreferredSize(new Dimension(1500, 900));
        frame.setBackground(new Color(185, 151, 144));

        for (int i = 0; i < 12; i++) {
            panel = new TicketPanel();
            panel.setBackground(new Color(255, 255, 255));
            panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            frame.add(panel);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TicketPage();
    }
}
