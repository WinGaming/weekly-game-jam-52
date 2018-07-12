package de.wingaming.parable.game.side;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.CollideFallback;
import de.wingaming.parable.game.World;
import de.wingaming.parable.input.KeyboardManager;
import de.wingaming.parable.utils.Location;
import de.wingaming.parable.utils.Vector2d;
import javafx.scene.input.KeyCode;

public class Player implements CollideFallback {
	
	private Location location;
	private double size;
	private Vector2d velocity;
	private Vector2d maxVelocity = new Vector2d(10, 20);
	private boolean onGround = false;
	
	public Player(Location location) {
		this.location = location;
		
		this.size = 40;
		this.velocity = new Vector2d(0, 0);
	}
	
	public void update(World world) {
		this.location = world.collide(location, velocity.getX(), velocity.getY(), size, size, this);
		
		Vector2d camera = world.getCamera();
		if (location.getX() - camera.getX() > Main.WIDTH*0.9) world.setCamera(new Vector2d(location.getX() - Main.WIDTH*0.9, camera.getY()));
		if (location.getX() - camera.getX() < Main.WIDTH*0.1) world.setCamera(new Vector2d(location.getX() - Main.WIDTH*0.1, camera.getY()));
		if (location.getY() - camera.getY() > Main.HEIGHT*0.90) world.setCamera(new Vector2d(camera.getX(), location.getY() - Main.HEIGHT*0.90));
		if (location.getY() - camera.getY() < Main.HEIGHT*0.05) world.setCamera(new Vector2d(camera.getX(), location.getY() - Main.HEIGHT*0.05));
		
		if (KeyboardManager.isDown(KeyCode.RIGHT)) {
			velocity.add(new Vector2d(onGround ? 1 : .25, 0));
		}
		if (KeyboardManager.isDown(KeyCode.LEFT)) {
			velocity.add(new Vector2d(-(onGround ? 1 : .25), 0));
		}
		if (onGround && KeyboardManager.isDown(KeyCode.SPACE)) {
			KeyboardManager.release(KeyCode.SPACE);
			velocity.add(new Vector2d(0, -10));
		}
		
		//TODO: keep?
		if (onGround && KeyboardManager.isDown(KeyCode.SHIFT)) {
			KeyboardManager.release(KeyCode.SHIFT);
			velocity.multiply(2, 0);
		}
		
		//Attacks
		if (KeyboardManager.isDown(KeyCode.R)) {
			KeyboardManager.release(KeyCode.R);
			System.out.println("Example attack!!!");
		}
		//End attacks
		
		velocity.setY(Math.min(velocity.getY() + World.GRAVITY, maxVelocity.getY()));
		
		this.onGround = false;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public double getSize() {
		return size;
	}
	
	public void collideX() {
		velocity.setX(0);
	}
	
	public void collideY(boolean top) {
		velocity.setY(0);
		velocity.multiply(0.975, 1);
		
		if (top) onGround = true;
	}
}
