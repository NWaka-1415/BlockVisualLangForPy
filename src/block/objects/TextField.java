package block.objects;

import block.InputtableBlock;
import block.enums.TextType;
import processing.core.PConstants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class TextField extends AppletObject {
    private static TextField selectTextField = null;
    private static ArrayList<TextField> textFields;

    private String text;
    private InputtableBlock parentBlock;
    private TextType textType;
    private boolean isFocus;
    private int x, y, w, h;
    private int dW, dH;

    public static boolean isNumeric(String str) {
        try {
            int textInt = Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static String parseIntToString(String str) {
        String text = "";
        try {
            int textInt = Integer.parseInt(str);
            text = String.format("%d", textInt);
        } catch (NumberFormatException | NullPointerException ignored) {
        }
        return text;
    }

    public static ArrayList<TextField> getTextFields() {
        return textFields;
    }

    public static void initialize() {
        textFields = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public int getDW() {
        return dW;
    }

    public int getDH() {
        return dH;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
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

    public void setTextType(TextType textType) {
        this.textType = textType;
    }

    public void setText(String text) {
        if (textType == TextType.Integer) {
            if (isNumeric(text)) text = parseIntToString(text);
            else return;
        }
        this.text = text;
        if (text.length() > 1) {
            w = dW + (text.length() - 1) * 13;
            parentBlock.setSize(parentBlock.getDw() + (text.length() - 1) * 13, parentBlock.getDh());
        } else {
            w = dW;
            parentBlock.resetSize();
        }
    }

    public void addText(String text) {
        setText(this.text + text);
    }

    public void addText(char text) {
        setText(this.text + text);
    }

    public TextField(String text, InputtableBlock inputtableBlock) {
        this.parentBlock = inputtableBlock;
        this.text = text;
        this.x = 0;
        this.y = 0;
        this.w = 10;
        this.dW = w;
        this.h = 10;
        isFocus = true;
        textFields.add(this);
        focus();
    }

    public TextField(String text, int x, int y, int w, int h, InputtableBlock inputtableBlock) {
        this.parentBlock = inputtableBlock;
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        dW = w;
        this.h = h;
        isFocus = true;
        textFields.add(this);
        focus();
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void move(int addX, int addY) {
        this.x += addX;
        this.y += addY;
    }

    public void focus() {
        if (selectTextField != null) selectTextField.isFocus = false;
        isFocus = true;
        selectTextField = this;
    }

    public boolean isPressed() {
        return x <= applet.mouseX && applet.mouseX <= x + w &&
                y <= applet.mouseY && applet.mouseY <= y + h;
    }

    public static void set(char key) {
        if (selectTextField == null) return;
        selectTextField.addText(key);
    }
}
