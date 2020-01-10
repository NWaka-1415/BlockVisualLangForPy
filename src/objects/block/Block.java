package objects.block;

import objects.AppletObject;
import objects.block.codeOption.ICodeOption;
import processing.core.PSurface;

public abstract class Block extends AppletObject {
    //    protected static PApplet applet = null;
    protected static PSurface surface = null;
    protected String name;
    protected String displayName;
    private String code;
    protected ICodeOption codeOption;
    private int dw, dh;//デフォルトサイズ
    public int x, y, w, h;
    public Block prevBlock;
    public Block postBlock;
    public Block parentBlock;

    protected final int MARGIN = 20;    //許容する二つのブロックの距離の差。適宜変更したり、xとy座標でそれぞれ分けるのもありです

    public String code() {
        exchangeCode();
        return code;
    }

    public String code(ICodeOption codeOption) {
        this.codeOption = codeOption;
        return code();
    }

    public int getDw() {
        return dw;
    }

    public int getDh() {
        return dh;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void addCode(String code) {
        this.code += code;
    }

    public static void initialize(PSurface surface) {
        Block.surface = surface;
    }

    public Block(String name, int x, int y, int w, int h) {
        this.name = name;
        this.dw = w;
        this.dh = h;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Block(String name, int x, int y, int w) {
        this.name = name;
        this.dw = w;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = 30;
        this.dh = h;
    }

    public Block(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = 120;
        this.h = 30;
        this.dw = w;
        this.dh = h;
    }

    public static void createConnectBlock(int x, int y, int w, int h) {
        int mainCurve = 6;
        int connectWidth = 25;
        int connectHeight = 15;
        applet.beginShape();
        {
            applet.vertex(x + mainCurve, y);
            applet.vertex(x + mainCurve + 10, y);
            applet.vertex(x + mainCurve + 10 + connectWidth / 2, y + connectHeight);
            applet.vertex(x + mainCurve + 10 + connectWidth, y);
            applet.vertex(x + w - 1, y);
            applet.vertex(x + w, y + 1);
            applet.vertex(x + w, y + h - 1);
            applet.vertex(x + w - 1, y + h);
            applet.vertex(x + mainCurve + 10 + connectWidth, y + h);
            applet.vertex(x + mainCurve + 10 + connectWidth / 2, y + h + connectHeight);
            applet.vertex(x + mainCurve + 10, y + h);
            applet.vertex(x + mainCurve, y + h);
            applet.vertex(x, y + h - mainCurve);
            applet.vertex(x, y + mainCurve);
            applet.vertex(x + mainCurve, y);
        }
        applet.endShape();
    }

    public static void createEncloseBlock(int x, int y, int w, int h, int internalW, int internalH) {
        int mainCurve = 6;
        int connectWidth = 25;
        int connectHeight = 15;
        applet.beginShape();
        {
            applet.vertex(x + mainCurve, y);
            applet.vertex(x + mainCurve + 10, y);
            applet.vertex(x + mainCurve + 10 + connectWidth / 2, y + connectHeight);
            applet.vertex(x + mainCurve + 10 + connectWidth, y);
            applet.vertex(x + w - 1, y);
            applet.vertex(x + w, y + 1);
            applet.vertex(x + w, y + internalH - 1);
            applet.vertex(x + w - 1, y + internalH);
            applet.vertex(x + internalW + mainCurve + 10 + connectWidth, y + internalH);
            applet.vertex(x + internalW + mainCurve + 10 + connectWidth / 2, y + internalH + connectHeight);
            applet.vertex(x + internalW + mainCurve + 10, y + internalH);
            applet.vertex(x + internalW + 5, y + internalH);
            applet.vertex(x + internalW + 5, y + h - internalH);
            applet.vertex(x + w - 1, y + h - internalH);
            applet.vertex(x + w, y + h - internalH + 1);
            applet.vertex(x + w, y + h - 1);
            applet.vertex(x + w - 1, y + h);
            applet.vertex(x + mainCurve + 10 + connectWidth, y + h);
            applet.vertex(x + mainCurve + 10 + connectWidth / 2, y + h + connectHeight);
            applet.vertex(x + mainCurve + 10, y + h);
            applet.vertex(x + mainCurve, y + h);
            applet.vertex(x, y + h - mainCurve);
            applet.vertex(x, y + mainCurve);
            applet.vertex(x + mainCurve, y);
        }
        applet.endShape();
    }

    // コードオプションのリセット
    protected void resetCodeOption() {
        codeOption = null;
    }

    //描画部をここに記述
    public abstract void display();

    //コードに変換
    public abstract void exchangeCode();

    //相手のブロックとつながれるか
    public abstract boolean canConnect(Block block);

    //自分がつながるブロックか
    public abstract boolean connectable();

    //ブロックを内包
    public abstract void enterBlock(Block block);

    //ブロック内包表示を強制
    public abstract void enter();

    //ブロック内包表示
    protected abstract void enterDisplay(Block block);

    //内包ブロックを吐き出す
    public abstract void outBlock();

    //相手のブロックを中に入れられるか
    public abstract boolean canConnectElement(Block block);

    //自分がブロックに入れるか
    public abstract boolean connectableElement();

    //サイズのセット
    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
        if (parentBlock != null) parentBlock.setSize(parentBlock.getDw() + w, parentBlock.getDh());
    }

    public void setDefaultSize(int w, int h) {
        dw = w;
        dh = h;
    }

    public void resetSize() {
        w = dw;
        h = dh;
    }

    public void move(int addX, int addY) {
        x += addX;
        y += addY;
        //次のブロックが存在していればそれも合わせて移動させる
        if (postBlock != null) postBlock.move(addX, addY);
    }

    //上のブロックに対して、引数に渡されたブロックを「下」に接続します
    public void connectPostBlock(Block block) {
        if (!block.connectable()) return;
        if (this.postBlock != null) return;
        //接続関係を設定
        this.postBlock = block;
        block.prevBlock = this;

        connectDisplay(block);
    }

    public void connect() {
        if (postBlock == null) return;
        connectDisplay(postBlock);
    }

    //つながっているように見せる
    private void connectDisplay(Block block) {
        //複数ブロックの移動の時のために、指定した座標に移動させるのではなく
        //必ず移動量を指定して移動させるようにする。
        int addX = this.x - block.x;
        int addY = (this.y + this.h) - block.y;
        block.move(addX, addY);
    }

    //上にあるブロックと自分のブロックのつながりを解除
    public void disconnectPreBlock() {
        resetCodeOption();
        this.prevBlock.postBlock = null;
        this.prevBlock = null;
    }

    //マウスがブロック内にあるかどうか
    @Override
    public boolean isPressed() {
        return x <= applet.mouseX && applet.mouseX <= x + w &&
                y <= applet.mouseY && applet.mouseY <= y + h;
    }
}
