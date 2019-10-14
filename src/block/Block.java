package block;

import processing.core.PApplet;

public abstract class Block {
    protected PApplet applet;
    protected String name;
    protected String code;
    protected int x, y, w, h;
    public Block prevBlock;
    protected Block postBlock;

    protected final int MARGIN = 20;    //許容する二つのブロックの距離の差。適宜変更したり、xとy座標でそれぞれ分けるのもありです

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public int W() {
        return w;
    }

    public int H() {
        return h;
    }

    public String Code() {
        exchangeCode();
        return code;
    }

    public Block(PApplet applet, String name, int x, int y, int w, int h) {
        this.applet = applet;
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Block(PApplet applet, String name, int x, int y, int w) {
        this.applet = applet;
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = 30;
    }

    public Block(PApplet applet, String name, int x, int y) {
        this.applet = applet;
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = 120;
        this.h = 30;
    }

    //描画部をここに記述
    abstract public void display();

    //コードに変換
    abstract public void exchangeCode();

    public void move(int addX, int addY) {
        x += addX;
        y += addY;
        //次のブロックが存在していればそれも合わせて移動させる
        if (postBlock != null) postBlock.move(addX, addY);
    }

    //上のブロックに対して、引数に渡されたブロックを「下」に接続します
    public void connectPostBlock(Block block) {
        //接続関係を設定
        this.postBlock = block;
        block.prevBlock = this;

        //複数ブロックの移動の時のために、指定した座標に移動させるのではなく
        //必ず移動量を指定して移動させるようにする。
        int addX = this.x - block.x;
        int addY = (this.y + this.h) - block.y;
        block.move(addX, addY);
    }

    //上にあるブロックと自分のブロックのつながりを解除
    public void disconnectPreBlock() {
        this.prevBlock.postBlock = null;
        this.prevBlock = null;
    }

    //マウスがブロック内にあるかどうか
    public boolean isPressed() {
        return x <= applet.mouseX && applet.mouseX <= x + w &&
                y <= applet.mouseY && applet.mouseY <= y + h;
    }

    public abstract boolean canConnect(Block block);

    public abstract boolean connectable();
}
