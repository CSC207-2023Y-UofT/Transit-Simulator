package ui;

import javax.swing.*;

/**
 * MainScreen is a JFrame that displays the main screen of the application.
 * It is used by the UIController to display the main screen of the application.
 *
 * @see UIController
 */
public class MainScreen extends JFrame {
    /**
     * The UIController that is used to switch panels.
     */
    private final UIController UIController;
    /**
     * The content pane of the JFrame.
     */
    private JPanel contentPane;

    /**
     * Constructs a new MainScreen object.
     *
     * @param UIController the controller used to switch panels
     */
    public MainScreen(UIController UIController) {
        super("Transit Simulator");

        this.UIController = UIController;

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
