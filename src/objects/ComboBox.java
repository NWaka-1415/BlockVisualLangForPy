package objects;

import objects.block.Block;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.Arrays;

public class ComboBox extends AppletObject {
    private static ComboBox selectComboBox = null;
    private static ArrayList<ComboBox> comboBox;

    private ArrayList<String> contentsList;
    private int selectContentIndex;
    private Block parentBlock;
    private boolean isFocus;
    private boolean openFlag = false;

    public boolean openFlag() {
        return openFlag;
    }

    private int x, y, w, h;
    private int dW, dH;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public ComboBox(int x, int y) {
        contentsList = new ArrayList<>();
        this.x = x;
        this.y = y;
        selectContentIndex = -1;
        isFocus = true;
        setDefaultSize();
    }

    public ComboBox(String[] contentsList, int x, int y) {
        this.contentsList = new ArrayList<>();
        this.contentsList.addAll(Arrays.asList(contentsList));
        this.x = x;
        this.y = y;
        isFocus = true;
        if (this.contentsList.size() <= 0) return;
        selectContentIndex = 0;
        setDefaultSize();
    }

    public ComboBox(ArrayList<String> contentsList, int x, int y) {
        this.contentsList = contentsList;
        this.x = x;
        this.y = y;
        isFocus = true;
        if (this.contentsList.size() <= 0) return;
        selectContentIndex = 0;
        setDefaultSize();
    }

    public ComboBox setParentBlock(Block parentBlock) {
        this.parentBlock = parentBlock;
        return this;
    }

    public ComboBox addContent(String content) {
        contentsList.add(content);
        return this;
    }

    public ComboBox setDefaultSize() {
        if (contentsList.size() <= 0) return this;
        int longContentLength = 0;
        for (String content : contentsList) {
            if (content.length() > longContentLength) longContentLength = content.length();
        }
        setDefaultSize(15 * longContentLength, 25).setSize(dW, dH);
        return this;
    }

    public ComboBox setDefaultSize(int w, int h) {
        dW = w;
        dH = h;
        return this;
    }

    public ComboBox setSize(int w, int h) {
        this.w = w;
        this.h = h;
        return this;
    }

    public ComboBox open() {
        openFlag = true;
        return this;
    }

    public ComboBox close() {
        AppletObject.debugLog("close()");
        openFlag = false;
        return this;
    }


    public String getSelectContent() {
        return contentsList.get(selectContentIndex);
    }

    public void focus() {
        if (selectComboBox != null) {
            selectComboBox.close();
            selectComboBox.isFocus = false;
        }
        isFocus = true;
        selectComboBox = this;
    }

    public static void focusOut() {
        if (selectComboBox != null) {
            selectComboBox.select();
            selectComboBox.isFocus = false;
            selectComboBox.close();
            selectComboBox = null;
        }
    }

    public static void openOrClose() {
        if (selectComboBox != null) {
            if (selectComboBox.openFlag) {
                selectComboBox.h = selectComboBox.dH * selectComboBox.contentsList.size();
                selectComboBox.select();
            }
            selectComboBox.openFlag = !selectComboBox.openFlag;
        }
    }

    public static void selectDisplay() {
        if (selectComboBox != null) selectComboBox.display();
    }

    private boolean isPressedContent(int index) {
        return x <= applet.mouseX && applet.mouseX <= x + dW &&
                y + dH * index <= applet.mouseY && applet.mouseY <= y + dH * (index + 1);
    }

    private void select() {
        for (int i = 0; i < contentsList.size(); i++) {
//            AppletObject.debugLog("index:%d", i);
//            AppletObject.debugLog("isPressed(%d) : %b", i, isPressedContent(i));
            if (isPressedContent(i + 1)) {
                selectContentIndex = i;
                return;
            }
        }
        selectContentIndex = 0;
    }

    @Override
    public void display() {
        applet.strokeWeight(1);
        applet.stroke(100, 100, 100);
        applet.fill(255);
        applet.rect(x, y, dW, dH);
        applet.fill(0);
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
        applet.textSize(20);
        applet.text(contentsList.get(selectContentIndex), x + dW / 2, y + dH / 2);
        if (openFlag) {
            int i = 1;
            for (String content : contentsList) {
                applet.strokeWeight(1);
                applet.stroke(100, 100, 100);
                applet.fill(255);
                applet.rect(x, y + dH * i, dW, dH);
                if (i - 1 == selectContentIndex) applet.fill(0);
                else applet.fill(115);
                applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
                applet.textSize(20);
                applet.text(content, x + dW / 2, y + (dH * i) + dH / 2);
                i++;
            }
            applet.fill(0);
            {
                applet.beginShape();
                applet.vertex(x + dW - 12, y + dH * 3 / 5);
                applet.vertex(x + dW - 6, y + dH * 3 / 5);
                applet.vertex(x + dW - 9, y + dH * 2 / 5);
                applet.endShape();
            }
        } else {
            w = dW;
            h = dH;

            applet.fill(0);
            {
                applet.beginShape();
                applet.vertex(x + dW - 12, y + dH * 2 / 5);
                applet.vertex(x + dW - 6, y + dH * 2 / 5);
                applet.vertex(x + dW - 9, y + dH * 3 / 5);
                applet.endShape();
            }
        }
    }

    @Override
    public void move(int addX, int addY) {
        this.x += addX;
        this.y += addY;
    }

    @Override
    public boolean isPressed() {
        return x <= applet.mouseX && applet.mouseX <= x + w &&
                y <= applet.mouseY && applet.mouseY <= y + h;
    }
}
