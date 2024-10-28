package ru.mlagsoft;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;


public class Window extends JFrame {
    private String _en = "en";
    private String _ru = "ru";

    private static String title = "Perfect";
    private static int width = 1280 / 2;
    private static int height = 720 / 2;
    private JButton confirm_but = new JButton("Confirm");

    private int _height;
    private int _width;

    public Window() {
        _width = Toolkit.getDefaultToolkit().getScreenSize().width;
        _height = Toolkit.getDefaultToolkit().getScreenSize().height;
        onDraw();

        localization();
    }


    public void localization() {
        if (System.getProperty("user.language").equalsIgnoreCase(_ru)) {

            System.out.println(true);

        }
    }


    public void onDraw() {
        setTitle(title);
        setVisible(true);
        int x = width / 2;
        int y = height / 2;
        setBounds(x, y, width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}