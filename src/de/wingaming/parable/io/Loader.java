package de.wingaming.parable.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import de.wingaming.parable.game.TileType;
import de.wingaming.parable.game.World;

public class Loader {
	
	public static World loadWorld(String fileName) {
		try {
//			InputStream is = Main.class.getResourceAsStream("saves/"+fileName+".world");
			File file = new File("saves/"+fileName+".world");
			if (!file.exists()) {
				World w = new World();
				
				return w;
			}

			World world = new World();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			while((line = reader.readLine()) != null) {
				if (line.contains(">>") && !line.contains(">>>")) {
					String[] data = line.split(">>");
					int dx = 0;
					int y = Integer.parseInt(data[0]);
					String blockString = data[1];
					
					String[] blocksdata = blockString.split(";");
					
					for (String blockdata : blocksdata) {
						int count;
						TileType type;
						
						if (!blockdata.contains(",")) {
							count = 1;
							type = TileType.getType(blockdata);
						} else {
							String[] blockdataData = blockdata.split(",");
							count = Integer.parseInt(blockdataData[0]);
							type = TileType.getType(blockdataData[1]);
						}
						
						for (int i = 0; i < count; i++) {
							world.setTile(i+dx, y, type);
						}

						dx += count;
					}
				} else if (line.contains(">>>")) {
					String[] data = line.split(">>>");
					int dx = 0;
					int y = Integer.parseInt(data[0]);
					String blockString = data[1];
					
					String[] blocksdata = blockString.split(";");
					
					for (String blockdata : blocksdata) {
						int count;
						TileType type;
						
						if (!blockdata.contains(",")) {
							count = 1;
							type = TileType.getType(blockdata);
						} else {
							String[] blockdataData = blockdata.split(",");
							count = Integer.parseInt(blockdataData[0]);
							type = TileType.getType(blockdataData[1]);
						}
						
						for (int i = 0; i < count; i++) {
							world.setTopTile(i+dx, y, type);
						}

						dx += count;
					}
				}
			}
			
			reader.close();
			
			return world;
		} catch (IOException ex) {
			ex.printStackTrace();
			World w = new World();
			
			return w;
		}
	}
	
	public static void saveWorld(String name, World world) {
		File file = new File("saves/"+name+".world");
		
		try {
			if (!file.exists()) file.createNewFile();
			
			TileType lastType = null;
			int typeCount = 0;
			
			StringBuilder builder = new StringBuilder();
			for (int y = 0; y < world.getTiles().length; y++) {
				StringBuilder lineBuilder = new StringBuilder();
				lineBuilder.append(y+">>");
				
				for (int x = 0; x < world.getTiles()[y].length; x++) {
					TileType type = world.getTileTypeAt(x, y);
					
					if (type != lastType) {
						if (typeCount == 1)
							lineBuilder.append(TileType.getSymbol(lastType)+";");
						else if (typeCount > 0)
							lineBuilder.append(typeCount+","+TileType.getSymbol(lastType)+";");
						
						typeCount = 1;
						lastType = type;
					} else {
						typeCount++;
					}
				}
				
				typeCount = 0;
				
//				lineBuilder.append(typeCount+","+TileType.getSymbol(lastType));
				
				if (!lineBuilder.toString().endsWith(">>")) {
					builder.append(lineBuilder.toString() + "\n");
				}
			}
			
			typeCount = 0;
			lastType = null;
			for (int y = 0; y < world.getTopTiles().length; y++) {
				StringBuilder lineBuilder = new StringBuilder();
				lineBuilder.append(y+">>>");
				
				for (int x = 0; x < world.getTopTiles()[y].length; x++) {
					TileType type = world.getTopTileTypeAt(x, y);
					
					if (type != lastType) {
						if (typeCount == 1)
							lineBuilder.append(TileType.getSymbol(lastType)+";");
						else if (typeCount > 0)
							lineBuilder.append(typeCount+","+TileType.getSymbol(lastType)+";");
						
						typeCount = 1;
						lastType = type;
					} else {
						typeCount++;
					}
				}
				
				typeCount = 0;
				
//				lineBuilder.append(typeCount+","+TileType.getSymbol(lastType));
				
				if (!lineBuilder.toString().endsWith(">>")) {
					builder.append(lineBuilder.toString() + "\n");
				}
			}
			
			builder.append("\n");
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(builder.toString());
			writer.close();
			
			System.out.println("Saved!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}