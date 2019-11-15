package objects;

import objects.block.Block;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.Arrays;

public class ListSelector extends AppletObject {
    private ArrayList<String> contentsList;
    private int selectContentIndex;
    private Block parentBlock;
    private boolean openFlag = false;

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

    public ListSelector(int x, int y) {
        contentsList = new ArrayList<>();
        this.x = x;
        this.y = y;
        selectContentIndex = -1;
        setDefaultSize();
    }

    public ListSelector(String[] contentsList, int x, int y) {
        this.contentsList = new ArrayList<>();
        this.contentsList.addAll(Arrays.asList(contentsList));
        this.x = x;
        this.y = y;
        if (this.contentsList.size() <= 0) return;
        selectContentIndex = 0;
        setDefaultSize();
    }

    public ListSelector(ArrayList<String> contentsList, int x, int y) {
        this.contentsList = contentsList;
        this.x = x;
        this.y = y;
        if (this.contentsList.size() <= 0) return;
        selectContentIndex = 0;
        setDefaultSize();
    }

    public ListSelector setParentBlock(Block parentBlock) {
        this.parentBlock = parentBlock;
        return this;
    }

    public ListSelector addContent(String content) {
        contentsList.add(content);
        return this;
    }

    public ListSelector setDefaultSize() {
        if (contentsList.size() <= 0) return this;
        int longContentLength = 0;
        for (String content : contentsList) {
            if (content.length() > longContentLength) longContentLength = content.length();
        }
        setDefaultSize(15 * longContentLength, 25).setSize(dW, dH);
        return this;
    }

    public ListSelector setDefaultSize(int w, int h) {
        dW = w;
        dH = h;
        return this;
    }

    public ListSelector setSize(int w, int h) {
        this.w = w;
        this.h = h;
        return this;
    }

    public ListSelector open() {
        openFlag = true;
        return this;
    }

    public String getSelectContent() {
        return contentsList.get(selectContentIndex);
    }

    @Override
    public void display() {
        applet.strokeWeight(1);
        applet.stroke(100, 100, 100);
        applet.fill(255);
        applet.rect(x, y, w, h);
        applet.fill(0);
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
        applet.textSize(20);
        applet.text(contentsList.get(selectContentIndex), x + w / 2, y + h / 2);
        if (openFlag) {
            int i = 0;
            applet.fill(0);
            {
                applet.beginShape();
                applet.vertex(x + w - 12, y + h * 4 / 5);
                applet.vertex(x + w - 6, y + h * 4 / 5);
                applet.vertex(x + w - 9, y + h * 2 / 5);
                applet.endShape();
            }
            for (String content : contentsList) {
                applet.strokeWeight(1);
                applet.stroke(100, 100, 100);
                applet.fill(255);
                applet.rect(x, y + h * i, w, h);
                applet.fill(0);
                applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
                applet.textSize(20);
                applet.text(content, x + w / 2, y + h / 2);
                i++;
            }
        } else {
            applet.fill(0);
            {
                applet.beginShape();
                applet.vertex(x + w - 12, y + h * 2 / 5);
                applet.vertex(x + w - 6, y + h * 2 / 5);
                applet.vertex(x + w - 9, y + 3 * h / 5);
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
