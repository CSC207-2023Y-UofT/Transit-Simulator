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
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(5, 1));

        panel.add(new JLabel("  "));

        JLabel label = new JLabel("<html><div style='text-align: center;'>Thank you for your purchase.<br/>Your ticket is valid for 2 hours upon activation.</div></html>", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(20.0f)); // Increase font size
        panel.add(label);

        activateButton = new RoundedButton("Activate Ticket");
        activateButton.setPreferredSize(new Dimension(200, 50));  // Resize the button
        activateButton.setFont(activateButton.getFont().deriveFont(20.0f));  // Increase font size
        activateButton.setBackground(new Color(0, 151, 8));
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrainTicketPage().setVisible(true);
                frame.dispose();
            }
        });
        panel.add(new JLabel("  "));
        panel.add(activateButton);

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
