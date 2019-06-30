package verti.roguelike.util.mapObjects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representing a (more or less) static Map Tile
 * @author Vertixico
 *
 */
@Data
@NoArgsConstructor
public class Tile {

	private Boolean blocked;
	private Boolean opaque;
	
	public Tile(Boolean blocked) {
		this(blocked, blocked);
	}
	public Tile(Boolean blocked, Boolean opaque) {
		this.blocked = blocked;
		this.opaque = opaque;
	}
}
