package de.wingaming.parable.menue;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.side.GameSideView;
import de.wingaming.parable.input.KeyboardManager;
import de.wingaming.parable.menue.background.LightVertexBackground;
import de.wingaming.parable.utils.TextUtils;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIGameOver implements UI {
	
	public static final UIGameOver INSTANCE = new UIGameOver();
	private static LightVertexBackground gameOverBackground = new LightVertexBackground(Color.BLACK);
	
	static {
		gameOverBackground.setOpacityBordersForVertexes(0, 0.25f);
	}
	
	public void update() {
		if (KeyboardManager.isDown(KeyCode.ENTER)) {
			KeyboardManager.release(KeyCode.ENTER);
			
			GameSideView.INSTANCE.resetPlayer();
			Main.renderer.setCurrentUI(GameSideView.INSTANCE);
		}
		
		if (KeyboardManager.isDown(KeyCode.ESCAPE)) {
			KeyboardManager.release(KeyCode.ESCAPE);
			
			Main.renderer.setCurrentUI(UIMainMenue.INSTANCE);
		}
		
		gameOverBackground.setOpacityBordersForVertexes(0, 0.5f);
		
		gameOverBackground.update();
	}

	public void render() {
		GameSideView.INSTANCE.render();

		gameOverBackground.render();
		
		Main.gc.save();
		Main.gc.setGlobalAlpha(.75);
		Main.gc.setFill(Color.BLACK);
		Main.gc.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Main.gc.restore();
		
		Main.gc.setFill(UIStartOptions.MENUE_TEXT);
		Main.gc.setFont(Font.font(Main.fontName, 100));
		Main.gc.fillText("GameOver", Main.WIDTH/2 - TextUtils.getWidth("GameOver", 100)/2, 100);

		Main.gc.setFill(UIStartOptions.MENUE_TEXT);
		Main.gc.setFont(Font.font(Main.fontName, 75));
		String gemString = "Collected gems: " + GameSideView.INSTANCE.getGemCount() + "/" + GameSideView.INSTANCE.getMaxGemCount();
		Main.gc.fillText(gemString, Main.WIDTH/2 - TextUtils.getWidth(gemString, 75)/2, Main.HEIGHT/2+75/2);
		
		Main.gc.setFont(Font.font(Main.fontName, 50));
		Main.gc.fillText("Press ENTER to continue", Main.WIDTH/2 - TextUtils.getWidth("Press ENTER to continue", 50)/2, Main.HEIGHT - 20 - 35);
		
		Main.gc.setFill(UIStartOptions.MENUE_TEXT.darker());
		Main.gc.setFont(Font.font(Main.fontName, 30));
		Main.gc.fillText("Or ESCAPE to go back to mainmenu", Main.WIDTH/2 - TextUtils.getWidth("Or ESCAPE to go back to mainmenu", 30)/2, Main.HEIGHT - 20);
	}

	public void resize() {}
}
