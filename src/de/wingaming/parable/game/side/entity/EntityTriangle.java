package de.wingaming.parable.game.side.entity;

import de.wingaming.parable.game.CollideFallback;
import de.wingaming.parable.game.World;
import de.wingaming.parable.game.side.GameSideView;
import de.wingaming.parable.game.side.entity.weapon.EntityProjectile;
import de.wingaming.parable.menue.background.Vertex;
import de.wingaming.parable.utils.Location;
import de.wingaming.parable.utils.Vector2d;
import javafx.scene.paint.Color;

public class EntityTriangle extends LivingEntity implements CollideFallback {

	private boolean onGround = false;
	private Vector2d velocity;
	private Vector2d maxVelocity = new Vector2d(10, 20);
	private long lastShot = System.currentTimeMillis();
	
	public EntityTriangle(Location location) {
		super(location, null, 4);
		
		Vertex[][] matrix = new Vertex[2][2];
		matrix[0][1] = new Vertex(0, 80, null, false, 0.25f, 1);
		matrix[1][0] = new Vertex(40, 0, null, false, 0.25f, 1);
		matrix[1][1] = new Vertex(40, 80, null, false, 0.25f, 1);
		setMatrix(matrix);
		
		setColor(Color.ORANGE);
		
		this.velocity = new Vector2d(0, 0);
	}
	
	public void update(World world) {
		super.update(world);
		
		setLocation(world.collide(getLocation().add(20, 40), velocity.getX(), velocity.getY(), 40, 80, this).add(-20, 0));
		
		if (System.currentTimeMillis() - lastShot > 3000) {
			Location player = GameSideView.INSTANCE.getPlayer().getLocation();
			double a = Math.max(player.getX(), getLocation().getX()) - Math.min(player.getX(), getLocation().getX());
			double b = Math.max(player.getY(), getLocation().getY()) - Math.min(player.getY(), getLocation().getY());
			double playerDistance = Math.sqrt(a*a + b*b);

			if (playerDistance <= 900) {
				GameSideView.INSTANCE.spawnEntity(new EntityProjectile(getLocation().clone().add(0, 10), new Vector2d(-1, 0), 0, 10));
				GameSideView.INSTANCE.spawnEntity(new EntityProjectile(getLocation().clone().add(0, 50), new Vector2d(-1, 0), 0, 10));
				GameSideView.INSTANCE.spawnEntity(new EntityProjectile(getLocation().clone().add(40, 10), new Vector2d(1, 0), 0, 10));
				GameSideView.INSTANCE.spawnEntity(new EntityProjectile(getLocation().clone().add(40, 50), new Vector2d(1, 0), 0, 10));
				
				lastShot = System.currentTimeMillis();
			}
		}
		
		velocity.setY(Math.min(velocity.getY() + World.GRAVITY, maxVelocity.getY()));
		onGround = false;
	}
	
	public boolean collide(double x, double y) {
		double rx = x - getLocation().getX();
		double ry = y - getLocation().getY();
		double width = 40, height = 80;
		
		if (rx < 0 || ry < 0 || rx > width || ry > height) return false;
		
		return (rx >= (ry / height) * width);
	}
	
	public void collideX() {
		velocity.setX(0);
	}
	
	public void collideY(boolean top) {
		velocity.setY(0);
		velocity.multiply(0.975, 1);
		
		if (top) onGround = true;
	}
	
	public boolean isOnGround() {
		return onGround;
	}
}