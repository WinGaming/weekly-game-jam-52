package de.wingaming.parable;

import de.wingaming.parable.game.TileType;
import de.wingaming.parable.game.World;
import de.wingaming.parable.game.side.GameSideView;
import de.wingaming.parable.input.KeyboardManager;
import de.wingaming.parable.input.Mouse;
import de.wingaming.parable.menue.UIExtras;
import de.wingaming.parable.menue.UIMainMenue;
import de.wingaming.parable.menue.UIStartOptions;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	
	public static double WIDTH = 1280, HEIGHT = 720;
	public static GraphicsContext gc;
	public static Stage window;
	public static Scene scene;
	public static Renderer renderer;
	public static Font font;
	public static String fontName;
	
	public static final boolean DEBUG = false;
	
	public void start(Stage window) throws Exception {
		font = Font.loadFont(Main.class.getResourceAsStream("assets/fonts/Roboto-Light.ttf"), 10);
		fontName = font.getName();
		
		window.setTitle("N4m�");
		window.setWidth(WIDTH);
		window.setHeight(HEIGHT);
		window.setResizable(false);
		window.initStyle(StageStyle.UNDECORATED);
		
		Main.window = window;
		
		Group group = new Group();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		scene = new Scene(group);

		scene.setOnKeyPressed(e -> KeyboardManager.press(e.getCode()));
		scene.setOnKeyReleased(e -> KeyboardManager.release(e.getCode()));
		
		scene.setOnMousePressed(e -> { Mouse.setPressed(true); Mouse.setLastButton(e.getButton()); });
		scene.setOnMouseReleased(e -> Mouse.setPressed(false));
		
		group.getChildren().add(canvas);
		
		window.setScene(scene);
		window.show();
		
		renderer = new Renderer();
		renderer.start();
		
		renderer.setCurrentUI(UIMainMenue.INSTANCE);
		
		scene.setOnMouseClicked(e ->  {
			if (!DEBUG) return;
			if (Main.renderer.getCurrentUI() != GameSideView.INSTANCE) return;
			
			int tileX = (int) ((e.getX() + GameSideView.INSTANCE.getWorld().getCamera().getX()) / World.TILE_SIZE);
			int tileY = (int) ((e.getY() + GameSideView.INSTANCE.getWorld().getCamera().getY()) / World.TILE_SIZE);
			
			if (GameSideView.INSTANCE.getWorld() != null)
				if (KeyboardManager.isDown(KeyCode.CONTROL))
					GameSideView.INSTANCE.getWorld().setTopTile(tileX, tileY, e.getButton() == MouseButton.PRIMARY ? TileType.getCurrentType() : null);
				else
					GameSideView.INSTANCE.getWorld().setTile(tileX, tileY, e.getButton() == MouseButton.PRIMARY ? TileType.getCurrentType() : null);
		});
		
		scene.setOnMouseDragged(e -> {
			if (!DEBUG) return;
			if (Main.renderer.getCurrentUI() != GameSideView.INSTANCE) return;
			
			int tileX = (int) ((e.getX() + GameSideView.INSTANCE.getWorld().getCamera().getX()) / World.TILE_SIZE);
			int tileY = (int) ((e.getY() + GameSideView.INSTANCE.getWorld().getCamera().getY()) / World.TILE_SIZE);
			
			if (GameSideView.INSTANCE.getWorld() != null)
				if (KeyboardManager.isDown(KeyCode.CONTROL))
					GameSideView.INSTANCE.getWorld().setTopTile(tileX, tileY, e.getButton() == MouseButton.PRIMARY ? TileType.getCurrentType() : null);
				else
					GameSideView.INSTANCE.getWorld().setTile(tileX, tileY, e.getButton() == MouseButton.PRIMARY ? TileType.getCurrentType() : null);
		});
		
		//Preloading classes:
		UIExtras.a();
		UIStartOptions.a();
	}
	
	public static void resize() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
