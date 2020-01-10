package objects.block;

import objects.IncludeField;
import objects.block.codeOption.Nest;
import processing.core.PApplet;
import processing.core.PConstants;

public class WhileLoopBlock extends CanBeEnclosedBlock implements IHaveIncludeField {

    private IncludeField[] includeField = new IncludeField[1];

    public WhileLoopBlock(int x, int y) {
        super("while", x, y);
        includeField[0] = new IncludeField(this);
        includeField[0].setColor(0, 224, 194).setSize(30, 40).setDefaultSize(30, 40);
        includeField[0].setPos(x + getDw() - includeField[0].getDW() - 10, y + internalH / 10);
    }

    @Override
    public void exchangeCode() {
        String option = "";
        int nestNum = 1;
        if (codeOption != null) {
            option = codeOption.option();
            nestNum += codeOption.getOptionNum();
        }
        setCode(option + name + "(" + includeField[0].code() + ")");

        addCode("\n" + option + "{");
        if (encloseBlock != null) addCode(option + "\n" + encloseBlock.code(new Nest(nestNum)));
        addCode("\n" + option + "}");
        if (postBlock == null) return;
        addCode(option + "\n" + postBlock.code());
    }

    @Override
    public void display() {
        super.display();
        applet.strokeWeight(3);
        applet.stroke(0, 224, 194);
        applet.fill(0, 188, 166);
        Block.createEncloseBlock(x, y, w, h, internalW, internalH);
        applet.fill(255);
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);
        applet.textSize(24);
        applet.text(name, x + 75, y + internalH / 2 - internalH / 10);
        includeField[0].display();
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        includeField[0].move(addX, addY);
    }

    @Override
    public boolean canConnect(Block block) {
        if (!block.connectable()) return false;
        int bx = block.x;
        int by = block.y;
        return PApplet.abs(x - bx) <= MARGIN && PApplet.abs(y + h - by) <= MARGIN;
    }

    @Override
    public boolean connectable() {
        return true;
    }

    @Override
    public void enterBlock(Block block) {
        if (includeField[0].includeBlock() != null) return;
        if (block.parentBlock != null) return;
        includeField[0].enterBlock(block);

        this.w = getDw() + includeField[0].getW();

        enterDisplay(block);
    }

    @Override
    public void enter() {
        if (includeField[0].includeBlock() == null) return;
        enterDisplay(includeField[0].includeBlock());
    }

    @Override
    protected void enterDisplay(Block block) {
        //pass
    }

    @Override
    public void outBlock() {
        includeField[0].outBlock();
        resetSize();
    }

    @Override
    public void outEnclose() {
        super.outEnclose();
    }

    @Override
    public boolean canConnectElement(Block block) {
        if (!block.connectableElement()) return false;
        return includeField[0].canIncludeBlock(block);
    }

    @Override
    public boolean connectableElement() {
        return false;
    }

    @Override
    public IncludeField[] getIncludeFields() {
        return includeField;
    }
}
