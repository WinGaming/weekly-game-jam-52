package de.wingaming.parable.game.side.entity.weapon;

import java.util.ArrayList;
import java.util.List;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.World;
import de.wingaming.parable.game.side.GameSideView;
import de.wingaming.parable.game.side.Player;
import de.wingaming.parable.game.side.entity.LivingEntity;
import de.wingaming.parable.game.side.entity.VertexEntity;
import de.wingaming.parable.menue.background.Vertex;
import de.wingaming.parable.utils.Location;
import de.wingaming.parable.utils.Vector2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EntityProjectile extends LivingEntity {

	private Vector2d velocity;
	private double rotation, spin;
	private double size;
	private double damage = 1;
	
	public EntityProjectile(Location location, Vector2d velocity, double rotation, double spin) {
		super(location, null, 1);
		
		this.velocity = velocity;
		this.rotation = rotation;
		this.spin = spin;
		
		this.size = 10;
		
		Vertex[][] matrix = new Vertex[2][2];
		matrix[0][1] = new Vertex(0, size, null, false, 0.5f, 1);
		matrix[1][0] = new Vertex(size, 0, null, false, 0.5f, 1);
		matrix[1][1] = new Vertex(size, size, null, false, 0.5f, 1);
		setMatrix(matrix);
		
		setColor(Color.WHITE);
	}
	
	public void update(World world) {
		super.update(world);
		
		setLocation(getLocation().add(velocity.getX(), velocity.getY()));
		
		this.rotation += spin;
		
		if (rotation > 360) spin -= 360;

		Player player = GameSideView.INSTANCE.getPlayer();
		Location pos = player.getLocation().clone().add(-(player.getSize()/2), -(player.getSize()/2));
		
		boolean foundTarget = false;
		if (getLocation().getX() >= pos.getX() && getLocation().getY() >= pos.getY()) {
			if (getLocation().getX() <= pos.getX() + player.getSize() && getLocation().getY() <= pos.getY() + player.getSize()) {
				player.damage(damage);
				
				foundTarget = true;
			}
		}
		
		if (!foundTarget) {
			List<VertexEntity> entities = GameSideView.INSTANCE.getEntitiesCollidingAt(getLocation());
			
			for (VertexEntity vertexEntity : entities) {
				if (vertexEntity.equals(this)) continue;
				
				if (vertexEntity instanceof LivingEntity) {
					LivingEntity entity = (LivingEntity) vertexEntity;
					entity.damage(damage);
					foundTarget = true;
				}
			}
		}
		
		if (foundTarget) {
			GameSideView.INSTANCE.killEntity(this);
			return;
		}

		Vector2d camera = world.getCamera();
		double x = getLocation().getX() - camera.getX();
		double y = getLocation().getY() - camera.getY();
		if (x <= 0 || y <= 0 || x >= Main.WIDTH || y >= Main.HEIGHT) {
			GameSideView.INSTANCE.killEntity(this);
			return;
		}
		
		if (!foundTarget) {
			List<Location> locations = new ArrayList<>();
			locations.add(getLocation().clone());
			if (world.collide(locations)) {
				GameSideView.INSTANCE.killEntity(this);
				return;
			}
		}
	}
	
	public void render(Vector2d camera) {
		GraphicsContext gc = Main.gc;
		
		gc.save();
		gc.translate(getLocation().getX() + size/2 - camera.getX(), getLocation().getY() + size/2 - camera.getY());
		gc.rotate(rotation);
		gc.translate(-(getLocation().getX() + size/2 - camera.getX()), -(getLocation().getY() + size/2 - camera.getY()));
		
		super.render(camera);
		
		gc.restore();
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public Vector2d getVelocity() {
		return velocity;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public double getSpin() {
		return spin;
	}
}
