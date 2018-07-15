package de.wingaming.parable.game.side;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.CollideFallback;
import de.wingaming.parable.game.World;
import de.wingaming.parable.game.side.entity.weapon.EntityProjectile;
import de.wingaming.parable.input.KeyboardManager;
import de.wingaming.parable.menue.UIGameOver;
import de.wingaming.parable.utils.Location;
import de.wingaming.parable.utils.Vector2d;
import javafx.scene.input.KeyCode;

public class Player implements CollideFallback {
	
	private Location location;
	private double size;
	private Vector2d velocity;
	private Vector2d maxVelocity = new Vector2d(20, 20);
	private boolean onGround = false;
	
	private long lastHit = 0;
	private double health, maxHealth;

	private long lastShoot = 0;
	
	public Player(Location location) {
		this.location = location;
		
		this.size = 40;
		this.velocity = new Vector2d(0, 0);
		
		this.maxHealth = 5;
		this.health = maxHealth;
	}
	
	public void update(World world) {
		this.location = world.collide(location.clone().add(-(size/2), -(size/2)), velocity.getX(), 0, size, size, this).add(size/2, size/2);
		this.location = world.collide(location.clone().add(-(size/2), -(size/2)), 0, velocity.getY(), size, size, this).add(size/2, size/2);
		
		Vector2d camera = world.getCamera();
		if (location.getX() - camera.getX() > Main.WIDTH*0.8) world.setCamera(new Vector2d(location.getX() - Main.WIDTH*0.8, camera.getY()));
		if (location.getX() - camera.getX() < Main.WIDTH*0.1) world.setCamera(new Vector2d(location.getX() - Main.WIDTH*0.1, camera.getY()));
		if (location.getY() - camera.getY() > Main.HEIGHT*0.90) world.setCamera(new Vector2d(camera.getX(), location.getY() - Main.HEIGHT*0.90));
		if (location.getY() - camera.getY() < Main.HEIGHT*0.05) world.setCamera(new Vector2d(camera.getX(), location.getY() - Main.HEIGHT*0.05));

		if (onGround && KeyboardManager.isDown(KeyCode.SPACE)) {
			KeyboardManager.release(KeyCode.SPACE);
			velocity.add(new Vector2d(0, -10));
		}
		if (KeyboardManager.isDown(KeyCode.RIGHT)) {
			velocity.add(new Vector2d(onGround ? .75 : .15, 0));
		}
		if (KeyboardManager.isDown(KeyCode.LEFT)) {
			velocity.add(new Vector2d(-(onGround ? .75 : .15), 0));
		}
		
		//Attacks
		if (KeyboardManager.isDown(KeyCode.R)) {
//			KeyboardManager.release(KeyCode.R);
			
			if (System.currentTimeMillis() - lastShoot > 1000) {
				double radius = GameSideView.INSTANCE.getPlayer().getSize() * .75;
				Player player = GameSideView.INSTANCE.getPlayer();
				
				double factor = GameSideView.INSTANCE.getGemCount() >= 6 ? 2 : 1;
				for (int i = 0; i < 18*factor; i++) {
					double rotation = i * 20/factor;
					double x = (radius * Math.cos(Math.toRadians(rotation)));
					double y = (radius * Math.sin(Math.toRadians(rotation)));
					
					EntityProjectile projectile = new EntityProjectile(player.getLocation().clone().add(x, y), new Vector2d(x*.2, y*.2), rotation  - 45, 0);
					
					GameSideView.INSTANCE.spawnEntity(projectile);
				}
				
				lastShoot = System.currentTimeMillis();
			}
		}
		//End attacks
		
		velocity.setX(velocity.getX() > 0 ? Math.min(velocity.getX(), maxVelocity.getX()) : Math.max(velocity.getX(), -maxVelocity.getX()));
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
		velocity.setX(velocity.getX() * -.25);
	}
	
	public void collideY(boolean top) {
		velocity.setY(0);
		velocity.multiply(0.975, 1);
		
		if (top) onGround = true;
	}
	
	public void damage(double damage) {
		this.health -= damage;
		
		lastHit = System.currentTimeMillis();
		
		if (this.health <= 0) {
			Main.renderer.setCurrentUI(UIGameOver.INSTANCE);
		}
	}
	
	public long getLastHit() {
		return lastHit;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public void setMaxHealth(double maxHhealth) {
		this.maxHealth = maxHhealth;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
}
