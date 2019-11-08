package block.objects;

import processing.core.PConstants;

public class TextField extends AppletObject {
    private String text;
    int x, y, w, h;

    public String getText() {
        return text;
    }

    public void draw() {
        applet.fill(0);
        applet.textAlign(PConstants.CENTER, PConstants.CENTER);  //テキストの描画位置をx,yともに真ん中に
        applet.textSize(20);
        applet.text(text, x + w / 2, y + h / 2);
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextField(String text) {
        this.text = text;
        this.x = 0;
        this.y = 0;
        this.w = 10;
        this.h = 10;
    }

    public TextField(String text, int x, int y, int w, int h) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void setSize(int w,int h){

    }

    public void move(int addX,int addY){
        
    }
}
