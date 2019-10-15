package block;

import processing.core.PApplet;

public abstract class CanIncludeElementBlock extends Block {

    protected int boxX, boxY, boxW, boxH, defaultBoxW, defaultBoxH;

    public Block includeBlock;

    public CanIncludeElementBlock(PApplet applet, String name, int x, int y, int w, int h) {
        super(applet, name, x, y, w, h);
        includeBlock = null;
        boxW = w / 4 - 10;
        boxH = h - 14;
        defaultBoxW = boxW;
        defaultBoxH = boxH;
        boxX = x + w - 5 - boxW;
        boxY = y + 7;
    }

    protected void resetBox() {
        boxW = defaultBoxW;
        boxH = defaultBoxH;
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        //内包ブロックも動かす
        boxX += addX;
        boxY += addY;
        if (includeBlock != null) includeBlock.move(addX, addY);
    }

    @Override
    public void enterBlock(Block block) {
        if (includeBlock != null) return;
        includeBlock = block;
        block.parentBlock = this;

        this.w += block.w;
        boxW += block.w;

        enterDisplay(block);
    }

    @Override
    public void enter() {
        if (includeBlock == null) return;
        enterDisplay(includeBlock);
    }

    @Override
    protected void enterDisplay(Block block) {
        int addX = this.boxX - block.x;
        int addY = this.boxY - block.y;
        block.move(addX, addY);
    }

    @Override
    public void outBlock() {
        this.includeBlock.parentBlock = null;
        this.includeBlock = null;
        this.w = getDw();
        this.h = getDh();
        resetBox();
    }

    //中にエレメントを入れられるか
    @Override
    public boolean canConnectElement(Block block) {
        if (!block.connectableElement()) return false;
        int bx = block.x;
        int by = block.y;
        return boxX <= bx && bx <= boxX + defaultBoxW
                && boxY <= by + block.h / 2 && by + block.h / 2 <= boxY + defaultBoxH;
    }
}
