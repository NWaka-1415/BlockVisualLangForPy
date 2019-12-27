package objects.block;

import objects.block.codeOption.Nest;
import processing.core.PApplet;
import processing.core.PConstants;

public class WhileLoopBlock extends CanBeEnclosedBlock {

    private Block booleanBlock;

    public WhileLoopBlock(int x, int y, int w, int h, int internalW, int internalH) {
        super("while", x, y, w, h, internalW, internalH);
    }

    public WhileLoopBlock(int x, int y, int w) {
        super("while", x, y, w);
    }

    public WhileLoopBlock(int x, int y) {
        super("while", x, y);
    }

    @Override
    public void exchangeCode() {
        String option = "";
        if (codeOption != null) option = codeOption.option();

        if (booleanBlock != null) setCode(option + name + "(" + booleanBlock.code() + ")");
        else setCode(option + name + "()");

        addCode(option + "{");
        if (encloseBlock != null) addCode(option + "\n" + encloseBlock.code(new Nest(1)));
        addCode(option + "}");
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

    }

    @Override
    public void enter() {

    }

    @Override
    protected void enterDisplay(Block block) {

    }

    @Override
    public void outBlock() {

    }

    @Override
    public boolean canConnectElement(Block block) {
        return false;
    }

    @Override
    public boolean connectableElement() {
        return false;
    }
}
