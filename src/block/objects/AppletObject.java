package block.objects;

import processing.core.PApplet;

public abstract class AppletObject {
    protected static PApplet applet = null;

    public static void setApplet(PApplet applet) {
        AppletObject.applet = applet;
    }
}
