import processing.core.PApplet;
import processing.core.PConstants;

public class Block {
    private PApplet applet;
    private String name;
    private int x, y, w, h;
    Block prevBlock;
    private Block postBlock;

    private final int MARGIN = 20;    //許容する二つのブロックの距離の差。適宜変更したり、xとy座標でそれぞれ分けるのもありです

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

    Block(PApplet applet, String name, int x, int y) {
        this.applet = applet;
        this.name = name;
        this.x = x;
        this.y = y;
        w = 120;
        h = 30;    //現状は決め打ちで
    }

    //描画部をここに記述
    void display() {
        applet.fill(241, 196, 15);   //塗りつぶしの色を黄色に
        applet.rect(x, y, w, h, 15);    //最後の引数は矩形の角の曲がり具合

        applet.fill(0);    //文字色を黒色に
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
        applet.strokeWeight(3);
        applet.stroke(243, 156, 18);
        applet.textSize(20);
        applet.text(name, x + w / 2, y + h / 3);
    }

    void move(int addX, int addY) {
        x += addX;
        y += addY;
        //次のブロックが存在していればそれも合わせて移動させる
        if (postBlock != null) postBlock.move(addX, addY);
    }

    //上のブロックに対して、引数に渡されたブロックを「下」に接続します
    void connectPostBlock(Block block) {
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
    void disconnectPreBlock() {
        this.prevBlock.postBlock = null;
        this.prevBlock = null;
    }

    //マウスがブロック内にあるかどうか
    boolean isPressed() {
        return x <= applet.mouseX && applet.mouseX <= x + w &&
                y <= applet.mouseY && applet.mouseY <= y + h;
    }

    boolean canConnect(Block block) {
        int bx = block.x;
        int by = block.y;
        //abs()は引数の絶対値を返す関数
        //yに関しては相手のブロックの「底」と比較するためにhを足すのを忘れないように
        return PApplet.abs(x - bx) <= MARGIN && PApplet.abs(y + h - by) <= MARGIN;
    }
}
