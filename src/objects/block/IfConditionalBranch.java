package objects.block;

import objects.IncludeField;
import objects.block.codeOption.Nest;
import objects.block.enums.ReturnType;
import processing.core.PApplet;
import processing.core.PConstants;

public class IfConditionalBranch extends CanBeEnclosedBlock implements IHaveIncludeField {

    private IncludeField[] includeField = new IncludeField[1];

    public IfConditionalBranch(int x, int y) {
        super("if", x, y);
        ReturnType[] accept = {ReturnType.Bool};
        includeField[0] = new IncludeField(this, accept);
        includeField[0].setColor(255, 161, 38).setSize(30, 40).setDefaultSize(30, 40);
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
        setCode(option + name + " (" + includeField[0].code() + ")");

        addCode("\n" + option + "{");
        if (encloseBlock != null) addCode("\n" + encloseBlock.code(new Nest(nestNum)));
        addCode("\n" + option + "}");
        if (postBlock == null) return;
        int postCodeOption = codeOption == null ? 0 : codeOption.getOptionNum();
        addCode(option + "\n" + postBlock.code(new Nest(postCodeOption)));
    }

    @Override
    public void display() {
        super.display();
        applet.strokeWeight(3);
        applet.stroke(255, 161, 38);
        applet.fill(255, 102, 0);
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
        if (!includeField[0].enterBlock(block)) return;

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
    public boolean canConnectElement(Block block) {
        if (!block.connectableElement()) return false;
        return includeField[0].canIncludeBlock(block);
    }

    @Override
    public boolean connectableElement() {
        return false;
    }

    @Override
    public IncludeField[] getIncludeField() {
        return includeField;
    }
}
