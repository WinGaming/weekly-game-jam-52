package de.wingaming.parable.menue.options;

public abstract class Option {
	
	private String text;
	
	public Option(String text) {
		this.text = text;
	}
	
	public abstract void update(boolean hover);
	public abstract void render(boolean hover);
	public abstract void click(double x, double ry);
	
	public String getText() {
		return text;
	}
}
