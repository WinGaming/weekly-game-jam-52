package de.wingaming.parable.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.wingaming.parable.utils.ImageUtils;
import javafx.scene.image.Image;

public class TileType {
	
	public static TileType GRASS = new TileType(ImageUtils.resample(new Image("file:res/grass.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType STONE = new TileType(ImageUtils.resample(new Image("file:res/stone.png"), 3), 1, 1, ImageDepth.DEFAULT);
//	public static TileType BUSH = new TileType(ImageUtils.resample(new Image("file:res/bush.png"), 3), 1, 1, ImageDepth.DEFAULT);
	
	public static TileType PATH_BOTTOM_LEFT = new TileType(ImageUtils.resample(new Image("file:res/path/bottom_left.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType PATH_BOTTOM_RIGHT = new TileType(ImageUtils.resample(new Image("file:res/path/bottom_right.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType PATH_LEFT_RIGHT = new TileType(ImageUtils.resample(new Image("file:res/path/left_right.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType PATH_TOP_DOWN = new TileType(ImageUtils.resample(new Image("file:res/path/top_down.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType PATH_TOP_LEFT = new TileType(ImageUtils.resample(new Image("file:res/path/top_left.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType PATH_TOP_RIGHT = new TileType(ImageUtils.resample(new Image("file:res/path/top_right.png"), 3), 1, 1, ImageDepth.DEFAULT);
	
//	public static TileType TREE_1 = new TileType(ImageUtils.resample(new Image("file:res/tree.png"), 3), 1, 2*1, ImageDepth.DEFAULT);
	
	public static TileType SKY_DIRT1 = new TileType(ImageUtils.resample(new Image("file:res/sky/dirt_1.png"), 3), 1, 1, ImageDepth.DEFAULT).setSolid(true);
	public static TileType SKY_DIRT2 = new TileType(ImageUtils.resample(new Image("file:res/sky/dirt_2.png"), 3), 1, 1, ImageDepth.DEFAULT).setSolid(true);
	public static TileType SKY_GRASS1 = new TileType(ImageUtils.resample(new Image("file:res/sky/grass_1.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType SKY_GRASS2 = new TileType(ImageUtils.resample(new Image("file:res/sky/grass_2.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType SKY_STONE = new TileType(ImageUtils.resample(new Image("file:res/sky/stone.png"), 3), 1, 1, ImageDepth.DEFAULT).setSolid(true);
	public static TileType SKY_STONE_WATER = new TileType(ImageUtils.resample(new Image("file:res/sky/stone_water.png"), 3), 1, 1, ImageDepth.DEFAULT);
	
	public static TileType SKY_STONE_BG = new TileType(ImageUtils.resample(new Image("file:res/sky/stone_bg.png"), 3), 1, 1, ImageDepth.DEFAULT);
	public static TileType SKY_DIRT2_BG = new TileType(ImageUtils.resample(new Image("file:res/sky/dirt_2_bg.png"), 3), 1, 1, ImageDepth.DEFAULT);
	
	private static int index;
	private static Map<String, TileType> tiles = new HashMap<>();
	private static List<TileType> list = new ArrayList<>();
	public static void register(String code, TileType type) {
		tiles.put(code, type);
		list.add(type);
	}
	
	static {
		register("!", GRASS);
		register("§", STONE);
		
		register("e", SKY_DIRT1);
		register("f", SKY_DIRT2);
		register("g", SKY_GRASS1);
		register("h", SKY_GRASS2);
		register("i", SKY_STONE);
		register("j", SKY_STONE_WATER);
		register("k", SKY_STONE_BG);
		//l
		register("m", SKY_DIRT2_BG);
		
		register("1", PATH_BOTTOM_LEFT);
		register("2", PATH_BOTTOM_RIGHT);
		register("3", PATH_LEFT_RIGHT);
		register("4", PATH_TOP_DOWN);
		register("5", PATH_TOP_LEFT);
		register("6", PATH_TOP_RIGHT);
	}
	
	public static TileType getCurrentType() {
		return list.get(index);
	}
	
	public static TileType getType(String code) {
		return tiles.get(code);
	}
	
	public static String getSymbol(TileType type) {
		for (String code : tiles.keySet()) {
			if (tiles.get(code) == type) {
				return code;
			}
		}
		
		return "~";
	}
	
	public static void next() {
		index++;
		
		if (index >= list.size()) index = 0;
	}
	
	private Image texture;
	private double height, width;
	private ImageDepth depth;
	private boolean solid;
	
	public TileType(Image image, double width, double height, ImageDepth depth) {
		this.texture = image;
		this.height = height;
		this.depth = depth;
		this.width = width;
	}
	
	public TileType setSolid(boolean solid) {
		this.solid = solid;
		
		return this;
	}
	
	public Image getTexture() {
		return texture;
	}
	
	public double getHeight() {
		return height;
	}
	
	public ImageDepth getDepth() {
		return depth;
	}
	
	public double getWidth() {
		return width;
	}
	
	public boolean isSolid() {
		return solid;
	}
}
