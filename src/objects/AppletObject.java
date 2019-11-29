package objects;

import objects.component.MainComponent;

public abstract class AppletObject {
    protected static MainComponent applet = null;

    public static void setApplet(MainComponent applet) {
        AppletObject.applet = applet;
    }

    public static void debugLog(String message) {
        System.out.println(message);
    }

    public static void debugLog(String message, Object... args) {
        System.out.println(String.format(message, args));
    }

    public static void debugLog(int message) {
        System.out.println(message);
    }

    public abstract void display();

    public abstract void move(int addX, int addY);

    public abstract boolean isPressed();
}
