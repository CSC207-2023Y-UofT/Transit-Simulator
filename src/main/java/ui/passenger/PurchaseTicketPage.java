package ui.passenger;

import ui.UIController;
import ui.round.RoundedButton;
import ui.round.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseTicketPage extends JPanel {

    private static final double ADULT_PRICE = 3.35;
    private static final double CHILD_PRICE = 2.40;
    private static final double SENIOR_PRICE = 2.30;
    private static final double STUDENT_PRICE = 2.35;

    private final JLabel adultCount;
    private final JLabel childCount;
    private final JLabel seniorCount;
    private final JLabel studentCount;
    private final JLabel totalCostLabel;

    public PurchaseTicketPage(UIController controller) {
        super(new GridLayout(0, 4));

        JLabel headerLabel = new JLabel("Tickets", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 35));
        this.add(headerLabel);

        // 3 empty labels to fill the space
        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        adultCount = createCountLabel();
        JButton adultMinus = createMinusButton();
        JButton adultPlus = createPlusButton();
        createRow("Adult", adultMinus, adultPlus, adultCount, ADULT_PRICE);

        childCount = createCountLabel();
        JButton childMinus = createMinusButton();
        JButton childPlus = createPlusButton();
        createRow("Child", childMinus, childPlus, childCount, CHILD_PRICE);

        seniorCount = createCountLabel();
        JButton seniorMinus = createMinusButton();
        JButton seniorPlus = createPlusButton();
        createRow("Senior", seniorMinus, seniorPlus, seniorCount, SENIOR_PRICE);

        studentCount = createCountLabel();
        JButton studentMinus = createMinusButton();
        JButton studentPlus = createPlusButton();
        createRow("Student", studentMinus, studentPlus, studentCount, STUDENT_PRICE);

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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset all the values
                adultCount.setText("0");
                childCount.setText("0");
                seniorCount.setText("0");
                studentCount.setText("0");
                totalCostLabel.setText("Total: $0.00");
            }
        });
        this.add(cancelButton);

        // Total cost label
        totalCostLabel = new JLabel("Total: $0.00");
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
        buyButton.addActionListener(e -> controller.open(new ConfirmPaymentPage(controller)));

        this.add(buyButton);

        // Make background color light gray
        this.setBackground(new Color(210, 207, 206));
    }

    private JLabel createCountLabel() {
        JLabel label = new RoundedLabel("0");
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(new Color(255, 255, 255, 255));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return label;
    }

    private JButton createMinusButton() {
        JButton button = new RoundedButton("-");
        button.setFont(new Font("Serif", Font.BOLD, 20));
        button.setBackground(new Color(218, 167, 155));
        return button;
    }

    private JButton createPlusButton() {
        JButton button = new RoundedButton("+");
        button.setFont(new Font("Serif", Font.BOLD, 20));
        button.setBackground(new Color(141, 203, 141));
        return button;
    }

    private void createRow(String name, JButton minusButton, JButton plusButton, JLabel countLabel, double price) {
        minusButton.addActionListener(e -> {
            int count = Integer.parseInt(countLabel.getText());
            if (count > 0) {
                count--;
                countLabel.setText(String.valueOf(count));
                updateTotalCost();
            }
        });

        plusButton.addActionListener(e -> {
            int count = Integer.parseInt(countLabel.getText());
            count++;
            countLabel.setText(String.valueOf(count));
            updateTotalCost();
        });

        String priceFormatted = String.format("$%.2f", price);
        JLabel lab = new JLabel(name + "   " + priceFormatted);
        lab.setFont(new Font("Serif", Font.BOLD, 25));
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lab);
        this.add(minusButton);
        this.add(countLabel);
        this.add(plusButton);
    }

    public double getTotalCost() {
        return Integer.parseInt(adultCount.getText()) * ADULT_PRICE
                + Integer.parseInt(childCount.getText()) * CHILD_PRICE
                + Integer.parseInt(seniorCount.getText()) * SENIOR_PRICE
                + Integer.parseInt(studentCount.getText()) * STUDENT_PRICE;
    }

    private void updateTotalCost() {
        double total = getTotalCost();
        String totalFormatted = String.format("$%.2f", total);
        totalCostLabel.setText("Total: " + totalFormatted);
    }

}
