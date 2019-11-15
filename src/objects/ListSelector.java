package objects;

import java.util.ArrayList;
import java.util.Arrays;

public class ListSelector extends AppletObject {
    private ArrayList<String> contentsList;
    private int selectContentIndex;

    public ListSelector() {
        contentsList = new ArrayList<>();
        selectContentIndex = -1;
    }

    public ListSelector(String[] contentsList) {
        this.contentsList = new ArrayList<>();
        this.contentsList.addAll(Arrays.asList(contentsList));
        if (this.contentsList.size() <= 0) return;
        selectContentIndex = 0;
    }

    public ListSelector(ArrayList<String> contentsList) {
        this.contentsList = contentsList;
        if (this.contentsList.size() <= 0) return;
        selectContentIndex = 0;
    }

    public ListSelector addContent(String content) {
        contentsList.add(content);
        return this;
    }

    @Override
    public boolean isPressed() {
        return false;
    }
}
