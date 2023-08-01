package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicketBuyingPage {
    private JFrame frame;
    private JPanel panel;
    private JLabel adultCount, childCount, seniorCount, studentCount;
    private JButton adultPlus, adultMinus, childPlus, childMinus, seniorPlus, seniorMinus, studentPlus, studentMinus;
    private JButton buyButton;
    private JLabel headerLabel;

    public TicketBuyingPage() {
        frame = new JFrame("Ticket Buying Page");
        panel = new JPanel(new GridLayout(0, 4));
        headerLabel = new JLabel("Buy Tickets", SwingConstants.CENTER);

        adultCount = createCountLabel();
        adultMinus = createMinusButton();
        adultPlus = createPlusButton();
        createRow("Adult", adultMinus, adultPlus, adultCount);

        childCount = createCountLabel();
        childMinus = createMinusButton();
        childPlus = createPlusButton();
        createRow("Child", childMinus, childPlus, childCount);

        seniorCount = createCountLabel();
        seniorMinus = createMinusButton();
        seniorPlus = createPlusButton();
        createRow("Senior", seniorMinus, seniorPlus, seniorCount);

        studentCount = createCountLabel();
        studentMinus = createMinusButton();
        studentPlus = createPlusButton();
        createRow("Student", studentMinus, studentPlus, studentCount);

        for (int i = 0; i < 4; i++) {  // Add empty labels as spacers
            panel.add(new JLabel(" "));
        }

        buyButton = new JButton("Buy Tickets");
        buyButton.setBackground(new Color(0, 151, 8));
        buyButton.setOpaque(true);
        buyButton.setBorderPainted(false);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = "You have bought: ";
                result += adultCount.getText() + " Adult tickets, ";
                result += childCount.getText() + " Child tickets, ";
                result += seniorCount.getText() + " Senior tickets, ";
                result += studentCount.getText() + " Student tickets.";
                JOptionPane.showMessageDialog(frame, result);
            }
        });
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
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

    private void createRow(String name, JButton minusButton, JButton plusButton, JLabel countLabel) {
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = Integer.parseInt(countLabel.getText());
                if (count > 0) {
                    count--;
                    countLabel.setText(String.valueOf(count));
                }
            }
        });

        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = Integer.parseInt(countLabel.getText());
                count++;
                countLabel.setText(String.valueOf(count));
            }
        });

        JLabel lab = new JLabel(name);
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lab);
        panel.add(minusButton);
        panel.add(countLabel);
        panel.add(plusButton);
    }

    public static void main(String[] args) {
        new TicketBuyingPage();
    }
}
