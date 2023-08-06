package ui.passenger;

import ui.UIController;
import ui.passenger.ticket.TicketPage;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;


/**
 * ThankYouPage is a JPanel that displays the thank-you page.
 * It is used by the UIController to display the thank-you page.
 *
 * @see UIController
 */
public class ThankYouPage extends JPanel {

    /**
     * Constructs a new ThankYouPage with the given UIController.
     *
     * @param controller the UIController that is used to control the UI
     */
    public ThankYouPage(UIController controller) {

        JLabel label1 = new JLabel("Thank you for your purchase.", SwingConstants.CENTER);
        label1.setFont(new Font("Serif", Font.BOLD, 28));

        JLabel label2 = new JLabel(
                "Please activate your ticket(s) before boarding. " +
                "Tickets are valid for 2 hours upon activation.", SwingConstants.CENTER
        );
        label2.setFont(new Font("Serif", Font.BOLD, 28));

        JLabel label3 = new JLabel(
                "You will be redirected to the home page. Do not close your tickets tab.",
                SwingConstants.CENTER
        );
        label3.setFont(new Font("Serif", Font.BOLD, 28));

        JButton openButton = new RoundedButton("Open Ticket(s)");
        openButton.setPreferredSize(new Dimension(200, 50));
        openButton.setFont(new Font("Serif", Font.BOLD, 25));
        openButton.addActionListener(e -> {
            new TicketPage();
            controller.open(new PassengerHomePage(controller));
        });

        this.setLayout(new GridLayout(5, 1));
        this.setBackground(Color.lightGray);

        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(new JLabel("  "));
        this.add(openButton);
    }

}
