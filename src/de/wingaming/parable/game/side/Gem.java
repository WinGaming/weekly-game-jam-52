package de.wingaming.parable.game.side;

import de.wingaming.parable.utils.ImageUtils;
import de.wingaming.parable.utils.Location;
import javafx.scene.image.Image;

public class Gem {

	public static final Image GEM_RED = ImageUtils.resample(new Image("file:res/gem_red.png"), 7);
	public static final Image GEM_GREEN = ImageUtils.resample(new Image("file:res/gem_green.png"), 7);
	public static final Image GEM_BLUE = ImageUtils.resample(new Image("file:res/gem_blue.png"), 7);
	
	private Image texture;
	private Location location;

	public Gem(Image texture, Location location) {
		this.texture = texture;
		this.location = location;
	}
	
	public Image getTexture() {
		return texture;
	}
	
	public Location getLocation() {
		return location;
	}
}
