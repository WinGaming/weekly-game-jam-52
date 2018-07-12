package de.wingaming.parable.menue.options;

import de.wingaming.parable.Main;
import de.wingaming.parable.input.KeyboardManager;
import de.wingaming.parable.menue.UIStartOptions;
import de.wingaming.parable.menue.background.VertexBackground;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class OptionValue extends Option {

	private int index;
	private String[] values;
	
	public OptionValue(String text, int index, String ... values) {
		super(text);
		
		this.index = index;
		this.values = values;
		
		onForcedChanged();
	}
	
	public void click(double x, double ry) {
		if (x >= Main.WIDTH-30-20 && x <= Main.WIDTH-30) index++;
		if (x >= Main.WIDTH-30-20-125-125 && x <= Main.WIDTH-30-125-125) index--;
		
		if (index < 0) index = values.length-1;
		if (index >= values.length) index = 0;
		
		onValueChanged();
	}
	
	public void onValueChanged() {}
	public void onForcedChanged() {}
	
	public void update(boolean hover) {
		if (hover) {
			int originalIndex = index;
			
			if (KeyboardManager.isDown(KeyCode.RIGHT)) {
				KeyboardManager.release(KeyCode.RIGHT);
				index++;
			}
			if (KeyboardManager.isDown(KeyCode.LEFT)) {
				KeyboardManager.release(KeyCode.LEFT);
				index--;
			}
			
			if (index < 0) index = values.length-1;
			if (index >= values.length) index = 0;
			
			if (index != originalIndex) onValueChanged();
		}
	}
	
	public String getCurrentOption() {
		return values[index];
	}

	public void render(boolean hover) {
		if (hover) {
			Color primaryColor = VertexBackground.SECONDARY.brighter();
			Color hoverColor = new Color(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), .5);
			Main.gc.setFill(hoverColor);
			Main.gc.fillRect(0, 0, Main.WIDTH, 45);
		}
		
		Main.gc.setFill(UIStartOptions.MENUE_TEXT);
		Main.gc.setFont(Font.font(Main.fontName, 35));
		Main.gc.fillText(getText(), 10, 35);
		
		Text text = new Text(values[index]);
        Font font = Font.font(Main.fontName, 35);
        text.setFont(font);
        double width = text.getLayoutBounds().getWidth();
        
		Main.gc.fillPolygon(new double[] {Main.WIDTH-30-20, Main.WIDTH-30-20, Main.WIDTH-30}, new double[] {5, 40, 40/2}, 3);
		Main.gc.fillText(values[index], Main.WIDTH-30-10-125-width/2, 35);
		Main.gc.fillPolygon(new double[] {Main.WIDTH-30-20-125-125, Main.WIDTH-30-125-125, Main.WIDTH-30-125-125}, new double[] {40/2, 5, 40}, 3);
	}
	
	public void setIndex(int index) {
		this.index = index;
		
		if (index < 0) index = values.length-1;
		if (index >= values.length) index = 0;
		
		onValueChanged();
	}
	
	public void forceIndex(int index) {
		this.index = index;
		
		onForcedChanged();
	}
}
