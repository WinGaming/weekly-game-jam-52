package de.wingaming.parable.menue.options;

import de.wingaming.parable.menue.UIStartOptions;
import javafx.scene.paint.Color;

public class SetupOptionColorFont extends OptionValue {

	public SetupOptionColorFont() {
		super("Font-Color", 2, "lightblue", "white", "darkblue", "black");
	}
	
	public void onValueChanged() {
		Color preColor = UIStartOptions.MENUE_TEXT;
		
		onForcedChanged();
		
		if (!preColor.equals(UIStartOptions.MENUE_TEXT)) UIStartOptions.INSTANCE.setComboToCustom();
	}
	
	public void onForcedChanged() {
		String value = getCurrentOption();
		
		if (value.equals("lightblue")) UIStartOptions.MENUE_TEXT = Color.LIGHTBLUE;
		if (value.equals("white")) UIStartOptions.MENUE_TEXT = Color.WHITE;
		if (value.equals("darkblue")) UIStartOptions.MENUE_TEXT = Color.DARKBLUE;
		if (value.equals("black")) UIStartOptions.MENUE_TEXT = Color.BLACK;
	}
}
