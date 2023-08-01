package ui;

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
    private JLabel adultCount, childCount, seniorCount, studentCount;
    private JButton adultPlus, adultMinus, childPlus, childMinus, seniorPlus, seniorMinus, studentPlus, studentMinus;
    private JButton buyButton, backButton, cancelButton;
    private JLabel totalCostLabel;
    private JLabel headerLabel;

    public TicketBuyingPage() {

        // Create the frame and panel
        frame = new JFrame("Ticket Buying Page");
        panel = new JPanel(new GridLayout(0, 4));
        headerLabel = new JLabel("Buy Tickets", SwingConstants.CENTER);

        // Add the "Buy Tickets" title
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
        backButton = new JButton("Back");
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
        cancelButton = new JButton("Cancel");
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
        panel.add(totalCostLabel);  // Add total cost label

        // Buy button
        buyButton = new JButton("Buy Tickets");
        buyButton.setBackground(new Color(0, 151, 8));
        buyButton.setOpaque(true);
        buyButton.setBorderPainted(false);
        buyButton.addActionListener(e -> {
            String result = "You have bought: ";
            result += adultCount.getText() + " Adult tickets, ";
            result += childCount.getText() + " Child tickets, ";
            result += seniorCount.getText() + " Senior tickets, ";
            result += studentCount.getText() + " Student tickets.";
            JOptionPane.showMessageDialog(frame, result);
        });
        panel.add(buyButton);

        // Add the header label and panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JLabel createCountLabel() {
        JLabel label = new JLabel("0");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JButton createMinusButton() {
        JButton button = new JButton("-");
        button.setBackground(new Color(248,180,166));
        return button;
    }

    private JButton createPlusButton() {
        JButton button = new JButton("+");
        button.setBackground(new Color(114,217,112));
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

    private void updateTotalCost() {
        double total = Integer.parseInt(adultCount.getText()) * ADULT_PRICE
                + Integer.parseInt(childCount.getText()) * CHILD_PRICE
                + Integer.parseInt(seniorCount.getText()) * SENIOR_PRICE
                + Integer.parseInt(studentCount.getText()) * STUDENT_PRICE;

        totalCostLabel.setText(String.format("Total: $%.2f", total));
    }

    public static void main(String[] args) {
        new TicketBuyingPage();
    }
}
