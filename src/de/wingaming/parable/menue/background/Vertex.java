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
	
	public Vertex(double x, double y, List<Vertex> connections, boolean fixed) {
		this.x = x;
		this.y = y;
		this.connections = connections == null ? new ArrayList<>() : connections;
		this.fixed = fixed;
		
		Random random = new Random();
		this.colorGradian = random.nextFloat();
		this.colorGrade = random.nextBoolean();
	}
	
	public Vertex(double x, double y, List<Vertex> connections) {
		this(x, y, connections, false);
	}
	
	public void update() {
		colorGradian += (colorGrade ? -1 : 1) * 0.025f;
		
		if (colorGradian < 0) {
			colorGradian = 0;
			colorGrade = false;
		} else if (colorGradian > 1) {
			colorGradian = 1;
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
			
			this.x += vector.getX();// + ranA;
			this.y += vector.getY();// + ranB;
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
}
