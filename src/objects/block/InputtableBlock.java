package objects.block;

import objects.TextField;


public abstract class InputtableBlock extends Block {
    protected String content;
    //    private JTextField textField;
    protected TextField textField;
    protected int contentX, contentY, contentW, contentH;

    public InputtableBlock(String name, int x, int y, int w, int h, String content) {
        super(name, x, y, w, h);
        this.content = content;
        setTextField();
    }

    public InputtableBlock(String name, int x, int y, int w, String content) {
        super(name, x, y, w);
        this.content = content;
        setTextField();
    }

    public InputtableBlock(String name, int x, int y, String content) {
        super(name, x, y);
        this.content = content;
        setTextField();
    }

    private void setTextField() {
        calcInputFiled();
        textField = new TextField(content, contentX, contentY, contentW, contentH, this);
    }

    protected void calcInputFiled() {
        contentX = x + w / 3;
        contentY = y + h / 10;
        contentW = w / 3;
        contentH = 8 * h / 10;
    }

    @Override
    public void display() {
        textField.display();
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        calcInputFiled();
        textField.move(addX, addY);
//        textField.setLocation(contentX + addX, contentY + addY);
    }

    public void setContent() {
        content = textField.getText();
    }

    public boolean isPressedInputField() {
        return textField.isPressed();
    }

    public void setFocusToContent() {
        textField.focus();
    }

    public static void createContentFiled(int x, int y, int w, int h) {
        applet.strokeWeight(1);
        applet.stroke(100, 100, 100);
        applet.fill(255);
        applet.rect(x, y, w, h);
    }
}
