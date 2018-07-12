package de.wingaming.parable.utils;

import de.wingaming.parable.Main;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextUtils {
	
	public static double getWidth(String text, double size) {
		Text textObj = new Text(text);
        Font font = Font.font(Main.fontName, size);
        textObj.setFont(font);
        return textObj.getLayoutBounds().getWidth();
	}
	
}
