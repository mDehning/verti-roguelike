package verti.roguelike.modules;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import asciiPanel.AsciiPanel;
import verti.roguelike.domain.Entity;
import verti.roguelike.domain.GameMap;

/**
 * Module Class to manage functions to draw and clear elements on the screen
 * @author Vertixico
 *
 */
public class RenderModule {

	public static void renderAll(AsciiPanel panel, List<Entity> entities, GameMap map, Map<String, Color> colors) {
		
		// Drawing all Tiles in the game map
		for(int y = 0; y < map.getHeight(); y++) {
			for(int x = 0; x < map.getWidth(); x++) {
				Boolean isWall = Boolean.TRUE.equals(map.getTiles()[x][y].getOpaque());
				if(isWall) {
					panel.write(' ', x, y, AsciiPanel.white, colors.get("darkWall"));
				} else {
					panel.write(' ', x, y, AsciiPanel.white, colors.get("darkGround"));
				}
			}
		}
		for(Entity entity : entities) {
			drawEntity(panel, entity);
		}
		
		panel.repaint();
	}
	
	public static void clearAll(AsciiPanel panel, List<Entity> entities) {
		for(Entity entity : entities)
			clearEntity(panel, entity);
	}
	
	public static void drawEntity(AsciiPanel panel, Entity entity) {
		if(entity.getBackground() != null) {
			panel.write(entity.getSymbol(), entity.getX(), entity.getY(), entity.getColor(), entity.getBackground());
		} else {
			panel.write(entity.getSymbol(), entity.getX(), entity.getY(), entity.getColor());
		}
	}
	
	public static void clearEntity(AsciiPanel panel, Entity entity) {
		panel.write(' ', entity.getX(), entity.getY(), panel.getDefaultForegroundColor(), panel.getDefaultBackgroundColor());
	}
}
