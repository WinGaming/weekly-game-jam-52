package de.wingaming.parable.game.side;

import java.util.ArrayList;
import java.util.List;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.TileType;
import de.wingaming.parable.game.World;
import de.wingaming.parable.game.side.entity.VertexEntity;
import de.wingaming.parable.input.KeyboardManager;
import de.wingaming.parable.io.Loader;
import de.wingaming.parable.menue.UI;
import de.wingaming.parable.utils.Location;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameSideView implements UI {
	
	public static GameSideView INSTANCE = new GameSideView();
	
	private World world;
	private Player player;
	private List<VertexEntity> entities = new ArrayList<>();
	
	private Location defaultPlayer = new Location(500, 100);
	private Location lastCheckpoint;
	
	public GameSideView() {
		world = Loader.loadWorld("side");
		player = new Player(defaultPlayer);
		lastCheckpoint = defaultPlayer;
		
//		entities.add(new EntityCubed(new Location(200, 200)));
	}
	
	public void update() {
		player.update(world);
		
		for (VertexEntity vertexEntity : entities) {
			vertexEntity.update(world);
		}
		
		if (World.TILE_SIZE != 48) World.TILE_SIZE = 48;
		
		if (Main.DEBUG) {
			if (KeyboardManager.isDown(KeyCode.Z)) {
				KeyboardManager.release(KeyCode.Z);
				
				Loader.saveWorld("side", world);
			}
			
			if (KeyboardManager.isDown(KeyCode.G)) {
				KeyboardManager.release(KeyCode.G);
				
				TileType.next();
			}
		}
		
		if (player.getLocation().getY() > 50 * World.TILE_SIZE) player = new Player(lastCheckpoint);
	}
	
	public void render() {
		Main.gc.setFill(Color.DEEPSKYBLUE);
		Main.gc.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		world.render();
		
		for (VertexEntity vertexEntity : entities) {
			vertexEntity.render();
		}

		Main.gc.setFill(Color.BLACK);
		Main.gc.setFont(Font.font(Main.fontName, 40));
		Main.gc.fillText("Use LEFT and RIGHT to move", 220 - world.getCamera().getX(), 230 - world.getCamera().getY());
		Main.gc.fillText("Use SPACE to jump", 770 - world.getCamera().getX(), 575 - world.getCamera().getY());
		Main.gc.fillText("But be careful:", 1460 - world.getCamera().getX(), 230 - world.getCamera().getY());
		Main.gc.fillText(" You have less control over your speed in the air", 1180 - world.getCamera().getX(), 275 - world.getCamera().getY());
		
//		Main.gc.fillText("This is an BETA-Version an can contain bugs", Main.WIDTH/2 - TextUtils.getWidth("This is an BETA-Version an can contain bugs", 40)/2, 40);
		Main.gc.fillText("This is an BETA-Version an can contain bugs", 0-world.getCamera().getX(), Main.HEIGHT);
		
//		Render player
		{
			Main.gc.setFill(Color.WHITE);
			Location pos = player.getLocation();
			Main.gc.fillRoundRect(pos.getX() - world.getCamera().getX(), pos.getY() - world.getCamera().getY(), player.getSize(), player.getSize(), 300, 300);
		}
		
		if (Main.DEBUG) Main.gc.drawImage(TileType.getCurrentType().getTexture(), 10, 10);
	}

	public void spawnEntity(VertexEntity entity) {
		entities.add(entity);
	}
	
	public void resize() {
		//TODO:
	}
	
	public World getWorld() {
		return world;
	}
	
	public Location getLastCheckpoint() {
		return lastCheckpoint;
	}
}
