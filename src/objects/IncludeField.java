package objects;

import objects.block.Block;

public class IncludeField extends AppletObject {
    private int x, y;
    private int w, h, dW, dH;

    private Block includeBlock;
    private Block parentBlock;

    //ブロックが入れます
    private boolean includeFlag;

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

    public int getDW() {
        return dW;
    }

    public int getDH() {
        return dH;
    }

    public boolean includeFlag() {
        return includeFlag;
    }

    public IncludeField(Block parentBlock) {
        this.parentBlock = parentBlock;
        includeBlock = null;
    }

    public IncludeField(Block parentBlock, Block block) {
        this.parentBlock = parentBlock;
        includeBlock = block;
    }

    public IncludeField setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public IncludeField setDefaultSize(int w, int h) {
        this.dW = w;
        this.dH = h;
        return this;
    }

    public IncludeField setSize(int w, int h) {
        this.w = w;
        this.h = h;
        return this;
    }

    public IncludeField resetSize() {
        w = dW;
        h = dH;
        return this;
    }

    public Block includeBlock() {
        return includeBlock;
    }

    public void enterBlock(Block block) {
        includeBlock = block;
        this.w = block.w;

        enterDisplay(block);
    }

    public void enterDisplay(Block block) {
        int addX = this.x - block.x;
        int addY = this.y - block.y;
        block.move(addX, addY);
    }

    @Override
    public void display() {
        if (includeBlock == null) {
            applet.strokeWeight(3);
            applet.stroke(90, 0, 180);
            applet.fill(255);
            applet.rect(x, y, dW, dH, 5);
        } else {
            applet.strokeWeight(3);
            applet.stroke(90, 0, 180);
            applet.fill(255);
            applet.rect(x, y, w, h, 5);
        }
    }

    public String code() {
        if (includeBlock == null) return "";
        return includeBlock.code();
    }

    @Override
    public void move(int addX, int addY) {
        x += addX;
        y += addY;
        if (includeBlock != null) includeBlock.move(addX, addY);
    }

    public void outBlock() {
        debugLog("includeFlag:%b", includeFlag);
        if (includeFlag) return;
        debugLog("None return");
        includeBlock.parentBlock = null;
        includeBlock = null;
        resetSize();
    }

    public boolean canIncludeBlock(Block block) {
        if (!block.connectableElement()) return false;
        int bx = block.x;
        int by = block.y;
        boolean result = x <= bx && bx <= x + dW &&
                y <= by + block.h / 2 && by + block.h / 2 <= y + dH;
        includeFlag = result;
        return result;
    }

    @Override
    public boolean isPressed() {
        return x <= applet.mouseX && applet.mouseX <= x + w &&
                y <= applet.mouseY && applet.mouseY <= y + h;
    }
}
