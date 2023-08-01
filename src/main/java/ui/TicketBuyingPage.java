package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicketBuyingPage {
    // Prices for each ticket type
    private static final double ADULT_PRICE = 3.35;
    private static final double CHILD_PRICE = 2.40;
    private static final double SENIOR_PRICE = 2.30;
    private static final double STUDENT_PRICE = 2.35;

    private JFrame frame;
    private JPanel panel;
    private JLabel adultCount, childCount, seniorCount, studentCount;
    private JButton adultPlus, adultMinus, childPlus, childMinus, seniorPlus, seniorMinus, studentPlus, studentMinus;
    private JButton buyButton;
    private JLabel totalCostLabel;  // New label for total cost
    private JLabel headerLabel;

    public TicketBuyingPage() {
        frame = new JFrame("Ticket Buying Page");
        panel = new JPanel(new GridLayout(0, 4));
        headerLabel = new JLabel("Buy Tickets", SwingConstants.CENTER);

        adultCount = createCountLabel();
        adultMinus = createMinusButton();
        adultPlus = createPlusButton();
        createRow("Adult", adultMinus, adultPlus, adultCount, ADULT_PRICE);

        childCount = createCountLabel();
        childMinus = createMinusButton();
        childPlus = createPlusButton();
        createRow("Child", childMinus, childPlus, childCount, CHILD_PRICE);

        seniorCount = createCountLabel();
        seniorMinus = createMinusButton();
        seniorPlus = createPlusButton();
        createRow("Senior", seniorMinus, seniorPlus, seniorCount, SENIOR_PRICE);

        studentCount = createCountLabel();
        studentMinus = createMinusButton();
        studentPlus = createPlusButton();
        createRow("Student", studentMinus, studentPlus, studentCount, STUDENT_PRICE);

        totalCostLabel = new JLabel("Total: $0.00");  // Initialize total cost label
        totalCostLabel.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < 2; i++) {  // Add empty labels as spacers
            panel.add(new JLabel(" "));
        }

        panel.add(totalCostLabel);  // Add total cost label

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
        button.setBackground(Color.PINK);
        return button;
    }

    private JButton createPlusButton() {
        JButton button = new JButton("+");
        button.setBackground(new Color(133, 184, 135));
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

    // New method for updating total cost
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
