package ui;

import bootstrap.InteractorPool;

import javax.swing.*;

public class MainScreen extends JFrame {
    private final InteractorPool interactorPool;
    private JPanel contentPane;

    public MainScreen(InteractorPool interactorPool) {
        super("Transit Simulator");

        this.interactorPool = interactorPool;

        contentPane = new WelcomePage(interactorPool);
        setContentPane(contentPane);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
