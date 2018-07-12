package de.wingaming.parable.menue;

import java.awt.MouseInfo;
import java.awt.Point;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.side.GameSideView;
import de.wingaming.parable.input.Mouse;
import de.wingaming.parable.input.MouseListener;
import de.wingaming.parable.menue.background.VertexBackground;
import de.wingaming.parable.utils.TextUtils;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIMainMenue implements UI, MouseListener {
	
	public static final UIMainMenue INSTANCE = new UIMainMenue();
	public static Color MENUE_TEXT = Color.LIGHTBLUE;
	
//	private VertexBackground background = new VertexBackground();
	
	public UIMainMenue() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				registerAsMouseListener();
			}
		}).start();
	}
	
	private void registerAsMouseListener() {
		Mouse.registerListener(this);
	}
	
	public void update() {
		VertexBackground.GLOBAL_INSTANCE.update();
	}

	public void render() {
		//background
		VertexBackground.GLOBAL_INSTANCE.render();
		
		Main.gc.setFill(UIStartOptions.MENUE_TEXT);
		Main.gc.setFont(Font.font(Main.fontName, 50));
		
		Main.gc.fillText("Start", 30, Main.HEIGHT - 30 - 60*3);
		Main.gc.fillText("Options", 30, Main.HEIGHT - 30 - 60*2);
		Main.gc.fillText("Links", 30, Main.HEIGHT - 30 - 60);
		Main.gc.fillText("Exit", 30, Main.HEIGHT - 30);
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		double x = p.getX() - Main.window.getX();
		double y = p.getY() - Main.window.getY();
		
		if (x > 30) {
			if (y >= Main.HEIGHT - 30 - 60*3 - 50 && y <= Main.HEIGHT - 30 - 60*3 && x <= 30 + TextUtils.getWidth("Start", 50))
				Main.gc.fillRect(30, Main.HEIGHT - 30 - 60*3 + 10, TextUtils.getWidth("Start", 50), 5);
			if (y >= Main.HEIGHT - 30 - 60*2 - 50 && y <= Main.HEIGHT - 30 - 60*2 && x <= 30 + TextUtils.getWidth("Options", 50))
				Main.gc.fillRect(30, Main.HEIGHT - 30 - 60*2 + 10, TextUtils.getWidth("Options", 50), 5);
			if (y >= Main.HEIGHT - 30 - 60 - 50 && y <= Main.HEIGHT - 30 - 60 && x <= 30 + TextUtils.getWidth("Links", 50))
				Main.gc.fillRect(30, Main.HEIGHT - 30 - 60 + 10, TextUtils.getWidth("Links", 50), 5);
			if (y >= Main.HEIGHT - 30 - 50 && y <= Main.HEIGHT - 30 && x <= 30 + TextUtils.getWidth("Exit", 50))
				Main.gc.fillRect(30, Main.HEIGHT - 30 + 10, TextUtils.getWidth("Exit", 50), 5);
		}
	}
	
	public void resize() {
		
	}
	
	public void onRelease() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		double x = p.getX() - Main.window.getX();
		double y = p.getY() - Main.window.getY();
		
		if (x < 0 || x > Main.WIDTH || y < 0 || y > Main.HEIGHT) return;
		
		//Get option
		if (x > 30) {
			if (y >= Main.HEIGHT - 30 - 60*3 - 50 && y <= Main.HEIGHT - 30 - 60*3 && x <= 30 + TextUtils.getWidth("Start", 50))
				Main.renderer.setCurrentUI(GameSideView.INSTANCE);
			if (y >= Main.HEIGHT - 30 - 60*2 - 50 && y <= Main.HEIGHT - 30 - 60*2 && x <= 30 + TextUtils.getWidth("Options", 50))
				Main.renderer.setCurrentUI(UIStartOptions.INSTANCE);
			if (y >= Main.HEIGHT - 30 - 60 - 50 && y <= Main.HEIGHT - 30 - 60 && x <= 30 + TextUtils.getWidth("Links", 50))
				; //TODO: show links
			if (y >= Main.HEIGHT - 30 - 50 && y <= Main.HEIGHT - 30 && x <= 30 + TextUtils.getWidth("Exit", 50))
				System.exit(1);
		}
	}
	
	public void onPress() {}
}
