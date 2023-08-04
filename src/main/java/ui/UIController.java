package ui;

import bootstrap.InteractorPool;

import javax.swing.*;
import java.awt.*;

public class UIController {
    private final MainScreen mainScreen;
    private final InteractorPool interactorPool;

    public UIController(InteractorPool interactorPool) {
        this.interactorPool = interactorPool;
        this.mainScreen = new MainScreen(this);
    }

    public InteractorPool getInteractorPool() {
        return interactorPool;
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public void open(JPanel panel) {
        mainScreen.setContentPane(panel);
        mainScreen.setPreferredSize(new Dimension(900, 600));
        mainScreen.pack();
        mainScreen.revalidate();
        mainScreen.repaint();
    }
}
