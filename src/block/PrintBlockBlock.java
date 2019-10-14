package block;

import processing.core.PApplet;
import processing.core.PConstants;

public class PrintBlockBlock extends CanIncludeElementBlock {
    public PrintBlockBlock(PApplet applet, int x, int y) {
        super(applet, "print", x, y, 200, 50);
    }

    @Override
    public void display() {
        applet.strokeWeight(3);
        applet.stroke(241, 196, 15);
        applet.fill(243, 156, 18);   //塗りつぶしの色を黄色に
        applet.rect(x, y, w, h, 15);    //最後の引数は矩形の角の曲がり具合
        //Text
        applet.fill(255);    //文字色を黒色に
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);
        applet.textSize(20);
        applet.text(name, x + w / 4, y + h / 2 - h / 10);
    }

    @Override
    public void exchangeCode() {
        setCode(name + "(" + includeBlock.code() + ")");
    }

    @Override
    public boolean canConnect(Block block) {
        if (!block.connectable()) return false;
        int bx = block.x;
        int by = block.y;
        //abs()は引数の絶対値を返す関数
        //yに関しては相手のブロックの「底」と比較するためにhを足すのを忘れないように
        return PApplet.abs(x - bx) <= MARGIN && PApplet.abs(y + h - by) <= MARGIN;
    }

    @Override
    public boolean connectable() {
        return true;
    }
}
