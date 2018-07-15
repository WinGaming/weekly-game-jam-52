package de.wingaming.parable.menue.background;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.wingaming.parable.utils.Vector2d;

public class Vertex {
	
	private double x, y;
	private boolean fixed;
	private List<Vertex> connections;
	
	private boolean colorGrade;
	private float colorGradian;
	
	private float minOpacity, maxOpacity;
	
	public Vertex(double x, double y, List<Vertex> connections, boolean fixed, float minOpacity, float maxOpacity) {
		this.x = x;
		this.y = y;
		this.connections = connections == null ? new ArrayList<>() : connections;
		this.fixed = fixed;

		this.minOpacity = minOpacity;
		this.maxOpacity = maxOpacity;
		
		Random random = new Random();
		this.colorGradian = random.nextFloat();
		this.colorGrade = random.nextBoolean();
	}
	
	public Vertex(double x, double y, List<Vertex> connections, boolean fixed) {
		this(x, y, connections, fixed, 0, 1);
	}
	
	public Vertex(double x, double y, List<Vertex> connections) {
		this(x, y, connections, false);
	}
	
	public void update() {
		colorGradian += (colorGrade ? -maxOpacity : maxOpacity) * 0.025f;
		
		if (colorGradian < minOpacity) {
			colorGradian = minOpacity;
			colorGrade = false;
		} else if (colorGradian > maxOpacity) {
			colorGradian = maxOpacity;
			colorGrade = true;
		}
		
		if (!fixed) {
			Vector2d vector = new Vector2d(0, 0);
			
			for (Vertex vertex : connections) {
				boolean left = vertex.getX() < this.getX();
				boolean over = vertex.getY() < this.getY();
				
				double a = left ? (getX() - vertex.getX()) : (vertex.getX() - getX()); //x
				double b = over ? (getY() - vertex.getY()) : (vertex.getY() - getY()); //y
				double c = Math.sqrt(a*a + b*b);
				double factor = c/10000;
				
				a *= factor;
				b *= factor;
				
//				vector.add(new Vector2d(left ? -a : a, over ? -b : b));
			}
			
			this.x += vector.getX();
			this.y += vector.getY();
		}
	}
	
	public float getColorGradian() {
		return colorGradian;
	}
	
	public void increasePosition(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void addVertex(Vertex vertex) {
		if (!this.connections.contains(vertex)) this.connections.add(vertex);
	}
	
	public void addVertecies(List<Vertex> vertecies) {
		this.connections.addAll(vertecies);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean isFixed() {
		return fixed;
	}
	
	public List<Vertex> getConnections() {
		return connections;
	}
	
	public void setMinOpacity(float minOpacity) {
		this.minOpacity = minOpacity;
	}
	
	public void setMaxOpacity(float maxOpacity) {
		this.maxOpacity = maxOpacity;
	}
}
