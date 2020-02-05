package objects;

import objects.block.Block;
import objects.block.enums.TextType;
import processing.core.PConstants;

import java.util.ArrayList;

public class TextField extends AppletObject {
    private static TextField selectTextField = null;
    private static ArrayList<TextField> textFields;

    private String text;
    private Block parentBlock;
    private TextType textType;
    private boolean isFocus;
    private int textPosition;
    private int x, y, w, h;
    private int dW, dH;

    public static boolean existFocusedTextField() {
        return selectTextField != null;
    }

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

    public TextField(String text, Block block) {
        this.parentBlock = block;
        setText(text);
        textPosition = text.length() - 1;
        this.x = 0;
        this.y = 0;
        this.w = 10;
        this.dW = w;
        this.h = 10;
        isFocus = true;
        textFields.add(this);
        focus();
    }

    public TextField(String text, int x, int y, int w, int h, Block block) {
        this.parentBlock = block;
        setText(text);
        textPosition = text.length() - 1;
        this.x = x;
        this.y = y;
        this.w = w;
        dW = w;
        this.h = h;
        isFocus = true;
        textFields.add(this);
        focus();
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

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    @Override
    public void move(int addX, int addY) {
        this.x += addX;
        this.y += addY;
    }

    public void focus() {
        if (selectTextField != null) selectTextField.isFocus = false;
        isFocus = true;
        selectTextField = this;
    }

    public static void focusOut() {
        if (selectTextField != null) {
            selectTextField.isFocus = false;
            selectTextField = null;
        }
    }

    @Override
    public boolean isPressed() {
        return x <= applet.mouseX && applet.mouseX <= x + w &&
                y <= applet.mouseY && applet.mouseY <= y + h;
    }

    public void setTextType(TextType textType) {
        this.textType = textType;
    }

    public boolean setText(String text) {
        if (textType == TextType.Integer) {
            if (isNumeric(text)) text = parseIntToString(text);
            else return false;
        }
        this.text = text;
        if (text.length() > 1) {
            w = dW + (text.length() - 1) * 13;
            parentBlock.setSize(parentBlock.getDw() + (text.length() - 1) * 13, parentBlock.getDh());
        } else {
            w = dW;
            parentBlock.resetSize();
        }
        return true;
    }

    public void addText(String text) {
        if (setText(this.text + text)) setTextPosition();
    }

    public void addText(char text) {
        if (setText(this.text + text)) setTextPosition();
    }

    public void minusText() {
        if (text.equals("")) return;
        String[] stringArray = text.split("");
        AppletObject.debugLog(String.format(" textP : %d", textPosition));
        if (stringArray.length < textPosition + 1 || textPosition < 0) return;
        if (stringArray[textPosition] == null) return;
        stringArray[textPosition] = "";
        text = "";
        for (String string : stringArray) {
            text += string;
        }
        if (textType == TextType.Integer && text.equals("")) text = "0";
        setText(text);
        AppletObject.debugLog(String.format(" textP sets : %d", textPosition));
        minusTextPosition();
        AppletObject.debugLog(String.format(" textP minus : %d", textPosition));
    }


    private void setTextPosition() {
        AppletObject.debugLog(String.format(" textP  STP: %d", textPosition));
        textPosition = text.length() - 1;
        AppletObject.debugLog(String.format(" textP  after STP: %d", textPosition));

        if (textPosition < 0) textPosition = -1;
    }

    private void addTextPosition() {
        textPosition++;
        AppletObject.debugLog(String.format(" textP ATP : %d", textPosition));
    }

    private void minusTextPosition() {
        textPosition--;
        AppletObject.debugLog(String.format(" textP MTP : %d", textPosition));
        if (textPosition < 0) textPosition = -1;
    }

    public static void set(char key) {
        if (selectTextField == null) return;
        selectTextField.addText(key);
    }

    public static void minusSet() {
        if (selectTextField == null) return;
        selectTextField.minusText();
    }

    public static void moveTextPosition(boolean add) {
        if (selectTextField == null) return;
        AppletObject.debugLog(String.format(" mTP add?: %b", add));
        if (add) selectTextField.addTextPosition();
        else selectTextField.minusTextPosition();
    }
}
