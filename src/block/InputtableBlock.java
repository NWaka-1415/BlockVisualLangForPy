package block;

public abstract class InputtableBlock extends Block {

    protected String content;
    protected boolean inputActiveFlag;
    protected int contentX, contentY, contentW, contentH;

    public InputtableBlock(String name, int x, int y, int w, int h, String content) {
        super(name, x, y, w, h);
        this.content = content;
        inputActiveFlag = false;
    }

    public InputtableBlock(String name, int x, int y, int w, String content) {
        super(name, x, y, w);
        this.content = content;
        inputActiveFlag = false;
    }

    public InputtableBlock(String name, int x, int y, String content) {
        super(name, x, y);
        this.content = content;
        inputActiveFlag = false;
    }

    protected void calcInputFiled() {
        contentX = x + w / 3;
        contentY = y + h / 10;
        contentW = w / 3;
        contentH = 8 * h / 10;
    }

    //入力状態に移行
    public void inputActivate() {
        inputActiveFlag = true;
    }

    //入力状態解除
    public void inputDeactivate() {
        inputActiveFlag = false;
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
