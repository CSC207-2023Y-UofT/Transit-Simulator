package ui.passenger;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThankYouPage {

    private JFrame frame;
    private JPanel panel;
    private JLabel headerLabel;
    private JButton activateButton, returnButton;

    public ThankYouPage() {

        // Create the frame and panel
        frame = new JFrame("Thank You Page");
        panel = new JPanel(new GridLayout(5, 1));

        panel.add(new JLabel("  "));

        JLabel label = new JLabel("<html><div style='text-align: center;'>Thank you for your purchase.<br/>Your ticket is valid for 2 hours upon activation.</div></html>", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(12.0f)); // Increase font size
        panel.add(label);

        activateButton = new RoundedButton("Activate Ticket");
        activateButton.setBackground(new Color(255, 134, 36));
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrainTicketPage().setVisible(true);
                frame.dispose();
            }
        });

        panel.add(new JLabel("  "));

        panel.add(activateButton);


        new RoundedButton("Return to Main Menu");

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 200);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new ThankYouPage();
    }

}
