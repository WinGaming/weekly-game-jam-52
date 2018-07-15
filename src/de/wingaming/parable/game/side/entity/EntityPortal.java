package de.wingaming.parable.game.side.entity;

import de.wingaming.parable.game.World;
import de.wingaming.parable.game.side.GameSideView;
import de.wingaming.parable.game.side.Player;
import de.wingaming.parable.menue.background.Vertex;
import de.wingaming.parable.utils.Location;
import javafx.scene.paint.Color;

public class EntityPortal extends VertexEntity {

	private EntityPortal destiny;
	private boolean setCheckpoint = false;
	
	public EntityPortal(Location location) {
		super(location, null);
		
		Vertex[][] matrix = new Vertex[2][2];
		
		setColor(Color.RED);
		
		matrix[0][0] = new Vertex(0, 0, null);
		matrix[1][0] = new Vertex(35, 0, null);
		matrix[0][1] = new Vertex(0, 75, null);
		matrix[1][1] = new Vertex(35, 75, null);
		
		setMatrix(matrix);
	}
	
	public void setSetCheckpoint(boolean setCheckpoint) {
		this.setCheckpoint = setCheckpoint;
	}
	
	public void update(World world) {
		super.update(world);
		
		Player player = GameSideView.INSTANCE.getPlayer();
		
		if (destiny != null && player.getLocation().getX() > getLocation().getX() && player.getLocation().getX() < getLocation().getX() + 35) {
			if (player.getLocation().getY() > getLocation().getY() && player.getLocation().getY() < getLocation().getY() + 75) {
				player.getLocation().setX(destiny.getLocation().getX());
				player.getLocation().setY(destiny.getLocation().getY());
				world.getCamera().setY(0);
				
				if (setCheckpoint) {
					GameSideView.INSTANCE.setLastCheckpoint(destiny.getLocation());
				}
			}
		}
	}
	
	public void setDestiny(EntityPortal destiny) {
		this.destiny = destiny;
	}
	
	public EntityPortal getDestiny() {
		return destiny;
	}
}
