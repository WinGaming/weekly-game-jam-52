package de.wingaming.parable.input;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseButton;

public class Mouse {
	
	private static boolean pressed;
	private static MouseButton button;
	private static List<MouseListener> listeners = new ArrayList<>();
	
	public static void setPressed(boolean pressed) {
		Mouse.pressed = pressed;
		
		for (MouseListener mouseListener : listeners) {
			if (pressed) mouseListener.onPress();
			else mouseListener.onRelease();
		}
	}
	
	public static void registerListener(MouseListener listener) {
		if (!listeners.contains(listener)) listeners.add(listener);
	}
	
	public static void unregisterListener(MouseListener listener) {
		if (listeners.contains(listener)) listeners.remove(listener);
	}
	
	public static boolean isPressed() {
		return pressed;
	}
	
	public static MouseButton getLastutton() {
		return button;
	}

	public static void setLastButton(MouseButton button) {
		Mouse.button = button;
	}
}