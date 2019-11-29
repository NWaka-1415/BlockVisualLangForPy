import objects.component.MainComponent;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        JFrame window = new Main();
        window.setVisible(true);
    }

    Main() {
        setSize(1200, 600);
//        PApplet second = new Main(frame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        //メニューバー
//        JMenuBar menuBar = new JMenuBar();
//        JMenu menuFile, menuEdit;
//        menuFile = new JMenu("File");
//        menuEdit = new JMenu("Edit");
//        menuBar.add(menuFile);
//        menuBar.add(menuEdit);
//
//        setJMenuBar(menuBar);


        MainComponent applet = new MainComponent(1500, 700);
        Canvas canvas = applet.canvas;
        add(canvas);
        applet.showCanvas();

        JTextArea codeTextArea = new JTextArea();
        Font font = new Font("SansSerif", Font.PLAIN, 32);
        codeTextArea.setFont(font);
        codeTextArea.setSize(400, 600);
        add(codeTextArea);
        applet.setCodeTextArea(codeTextArea);
    }
}
