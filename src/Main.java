import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    ArrayList<Block> blocks;
    Block selectedBlock;

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    private void initialize() {
        blocks = new ArrayList<>();
        for (int i = 0; i < 3; i++) blocks.add(new Block(this, "Test", 500, 250));
    }

    @Override
    public void setup() {
        size(800, 450);
        initialize();
    }

    @Override
    public void draw() {
        background(255);
        for (Block block : blocks) {
            block.display();
        }
    }

    //マウスが押されたときに呼ばれる関数
    public void mousePressed() {
        //ここで逆順に走査しているのはリストの後ろ側にあるブロックの方が
        //画面上では手前にあるブロックであるため
        for (int i = blocks.size() - 1; i >= 0; i--) {
            Block block = blocks.get(i);
            if (block.isPressed()) { //マウスがそのブロック内にあれば
                selectedBlock = block;
                break;
            }
        }
    }

    //マウスをドラッグしている間呼ばれる関数
    public void mouseDragged() {
        if (selectedBlock != null) {
            selectedBlock.move(mouseX - pmouseX, mouseY - pmouseY); //移動量はカーソルが前フレームから移動した量
        }
    }

    //マウスが離されたとき呼ばれる関数
    public void mouseReleased(){
        if(selectedBlock != null){
            for(Block block : blocks){
                if(selectedBlock != block &&  block.canConnect(selectedBlock)){
                    block.connectPostBlock(selectedBlock);  //ブロックの接続
                }
            }

            if(selectedBlock.prevBlock != null){ //選択しているブロックの上でほかのぶとっくとつながっていて
                if(!selectedBlock.prevBlock.canConnect(selectedBlock)){  //それがつながる位置にいなければ
                    selectedBlock.disconnectPreBlock(); //ブロックのつながりを解除
                }
            }
            selectedBlock = null;   //ドラッグ終了のため選択されたブロックは必ずnullに
        }
    }

    //キーボードが押されている間呼ばれる関数
    public void keyPressed() {
        if (key == 'p') { //キーボードの'p'が押されたら
            blocks.add(new Block(this, "print", width / 2, height / 2));    //新しいブロックを画面中央に追加
        }
    }
}
