package verti.roguelike.modules;

import java.util.List;

import asciiPanel.AsciiPanel;
import verti.roguelike.entities.Entity;

/**
 * Module Class to manage functions to draw and clear elements on the screen
 * @author Vertixico
 *
 */
public class RenderModule {

	public static void renderAll(AsciiPanel panel, List<Entity> entities) {
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
