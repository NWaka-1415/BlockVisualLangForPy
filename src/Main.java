import block.Block;
import block.CanIncludeElementBlock;
import block.PrintBlockBlock;
import block.element.IntBlock;
import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    ArrayList<Block> blocks;
    Block selectedBlock;
    String code;

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    private void initialize() {
        code = "";
        blocks = new ArrayList<>();
        blocks.add(new PrintBlockBlock(this, 500, 250));
    }

    @Override
    public void setup() {
        size(1000, 500);
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
        for (int i = blocks.size() - 1; i >= 0; i--) {
            Block block = blocks.get(i);
            if (block.isPressed()) { //マウスがそのブロック内にあれば
                selectedBlock = block;
                //IncludeElementを手前にソート
                //ホントは生成時にすべき
                ArrayList<Block> tmpCanIncludeBlocks = new ArrayList<>();
                ArrayList<Block> tmpElemBlocks = new ArrayList<>();
                for (Block bl : blocks) {
                    if (bl instanceof CanIncludeElementBlock) tmpCanIncludeBlocks.add(bl);
                    else tmpElemBlocks.add(bl);
                }
                blocks = tmpCanIncludeBlocks;
                blocks.addAll(tmpElemBlocks);
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
    public void mouseReleased() {
        if (selectedBlock != null) {
            for (Block block : blocks) {
                if (selectedBlock != block && block.canConnect(selectedBlock)) {
                    block.connectPostBlock(selectedBlock);  //ブロックの接続
                }
                if (selectedBlock != block && block.canConnectElement(selectedBlock)) {
                    block.enterBlock(selectedBlock);
                }
            }

            if (selectedBlock.prevBlock != null) { //選択しているブロックの上でほかのぶとっくとつながっていて
                if (!selectedBlock.prevBlock.canConnect(selectedBlock)) {  //それがつながる位置にいなければ
                    selectedBlock.disconnectPreBlock(); //ブロックのつながりを解除
                }
            }
            //選択しているブロックの親が内包可能ブロックである
            if (selectedBlock.parentBlock instanceof CanIncludeElementBlock) {
                if (!selectedBlock.parentBlock.canConnectElement(selectedBlock)) {
                    selectedBlock.parentBlock.outBlock();
                }
            }
            selectedBlock.connect();
            changeCode();

            selectedBlock = null;   //ドラッグ終了のため選択されたブロックは必ずnullに
        }
    }

    //キーボードが押されている間呼ばれる関数
    public void keyPressed() {
        if (key == 'p') blocks.add(new PrintBlockBlock(this, mouseX, mouseY));
        else if (key == 'i') blocks.add(new IntBlock(this, mouseX, mouseY));
    }

    private void changeCode() {
        ArrayList<Block> topBlocks = new ArrayList<>();//一応複数想定
        for (Block block : blocks) {
            if (block.prevBlock == null && block.parentBlock == null) {
                topBlocks.add(block);
            }
        }
        code = "#code-----------------\n";
        for (Block topBlock : topBlocks) {
            code += topBlock.code();
        }
        System.out.println(code);
    }
}
