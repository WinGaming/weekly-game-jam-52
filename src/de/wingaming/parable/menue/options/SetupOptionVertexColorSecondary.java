package de.wingaming.parable.menue.options;

import de.wingaming.parable.menue.UIStartOptions;
import de.wingaming.parable.menue.background.VertexBackground;
import javafx.scene.paint.Color;

public class SetupOptionVertexColorSecondary extends OptionValue {

	public SetupOptionVertexColorSecondary() {
		super("Background-Secondary", 0, "darkblue", "mediumblue", "white", "black");
	}
	
	public void onValueChanged() {
		Color preColor = VertexBackground.SECONDARY;
		
		onForcedChanged();
		
		if (!preColor.equals(VertexBackground.SECONDARY)) UIStartOptions.INSTANCE.setComboToCustom();
	}
	
	public void onForcedChanged() {
		String value = getCurrentOption();
		
		if (value.equals("darkblue")) VertexBackground.SECONDARY = Color.DARKSLATEBLUE;
		if (value.equals("mediumblue")) VertexBackground.SECONDARY = Color.MEDIUMSLATEBLUE;
		if (value.equals("white")) VertexBackground.SECONDARY = Color.WHITE;
		if (value.equals("black")) VertexBackground.SECONDARY = Color.BLACK;
	}
}
