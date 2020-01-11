package objects.component;

import objects.ComboBox;
import objects.IncludeField;
import objects.block.*;
import objects.block.element.BoolBlock;
import objects.block.element.ConditionalExpressionBlock;
import objects.block.element.IntBlock;
import objects.AppletObject;
import objects.TextField;
import objects.block.enums.ReturnType;
import processing.core.PApplet;
import processing.core.PSurface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainComponent extends PApplet {
    public Canvas canvas;
    private ArrayList<Block> blocks;
    private Block selectedBlock;
    private String code;
    private JTextArea codeTextArea;

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

    public void setCodeTextArea(JTextArea codeTextArea) {
        this.codeTextArea = codeTextArea;
    }

    private void initialize() {
        AppletObject.setApplet(this);
        Block.initialize(this.surface);
        TextField.initialize();
        code = "";
        blocks = new ArrayList<>();
        blocks.add(new PrintBlockBlock(250, 250,
                new ReturnType[]{ReturnType.Bool, ReturnType.Int, ReturnType.String,
                        ReturnType.Float, ReturnType.Double, ReturnType.Char}));
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
        ComboBox.selectDisplay();
//        CanBeEnclosedBlock.calculationAll();
        sortDisplay();
    }

    private void sortDisplay() {
        if (selectedBlock == null) return;
        ArrayList<Block> nIncludeBlocks = new ArrayList<>();
        if (selectedBlock instanceof IHaveIncludeField) {
            for (IncludeField includeField : ((IHaveIncludeField) selectedBlock).getIncludeFields()) {
                if (includeField.includeBlock() != null) nIncludeBlocks.add(includeField.includeBlock());
            }
        }
        selectedBlock.display();
        for (Block block : nIncludeBlocks) {
            block.display();
        }
    }

    public void setTopBlock(Block block) {
        ArrayList<Block> tmpBlocks = new ArrayList<>();
        for (Block bl : blocks) {
            if (bl != block) tmpBlocks.add(bl);
        }
        blocks = tmpBlocks;
        blocks.add(block);
    }

    //マウスが押されたときに呼ばれる関数
    public void mousePressed() {
        boolean select = false;
        for (int i = blocks.size() - 1; i >= 0; i--) {
            Block block = blocks.get(i);
            if (block.isPressed()) { //マウスがそのブロック内にあれば
                select = true;
                selectedBlock = block;
                if (block instanceof IHaveContent && ((IHaveContent) block).isPressedContent()) {
//                    AppletObject.debugLog("isPressedContent:%b", ((IHaveContent) block).isPressedContent());
                    //コンテンツ持ちブロック
                    ((IHaveContent) block).setFocusToContent();
                    selectedBlock = null;
                }

                {
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
                }

                break;
            }

        }
        if (!select) {
            //選ばれたブロックが1つもなければ
            TextField.focusOut();
            ComboBox.focusOut();
        }
        changeCode();
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
                    block.enterBlock(selectedBlock); //blockがselectedBlockを入れられるなら入れる
                }
                if (selectedBlock != block && block instanceof CanBeEnclosedBlock
                        && ((CanBeEnclosedBlock) block).ableToEnclose(selectedBlock)) {
                    ((CanBeEnclosedBlock) block).encloseBlock(selectedBlock);
                }
            }

            if (selectedBlock.prevBlock != null) { //選択しているブロックの上でほかのぶとっくとつながっていて
                if (selectedBlock.prevBlock instanceof CanBeEnclosedBlock &&
                        ((CanBeEnclosedBlock) selectedBlock.prevBlock).getEncloseBlock() == selectedBlock) {
                    if (!((CanBeEnclosedBlock) selectedBlock.prevBlock).ableToEnclose(selectedBlock)) {
                        ((CanBeEnclosedBlock) selectedBlock.prevBlock).outEnclose();
                    } else {
                        ((CanBeEnclosedBlock) selectedBlock.prevBlock).enclose();
                    }
                } else if (!selectedBlock.prevBlock.canConnect(selectedBlock)) {  //それがつながる位置にいなければ
                    selectedBlock.disconnectPreBlock(); //ブロックのつながりを解除
                }
            }
            //選択しているブロックの親が内包可能ブロックである
            if (selectedBlock.parentBlock instanceof IHaveIncludeField) {
                if (!selectedBlock.parentBlock.canConnectElement(selectedBlock)) {
                    selectedBlock.parentBlock.outBlock();
                } else {
                    selectedBlock.parentBlock.enter();
                }
            }
            selectedBlock.connect();
            CanBeEnclosedBlock.calculationAll();

            selectedBlock = null;   //ドラッグ終了のため選択されたブロックは必ずnullに
        }
    }

    //キーボードが押されている間呼ばれる関数
    public void keyPressed() {
        System.out.println(key);
        TextField.set(key);
        changeCode();
        if (key == 'p') blocks.add(new PrintBlockBlock(mouseX, mouseY,
                new ReturnType[]{ReturnType.Bool, ReturnType.Int, ReturnType.String,
                        ReturnType.Float, ReturnType.Double, ReturnType.Char}));
        else if (key == 'i') blocks.add(new IntBlock(mouseX, mouseY));
        else if (key == 'b') blocks.add(new BoolBlock(mouseX, mouseY));
        else if (key == 'c') blocks.add(new ConditionalExpressionBlock(mouseX, mouseY));
        else if (key == 'w') blocks.add(new WhileLoopBlock(mouseX, mouseY));
        switch (keyCode) {
            case BACKSPACE:
                TextField.minusSet();
                break;
            case RIGHT:
                TextField.moveTextPosition(true);
                AppletObject.debugLog("Right");
                break;
            case LEFT:
                TextField.moveTextPosition(false);
                AppletObject.debugLog("Left");
                break;
        }
    }

    private void changeCode() {
        ArrayList<Block> topBlocks = new ArrayList<>();//一応複数想定
        for (Block block : blocks) {
            if (block instanceof InputtableBlock) ((InputtableBlock) block).setContent();
            if (block.prevBlock == null && block.parentBlock == null) {
                topBlocks.add(block);
            }
        }
        code = "";
        for (Block topBlock : topBlocks) {
            code += topBlock.code();
        }
        codeTextArea.setText(code);
    }
}
