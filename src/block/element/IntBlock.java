package block.element;

import block.Block;
import block.InputtableBlock;
import block.enums.TextType;


public class IntBlock extends InputtableBlock {

    public IntBlock(int x, int y) {
        super("int", x, y, 75, "0");
        textField.setTextType(TextType.Integer);
    }

    @Override
    public void display() {
        applet.strokeWeight(3);
        applet.stroke(40, 90, 225);
        applet.fill(68, 105, 225);
        applet.rect(x, y, w, h, 5);    //最後の引数は矩形の角の曲がり具合
        super.display();
        //Content
        //calcInputFiled();
        //InputtableBlock.createContentFiled(contentX, contentY, contentW, contentH);
        //Text
//        applet.fill(0);
//        applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
//        applet.textSize(20);
//        applet.text(content, x + w / 2, y + h / 2 - h / 10);
    }

    @Override
    public void exchangeCode() {
        setCode(content);
    }

    @Override
    public boolean canConnect(Block block) {
        return false;
    }

    @Override
    public boolean connectable() {
        return false;
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
        return true;
    }
}
