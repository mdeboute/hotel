package hotelproject.controllers.utils;

import javafx.scene.text.Font;

public class Default {
    public static String SFPath = "file:assets/font/SF_Pro.ttf";

    public static Font getSFPro(int size) {
        return Font.loadFont(SFPath, size);
    }
}
