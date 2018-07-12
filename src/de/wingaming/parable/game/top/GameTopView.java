package de.wingaming.parable.game.top;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.TileType;
import de.wingaming.parable.game.World;
import de.wingaming.parable.input.KeyboardManager;
import de.wingaming.parable.io.Loader;
import de.wingaming.parable.menue.UI;
import de.wingaming.parable.menue.background.VertexBackground;
import de.wingaming.parable.utils.Location;
import javafx.scene.input.KeyCode;

public class GameTopView implements UI {
	
	public static GameTopView INSTANCE = new GameTopView();
	
	private World world;
	private Location location;
	
	public GameTopView() {
		world = Loader.loadWorld("world");
	}
	
	public void update() {
		if (World.TILE_SIZE != 32) World.TILE_SIZE = 32;
		
		if (KeyboardManager.isDown(KeyCode.Z)) {
			KeyboardManager.release(KeyCode.Z);
			
			Loader.saveWorld("world", world);
		}
		
		if (KeyboardManager.isDown(KeyCode.G)) {
			KeyboardManager.release(KeyCode.G);
			
			TileType.next();
		}
	}
	
	public void render() {
		Main.gc.setFill(VertexBackground.PRIMARY);
		Main.gc.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		world.render();
		
		Main.gc.drawImage(TileType.getCurrentType().getTexture(), 10, 10);
	}

	public void resize() {
		//TODO:
	}
	
	public World getWorld() {
		return world;
	}
}
