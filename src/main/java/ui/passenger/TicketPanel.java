package ui.passenger;

import interface_adapter.viewmodel.TicketViewModel;
import app_business.dto.TicketDTO;
import ui.UIController;
import ui.util.ShadowedButton;
import ui.util.ShadowPanel;
import ui.util.SuppliedLabel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

/**
 * This class represents a UI panel that displays a single train ticket.
 */
public class TicketPanel extends JPanel {

    /**
     * The view model for this panel.
     */
    private final TicketViewModel viewModel;

    /**
     * The UIController instance responsible for controlling UI interactions.
     */
    private final UIController controller;

    /**
     * The timer that updates the UI.
     */
    private final Timer timer = new Timer(100, e -> this.update());

    /**
     * The inner panel that contains the ticket information.
     */
    private final JPanel innerPanel;

    /**
     * Constructs a TicketPanel with the given UIController and view model.
     *
     * @param controller the UIController instance responsible for controlling UI interactions
     * @param viewModel the view model for this panel
     */
    public TicketPanel(UIController controller, TicketViewModel viewModel) {
        super(new GridLayout(1, 1));

        this.viewModel = viewModel;
        this.controller = controller;

        // Margin
        setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel borderPanel = new ShadowPanel(new GridLayout(1, 1));
        innerPanel = new JPanel(new GridLayout(0, 1));
        borderPanel.add(innerPanel);
        add(borderPanel);
        updateComponents();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        timer.start();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        timer.stop();
    }

    /**
     * Updates the UI.
     */
    private void update() {
        if (viewModel.getTicket() == null) {
            return;
        }

        Optional<TicketDTO> ticket = controller.getControllerPool()
                .getTicketController()
                .getTicket(viewModel.getTicket().getTicketId());

        if (!Objects.equals(ticket.orElse(null), viewModel.getTicket())) {
            viewModel.setTicket(ticket.orElse(null));
            updateComponents();
        }
        repaint();

    }

    /**
     * Updates the components in this panel.
     */
    private void updateComponents() {
        TicketDTO ticket = viewModel.getTicket();
        if (ticket == null) {
            expired(innerPanel);
        } else {
            defaultComponents(innerPanel);
        }
        revalidate();
    }

    /**
     * Displays the ticket as expired.
     *
     * @param panel the panel to display the ticket in
     */
    private void defaultComponents(JPanel panel) {
        panel.removeAll();

        JLabel title = new JLabel("Train Ticket", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        // Message
        JLabel message = new JLabel("Do not close this tab.", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.PLAIN, 20));

        // Ticket ID
        JLabel ticketId = new JLabel("Ticket ID: " + viewModel.getTicket().getTicketId(), SwingConstants.CENTER);
        ticketId.setFont(new Font("Arial", Font.PLAIN, 20));

        // Valid Time
        SuppliedLabel validTime = new SuppliedLabel(() -> {
            long expiry = viewModel.getTicket().getExpiry();
            if (expiry != -1) {
                long timeLeft = expiry - System.currentTimeMillis();
                long hours = timeLeft / 3600000;
                long minutes = (timeLeft % 3600000) / 60000;
                long seconds = (timeLeft % 60000) / 1000;

                return "Ticket Validity: " + hours + ":" + minutes + ":" + seconds;
            } else {
                return "";
            }
        });

        validTime.setHorizontalAlignment(SwingConstants.CENTER);
        validTime.setFont(new Font("Arial", Font.PLAIN, 20));

        // Activate before boarding label
        JLabel activateLabel = new JLabel("Activate before boarding", SwingConstants.CENTER);
        activateLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // Ticket Type
        JLabel typeLabel = new JLabel("Ticket Type: " + viewModel.getTicket().getType(), SwingConstants.CENTER);
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // Activate button
        ShadowedButton activateButton = new ShadowedButton("Activate");
        activateButton.setBackground(new Color(238, 238, 238));
        activateButton.setFont(new Font("Arial", Font.PLAIN, 20));

        if (viewModel.getTicket().isActivated()) {
            activateButton.setBackground(new Color(255, 255, 255));
            activateButton.setEnabled(false);
            activateButton.setText("Active");
            panel.setBackground(Color.WHITE);
        } else {
            panel.setBackground(new Color(206, 184, 180));
        }

        activateButton.addActionListener(e -> {
            if (viewModel.getTicket().isActivated()) {
                return;
            }

            controller.getControllerPool().getTicketController()
                    .activateTicket(viewModel.getTicket().getTicketId());

            update();
        });

        activateButton.setRounded(false);

        panel.add(title);
        panel.add(message);
        panel.add(ticketId);
        panel.add(typeLabel);
        panel.add(activateLabel);
        panel.add(validTime);
        panel.add(activateButton);
    }

    /**
     * Displays the ticket as expired in the given panel.
     *
     * @param panel the panel to display the ticket in
     */
    private void expired(JPanel panel) {
        panel.removeAll();

        panel.setBackground(new Color(240, 150, 150));

        JLabel title = new JLabel("Ticket Expired", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        panel.add(title);
    }

}
