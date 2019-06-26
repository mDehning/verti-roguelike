package verti.roguelike.domain;

import lombok.Data;

@Data
public class GameMap {

	private Integer width;
	private Integer height;
	private Tile[][] tiles;
	
	public GameMap(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		tiles = initTiles();
	}
	
	public Boolean isBlocked(int x, int y) {
		if(x < 0 || x >= width) return true;
		if(y < 0 || y >= height) return true;
		
		return tiles[x][y].getBlocked();
	}
	private Tile[][] initTiles() {
		Tile[][] tiles = new Tile[width][height];
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++ ) {
				tiles[i][j] = new Tile(false);
			}
		}
		
		for(int i = 30; i <= 32; i++) {
			tiles[i][22].setBlocked(true);
			tiles[i][22].setOpaque(true);
		}
		
		return tiles;
	}
}
