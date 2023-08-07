package ui.staff;

import ui.UIController;
import ui.util.ShadowedButton;

import javax.swing.*;
import java.awt.*;

/**
 * StaffSelectPage is a JPanel that displays the staff select page.
 * It is used by the UIController to display the staff select page.
 *
 * @see UIController
 */
public class StaffSelectPage extends JPanel {
    /**
     * The label that displays the staff types.
     */
    private JLabel staffTypeLabel;
    /**
     * The buttons that allow the user to select their staff type.
     */
    private JButton adminButton, trainEngineerButton, trainOperatorButton, backButton;

    /**
     * Constructs a new StaffSelectPage object.
     *
     * @param controller the controller used to switch panels
     */
    public StaffSelectPage(UIController controller) {
        super(new GridLayout(0, 3));

        staffTypeLabel = new JLabel("Please select your staff type.", SwingConstants.CENTER);
        staffTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        staffTypeLabel.setFont(new Font("Serif", Font.BOLD, 25));

        // Admin button
        adminButton = new ShadowedButton("Admin");
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminButton.setBackground(new Color(189, 87, 231));
        adminButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        adminButton.setFont(new Font("Serif", Font.BOLD, 20));
        adminButton.addActionListener(e -> controller.open(new LoginPage(controller)));

        // Train Operator button
        trainOperatorButton = new ShadowedButton("Train Operator");
        trainOperatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        trainOperatorButton.setBackground(new Color(222, 144, 53));
        trainOperatorButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        trainOperatorButton.setFont(new Font("Serif", Font.BOLD, 20));
        trainOperatorButton.addActionListener(e -> controller.open(new LoginPage(controller)));

        // Train Engineer button
        trainEngineerButton = new ShadowedButton("Train Engineer");
        trainEngineerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        trainEngineerButton.setBackground(new Color(57, 210, 190));
        trainEngineerButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        trainEngineerButton.setFont(new Font("Serif", Font.BOLD, 20));
        trainEngineerButton.addActionListener(e -> controller.open(new LoginPage(controller)));

        // Back button
        backButton = new ShadowedButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.addActionListener(e -> controller.open(new StaffHomePage(controller)));


        // Add components to the panel

        this.setBackground(new Color(210, 207, 206));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(staffTypeLabel);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(adminButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(trainOperatorButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(trainEngineerButton);
        this.add(new JLabel("  "));

        for (int i = 0; i < 6; i++) {
            this.add(new JLabel("  "));
        }

        this.add(backButton);
        this.add(new JLabel("  "));
        this.add(new JLabel("  "));
    }

}
