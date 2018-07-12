package de.wingaming.parable.menue.options;

import de.wingaming.parable.menue.UIStartOptions;

public class SetupOptionColorCombo extends OptionValue {

	public SetupOptionColorCombo() {
		super("Color-Combination", 2, "custom", "green-blue", "red", "lime", "dark-blue", "white-black");
	}
	
	public void onValueChanged() {
		onForcedChanged();
	}
	
	public void onForcedChanged() {
		if (UIStartOptions.INSTANCE == null) { System.out.println("Failed to force changes: INSTANCE is null!"); return; };
		
		String value = getCurrentOption();
		
		if (value.equals("green-blue")) {UIStartOptions.INSTANCE.forceColor(1, 0, 0);}
		if (value.equals("red")) {UIStartOptions.INSTANCE.forceColor(1, 1, 3);}
		if (value.equals("lime")) {UIStartOptions.INSTANCE.forceColor(1, 0, 3);}
		if (value.equals("dark-blue")) {UIStartOptions.INSTANCE.forceColor(1, 3, 0);}
		if (value.equals("white-black")) {UIStartOptions.INSTANCE.forceColor(2, 2, 3);}
		if (value.equals("custom")) {}
	}
}
