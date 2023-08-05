package ui.passenger;

import ui.UIController;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThankYouPage extends JPanel {

    public ThankYouPage(UIController controller) {
        super(new GridLayout(5, 1));

        JLabel blank1 = new JLabel("  ");
        blank1.setOpaque(true);
        blank1.setBackground(Color.lightGray);
        this.add(blank1);

        JLabel label = new JLabel("<html><div style='text-align: center;'>Thank you for your purchase.<br/>Your ticket is valid for 2 hours upon activation.</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 28));
        label.setOpaque(true);
        label.setBackground(Color.lightGray);
        this.add(label);

        JButton activateButton = new RoundedButton("Activate Ticket");
        activateButton.setPreferredSize(new Dimension(200, 50));
        activateButton.setFont(new Font("Serif", Font.BOLD, 25));
        activateButton.setBackground(new Color(0, 151, 8));
        activateButton.addActionListener(e -> controller.open(new TrainTicketPage(controller)));
        JLabel blank2 = new JLabel("  ");
        blank2.setOpaque(true);
        blank2.setBackground(Color.lightGray);
        this.add(blank2);
        this.add(activateButton);

        JLabel blank3 = new JLabel("  ");
        blank3.setOpaque(true);
        blank3.setBackground(Color.lightGray);
        this.add(blank3);
    }

}
