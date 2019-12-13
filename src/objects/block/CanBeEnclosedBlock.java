package objects.block;

public abstract class CanBeEnclosedBlock extends Block {
    //中に入っているブロック一個目
    protected Block encloseBlock;
    private int topBlockH, encloseX, encloseY;

    public int getEncloseX() {
        return encloseX;
    }

    public int getEncloseY() {
        return encloseY;
    }

    public int getTopBlockH() {
        return topBlockH;
    }

    public CanBeEnclosedBlock(String name, int x, int y, int w, int h) {
        super(name, x, y, w, h);
    }

    public CanBeEnclosedBlock(String name, int x, int y, int w) {
        super(name, x, y, w);
    }

    public CanBeEnclosedBlock(String name, int x, int y) {
        super(name, x, y);
    }

    public void encloseBlock(Block block) {
        if (!block.connectable()) return;
        if (this.encloseBlock != null) return;
        this.encloseBlock = block;
        block.prevBlock = this;
    }

    private void encloseDisplay(Block block) {
        int addX = this.x - block.x;
        int addY = (this.y + this.h) - block.y;
        block.move(addX, addY);
    }

    @Override
    public void move(int addX, int addY) {
        super.move(addX, addY);
        encloseX += addX;
        encloseY += addY;
    }

    @Override
    public void display() {
        if (encloseBlock != null) {
            int addH = 0;
            Block block = encloseBlock;
            while (block != null) {
                addH += block.h;
                block = block.postBlock;
            }
            h = getDh() + addH;
        }
    }
}
