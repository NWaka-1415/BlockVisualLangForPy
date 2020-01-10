package objects.block;

import objects.IncludeField;

public abstract class CanIncludeElementBlock extends Block implements IHaveIncludeField {

    protected int boxX, boxY, boxW, boxH, defaultBoxW, defaultBoxH;

    public final IncludeField[] includeFields = new IncludeField[1];

    public CanIncludeElementBlock(String name, int x, int y, int w, int h) {
        super(name, x, y, w, h);
        includeFields[0] = new IncludeField(this);
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
    public void display() {
        if (includeFields[0].includeBlock() != null) boxW = includeFields[0].includeBlock().w + 15;
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        //内包ブロックも動かす
        boxX += addX;
        boxY += addY;
        if (includeFields[0] != null) includeFields[0].move(addX, addY);
    }

    @Override
    public void enterBlock(Block block) {
        if (includeFields[0].includeBlock() != null) return;//既に自分が中にブロックを保持していたら無視
        if (block.parentBlock != null) return;//既に相手のブロックを保持しているブロックがあれば無視
        includeFields[0].enterBlock(block);
        block.parentBlock = this;

        this.w = getDw() + block.w;
        boxW = defaultBoxW + block.w;

        enterDisplay(block);
    }

    @Override
    public void enter() {
        if (includeFields[0].includeBlock() == null) return;
        enterDisplay(includeFields[0].includeBlock());
    }

    @Override
    protected void enterDisplay(Block block) {
        int addX = this.boxX - block.x;
        int addY = this.boxY - block.y;
        block.move(addX, addY);
    }

    @Override
    public void outBlock() {
        this.includeFields[0].includeBlock().parentBlock = null;
        this.includeFields[0].outBlock();
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

    @Override
    public IncludeField[] getIncludeFields() {
        return includeFields;
    }
}
