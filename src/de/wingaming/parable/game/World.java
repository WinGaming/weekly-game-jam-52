package de.wingaming.parable.game;

import java.util.ArrayList;
import java.util.List;

import de.wingaming.parable.Main;
import de.wingaming.parable.utils.Location;
import de.wingaming.parable.utils.Vector2d;

public class World {

	public static final double DEBUG_FACTOR = 0.00001;
	
	public static double TILE_SIZE = 32;
	public static double GRAVITY = 0.5;
	
	private TileType[][] tiles = new TileType[512][512];
	private TileType[][] topTiles = new TileType[512][512];
	
	private Vector2d camera = new Vector2d(0, 0);
	
	public World() {
		
	}
	
	public void setCamera(Vector2d camera) {
		this.camera = camera;
	}
	
	public void render() {
		renderWorldLevel(tiles, ImageDepth.DEFAULT);
		renderWorldLevel(topTiles, ImageDepth.DEFAULT);
		renderWorldLevel(tiles, ImageDepth.TOP);
		renderWorldLevel(topTiles, ImageDepth.TOP);
		
//		System.out.println(camera.getX());
	}
	
	private void renderWorldLevel(TileType[][] matrix, ImageDepth depth) {
		int startX = (int) (camera.getX()/TILE_SIZE);
		int startY = (int) (camera.getY()/TILE_SIZE);
		double tilesInWidth = Main.WIDTH/TILE_SIZE + 2;
		double tilesInHeight = Main.HEIGHT/TILE_SIZE + 2;
		
		double dx = camera.getX() - ((int) (camera.getX()/TILE_SIZE)) * TILE_SIZE;
		double dy = camera.getY() - ((int) (camera.getY()/TILE_SIZE)) * TILE_SIZE;
		
		for (int y = startY; y < startY+tilesInHeight; y++) {
			for (int x = startX; x < startX+tilesInWidth; x++) {
				TileType type = getTileTypeAt(matrix, x, y);
				if (type == null || type.getDepth() != depth) continue;
				
				Main.gc.drawImage(type.getTexture(), (x-startX) * TILE_SIZE - (type.getWidth()*TILE_SIZE-TILE_SIZE) - dx, (y-startY) * TILE_SIZE - (type.getHeight()*TILE_SIZE-TILE_SIZE) - dy, World.TILE_SIZE, type.getHeight()*World.TILE_SIZE);
			}
		}
	}

	public TileType getTileTypeAt(TileType[][] matrix, int x, int y) {
		if (x < 0 || y < 0) return null;
		return matrix[y][x];
	}
	
	public TileType getTopTileTypeAt(int x, int y) {
		return topTiles[y][x];
	}
	
	public TileType getTileTypeAt(int x, int y) {
		return tiles[y][x];
	}
	
	public void setTile(int x, int y, TileType tile) {
		tiles[y][x] = tile;
	}
	
	public void setTopTile(int x, int y, TileType tile) {
		topTiles[y][x] = tile;
	}
	
	public TileType[][] getTiles() {
		return tiles;
	}
	
	public TileType[][] getTopTiles() {
		return topTiles;
	}
	
	public Location collide(Location startLocation, double dx, double dy, double width, double height, CollideFallback feedback) {
		Location targetLocation = startLocation.clone().add(dx, dy);
		Location result = targetLocation.clone();
		
		Location topLeft = result.clone();
		Location bottomRight = result.clone().add(width, height);
		
		if (dy != 0) {
			Location toLocation = startLocation.clone().add(0, dy);
			Location newLocation = startLocation.clone();
			
			int xCount = bottomRight.getTileX() - topLeft.getTileX() + 1;
			List<Location> testLocations = new ArrayList<>();
			
			if (dy > 0) {
				for (int i = 0; i < xCount; i++) {
					testLocations.add(new Location(toLocation.getX()+i*TILE_SIZE, toLocation.getY()+height));
				}
				
				if (collide(testLocations)) {
					newLocation.setY(toLocation.clone().add(0, height).getTileY()*TILE_SIZE-height-DEBUG_FACTOR);
					feedback.collideY(true);
				} else {
					newLocation.add(0, dy);
				}
			} else if (dy < 0) {
				for (int i = 0; i < xCount; i++) {
					testLocations.add(new Location(toLocation.getX()+i*TILE_SIZE, toLocation.getY()));
				}
				
				if (collide(testLocations)) {
					newLocation.setY(toLocation.getTileY()*TILE_SIZE+TILE_SIZE+DEBUG_FACTOR);
					feedback.collideY(false);
				} else {
					newLocation.add(0, dy);
				}
			}
			
			return collide(newLocation, dx, 0, width, height, feedback);
		} else if (dx != 0) {
			Location toLocation = startLocation.clone().add(dx, 0);
			Location newLocation = startLocation.clone();
			
			int yCount = bottomRight.getTileY() - topLeft.getTileY() + 1;
			List<Location> testLocations = new ArrayList<>();
			
			if (dx > 0) {
				for (int i = 0; i < yCount; i++) {
					testLocations.add(new Location(toLocation.getX()+width, toLocation.getY()+i*TILE_SIZE));
				}
				
				if (collide(testLocations)) {
					newLocation.setX(toLocation.clone().add(width, 0).getTileX()*TILE_SIZE-( + (((int) width / TILE_SIZE)))-width-DEBUG_FACTOR);
					feedback.collideX();
				} else {
					newLocation.add(dx, 0);
				}
			} else if (dx < 0) {
				for (int i = 0; i < yCount; i++) {
					testLocations.add(new Location(toLocation.getX(), toLocation.getY()+i*TILE_SIZE));
				}
				
				if (collide(testLocations)) {
					newLocation.setX(toLocation.getTileX()*TILE_SIZE+TILE_SIZE+DEBUG_FACTOR);
					feedback.collideX();
				} else {
					newLocation.add(dx, 0);
				}
			}
			
			return collide(newLocation, 0, dy, width, height, feedback);
		} else {
			return targetLocation;
		}
	}
	
	public boolean collide(List<Location> locations) {
		for (Location location : locations) {
			int x = location.getTileX();
			int y = location.getTileY();
			
			if (x < 0 || y < 0) return true;
			
			if (getTileTypeAt(x, y) != null) {
				if (getTileTypeAt(x, y).isSolid()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
//	private boolean collide(List<Location> locations) {
//		for (Location location : locations) {
//			int x = location.getTileX();
//			int y = location.getTileY();
//			
//			if (getTileTypeAt(x, y) != null) {
//				if (getTileTypeAt(x, y).isSolid()) {
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
//	
//	public Location collide(Location startLocation, double dx, double dy, double width, double height, CollideFallback feedback) {
//		Location targetLocation = startLocation.clone().add(dx, dy);
//		Location result = targetLocation.clone();
//		
//		targetLocation.setX(Math.max(targetLocation.getX(), 0));
//		targetLocation.setY(Math.max(targetLocation.getY(), 0));
//		
//		Location topLeft = result.clone();
//		Location bottomRight = result.clone().add(width, height);
//		
//		if (dy != 0) {
//			Location toLocation = startLocation.clone().add(0, dy);
//			Location newLocation = startLocation.clone();
//			
//			int xCount = bottomRight.getTileX() - topLeft.getTileX() + 1;
//			List<Location> testLocations = new ArrayList<>();
//			
//			if (dy > 0) {
//				for (int i = 0; i < xCount; i++) {
//					testLocations.add(new Location(toLocation.getX()+i*TILE_SIZE, toLocation.getY()+height));
//				}
//				
//				if (collide(testLocations)) {
//					newLocation.setY(toLocation.clone().add(0, height).getTileY()*TILE_SIZE-height-DEBUG_FACTOR);
//					feedback.collideY(true);
//				} else {
//					newLocation.add(0, dy);
//				}
//			} else if (dy < 0) {
//				for (int i = 0; i < xCount; i++) {
//					testLocations.add(new Location(toLocation.getX()+i*TILE_SIZE, toLocation.getY()));
//				}
//				
//				if (collide(testLocations)) {
//					newLocation.setY(toLocation.getTileY()*TILE_SIZE+TILE_SIZE+DEBUG_FACTOR);
//					feedback.collideY(false);
//				} else {
//					newLocation.add(0, dy);
//				}
//			}
//			
//			return collide(newLocation, dx, 0, width, height, feedback);
//		} else if (dx != 0) {
//			Location toLocation = startLocation.clone().add(dx, 0);
//			Location newLocation = startLocation.clone();
//			
//			int yCount = bottomRight.getTileY() - topLeft.getTileY() + 1;
//			List<Location> testLocations = new ArrayList<>();
//			
//			if (dx > 0) {
////				for (int i = 0; i < yCount; i++) {
////					testLocations.add(new Location(toLocation.getX()+width, toLocation.getY()+i*TILE_SIZE));
////				}
////				
////				if (collide(testLocations)) {
////					newLocation.setX(toLocation.clone().add(width, 0).getTileX()*TILE_SIZE-width-DEBUG_FACTOR);
////					feedback.collideX();
////				} else {
////					newLocation.add(dx, 0);
////				}
//				
//				for (int i = 0; i < yCount; i++) {
//					testLocations.add(new Location(toLocation.getX()+width, toLocation.getY()+i*TILE_SIZE));
//				}
//				
//				if (collide(testLocations)) {
//					newLocation.setX(toLocation.clone().add(-width, 0).getTileX()*TILE_SIZE+TILE_SIZE-width-DEBUG_FACTOR);
//					feedback.collideX();
//				} else {
//					newLocation.add(dx, 0);
//				}
//			} else if (dx < 0) {
//				for (int i = 0; i < yCount; i++) {
//					testLocations.add(new Location(toLocation.getX(), toLocation.getY()+i*TILE_SIZE));
//				}
//				
//				if (collide(testLocations)) {
//					newLocation.setX(toLocation.getTileX()*TILE_SIZE+TILE_SIZE+DEBUG_FACTOR);
//					feedback.collideX();
//				} else {
//					newLocation.add(dx, 0);
//				}
//			}
//			
//			return collide(newLocation, 0, dy, width, height, feedback);
//		} else {
//			return targetLocation;
//		}
//	}
	
	public Vector2d getCamera() {
		return camera;
	}
}
