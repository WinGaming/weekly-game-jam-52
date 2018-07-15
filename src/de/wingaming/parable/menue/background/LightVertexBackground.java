package de.wingaming.parable.menue.background;

import de.wingaming.parable.Main;
import javafx.scene.paint.Color;

public class LightVertexBackground {
	
	private Color color;
	private Vertex[][] verticies = new Vertex[11][11];
	
	public LightVertexBackground(Color color) {
		//Generate
		for (int x = 0; x < verticies.length; x++) {
			for (int y = 0; y < verticies[x].length; y++) {
				boolean fixed = x == 0 || y == 0 || x == verticies.length-1 || y == verticies[x].length-1;
				
				//Sets fixed randomly to true
//				if (!fixed) fixed = new Random().nextInt(30) == 10;
				
				verticies[x][y] = new Vertex(x * 128, y * 72, null, fixed);
			}
		}
		
		//Connect verticies
		for (int x = 0; x < verticies.length; x++) {
			for (int y = 0; y < verticies[x].length; y++) {
				Vertex vertex = verticies[x][y];
				if (vertex == null) continue;
				
				Vertex top = y == 0 ? null : verticies[x][y-1];
				Vertex down = y == verticies[x].length-1 ? null : verticies[x][y+1];
				Vertex left = x == 0 ? null : verticies[x-1][y];
				Vertex right = x == verticies.length-1 ? null : verticies[x+1][y];
				
				if (top != null) vertex.addVertex(top);
				if (down != null) vertex.addVertex(down);
				if (left != null) vertex.addVertex(left);
				if (right != null) vertex.addVertex(right);
			}
		}
		
		this.color = color;
	}
	
	public void setOpacityBordersForVertexes(float min, float max) {
		for (int x = 0; x < verticies.length; x++) {
			for (int y = 0; y < verticies[x].length; y++) {
				Vertex vertex = verticies[x][y];
				if (vertex == null) continue;
				
				vertex.setMinOpacity(min);
				vertex.setMaxOpacity(max);
			}
		}
	}
	
	public void update() {
		for (int x = 0; x < verticies.length; x++) {
			for (int y = 0; y < verticies[x].length; y++) {
				Vertex vertex = verticies[x][y];
				if (vertex == null) continue;
				
				vertex.update();
			}
		}
	}
	
	public void render() {
		for (int x = 0; x < verticies.length; x++) {
			for (int y = 0; y < verticies[x].length; y++) {
				Vertex vertex = verticies[x][y];
				if (vertex == null) continue;
				
//				Main.gc.setFill(Color.BLACK);
//				Main.gc.fillRoundRect(vertex.getX() - 5, vertex.getY() - 5, 10, 10, 90, 90);
				
				//top-down-right
				Vertex under = y == verticies[x].length-1 ? null : verticies[x][y+1];
				Vertex right = x == verticies[x].length-1 ? null : verticies[x+1][y];
				
				if (under != null && right != null) {
					float opacity = (vertex.getColorGradian() + under.getColorGradian() + right.getColorGradian()) / 3;
					Color fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
					
					Main.gc.setFill(fillColor);
					Main.gc.fillPolygon(new double[] {vertex.getX(), under.getX(), right.getX()}, new double[] {vertex.getY(), under.getY(), right.getY()}, 3);
				}
				
				//bottom-left-top
				Vertex top = y == 0 ? null : verticies[x][y-1];
				Vertex left = x == 0 ? null : verticies[x-1][y];
				
				if (top != null && left != null) {
					float opacity = (vertex.getColorGradian() + top.getColorGradian() + left.getColorGradian()) / 3;
					Color fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
					
					Main.gc.setFill(fillColor);
					Main.gc.fillPolygon(new double[] {vertex.getX(), top.getX(), left.getX()}, new double[] {vertex.getY(), top.getY(), left.getY()}, 3);
				}
			}
		}
	}
}
