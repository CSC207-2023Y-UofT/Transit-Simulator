package ui.passenger;

import ui.UserTypePage;
import ui.round.RoundedButton;
import ui.round.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicketBuyingPage {

    private static final double ADULT_PRICE = 3.35;
    private static final double CHILD_PRICE = 2.40;
    private static final double SENIOR_PRICE = 2.30;
    private static final double STUDENT_PRICE = 2.35;

    private JFrame frame;
    private JPanel panel;
    private JLabel headerLabel, adultCount, childCount, seniorCount, studentCount, totalCostLabel;
    private JButton adultPlus, adultMinus, childPlus, childMinus, seniorPlus, seniorMinus, studentPlus, studentMinus;
    private JButton buyButton, backButton, cancelButton;

    public TicketBuyingPage() {

        // Create the frame and panel
        frame = new JFrame("Ticket Buying Page");
        panel = new JPanel(new GridLayout(0, 4));

        headerLabel = new JLabel("Buy Tickets", SwingConstants.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(20.0f));
        panel.add(headerLabel);

        // 3 empty labels to fill the space
        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        // First row: Adult tickets
        adultCount = createCountLabel();
        adultMinus = createMinusButton();
        adultPlus = createPlusButton();
        createRow("Adult", adultMinus, adultPlus, adultCount, ADULT_PRICE);

        // Second row: Child tickets
        childCount = createCountLabel();
        childMinus = createMinusButton();
        childPlus = createPlusButton();
        createRow("Child", childMinus, childPlus, childCount, CHILD_PRICE);

        // Third row: Senior tickets
        seniorCount = createCountLabel();
        seniorMinus = createMinusButton();
        seniorPlus = createPlusButton();
        createRow("Senior", seniorMinus, seniorPlus, seniorCount, SENIOR_PRICE);

        // Fourth row: Student tickets
        studentCount = createCountLabel();
        studentMinus = createMinusButton();
        studentPlus = createPlusButton();
        createRow("Student", studentMinus, studentPlus, studentCount, STUDENT_PRICE);


        // Add empty labels as spacers. Adjust the second parameter to change spacing.
        for (int i = 0; i < 4; i++) {
            panel.add(new JLabel(" "));
        }


        // Back button
        backButton = new RoundedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(0, 151, 8));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the ticket buying page and dispose of the current frame
                new UserTypePage();
                frame.dispose();
            }
        });
        panel.add(backButton);


        // Cancel button
        cancelButton = new RoundedButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setBackground(new Color(172,64,58));
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
        panel.add(cancelButton);

        // Total cost label
        totalCostLabel = new JLabel("Total: $0.00");
        totalCostLabel.setHorizontalAlignment(JLabel.CENTER);
        totalCostLabel.setOpaque(true);
        totalCostLabel.setBackground(new Color(255, 255, 255, 255));
        panel.add(totalCostLabel);

        // Buy button
        buyButton = new RoundedButton("Buy Tickets");
        buyButton.setBackground(new Color(0, 151, 8));
        buyButton.setBorderPainted(false);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfirmPaymentPage();
                frame.dispose();
            }
        });

        panel.add(buyButton);

        // Make background color light gray
        panel.setBackground(new Color(210, 207, 206));

        // Add the header label and panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private JLabel createCountLabel() {
        JLabel label = new RoundedLabel("0");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(new Color(255, 255, 255, 255));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return label;
    }

    private JButton createMinusButton() {
        JButton button = new RoundedButton("-");
        button.setBackground(new Color(218, 167, 155));
        return button;
    }

    private JButton createPlusButton() {
        JButton button = new RoundedButton("+");
        button.setBackground(new Color(141, 203, 141));
        return button;
    }

    private void createRow(String name, JButton minusButton, JButton plusButton, JLabel countLabel, double price) {
        minusButton.addActionListener(e -> {
            int count = Integer.parseInt(countLabel.getText());
            if (count > 0) {
                count--;
                countLabel.setText(String.valueOf(count));
                updateTotalCost();  // Update total cost
            }
        });

        plusButton.addActionListener(e -> {
            int count = Integer.parseInt(countLabel.getText());
            count++;
            countLabel.setText(String.valueOf(count));
            updateTotalCost();  // Update total cost
        });

        String priceFormatted = String.format("$%.2f", price);
        JLabel lab = new JLabel(name + "   " + priceFormatted);
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lab);
        panel.add(minusButton);
        panel.add(countLabel);
        panel.add(plusButton);
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


    public static void main(String[] args) {
        new TicketBuyingPage();
    }
}
