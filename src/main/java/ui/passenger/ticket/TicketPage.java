package ui.passenger.ticket;

import javax.swing.*;
import java.awt.*;

public class TicketPage {

    private JFrame frame;
    private JPanel panel;

    public TicketPage() {

        frame = new JFrame("Train Tickets");
        frame.setLayout(new GridLayout(3, 4));
        frame.setPreferredSize(new Dimension(1100, 500));
        frame.setBackground(new Color(185, 151, 144));

        for (int i = 0; i < 12; i++) {

            // TODO: code to call for the TYPE of ticket
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
