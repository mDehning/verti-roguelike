package verti.roguelike.domain;

import java.awt.Color;

import asciiPanel.AsciiPanel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Entity {

	@NonNull
	private Integer x;
	@NonNull
	private Integer y;
	
	private Character symbol = '@';
	private Color color = AsciiPanel.brightYellow;
	
	private Color background;

	public Entity(int x, int y, char symbol, Color color) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		if(color != null)
			this.color = color;
	}
	public void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}
}
