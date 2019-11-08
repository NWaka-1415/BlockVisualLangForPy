package block.objects;

import processing.core.PConstants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextField extends AppletObject implements KeyListener {
    private String text;
    int x, y, w, h;
    int dW, dH;

    public String getText() {
        return text;
    }

    public void display() {
        applet.strokeWeight(1);
        applet.stroke(100, 100, 100);
        applet.fill(255);
        applet.rect(x, y, w, h);
        applet.fill(0);
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
        applet.textSize(20);
        applet.text(text, x + w / 2, y + h / 2);
    }

    public void setText(String text) {
        this.text = text;
        if (text.length() > 3) w += (text.length() - 3) * 5;
        else w = dW;
    }

    public TextField(String text) {
        this.text = text;
        this.x = 0;
        this.y = 0;
        this.w = 10;
        this.dW = w;
        this.h = 10;
    }

    public TextField(String text, int x, int y, int w, int h) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        dW = w;
        this.h = h;
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void move(int addX, int addY) {
        this.x += addX;
        this.y += addY;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        System.out.println(key);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
