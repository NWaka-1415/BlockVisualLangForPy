package objects.block.codeOption;

public class Nest implements ICodeOption {

    private int nest;

    private final String nestStr = "    ";

    public Nest(int nest) {
        this.nest = nest;
    }

    @Override
    public String option() {
        StringBuilder option = new StringBuilder();
        for (int i = 0; i < nest; i++) {
            option.append(nestStr);
        }
        return option.toString();
    }
}
