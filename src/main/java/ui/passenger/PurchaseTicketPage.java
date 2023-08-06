package ui.passenger;

import controller.ticket.PurchaseTicketViewModel;
import ticket.TicketType;
import ui.UIController;
import ui.util.RoundedButton;
import ui.util.SuppliedLabel;
import ui.util.SuppliedRoundLabel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

/**
 * PurchaseTicketPage is a JPanel that displays the purchase ticket page.
 * It is used by the UIController to display the purchase ticket page.
 *
 * @see UIController
 */
public class PurchaseTicketPage extends JPanel {
    /**
     * The JLabel that displays the count of AdultTickets.
     */
    private final JLabel adultCount;
    /**
     * The JLabel that displays the count of ChildTickets.
     */
    private final JLabel childCount;
    /**
     * The JLabel that displays the count of SeniorTickets.
     */
    private final JLabel seniorCount;
    /**
     * The JLabel that displays the count of StudentTickets.
     */
    private final JLabel studentCount;
    /**
     * The JLabel that displays the total cost of the tickets.
     */
    private final JLabel totalCostLabel;

    private final PurchaseTicketViewModel viewModel;

    /**
     * Constructs a new PurchaseTicketPage with the given UIController.
     *
     * @param controller the UIController that is used to control the UI
     */
    public PurchaseTicketPage(UIController controller, PurchaseTicketViewModel viewModel) {
        super(new GridLayout(0, 4));

        this.viewModel = viewModel;

        JLabel headerLabel = new JLabel("Tickets", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 35));
        this.add(headerLabel);

        // 3 empty labels to fill the space
        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        adultCount = createCountLabel(() -> viewModel.count(TicketType.ADULT));
        JButton adultMinus = createMinusButton();
        JButton adultPlus = createPlusButton();
        createRow("Adult", adultMinus, adultPlus, adultCount, TicketType.ADULT);

        childCount = createCountLabel(() -> viewModel.count(TicketType.CHILD));
        JButton childMinus = createMinusButton();
        JButton childPlus = createPlusButton();
        createRow("Child", childMinus, childPlus, childCount, TicketType.CHILD);

        seniorCount = createCountLabel(() -> viewModel.count(TicketType.SENIOR));
        JButton seniorMinus = createMinusButton();
        JButton seniorPlus = createPlusButton();
        createRow("Senior", seniorMinus, seniorPlus, seniorCount, TicketType.SENIOR);

        studentCount = createCountLabel(() -> viewModel.count(TicketType.STUDENT));
        JButton studentMinus = createMinusButton();
        JButton studentPlus = createPlusButton();
        createRow("Student", studentMinus, studentPlus, studentCount, TicketType.STUDENT);

        for (int i = 0; i < 4; i++) {
            this.add(new JLabel(" "));
        }

        // Back button
        JButton backButton = new RoundedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.setBackground(new Color(166, 166, 166));
        backButton.addActionListener(e -> controller.open(new PassengerHomePage(controller)));
        this.add(backButton);


        // Cancel button
        JButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setFont(new Font("Serif", Font.BOLD, 20));
        cancelButton.setPreferredSize(new Dimension(200, 50));
        cancelButton.setBackground(new Color(172, 64, 58));
        cancelButton.addActionListener(e -> {
            viewModel.reset();
            repaint();
        });
        this.add(cancelButton);

        // Total cost label
        totalCostLabel = new SuppliedLabel(() -> "Total $" + String.format("%.2f", viewModel.getTotalCost()));
        totalCostLabel.setFont(totalCostLabel.getFont().deriveFont(20.0f));
        totalCostLabel.setHorizontalAlignment(JLabel.CENTER);
        totalCostLabel.setOpaque(true);
        totalCostLabel.setBackground(new Color(255, 255, 255, 255));
        this.add(totalCostLabel);

        // Buy button
        JButton buyButton = new RoundedButton("Buy");
        buyButton.setFont(new Font("Serif", Font.BOLD, 20));
        buyButton.setPreferredSize(new Dimension(200, 50));
        buyButton.setBackground(new Color(0, 151, 8));
        buyButton.setBorderPainted(false);
        buyButton.addActionListener(e -> controller.open(new ConfirmPaymentPage(controller, viewModel)));

        this.add(buyButton);

        // Make background color light gray
        this.setBackground(new Color(210, 207, 206));
    }

    /**
     * Creates a counting JLabel.
     *
     * @return the counting JLabel
     */
    private JLabel createCountLabel(Supplier<Integer> countSupplier) {
        JLabel label = new SuppliedRoundLabel(() -> countSupplier.get().toString());
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(new Color(255, 255, 255, 255));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return label;
    }

    /**
     * Creates a minus button.
     *
     * @return the minus button
     */
    private JButton createMinusButton() {
        JButton button = new RoundedButton("-");
        button.setFont(new Font("Serif", Font.BOLD, 20));
        button.setBackground(new Color(218, 167, 155));
        return button;
    }

    /**
     * Creates a plus button.
     *
     * @return the plus button
     */
    private JButton createPlusButton() {
        JButton button = new RoundedButton("+");
        button.setFont(new Font("Serif", Font.BOLD, 20));
        button.setBackground(new Color(141, 203, 141));
        return button;
    }

    /**
     * Creates a row in the UI for a ticket type.
     *
     * @param name        the name of the ticket type
     * @param minusButton the button to decrease the count
     * @param plusButton  the button to increase the count
     * @param countLabel  the label that displays the count
     * @param type        the ticket type
     */
    private void createRow(String name, JButton minusButton, JButton plusButton, JLabel countLabel, TicketType type) {
        minusButton.addActionListener(e -> {
            viewModel.removeTicket(type);
            repaint();
        });

        plusButton.addActionListener(e -> {
            viewModel.addTicket(type);
            repaint();
        });

        String priceFormatted = String.format("$%.2f", type.getPrice());
        JLabel lab = new JLabel(name + "   " + priceFormatted);
        lab.setFont(new Font("Serif", Font.BOLD, 25));
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lab);
        this.add(minusButton);
        this.add(countLabel);
        this.add(plusButton);
    }

    /**
     * Gets the total cost of the tickets.
     *
     * @return the total cost
     */
    public double getTotalCost() {
        return viewModel.getTotalCost();
    }

}
