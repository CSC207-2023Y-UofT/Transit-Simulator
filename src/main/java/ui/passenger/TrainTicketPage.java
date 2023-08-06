package ui.passenger;

import javax.swing.*;
import java.awt.*;


public class TrainTicketPage {

    private JFrame frame;

    public TrainTicketPage() {

        frame = new JFrame("Train Ticket");
        frame.setLayout(new GridLayout(3, 4));
        frame.setPreferredSize(new Dimension(1500, 900));

        // panel
        frame.add(new TicketPanel());
        frame.add(new JLabel(" "));
        frame.add(new JLabel(" "));
        frame.add(new JLabel(" "));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TrainTicketPage();
    }
}
