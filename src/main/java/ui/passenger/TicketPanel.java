package ui.passenger;

import controller.ticket.TicketViewModel;
import interactor.ticket.BoughtTicket;
import ui.UIController;
import ui.util.ShadowedButton;
import ui.util.ShadowPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Optional;
import java.util.Random;

public class TicketPanel extends JPanel {

    private final TicketViewModel viewModel;
    private final UIController controller;

    private final Timer timer = new Timer(100, e -> this.update());
    private final JPanel innerPanel;

    public TicketPanel(UIController controller, TicketViewModel viewModel) {
        super(new GridLayout(1, 1));

        this.viewModel = viewModel;
        this.controller = controller;

        // Margin
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel borderPanel = new ShadowPanel(new GridLayout(1, 1));
        innerPanel = new JPanel(new GridLayout(6, 1));
        borderPanel.add(innerPanel);

        innerPanel.setBackground(Color.WHITE);

        add(borderPanel);
    }

    private void update() {
        if (viewModel.getTicket() == null) {
            return;
        }
        Optional<BoughtTicket> ticket = controller.getControllerPool()
                .getTicketController()
                .getTicket(viewModel.getTicket().getTicketId());

        viewModel.setTicket(ticket.orElse(null));

        updateComponents();

        repaint();
    }

    private void updateComponents() {
        BoughtTicket ticket = viewModel.getTicket();
        if (ticket == null) {
            expired(innerPanel);
        } else if (ticket.isActivated()) {
            active(innerPanel);
        } else {
            inactive(innerPanel);
        }
    }

    private void active(JPanel panel) {
        panel.removeAll();

        JLabel title = new JLabel("Train Ticket", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));

        // Message
        JLabel message = new JLabel("Do not close this tab. You will lose your ticket.", SwingConstants.CENTER);
        message.setFont(new Font("Serif", Font.PLAIN, 25));

        // Ticket ID
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        JLabel ticketId = new JLabel("Ticket ID: " + randomId, SwingConstants.CENTER);
        ticketId.setFont(new Font("Serif", Font.PLAIN, 25));

        // Valid Time
        JLabel validTime = new JLabel("", SwingConstants.CENTER);
        validTime.setFont(new Font("Serif", Font.PLAIN, 25));

        // Ticket Type
        String ticketType = "Single";   // temporary
        JLabel typeLabel = new JLabel("Ticket Type: " + ticketType, SwingConstants.CENTER);
        typeLabel.setFont(new Font("Serif", Font.PLAIN, 25));

        panel.add(title);
        panel.add(message);
        panel.add(ticketId);
        panel.add(typeLabel);
        panel.add(validTime);
    }

    private void inactive() {
        ShadowedButton activateButton = new ShadowedButton("Activate");
        activateButton.setBackground(new Color(140, 247, 146));

    }
}
