package verti.roguelike.util;

import java.awt.Point;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RLAction {
	private Point 		move;
	private Boolean		fullscreen;
	private Boolean		exit;
	
	public Integer dx() {
		if(move != null)
			return (int) move.getX();
		return 0;
	}
	
	public Integer dy() {
		if(move != null) {
			return (int) move.getY();
		}
		return 0;
	}
}
