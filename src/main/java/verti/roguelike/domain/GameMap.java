package verti.roguelike.domain;

import java.awt.Point;

import lombok.Data;
import verti.roguelike.util.mapObjects.Rectangle;
import verti.roguelike.util.mapObjects.Tile;

@Data
public class GameMap {

	private Integer width;
	private Integer height;
	private Tile[][] tiles;
	private Point spawn;
	
	public GameMap(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		tiles = initTiles();
		this.spawn = new Point(width / 2, height / 2);
	}
	

	/**
	 * Convenience Method to check if a specific coordinate on the Map is Blocked for movement / entering.
	 * @param x
	 * @param y
	 * @return false, if the Tile is existing, not blocking and can be entered. True otherwise
	 */
	public Boolean isBlocked(int x, int y) {
		if(tile(x,y) == null) 
			return true;
		return tile(x,y).getBlocked();
	}
	
	/**
	 * Initializes all GameMap Tiles. Sets every Tile of the GameMap to blocking movement and blocking sight on default.
	 * @return
	 */
	private Tile[][] initTiles() {
		Tile[][] tiles = new Tile[width][height];
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++ ) {
				tiles[i][j] = new Tile(true);
			}
		}
		
		return tiles;
	}
	/**
	 * Convenience Method that returns a specified tile in the GameMap array. Returns null if the Tile would be out of bounds
	 * @param x
	 * @param y
	 * @return
	 */
	private Tile tile(int x, int y) {
		if(x >= 0 && x < width && y >= 0 && y < height)
			return tiles[x][y];
		return null;
	}
	
	/**
	 * Convenience Method that sets the blocking and opaque fields of a given Tile of this Map to false
	 * @param x
	 * @param y
	 * @return The tile. This method might return null if the coordinates were out of bounds
	 */
	private Tile carveOutTile(int x, int y) {
		if(tile(x,y) != null) {
			tile(x,y).setBlocked(false);
			tile(x,y).setOpaque(false);
			return tile(x,y);
		}
		return null;
	}
	/**
	 * Takes the defined Rectangle and carves out every tile (except for its walls) of the room.
	 * @param room
	 * @return
	 */
	public GameMap placeRoom(Rectangle room) {
		room= fitRoom(room);
		for(int x = room.getX1() + 1; x < room.getX2() -1; x ++) {
			for(int y = room.getY1() + 1; y < room.getY2() - 1; y++) {
				carveOutTile(x,y);
			}
		}
		return this;
	}
	
	/**
	 * Checks a room for being inside the boundaries of the map. If not, it will resize the room to be small enough
	 * @param room
	 * @return the Room, for convenient method chaining
	 */
	protected Rectangle fitRoom(Rectangle room) {
		if(room.getX1() < 0 )		room.setX1(0);
		if(room.getX2() >= width) 	room.setX2(width-1);
		if(room.getY1() < 0 ) 		room.setY1(0);
		if(room.getY2() >= height) 	room.setY2(height - 1);
		return room;
	}
	
	/** 
	 * Carves out a tunnel with a set y coordinate
	 * @param x1 The starting x coordinate of the tunnel (inclusive)
	 * @param x2 The ending x coordinate of the tunnel (also inclusive)
	 * @param y the y level of the tunnel
	 * @return The GameMap, for convenience and chaining methods
	 */
	public GameMap placeTunnel_horizontal(int x1, int x2, int y) {
		if(x1 > x2) {
			int c = x1;	x1 = x2;	x2 = c;
		}
		for(int x = x1; x <= x2; x ++) {
			carveOutTile(x,y);
		}
		return this;
	}
	
	/**
	 * Carves out a tunnel with a set x coordinate
	 * @param x The x Level of the tunnel
	 * @param y1 The starting y coordinate of the tunnel (inclusive)
	 * @param y2 The ending y coordinate of the tunnel (also inclusive)
	 * @return The GameMap, for convenience and chaining methods
	 */
	public GameMap placeTunnel_vertical(int x, int y1, int y2) {
		if(y1 > y2) {
			int c = y1; y1 = y2; y2 = c;
		}
		for(int y = y1; y <= y2; y ++) {
			carveOutTile(x,y);
		}
		return this;
	}
}
