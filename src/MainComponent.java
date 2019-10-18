import block.Block;
import block.CanIncludeElementBlock;
import block.InputtableBlock;
import block.PrintBlockBlock;
import block.element.IntBlock;
import processing.core.PApplet;
import processing.core.PSurface;

import java.awt.*;
import java.util.ArrayList;

public class MainComponent extends PApplet {
    Canvas canvas;
    ArrayList<Block> blocks;
    Block selectedBlock;
    String code;
    TextArea codeTextArea;

//    public static void main(String[] args) {
//        PApplet.main(Main.class.getName());
//    }

    public MainComponent(int w, int h) {
        PSurface surface = super.initSurface();
        surface.setSize(w, h);
        canvas = (Canvas) surface.getNative();
    }

    public void showCanvas() {
        this.startSurface();
    }

    public void setCodeTextArea(TextArea codeTextArea) {
        this.codeTextArea = codeTextArea;
    }

    private void initialize() {
        Block.initialize(this);
        code = "";
        blocks = new ArrayList<>();
        blocks.add(new PrintBlockBlock(500, 250));
    }

    @Override
    public void setup() {
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
            if (block instanceof InputtableBlock) {
                if (((InputtableBlock) block).isPressedInputField()) {
                    ((InputtableBlock) block).inputActivate();
                }
            }
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
                } else {
                    selectedBlock.parentBlock.enter();
                }
            }
            selectedBlock.connect();
            changeCode();

            selectedBlock = null;   //ドラッグ終了のため選択されたブロックは必ずnullに
        }
    }

    //キーボードが押されている間呼ばれる関数
    public void keyPressed() {
        if (key == 'p') blocks.add(new PrintBlockBlock(mouseX, mouseY));
        else if (key == 'i') blocks.add(new IntBlock(mouseX, mouseY));
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
        codeTextArea.setText(code);
    }
}
