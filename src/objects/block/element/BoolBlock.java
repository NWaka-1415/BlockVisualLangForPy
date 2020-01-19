package objects.block.element;

import objects.ComboBox;
import objects.block.Block;
import objects.block.IHaveContent;
import objects.block.enums.ReturnType;

public class BoolBlock extends Block implements IHaveContent {

    private final String[] contents = new String[]{"true", "false"};
    private ComboBox comboBox;

    int contentX, contentY;

    public BoolBlock(int x, int y, int w, int h) {
        super("bool", x, y, w, h);
        setReturnType(ReturnType.Bool);
        calcContentPosition();
        comboBox = new ComboBox(contents, contentX, contentY).setParentBlock(this);
        comboBox.setPos(x + (w - comboBox.getW()) / 2, comboBox.getY());
    }

    public BoolBlock(int x, int y, int w) {
        super("bool", x, y, w);
        setReturnType(ReturnType.Bool);
        calcContentPosition();
        comboBox = new ComboBox(contents, contentX, contentY).setParentBlock(this);
        comboBox.setPos(x + (w - comboBox.getW()) / 2, comboBox.getY());
    }

    public BoolBlock(int x, int y) {
        super("bool", x, y);
        setReturnType(ReturnType.Bool);
        calcContentPosition();
        comboBox = new ComboBox(contents, contentX, contentY).setParentBlock(this);
        comboBox.setPos(x + (w - comboBox.getW()) / 2, comboBox.getY());
    }

    private void calcContentPosition() {
        contentX = x + w / 4;
        contentY = y + h / 10;
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        comboBox.move(addX, addY);
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
        String option = "";
        if (codeOption != null) option = codeOption.option();

        setCode(option + comboBox.getSelectContent());
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
    public boolean isPressed() {
        return super.isPressed() || comboBox.isPressed();
    }

    @Override
    public boolean haveContent() {
        return comboBox != null;
    }
}
