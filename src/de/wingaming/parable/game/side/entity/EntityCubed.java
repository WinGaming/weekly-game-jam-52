package de.wingaming.parable.game.side.entity;

import de.wingaming.parable.menue.background.Vertex;
import de.wingaming.parable.utils.Location;
import javafx.scene.paint.Color;

public class EntityCubed extends VertexEntity {

	public EntityCubed(Location location) {
		super(location, null);
		
		Vertex[][] matrix = new Vertex[5][5];
		
		setColor(Color.RED);
		
		matrix[1][0] = new Vertex(50, 0, null);
		matrix[2][0] = new Vertex(150, 0, null);
		matrix[3][0] = new Vertex(250, 0, null);
		matrix[4][0] = new Vertex(350, 0, null);
		
		matrix[0][1] = new Vertex(0, 50, null);
		matrix[1][1] = new Vertex(50, 50, null);
		matrix[2][1] = new Vertex(150, 50, null);
		matrix[3][1] = new Vertex(250, 50, null);
		
		matrix[1][2] = new Vertex(150, 100, null);
		
		setMatrix(matrix);
	}
}
