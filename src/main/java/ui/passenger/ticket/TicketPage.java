package ui.passenger.ticket;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a UI page that displays multiple train tickets in a grid layout.
 * Each ticket is represented as a panel inside the main frame of the page.
 */
public class TicketPage {

    /** Main frame of the ticket page. */
    private JFrame frame;

    /** Panel to represent a single ticket. */
    private JPanel panel;

    /**
     * Constructs a new TicketPage, initializes the UI components,
     * and displays the frame containing the tickets.
     */
    public TicketPage() {

        frame = new JFrame("Train Tickets");
        frame.setLayout(new GridLayout(3, 4));
        frame.setPreferredSize(new Dimension(1100, 600));

        for (int i = 0; i < 12; i++) {

            // TODO: code to call for the TYPE of ticket
            panel = new TicketPanel();
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
