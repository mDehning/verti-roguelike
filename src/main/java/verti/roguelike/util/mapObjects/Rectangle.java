package verti.roguelike.util.mapObjects;

import java.awt.Point;

import lombok.Data;

@Data
public class Rectangle {


	public Rectangle() {
		x1 = 0; x2 = 0; 
		y1 = 0; y2 = 0;
	}
	public Rectangle(int x, int y, int w, int h) {
		this.x1 = x;
		this.y1 = y;
		this.x2 = x + w;
		this.y2 = y + h;
	}
	private Integer x1;
	private Integer y1;
	
	private Integer x2;
	private Integer y2;
	
	
	public Integer getWidth() {
		if(x2 == null || x1 == null) return 0;
		return Math.abs(x2 - x1);
	}
	public Integer getHeight() {
		if(y2 == null || y1 == null) return 0;
		return Math.abs(y2 - y1);
	}
	
	public Point center() {
		int center_x = (x1 + x2) / 2;
		int center_y = (y1 + y2) / 2;
		return new Point(center_x, center_y);
	}
	
	public Boolean intersect(Rectangle other) {
		if(other == null) return false;
		return(x1 <= other.getX2() && x2 >= other.getX1()
				&& y1 <= other.getY2() && y2 >= other.getY1());
		
	}
}
