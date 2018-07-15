package de.wingaming.parable.game.side.entity;

import de.wingaming.parable.game.side.GameSideView;
import de.wingaming.parable.menue.background.Vertex;
import de.wingaming.parable.utils.Location;

public class LivingEntity extends VertexEntity {
	
	private double health, maxHealth;
	
	public LivingEntity(Location location, Vertex[][] matrix, double maxHealth) {
		super(location, matrix);
		
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	public void damage(double damage) {
		this.health -= damage;
		
		if (this.health <= 0) GameSideView.INSTANCE.killEntity(this);
	}
	
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
}
