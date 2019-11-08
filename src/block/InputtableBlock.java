package block;

import javax.swing.*;

import controlP5.*;

public abstract class InputtableBlock extends Block {


    private static JLayeredPane pane;
    protected String content;
    //    private JTextField textField;
    private Textfield textfield;
    private ControlP5 cp5;
    protected boolean inputActiveFlag;
    protected int contentX, contentY, contentW, contentH;

    public static void paneSet(JLayeredPane pane) {
        InputtableBlock.pane = pane;
    }

    public InputtableBlock(String name, int x, int y, int w, int h, String content) {
        super(name, x, y, w, h);
        this.content = content;
        inputActiveFlag = false;
        cp5 = new ControlP5(applet);
        setTextField();
    }

    public InputtableBlock(String name, int x, int y, int w, String content) {
        super(name, x, y, w);
        this.content = content;
        inputActiveFlag = false;
        cp5 = new ControlP5(applet);
        setTextField();
    }

    public InputtableBlock(String name, int x, int y, String content) {
        super(name, x, y);
        this.content = content;
        inputActiveFlag = false;
        cp5 = new ControlP5(applet);
        setTextField();
    }

    private void setTextField() {
        calcInputFiled();
        textfield = cp5.addTextfield(content).setAutoClear(false).setFocus(true).setColorBackground(255);
        textfield.setSize(contentW, contentH);
        textfield.setPosition(contentX, contentY);
//        textField = new JTextField(content);
//        textField.setBounds(contentX, contentY, contentW, contentH);
//        pane.add(textField);
    }

    protected void calcInputFiled() {
        contentX = x + w / 3;
        contentY = y + h / 10;
        contentW = w / 3;
        contentH = 8 * h / 10;
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        calcInputFiled();
        textfield.setPosition(contentX + addX, contentY + addY);
//        textField.setLocation(contentX + addX, contentY + addY);
    }

    //入力状態に移行
    public void inputActivate() {
        inputActiveFlag = true;
    }

    //入力状態解除
    public void inputDeactivate() {
        inputActiveFlag = false;
    }

    public void setContent() {
//        content = textField.getText();
        content = textfield.getText();
    }

    public boolean isPressedInputField() {
        return contentX <= applet.mouseX && applet.mouseX <= contentX + contentW &&
                contentY <= applet.mouseY && applet.mouseY <= contentY + contentH;
    }

    public static void createContentFiled(int x, int y, int w, int h) {
        applet.strokeWeight(1);
        applet.stroke(100, 100, 100);
        applet.fill(255);
        applet.rect(x, y, w, h);
    }
}
