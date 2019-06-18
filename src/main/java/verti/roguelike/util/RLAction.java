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
}
