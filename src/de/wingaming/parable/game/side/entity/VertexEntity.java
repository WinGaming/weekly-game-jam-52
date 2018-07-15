package de.wingaming.parable.game.side.entity;

import de.wingaming.parable.Main;
import de.wingaming.parable.game.CollideFallback;
import de.wingaming.parable.game.World;
import de.wingaming.parable.menue.background.Vertex;
import de.wingaming.parable.utils.Location;
import de.wingaming.parable.utils.Vector2d;
import javafx.scene.paint.Color;

public class VertexEntity implements CollideFallback {

	private Location location;
	private Vertex[][] matrix;
	private Vector2d velocity;
	
	private double width;
	private double height;
	
	private Color color = Color.BLACK;
	
	public VertexEntity(Location location, Vertex[][] matrix) {
		this.location = location;
		this.matrix = matrix;
		
		this.velocity = new Vector2d(0, 0);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void update(World world) {
		updateDimensions();
		
		this.location = world.collide(location, velocity.getX(), velocity.getY(), width, height, this);
		
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
				Vertex vertex = matrix[x][y];
				if (vertex == null) continue;
				
				vertex.update();
			}
		}
	}
	
	public void render(Vector2d camera) {
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
				Vertex vertex = matrix[x][y];
				if (vertex == null) continue;
				
//				Main.gc.setFill(Color.BLACK);
//				Main.gc.fillRoundRect(location.getX() + vertex.getX() - 5, location.getY() + vertex.getY() - 5, 10, 10, 90, 90);
				
				//top-down-right
				Vertex under = y == matrix[x].length-1 ? null : matrix[x][y+1];
				Vertex right = x == matrix[x].length-1 ? null : matrix[x+1][y];
				
				if (under != null && right != null) {
					float opacity = (vertex.getColorGradian() + under.getColorGradian() + right.getColorGradian()) / 3;
					Color fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
					
					Main.gc.setFill(fillColor);
					Main.gc.fillPolygon(new double[] {location.getX() + vertex.getX() - camera.getX(), location.getX() + under.getX() - camera.getX(), location.getX() + right.getX() - camera.getX()}, new double[] {location.getY() + vertex.getY() - camera.getY(), location.getY() + under.getY() - camera.getY(), location.getY() + right.getY() - camera.getY()}, 3);
				}
				
				//bottom-left-top
				Vertex top = y == 0 ? null : matrix[x][y-1];
				Vertex left = x == 0 ? null : matrix[x-1][y];
				
				if (top != null && left != null) {
					float opacity = (vertex.getColorGradian() + top.getColorGradian() + left.getColorGradian()) / 3;
					Color fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
					
					Main.gc.setFill(fillColor);
					Main.gc.fillPolygon(new double[] {location.getX() + vertex.getX() - camera.getX(), location.getX() + top.getX() - camera.getX(), location.getX() + left.getX() - camera.getX()}, new double[] {location.getY() + vertex.getY() - camera.getY(), location.getY() + top.getY() - camera.getY(), location.getY() + left.getY() - camera.getY()}, 3);
				}
			}
		}
	}
	
	public void updateDimensions() {
		double minX = 0;
		double maxX = 0;
		double minY = 0;
		double maxY = 0;
		
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
				Vertex vertex = matrix[x][y];
				if (vertex == null) continue;
				
				double vx = vertex.getX();
				double vy = vertex.getY();
				
				minX = Math.min(minX, vx);
				maxX = Math.max(maxX, vx);
				minY = Math.min(minY, vy);
				maxY = Math.max(maxY, vy);
			}
		}
		
		this.width = maxX - minX;
		this.height = maxY - minY;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setMatrix(Vertex[][] matrix) {
		this.matrix = matrix;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Vertex[][] getMatrix() {
		return matrix;
	}
	
	public boolean collide(double x, double y) {
		return false;
	}
	
	public void collideX() {}
	public void collideY(boolean top) {}
}