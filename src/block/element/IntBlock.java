package block.element;

import block.Block;
import processing.core.PApplet;
import processing.core.PConstants;


public class Int extends Block {

    private String content;

    public Int(PApplet applet, int x, int y) {
        super(applet, "int", x, y, 75);
        content = "0";
    }


    @Override
    public void display() {
        applet.strokeWeight(3);
        applet.stroke(40, 90, 225);
        applet.fill(68, 105, 225);
        applet.rect(x, y, w, h, 5);    //最後の引数は矩形の角の曲がり具合
        //Content
        applet.strokeWeight(1);
        applet.stroke(100, 100, 100);
        applet.fill(255);
        applet.rect(x + w / 3, y + h / 10, w / 3, 8 * h / 10);
        //Text
        applet.fill(0);
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
        applet.textSize(20);
        applet.text(content, x + w / 2, y + h / 2 - h / 10);
    }

    @Override
    public void exchangeCode() {
        code = content;
    }

    @Override
    public boolean canConnect(Block block) {
        return false;
    }

    @Override
    public boolean connectable() {
        return false;
    }
}
