package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicketBuyingPage {
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> ticketTypes;
    private JButton buyButton;
    private JLabel resultLabel;

    public TicketBuyingPage() {
        frame = new JFrame("Ticket Buying Page");
        panel = new JPanel();
        String[] tickets = { "Adult", "Child", "Senior", "Student" };
        ticketTypes = new JComboBox<>(tickets);
        buyButton = new JButton("Buy Ticket");
        resultLabel = new JLabel("");

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTicket = ticketTypes.getSelectedItem().toString();
                resultLabel.setText("You have bought a " + selectedTicket + " ticket!");
            }
        });

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(ticketTypes);
        panel.add(buyButton);
        panel.add(resultLabel);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TicketBuyingPage();
    }
}
