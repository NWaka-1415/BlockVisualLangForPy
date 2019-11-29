package objects.block.element;

import objects.ComboBox;
import objects.block.Block;
import objects.block.IHaveContent;

public class ConditionalExpression extends Block implements IHaveContent {

    private final String[] contents = new String[]{"==", "!=", "<", "<=", ">", ">="};
    private ComboBox comboBox;

    public ConditionalExpression(String name, int x, int y, int w, int h) {
        super(name, x, y, w, h);
    }

    public ConditionalExpression(String name, int x, int y, int w) {
        super(name, x, y, w);
    }

    public ConditionalExpression(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public boolean isPressedContent() {
        return comboBox.isPressed();
    }

    @Override
    public void setFocusToContent() {
        comboBox.focus();
        applet.setTopBlock(this);
        ComboBox.openOrClose();
    }

    @Override
    public boolean haveContent() {
        return comboBox != null;
    }

    @Override
    public void display() {
        applet.strokeWeight(3);
        applet.stroke(90, 0, 180);
        applet.fill(128, 0, 225);
        applet.rect(x, y, w, h, 5);    //最後の引数は矩形の角の曲がり具合
        comboBox.display();
    }

    @Override
    public void exchangeCode() {
        setCode(comboBox.getSelectContent());
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
        return false;
    }

    @Override
    public boolean isPressed() {
        return super.isPressed() || comboBox.isPressed();
    }
}
