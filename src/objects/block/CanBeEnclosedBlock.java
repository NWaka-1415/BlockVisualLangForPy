package objects.block;

public abstract class CanBeEnclosedBlock extends Block {
    public CanBeEnclosedBlock(String name, int x, int y, int w, int h) {
        super(name, x, y, w, h);
    }

    public CanBeEnclosedBlock(String name, int x, int y, int w) {
        super(name, x, y, w);
    }

    public CanBeEnclosedBlock(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public void display() {

    }

    @Override
    public void exchangeCode() {

    }

    @Override
    public boolean canConnect(Block block) {
        return false;
    }

    @Override
    public boolean connectable() {
        return false;
    }

    @Override
    public void enterBlock(Block block) {

    }

    @Override
    public void enter() {

    }

    @Override
    protected void enterDisplay(Block block) {

    }

    @Override
    public void outBlock() {

    }

    @Override
    public boolean canConnectElement(Block block) {
        return false;
    }

    @Override
    public boolean connectableElement() {
        return false;
    }
}
