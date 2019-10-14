package block;

import processing.core.PApplet;

public abstract class CanIncludeElementBlock extends Block {

    protected int boxX, boxY, boxW, boxH;

    Block includeBlock;

    public CanIncludeElementBlock(PApplet applet, String name, int x, int y, int w, int h) {
        super(applet, name, x, y, w, h);
        includeBlock = null;
    }

    public void EnterBlock(Block block) {
        if (includeBlock != null) includeBlock = block;
    }

    //中にエレメントを入れられるか
    public boolean canConnectElement(Block block) {
        int bx = block.x;
        int by = block.y;
        return PApplet.abs(boxX - bx) <= MARGIN && PApplet.abs(boxY - by) <= MARGIN;
    }
}
