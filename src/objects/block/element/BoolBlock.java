package objects.block.element;

import objects.ListSelector;
import objects.block.Block;

public class BoolBlock extends Block {

    private final String[] contents = new String[]{"true", "false"};
    private ListSelector listSelector;

    int contentX, contentY;

    public BoolBlock(int x, int y, int w, int h) {
        super("bool", x, y, w, h);
        calcContentPosition();
        listSelector = new ListSelector(contents, contentX, contentY).setParentBlock(this);
        listSelector.setPos(x + (w - listSelector.getW()) / 2, listSelector.getY());
    }

    public BoolBlock(int x, int y, int w) {
        super("bool", x, y, w);
        calcContentPosition();
        listSelector = new ListSelector(contents, contentX, contentY).setParentBlock(this);
        listSelector.setPos(x + (w - listSelector.getW()) / 2, listSelector.getY());
    }

    public BoolBlock(int x, int y) {
        super("bool", x, y);
        calcContentPosition();
        listSelector = new ListSelector(contents, contentX, contentY).setParentBlock(this);
        listSelector.setPos(x + (w - listSelector.getW()) / 2, listSelector.getY());
    }

    private void calcContentPosition() {
        contentX = x + w / 4;
        contentY = y + h / 10;
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        listSelector.move(addX, addY);
    }

    @Override
    public void display() {
        applet.strokeWeight(3);
        applet.stroke(90, 0, 180);
        applet.fill(128, 0, 225);
        applet.rect(x, y, w, h, 5);    //最後の引数は矩形の角の曲がり具合
        listSelector.display();
    }

    @Override
    public void exchangeCode() {
        setCode(listSelector.getSelectContent());
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
}
