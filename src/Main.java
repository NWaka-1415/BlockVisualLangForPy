import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    Graphics2D g2;

    public static void main(String[] args) {
        JFrame window = new Main();
        window.setVisible(true);
    }

    Main() {
        setSize(1200, 600);
//        PApplet second = new Main(frame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        MainComponent applet = new MainComponent(1000, 500);
        Canvas canvas = applet.canvas;
        add("Center", canvas);
        applet.showCanvas();
        TextArea codeTextArea = new TextArea();
        add("East", codeTextArea);
        applet.setCodeTextArea(codeTextArea);
    }
}
