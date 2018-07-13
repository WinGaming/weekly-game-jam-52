package de.wingaming.parable.menue;

import java.awt.MouseInfo;
import java.awt.Point;

import de.wingaming.parable.Main;
import de.wingaming.parable.input.KeyboardManager;
import de.wingaming.parable.input.Mouse;
import de.wingaming.parable.input.MouseListener;
import de.wingaming.parable.menue.background.VertexBackground;
import de.wingaming.parable.utils.TextUtils;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

public class UIExtras implements UI, MouseListener {
	
	public static final UIExtras INSTANCE = new UIExtras();
	
	public UIExtras() {
		Mouse.registerListener(this);
	}

	public void update() {
		if (KeyboardManager.isDown(KeyCode.ESCAPE)) {
			KeyboardManager.release(KeyCode.ESCAPE);
			
			Main.renderer.setCurrentUI(UIMainMenue.INSTANCE);
			return;
		}
		
		VertexBackground.GLOBAL_INSTANCE.update();
	}
	
	public void render() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		double x = p.getX() - Main.window.getX();
		double y = p.getY() - Main.window.getY();
		
		VertexBackground.GLOBAL_INSTANCE.render();
		
		Main.gc.setFill(UIStartOptions.MENUE_TEXT);
		Main.gc.setFont(Font.font(Main.fontName, 45));
		
		Main.gc.fillRect(Main.WIDTH/2-2.5, 30, 5, Main.HEIGHT-60);

		Main.gc.fillText("Back to Mainmenu", Main.WIDTH/4*3 - TextUtils.getWidth("Back to Mainmenu", 45)/2, 100);
		Main.gc.fillText("Sourcecode", Main.WIDTH/4 - TextUtils.getWidth("Sourcecode", 45)/2, 50);
		Main.gc.fillText("Project", Main.WIDTH/4 - TextUtils.getWidth("Project", 45)/2, 400);		
		Main.gc.fillText("Matrices of entities", Main.WIDTH/4*3 - TextUtils.getWidth("Matrices of entities", 45)/2, 200);
		
		//Sourcecode
		Main.gc.setFont(Font.font(Main.fontName, 35));
		Main.gc.fillText("Week 52 - Boss Rush", 10, 150);
		Main.gc.fillText("Week 51 - From Zero to Hero", 10, 195);
		Main.gc.fillText("Week 50 - Remake a Classic", 10, 240);
		Main.gc.fillText("Week 43 - Your Brother's Birthday", 10, 285);
		
		//Project
		Main.gc.fillText("Week 52 - Boss Rush", 10, 350+150);
		Main.gc.fillText("Week 51 - From Zero to Hero", 10, 350+195);
		Main.gc.fillText("Week 50 - Remake a Classic", 10, 350+240);
		Main.gc.fillText("Week 43 - Your Brother's Birthday", 10, 350+285);
		
		if (x >= Main.WIDTH/4*3 - TextUtils.getWidth("Back to Mainmenu", 45)/2 && x <= Main.WIDTH/4*3 + TextUtils.getWidth("Back to Mainmenu", 45)/2) {
			if (y >= 100-45 && y <= 100) {
				Main.gc.fillRect(Main.WIDTH/4*3 - TextUtils.getWidth("Back to Mainmenu", 45)/2, 110, TextUtils.getWidth("Back to Mainmenu", 45), 10);
			}
		}
	}
	
	public void resize() {}
	
	public void onPress() {}
	public void onRelease() {
		if (Main.renderer.getCurrentUI() != this) return;
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		double x = p.getX() - Main.window.getX();
		double y = p.getY() - Main.window.getY();
		
		if (x >= Main.WIDTH/4*3 - TextUtils.getWidth("Back to Mainmenu", 45)/2 && x <= Main.WIDTH/4*3 + TextUtils.getWidth("Back to Mainmenu", 45)/2) {
			if (y >= 100-45 && y <= 100) {
				Main.renderer.setCurrentUI(UIMainMenue.INSTANCE);
			}
		}
	}
	
	public static void a() {}
}
