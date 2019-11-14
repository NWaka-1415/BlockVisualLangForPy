package objects.block.element;

import objects.AppletObject;
import objects.block.Block;
import objects.block.InputtableBlock;
import objects.block.enums.TextType;


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
    }

    @Override
    public void setContent() {
        content = textField.getText();
        if (content == null || content.equals("")) {
            textField.setText("0");
            content = "0";
        }
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
