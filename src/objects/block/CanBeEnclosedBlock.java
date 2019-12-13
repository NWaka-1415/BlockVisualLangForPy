package objects.block;

public abstract class CanBeEnclosedBlock extends Block {
    //中に入っているブロック一個目
    protected Block encloseBlock;

    public CanBeEnclosedBlock(String name, int x, int y, int w, int h) {
        super(name, x, y, w, h);
    }

    public CanBeEnclosedBlock(String name, int x, int y, int w) {
        super(name, x, y, w);
    }

    public CanBeEnclosedBlock(String name, int x, int y) {
        super(name, x, y);
    }

    public void encloseBlock(Block block){
        if(!block.connectable()) return;
        if(this.encloseBlock!=null) return;
        this.encloseBlock=block;
        block.prevBlock=this;


    }

    private void encloseDisplay(Block block){
        int addX=this.x
    }

    @Override
    public void display() {

    }
}
