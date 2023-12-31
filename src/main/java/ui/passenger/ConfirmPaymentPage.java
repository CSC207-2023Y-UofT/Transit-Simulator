package ui.passenger;

import interface_adapter.viewmodel.PurchaseTicketViewModel;
import interface_adapter.controller.TicketController;
import interface_adapter.viewmodel.TicketViewModel;
import app_business.dto.TicketDTO;
import org.jetbrains.annotations.NotNull;
import ui.UIController;
import ui.util.ShadowedButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ConfirmPaymentPage is a JPanel that displays the confirm payment page.
 * It is used by the UIController to display the confirm payment page.
 *
 * @see UIController
 */
public class ConfirmPaymentPage extends JPanel {

    /**
     * Constructs a new ConfirmPaymentPage object.
     *
     * @param controller the controller used to switch pages
     * @param viewModel  the view model for this page
     */
    public ConfirmPaymentPage(UIController controller, PurchaseTicketViewModel viewModel) {
        super(new GridLayout(0, 3));

        // Create the header label
        JLabel headerLabel = new JLabel("Confirm Payment?", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));

        // Create the total cost label
        JLabel totalCostLabel = new JLabel("Total Cost: $" + String.format("%.2f", viewModel.getTotalCost()));
        totalCostLabel.setFont(new Font("Arial", Font.BOLD, 28));
        totalCostLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create the confirm button
        JButton confirmButton = getjButton(controller, viewModel);

        // Create the cancel button
        JButton cancelButton = new ShadowedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 28));
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setBackground(new Color(172, 64, 58));
        cancelButton.addActionListener(e -> controller.open(new PurchaseTicketPage(controller, viewModel)));

        // Add components to the panel
        this.setBackground(new Color(210, 207, 206));

        // Line 1: empty
        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(headerLabel);
        this.add(new JLabel("  "));

        this.add(new JLabel("  "));
        this.add(totalCostLabel);  // Add total cost label
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(cancelButton);
        this.add(new JLabel("  "));
        this.add(confirmButton);
    }

    @NotNull
    private static JButton getjButton(UIController controller, PurchaseTicketViewModel viewModel) {
        JButton confirmButton = new ShadowedButton("Confirm");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 28));
        confirmButton.setPreferredSize(new Dimension(150, 50));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setBackground(new Color(0, 151, 8));
        confirmButton.addActionListener(e -> {
            TicketController ticketController = controller.getControllerPool().getTicketController();
            List<TicketDTO> tickets = ticketController.buyTickets(viewModel.getTicketTypesList());
            List<TicketViewModel> viewModels = new ArrayList<>();
            for (TicketDTO ticket : tickets) {
                viewModels.add(new TicketViewModel(ticket));
            }

            controller.open(new ThankYouPage(controller, viewModels));
        });
        return confirmButton;
    }

}
