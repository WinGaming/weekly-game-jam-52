package de.wingaming.parable.menue;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.wingaming.parable.Main;
import de.wingaming.parable.input.Mouse;
import de.wingaming.parable.input.MouseListener;
import de.wingaming.parable.menue.background.VertexBackground;
import de.wingaming.parable.menue.options.Option;
import de.wingaming.parable.menue.options.SetupOptionColorCombo;
import de.wingaming.parable.menue.options.SetupOptionColorFont;
import de.wingaming.parable.menue.options.SetupOptionVertexColor;
import de.wingaming.parable.menue.options.SetupOptionVertexColorSecondary;
import de.wingaming.parable.utils.TextUtils;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIStartOptions implements UI, MouseListener {

	public static final UIStartOptions INSTANCE = new UIStartOptions();
	public static Color MENUE_TEXT = Color.LIGHTBLUE;
	
	private List<Option> options = new ArrayList<>();
//	private VertexBackground background = new VertexBackground();
	
	private SetupOptionColorFont optionColorFont = new SetupOptionColorFont();
	private SetupOptionVertexColor optionColorPrimary = new SetupOptionVertexColor();
	private SetupOptionVertexColorSecondary optionColorSecondary = new SetupOptionVertexColorSecondary();
	private SetupOptionColorCombo optionColorCombo = new SetupOptionColorCombo();
	
	public UIStartOptions() {
		options.add(optionColorFont);
		options.add(optionColorPrimary);
		options.add(optionColorSecondary);
		options.add(optionColorCombo);
		
		Mouse.registerListener(this);
		
		new Thread(new Runnable() {
			
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				optionColorCombo.onForcedChanged();
			}
		}).start();
	}
	
	public void setColor(int i1, int i2, int i3) {
		optionColorFont.setIndex(i1);
		optionColorPrimary.setIndex(i2);
		optionColorSecondary.setIndex(i3);
	}
	
	public void forceColor(int i1, int i2, int i3) {
		optionColorFont.forceIndex(i1);
		optionColorPrimary.forceIndex(i2);
		optionColorSecondary.forceIndex(i3);
	}
	
	public void setComboToCustom() {
		optionColorCombo.setIndex(0);
	}
	
	public void update() {
		VertexBackground.GLOBAL_INSTANCE.update();

		Point p = MouseInfo.getPointerInfo().getLocation();
		for (int i = 0; i < options.size(); i++) {
			Option option = options.get(i);
			boolean hover = p.getX() - Main.window.getX() >= 0 && p.getX() - Main.window.getX() <= Main.WIDTH && p.getY() - Main.window.getY() >= 150 + 45*i && p.getY() - Main.window.getY() <= 150 + 45*(i+1);
			option.update(hover);
		}
	}

	public void render() {
		//background
		VertexBackground.GLOBAL_INSTANCE.render();
		
		//Title
		Main.gc.setFill(UIStartOptions.MENUE_TEXT);
		Main.gc.setFont(Font.font(Main.fontName, 50));
		Main.gc.fillText("SETUP", Main.WIDTH/2-TextUtils.getWidth("SETUP", 50)/2+25, 75);
		
		//options
		Point p = MouseInfo.getPointerInfo().getLocation();
		for (int i = 0; i < options.size(); i++) {
			Option option = options.get(i);
			boolean hover = p.getX() - Main.window.getX() >= 0 && p.getX() - Main.window.getX() <= Main.WIDTH && p.getY() - Main.window.getY() >= 150 + 45*i && p.getY() - Main.window.getY() <= 150 + 45*(i+1);
			
			Main.gc.save();
			Main.gc.translate(0, 150 + 45*i);
			option.render(hover);
			Main.gc.restore();
		}
		
		//Play
		Main.gc.setFill(UIStartOptions.MENUE_TEXT);
		Main.gc.setFont(Font.font(Main.fontName, 100));
		Main.gc.fillText("BACK", Main.WIDTH/2-TextUtils.getWidth("BACK", 100)/2-25, Main.HEIGHT - 50);
		
		double playWidth = TextUtils.getWidth("BACK", 100);
		double playX = Main.WIDTH/2-playWidth/2-25;
		double px = p.getX() - Main.window.getX();
		double py = p.getY() - Main.window.getY();
		
		if (px >= playX && px <= playX + playWidth && py >= Main.HEIGHT - 150 && py < Main.HEIGHT - 50) {
			Main.gc.fillRect(playX, Main.HEIGHT - 40, playWidth, 5);
		}
	}
	
	public void resize() {
		
	}
	
	public void onRelease() {
		if (Main.renderer.getCurrentUI() != this) return;
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		double x = p.getX() - Main.window.getX();
		double y = p.getY() - Main.window.getY();
		
		if (x < 0 || x > Main.WIDTH || y < 0 || y > Main.HEIGHT) return;
		
		//Get option
		for (int i = 0; i < options.size(); i++) {
			if (y >= 150 + 45*i && y <= 150 + 45*(i+1)) {
				//Test left arrow
				Option option = options.get(i);
				
				if (option != null) option.click(x, y - 150 + 45*i);
			}
		}
		
		double playWidth = TextUtils.getWidth("BACK", 100);
		double playX = Main.WIDTH/2-playWidth/2-25;
		
		if (x >= playX && x <= playX + playWidth && y >= Main.HEIGHT - 150 && y < Main.HEIGHT - 50) {
			Main.renderer.setCurrentUI(UIMainMenue.INSTANCE);
		}
	}
	
	public void onPress() {}
	
	public static void a() {}
}