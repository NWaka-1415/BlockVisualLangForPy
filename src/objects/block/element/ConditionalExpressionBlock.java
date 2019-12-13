package objects.block.element;

import objects.ComboBox;
import objects.IncludeField;
import objects.block.Block;
import objects.block.IHaveContent;

public class ConditionalExpressionBlock extends Block implements IHaveContent {

    private final String[] contents = new String[]{" == ", " != ", " < ", " <= ", " > ", " >= "};
    private ComboBox comboBox;

    public final IncludeField[] includeFields = new IncludeField[2];

    public ConditionalExpressionBlock(int x, int y, int w, int h) {
        super("", x, y, w, h);
        initialize();
    }

    public ConditionalExpressionBlock(int x, int y, int w) {
        super("", x, y, w);
        initialize();
    }

    public ConditionalExpressionBlock(int x, int y) {
        super("", x, y);
        initialize();
    }

    private void initialize() {
        comboBox = new ComboBox(contents, x, y + h / 10);

        includeFields[0] = new IncludeField(this);
        includeFields[1] = new IncludeField(this);
        int count = 0;
        int tmpW = w, tmpH = h;
        w += comboBox.getW();
        h += 10;
        for (IncludeField includeField : includeFields) {
            includeField.setSize(tmpW / 2, tmpH).setDefaultSize(tmpW / 4, tmpH)
                    .setPos(count == 0 ? x + 5 : x + w - 5 - includeField.getW() / 2, y + h / 10);
            count++;
        }
        setDefaultSize(w, h);
        comboBox.setPos(x + getDw() / 2 - comboBox.getW() / 2, comboBox.getY() + 5);
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        comboBox.move(addX, addY);
        for (IncludeField includeField : includeFields) {
            includeField.move(addX, addY);
        }
    }

    @Override
    public boolean isPressedContent() {
        return comboBox.isPressed();
    }

    @Override
    public void setFocusToContent() {
        comboBox.focus();
        applet.setTopBlock(this);
        for (IncludeField includeField : includeFields) applet.setTopBlock(includeField.includeBlock());
        ComboBox.openOrClose();
    }

    @Override
    public boolean haveContent() {
        return comboBox != null;
    }

    @Override
    public void display() {
        int count = 0;
        for (IncludeField includeField : includeFields) {
            includeField.setPos(count == 0 ? x + 5 : x + w - 5 - includeField.getW() / 2, y + h / 10);
            count++;
        }
        comboBox.setPos(x + w / 2 - comboBox.getW() / 2, comboBox.getY());

        applet.strokeWeight(3);
        applet.stroke(90, 0, 180);
        applet.fill(128, 0, 225);
        applet.rect(x, y, w, h, 5);    //最後の引数は矩形の角の曲がり具合
        for (IncludeField includeField : includeFields) {
            includeField.display();
        }
        comboBox.display();
    }

    @Override
    public void exchangeCode() {
        setCode(includeFields[0].code() + comboBox.getSelectContent() + includeFields[1].code());
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
        for (IncludeField includeField : includeFields) {
            if (includeField.includeBlock() == null
                    && block.parentBlock == null
                    && includeField.includeFlag()) {
                includeField.enterBlock(block);
                block.parentBlock = this;
            }
        }
        if (includeFields[0].includeBlock() != null && includeFields[1].includeBlock() != null) {
            w = getDw() + includeFields[0].getW() + includeFields[1].getW();
        } else if (includeFields[0].includeBlock() != null) {
            w = getDw() + includeFields[0].getW();
        } else if (includeFields[1].includeBlock() != null) {
            w = getDw() + includeFields[1].getW();
        }
    }

    @Override
    public void enter() {
        for (IncludeField includeField : includeFields) {
            if (includeField.includeBlock() != null) includeField.enterDisplay(includeField.includeBlock());
        }
    }

    @Override
    protected void enterDisplay(Block block) {
        // pass
    }

    @Override
    public void outBlock() {
        for (IncludeField includeField : includeFields) {
            includeField.outBlock();
        }
        w = getDw();
        h = getDh();
    }

    @Override
    public boolean canConnectElement(Block block) {
        if (!block.connectableElement()) return false;
        boolean result;
        for (IncludeField includeField : includeFields) {
            result = includeField.canIncludeBlock(block);
            if (result) return result;
        }
        return false;
    }

    @Override
    public boolean connectableElement() {
        return true;
    }

    @Override
    public boolean isPressed() {
        return super.isPressed() || comboBox.isPressed();
    }
}
