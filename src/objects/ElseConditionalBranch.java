package objects;

import objects.block.Block;
import objects.block.CanBeEnclosedBlock;
import objects.block.IfConditionalBranch;
import objects.block.codeOption.Nest;
import processing.core.PApplet;
import processing.core.PConstants;

public class ElseConditionalBranch extends CanBeEnclosedBlock {
    public ElseConditionalBranch(int x, int y) {
        super("else", x, y);
    }

    @Override
    public void exchangeCode() {
        String option = "";
        int nestNum = 1;
        if (codeOption != null) {
            option = codeOption.option();
            nestNum += codeOption.getOptionNum();
        }
        setCode(option + name);

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
    }

    @Override
    public boolean canConnect(Block block) {
        if (!block.connectable(this)) return false;
        int bx = block.x;
        int by = block.y;
        return PApplet.abs(x - bx) <= MARGIN && PApplet.abs(y + h - by) <= MARGIN;
    }

    @Override
    public boolean connectable(Block block) {
        return block instanceof IfConditionalBranch;
    }

    @Override
    public void enterBlock(Block block) {
        //pass
    }

    @Override
    public void enter() {
        //pass
    }

    @Override
    protected void enterDisplay(Block block) {
        //pass
    }

    @Override
    public void outBlock() {
        //pass
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
