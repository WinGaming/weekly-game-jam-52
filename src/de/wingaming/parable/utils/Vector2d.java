package de.wingaming.parable.utils;

public class Vector2d {
	
	private double x, y;
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2d add(Vector2d value) {
		this.x += value.getX();
		this.y += value.getY();
		
		return this;
	}
	
	public void multiply(Vector2d value) {
		this.x *= value.getX();
		this.y *= value.getY();
	}
	
	public void multiply(double xValue, double yValue) {
		this.x *= xValue;
		this.y *= yValue;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
}
