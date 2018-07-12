package de.wingaming.parable.menue.options;

import de.wingaming.parable.menue.UIStartOptions;
import de.wingaming.parable.menue.background.VertexBackground;
import javafx.scene.paint.Color;

public class SetupOptionVertexColor extends OptionValue {

	public SetupOptionVertexColor() {
		super("Background-Primary", 0, "lime", "red", "white", "black");
	}
	
	public void onValueChanged() {
		Color preColor = VertexBackground.PRIMARY;
		
		onForcedChanged();
		
		if (!preColor.equals(VertexBackground.PRIMARY)) UIStartOptions.INSTANCE.setComboToCustom();
	}
	
	public void onForcedChanged() {
		String value = getCurrentOption();
		
		if (value.equals("lime")) VertexBackground.PRIMARY = Color.LIMEGREEN;
		if (value.equals("red")) VertexBackground.PRIMARY= Color.RED;
		if (value.equals("white")) VertexBackground.PRIMARY = Color.WHITE;
		if (value.equals("black")) VertexBackground.PRIMARY = Color.BLACK;
	}
}
