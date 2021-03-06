package objects.block;

import objects.block.enums.ReturnType;
import processing.core.PApplet;
import processing.core.PConstants;

public class PrintBlockBlock extends CanIncludeElementBlock {
    public PrintBlockBlock(int x, int y, ReturnType[] returnTypes) {
        super("print", x, y, 150, 50, returnTypes);
        includeFields[0].setColor(241, 196, 15);
    }

    @Override
    public void display() {
        applet.strokeWeight(3);
        applet.stroke(241, 196, 15);
        applet.fill(243, 156, 18);   //塗りつぶしの色を黄色に
        Block.createConnectBlock(x, y, w, h);
        applet.strokeWeight(1);
//        applet.stroke(100, 100, 100);

        includeFields[0].display();
//        applet.stroke(241, 196, 15);
//        applet.fill(255);
//        applet.rect(boxX, boxY, boxW, boxH, 5);    //最後の引数は矩形の角の曲がり具合
        //applet.rect(x, y, w, h, 15);
        //Text
        applet.fill(255);
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);
        applet.textSize(24);
        applet.text(name, x + 75, y + h / 2 - h / 10);
    }

    @Override
    public void exchangeCode() {
        String option = "";
        if (codeOption != null) option = codeOption.option();
        setCode(option + name + "(" + includeFields[0].code() + ");");
        if (postBlock == null) return;
        addCode(option + "\n" + postBlock.code(codeOption));
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
        return true;
    }

    @Override
    protected void enterDisplay(Block block) {
        //pass
    }

    @Override
    public boolean connectableElement() {
        return false;
    }
}
