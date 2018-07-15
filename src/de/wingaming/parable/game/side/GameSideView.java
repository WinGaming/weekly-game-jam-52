package de.wingaming.parable.game.side;

import java.util.ArrayList;
import java.util.List;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.TileType;
import de.wingaming.parable.game.World;
import de.wingaming.parable.game.side.entity.EntityMiniTriangle;
import de.wingaming.parable.game.side.entity.EntityPortal;
import de.wingaming.parable.game.side.entity.EntityTriangle;
import de.wingaming.parable.game.side.entity.VertexEntity;
import de.wingaming.parable.game.side.entity.weapon.EntityProjectile;
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
	private List<VertexEntity> toSpawn = new ArrayList<>();
	private List<VertexEntity> toRemove = new ArrayList<>();
	
	private List<Gem> gems = new ArrayList<>();
	private List<Gem> collectedGems = new ArrayList<>();
	
	private Location defaultPlayer = new Location(500, 100);
	private Location lastCheckpoint;
	
	private int gemCount;
	private int maxGems;
	
	public GameSideView() {
		world = Loader.loadWorld("side");
		player = new Player(defaultPlayer);
		lastCheckpoint = defaultPlayer;
		
		EntityPortal firstPortal = new EntityPortal(new Location(1776.25, 501));
		EntityPortal cavePortal = new EntityPortal(new Location(4687, 404.75));
		firstPortal.setDestiny(cavePortal);
		cavePortal.setDestiny(firstPortal);
		
		spawnEntity(firstPortal);
		spawnEntity(cavePortal);
		
		EntityPortal caveEndPortal = new EntityPortal(new Location(4951, 981));
		EntityPortal bossOneInPortal = new EntityPortal(new Location(6300, 300));
		caveEndPortal.setDestiny(bossOneInPortal);
		caveEndPortal.setSetCheckpoint(true);
		spawnEntity(caveEndPortal);
		spawnEntity(bossOneInPortal);
		
		EntityPortal bossOneEndPortal = new EntityPortal(new Location(7590, 500));
		EntityPortal finalPortal = new EntityPortal(new Location(7758, 500));
		bossOneEndPortal.setDestiny(finalPortal);
		bossOneEndPortal.setSetCheckpoint(true);
		spawnEntity(bossOneEndPortal);
		spawnEntity(finalPortal);
		
		//Enemies:
		spawnEntity(new EntityTriangle(new Location(4200, 450)));
		spawnEntity(new EntityMiniTriangle(new Location(6864, 250)));
		spawnEntity(new EntityMiniTriangle(new Location(7064, 250)));
		spawnEntity(new EntityMiniTriangle(new Location(7264, 250)));
		
		replaceGems();
	}
	
	private void replaceGems() {
		gems.clear();
		collectedGems.clear();
		gemCount = 0;
		
		gems.add(new Gem(Gem.GEM_GREEN, new Location(4247.5, 1040)));
		gems.add(new Gem(Gem.GEM_GREEN, new Location(4297.5, 1000)));
		gems.add(new Gem(Gem.GEM_GREEN, new Location(4345.5, 1000)));
		gems.add(new Gem(Gem.GEM_GREEN, new Location(4395.5, 1040)));
		
		gems.add(new Gem(Gem.GEM_BLUE, new Location(6625, 417)));
		gems.add(new Gem(Gem.GEM_BLUE, new Location(6672.5, 417)));
		gems.add(new Gem(Gem.GEM_BLUE, new Location(6721, 417)));
		
		gems.add(new Gem(Gem.GEM_BLUE, new Location(6891, 417)));
		
		gems.add(new Gem(Gem.GEM_BLUE, new Location(7035, 417)));
		gems.add(new Gem(Gem.GEM_BLUE, new Location(7085, 417)));
		
		gems.add(new Gem(Gem.GEM_BLUE, new Location(8710, 320)));
		
		gems.add(new Gem(Gem.GEM_RED, new Location(8037, 80)));
		
		maxGems = gems.size();
	}
	
	@SuppressWarnings("unused")
	public void update() {
//		Buggy:
//		player.setSize(20 + gemCount);
		player.setSize(30);
		player.update(world);
		
		if (Main.DEBUG && KeyboardManager.isDown(KeyCode.H)) {
			KeyboardManager.release(KeyCode.H);
			
			replaceGems();
		}
		
		for (VertexEntity vertexEntity : toSpawn) {
			entities.add(vertexEntity);
		}
		toSpawn.clear();

		for (VertexEntity vertexEntity : toRemove) {
			entities.remove(vertexEntity);
		}
		toRemove.clear();
		
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
		
		if (player.getLocation().getY() > 50 * World.TILE_SIZE) player.setLocation(lastCheckpoint);
		
		for (Gem gem : gems) {
			if (collectedGems.contains(gem)) continue;
			
			double a = Math.max(player.getLocation().getX(), gem.getLocation().getX()) - Math.min(player.getLocation().getX(), gem.getLocation().getX());
			double b = Math.max(player.getLocation().getY(), gem.getLocation().getY()) - Math.min(player.getLocation().getY(), gem.getLocation().getY());
			double c = Math.sqrt(a*a + b*b);
			
			if (c <= player.getSize()/2 + 10) {
				gemCount++;
				collectedGems.add(gem);
				
				double factor = 1;
				if (gemCount > 4) factor = 1.5;
				
				player.setMaxHealth(player.getMaxHealth() + factor);
				player.setHealth(player.getHealth() + factor);
			}
		}
	}
	
	public List<VertexEntity> getEntitiesCollidingAt(Location location) {
		List<VertexEntity> result = new ArrayList<>();
		for (VertexEntity vertexEntity : entities) {
			if (vertexEntity.collide(location.getX(), location.getY())) result.add(vertexEntity);
		}
		
		return result;
	}
	
	public void render() {
		Main.gc.setFill(Color.DEEPSKYBLUE);
		Main.gc.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		world.render();
		
		for (VertexEntity vertexEntity : entities) {
			vertexEntity.render(world.getCamera());
		}

		Main.gc.setFill(Color.BLACK);
		Main.gc.setFont(Font.font(Main.fontName, 40));
		Main.gc.fillText("Use LEFT and RIGHT to move", 220 - world.getCamera().getX(), 230 - world.getCamera().getY());
		Main.gc.fillText("Use SPACE to jump", 770 - world.getCamera().getX(), 575 - world.getCamera().getY());
		Main.gc.fillText("But be careful:", 1460 - world.getCamera().getX(), 230 - world.getCamera().getY());
		Main.gc.fillText(" You have less control over your speed in the air", 1180 - world.getCamera().getX(), 275 - world.getCamera().getY());
		
//		Main.gc.fillText("This is an BETA-Version an can contain bugs", Main.WIDTH/2 - TextUtils.getWidth("This is an BETA-Version an can contain bugs", 40)/2, 40);
		Main.gc.fillText("This is an BETA-Version an can contain bugs", 0-world.getCamera().getX(), Main.HEIGHT);
		Main.gc.fillText("Your health -> ", Main.WIDTH - 400 -world.getCamera().getX(), 50);
		Main.gc.fillText("Hooray! Portals! :)", 1825-world.getCamera().getX(), 490);
		
		Main.gc.setFill(Color.WHITE);
		Main.gc.fillText("ss R to shoot", 4417.5 - world.getCamera().getX(), 526 - world.getCamera().getY());
		Main.gc.fillText("Pre", 4357 - world.getCamera().getX(), 526 - world.getCamera().getY());
		
		Main.gc.setFont(Font.font(Main.fontName, 25));
		Main.gc.fillText("Warning: You can hit yourself", 4345 - world.getCamera().getX(), 490 - world.getCamera().getY());
		
		Main.gc.setFill(Color.DEEPSKYBLUE);
		Main.gc.setFont(Font.font(Main.fontName, 40));
		Main.gc.fillText("Collect gems to deal more damage and increace your health", 3890 - world.getCamera().getX(), 1050 - world.getCamera().getY());
		
		Main.gc.setFill(Color.WHITE);
		Main.gc.setFont(Font.font(Main.fontName, 50));
		Main.gc.fillText("Sorry...", 6440 - world.getCamera().getX(), 360 - world.getCamera().getY());
		
		Main.gc.setFill(Color.BLACK);
		Main.gc.setFont(Font.font(Main.fontName, 40));
		Main.gc.fillText("       Thanks for playing!\nYou collected " + gemCount + "/" + maxGems + " gems", 8340 - world.getCamera().getX(), 60 - world.getCamera().getY());
		
//		Render player
		{
			double redFactor = 0;
			
			{
				long sinceLastHit = System.currentTimeMillis() - player.getLastHit();
				if (sinceLastHit < 500) {
					double sinceD = (double) sinceLastHit;
					if (sinceD > 250) sinceD = 500 - sinceD;
					double factor = sinceD/250;
					redFactor = factor * .5;
				}
			}
			
			Color playerColor = new Color(Color.WHITE.getRed(), Color.WHITE.getGreen() - redFactor, Color.WHITE.getBlue() - redFactor, 1);
			Main.gc.setFill(playerColor);
			Location pos = player.getLocation();
			Main.gc.fillRoundRect(pos.getX() - world.getCamera().getX() - player.getSize()/2, pos.getY() - world.getCamera().getY() - player.getSize()/2, player.getSize(), player.getSize(), 300, 300);
		}
		
		if (Main.DEBUG) Main.gc.drawImage(TileType.getCurrentType().getTexture(), 10, 10);
		
		for (Gem gem : gems) {
			if (collectedGems.contains(gem)) continue;
			
			Main.gc.drawImage(gem.getTexture(), gem.getLocation().getX() - 10 - world.getCamera().getX(), gem.getLocation().getY() - 10 - world.getCamera().getY(), 20, 20);
		}
		
		//Rendering UI
		Main.gc.setFill(Color.FORESTGREEN);
		for (int i = 0; i < player.getHealth(); i++) {
			Main.gc.fillRect(Main.WIDTH - 25*(i+1), 10, 15, 40);
		}
		Main.gc.setFill(Color.DARKRED);
		for (int i = 0; i < player.getMaxHealth() - player.getHealth(); i++) {
			Main.gc.fillRect(Main.WIDTH - (25*(player.getHealth())) - 25*(i+1), 10, 15, 40);
		}
	}

	public void spawnEntity(VertexEntity entity) {
		toSpawn.add(entity);
	}
	
	public void killEntity(VertexEntity entity) {
		toRemove.add(entity);
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
	
	public Player getPlayer() {
		return player;
	}
	
	public static void reset() {
		INSTANCE = new GameSideView();
	}
	
	public void resetPlayer() {
		world.getCamera().setX(0);
		world.getCamera().setY(0);
		
		player.setLocation(lastCheckpoint);
		player.setHealth(player.getMaxHealth());
		
		for (VertexEntity vertexEntity : entities) {
			if (vertexEntity instanceof EntityProjectile) {
				killEntity(vertexEntity);
			}
		}
	}
	
	public int getGemCount() {
		return gemCount;
	}
	
	public int getMaxGemCount() {
		return maxGems;
	}
	
	public void setLastCheckpoint(Location lastCheckpoint) {
		this.lastCheckpoint = lastCheckpoint;
	}
}
