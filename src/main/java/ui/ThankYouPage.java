package ui;

import javax.swing.*;

public class ThankYouPage {

    private JFrame frame;
    private JPanel panel;
    private JLabel headerLabel;
    private JButton nextButton;

    public ThankYouPage() {

        JFrame frame = new JFrame("Thank You Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("<html><div style='text-align: center;'>Purchase confirmed.<br/>Ticket valid for 24 hours.<br/>Thank you.</div></html>", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(20.0f)); // Increase font size
        frame.getContentPane().add(label);

        frame.setSize(400, 200);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new ThankYouPage();
    }

}
