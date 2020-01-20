package objects.block.element;

import objects.block.Block;
import objects.block.InputtableBlock;
import objects.block.enums.ReturnType;
import objects.block.enums.TextType;

public class StringBock extends InputtableBlock {
    public StringBock(int x, int y, String content) {
        super("string", x, y, content);
        setReturnType(ReturnType.String);
        textField.setTextType(TextType.String);
    }

    @Override
    public void display() {
        applet.strokeWeight(3);
        applet.stroke(0, 128, 128);
        applet.fill(20, 142, 142);
        applet.rect(x, y, w, h, 5);    //最後の引数は矩形の角の曲がり具合
        super.display();
    }


    @Override
    public void setContent() {
        content = textField.getText();
    }

    @Override
    public void exchangeCode() {

    }

    @Override
    public boolean canConnect(Block block) {
        return false;
    }

    @Override
    public boolean connectable(Block block) {
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
