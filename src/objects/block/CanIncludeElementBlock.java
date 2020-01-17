package objects.block;

import objects.IncludeField;
import objects.block.enums.ReturnType;

public abstract class CanIncludeElementBlock extends Block implements IHaveIncludeField {

    public final IncludeField[] includeFields = new IncludeField[1];

    public CanIncludeElementBlock(String name, int x, int y, int w, int h, ReturnType[] returnTypes) {
        super(name, x, y, w, h);
        setIncludeFields(returnTypes);
    }

    protected void setIncludeFields(ReturnType[] returnTypes) {
        includeFields[0] = new IncludeField(this, returnTypes);
        includeFields[0].setSize(w / 4 - 10, h - 14).setDefaultSize(w / 4 - 10, h - 14);
        debugLog(includeFields[0].getW());
        includeFields[0].setPos(x + w - 5 - includeFields[0].getW(), y + 7);
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        //内包ブロックも動かす
        if (includeFields[0] != null) includeFields[0].move(addX, addY);
    }

    @Override
    public void enterBlock(Block block) {
        if (includeFields[0].includeBlock() != null) return;//既に自分が中にブロックを保持していたら無視
        if (block.parentBlock != null) return;//既に相手のブロックを保持しているブロックがあれば無視
        if (!includeFields[0].enterBlock(block)) return;

        this.w = getDw() + block.w;

        enterDisplay(block);
    }

    @Override
    public void enter() {
        if (includeFields[0].includeBlock() == null) return;
        enterDisplay(includeFields[0].includeBlock());
    }

    @Override
    public void outBlock() {
        this.includeFields[0].outBlock();
        this.w = getDw();
        this.h = getDh();
    }

    //中にエレメントを入れられるか
    @Override
    public boolean canConnectElement(Block block) {
        if (!block.connectableElement()) return false;
        return includeFields[0].canIncludeBlock(block);
    }

    @Override
    public IncludeField[] getIncludeField() {
        return includeFields;
    }
}
