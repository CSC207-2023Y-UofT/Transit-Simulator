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
     * Constructs a new MainScreen object.
     *
     * @param UIController the controller used to switch panels
     */
    public MainScreen() {
        super("Transit Simulator");

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
