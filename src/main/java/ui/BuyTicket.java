package ui;

import javax.swing.*;
import java.awt.*;

public class BuyTicket extends JFrame {

    private JPanel contentPane;
    private JButton regTicketAdd;
    private JButton regTicketDel;
    private JButton buyButton;
    private JButton childTicketAdd;
    private JButton childTicketDel;
    private JButton adultTicketAdd;
    private JButton adultTicketDel;
    private JButton seniorTicketAdd;
    private JButton seniorTicketDel;
    private JLabel buyTicketsLabel;
    private JButton backButton;
    private JButton cancelButton;

    public BuyTicket() {
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setTitle("Buy Tickets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        buyTicketsLabel.setBorder(
                BorderFactory.createLineBorder(
                        Color.PINK,
                        4
                )
        );
    }

    public static void main(String[] args) {
        BuyTicket buyTicket = new BuyTicket();
    }
}
