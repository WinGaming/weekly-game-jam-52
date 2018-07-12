package de.wingaming.parable;

import de.wingaming.parable.menue.UI;
import javafx.animation.AnimationTimer;

public class Renderer extends AnimationTimer {
	
	private UI currentUI;
	
	public void handle(long now) {
		if (currentUI != null) {
			currentUI.update();
			currentUI.render();
		}
	}
	
	public void resize() {
		if (currentUI != null) currentUI.resize();
	}
	
	public void setCurrentUI(UI currentUI) {
		this.currentUI = currentUI;
	}
	
	public UI getCurrentUI() {
		return currentUI;
	}
}