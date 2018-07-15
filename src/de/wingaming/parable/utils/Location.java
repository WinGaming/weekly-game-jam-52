package de.wingaming.parable.utils;

import de.wingaming.parable.game.World;

public class Location {
	
	private double x, y;
	
	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Location clone() {
		return new Location(x, y);
	}
	
	public Location add(double dx, double dy) {
		this.x += dx;
		this.y += dy;
		
		return this;
	}
	
	 public int getTileX() {
		 return (int) (x/World.TILE_SIZE);
	 }
	 
	 public int getTileY() {
		 return (int) (y/World.TILE_SIZE);
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
	
	public String toString() {
		return "*.utils.Location:[x=\""+x+"\", y=\""+y+"\"]";
	}
}
