package objects.block;

import objects.TextField;
import objects.block.codeOption.Nest;
import objects.block.enums.TextType;
import processing.core.PApplet;
import processing.core.PConstants;

public class ForLoopBlock extends CanBeEnclosedBlock implements IHaveContent {

    private TextField[] textFields;

    public ForLoopBlock(int x, int y) {
        super("for", x, y, 450, 170, 50, 50);
        textFields = new TextField[3];
        int margin = 50;
        for (int i = 0; i < 3; i++) {
            textFields[i] = new TextField(i == 0 ? "0" : (i == 1) ? "10" : "1", x + margin + w / 3, y + internalH / 10, 40, 40, this);
            textFields[i].setTextType(TextType.Integer);
            margin += 100;
        }
    }

    @Override
    public void exchangeCode() {
        String option = "";
        int nestNum = 1;
        if (codeOption != null) {
            option = codeOption.option();
            nestNum += codeOption.getOptionNum();
        }

        int plus = Integer.parseInt(textFields[2].getText());
        String var = "i";
        setCode(option + name + "(int " + var + " = " + textFields[0].getText()
                + "; " + var + " < " + textFields[1].getText()
                + "; " + var + (plus == 1 ? "++" : (" += " + plus))
        );
        addCode(")");
        addCode("\n" + option + "{");
        if (encloseBlock != null) addCode("\n" + encloseBlock.code(new Nest(nestNum)));
        addCode("\n" + option + "}");
        if (postBlock == null) return;
        int postCodeOption = codeOption == null ? 0 : codeOption.getOptionNum();
        addCode(option + "\n" + postBlock.code(new Nest(postCodeOption)));
    }

    @Override
    public void display() {
        super.display();
        applet.strokeWeight(3);
        applet.stroke(0, 244, 194);
        applet.fill(0, 188, 166);
        createEncloseBlock(x, y, w, h, internalW, internalH);
        applet.fill(255);
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);
        applet.textSize(24);
        int textPosY = y + internalH / 2 - internalH / 10;
        applet.text(name, x + 75, textPosY);
        applet.text("from", x + 160, textPosY);
        applet.text("to", x + 270, textPosY);
        applet.text("by", x + 370, textPosY);
        for (TextField textField : textFields) {
            textField.display();
        }
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        for (TextField textField : textFields) {
            textField.move(addX, addY);
        }
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
    public void enterBlock(Block block) {
        //pass
    }

    @Override
    public void enter() {
        //pass
    }

    @Override
    protected void enterDisplay(Block block) {
        //pass
    }

    @Override
    public void outBlock() {
        //pass
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
    public boolean isPressedContent() {
        return textFields[0].isPressed() || textFields[1].isPressed() || textFields[2].isPressed();
    }

    @Override
    public boolean isPressed() {
        return super.isPressed() || isPressedContent();
    }

    @Override
    public void setFocusToContent() {
        for (TextField textField : textFields) {
            if (textField.isPressed()) textField.focus();
        }
    }

    @Override
    public boolean haveContent() {
        return textFields[0] != null || textFields[1] != null || textFields[2] != null;
    }
}
