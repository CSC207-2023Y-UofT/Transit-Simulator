package ui;

import javax.swing.*;

public class MainScreen extends JFrame {
    private final UIController UIController;
    private JPanel contentPane;

    public MainScreen(UIController UIController) {
        super("Transit Simulator");

        this.UIController = UIController;

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
