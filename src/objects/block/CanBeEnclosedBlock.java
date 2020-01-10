package objects.block;

import processing.core.PApplet;

public abstract class CanBeEnclosedBlock extends Block {
    //中に入っているブロック一個目
    protected Block encloseBlock;

    public Block getEncloseBlock() {
        return encloseBlock;
    }

    protected int internalW, internalH;

    public int getInternalH() {
        return internalH;
    }

    public CanBeEnclosedBlock(String name, int x, int y, int w, int h, int internalW, int internalH) {
        super(name, x, y, w, h);
        this.internalW = internalW;
        this.internalH = internalH;
    }

    public CanBeEnclosedBlock(String name, int x, int y, int w) {
        super(name, x, y, w, 170);
        internalW = 50;
        internalH = 50;
    }

    public CanBeEnclosedBlock(String name, int x, int y) {
        super(name, x, y, 120, 170);
        internalW = 50;
        internalH = 50;
    }

    public boolean ableToEnclose(Block block) {
        if (!block.connectable()) return false;
        int bx = block.x;
        int by = block.y;
        return PApplet.abs(x + internalW - bx) <= MARGIN && PApplet.abs(y + internalH - by) <= MARGIN;
    }

    public void encloseBlock(Block block) {
        if (!block.connectable()) return;
        if (this.encloseBlock != null) return;
        if (block.prevBlock != null) return;
        this.encloseBlock = block;
        block.prevBlock = this;
        encloseDisplay(block);
    }

    private void encloseDisplay(Block block) {
        calcSizeFromEnclose();
        int addX = this.x + internalW - block.x;
        int addY = (this.y + this.internalH) - block.y;
        block.move(addX, addY);
    }

    private void calcSizeFromEnclose(){
        if (encloseBlock != null) {
            int addH = 0;
            int addW_max;
            Block block = encloseBlock;
            addW_max = block.w;
            while (block != null) {
                addH += block.h;
                if (addW_max < block.w) addW_max = block.w;
                block = block.postBlock;
            }
            h = getDh() + addH;
        } else {
            w = getDh();
            h = getDw();
        }
    }

    public void enclose() {
        if (encloseBlock == null) return;
        encloseDisplay(encloseBlock);
        if (postBlock != null) connect();
    }

    public void outEnclose() {
        if (encloseBlock == null) return;
        encloseBlock.resetCodeOption();
        encloseBlock.prevBlock = null;
        encloseBlock = null;
        resetSize();
        calcSizeFromEnclose();
        if (postBlock != null) connect();
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        if (encloseBlock != null) encloseBlock.move(addX, addY);
    }

    @Override
    public void display() {
        calcSizeFromEnclose();
    }

    @Override
    public boolean isPressed() {
        boolean top, center, bottom;
        top = x <= applet.mouseX && applet.mouseX <= x + w &&
                y <= applet.mouseY && applet.mouseY <= y + internalH;
        center = x <= applet.mouseX && applet.mouseX <= x + internalW &&
                y + internalH <= applet.mouseY && applet.mouseY <= y + h - internalH;
        bottom = x <= applet.mouseX && applet.mouseX <= x + w &&
                y + h - internalH <= applet.mouseY && applet.mouseY <= y + h;

        return top || center || bottom;
    }
}
