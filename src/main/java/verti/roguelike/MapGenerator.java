package verti.roguelike;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asciiPanel.AsciiPanel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import verti.roguelike.domain.GameMap;
import verti.roguelike.modules.RenderModule;
import verti.roguelike.util.mapObjects.Rectangle;

@ToString
@Slf4j
@NoArgsConstructor
public class MapGenerator {

	
	private GameMap map;
	private Integer mapWidth = 80;
	private Integer mapHeight = 45;
	private Integer roomMaxSize = 20;
	private Integer roomMinSize = 6;
	private Integer maxRooms = 30;
	
	// For testing purposes, give panel to let the generator render after each step
	private AsciiPanel panel;
	
	public MapGenerator panel(AsciiPanel panel) {
		this.panel = panel;
		return this;
	}
	public MapGenerator mapWidth(int width) {
		if(width > 0)
			mapWidth = width;
		return this;
	}
	
	public MapGenerator mapHeight(int height) {
		if(height > 0)
			mapHeight = height;
		return this;
	}
	
	public MapGenerator roomMaxSize(int size) {
		if(size > 0)
			roomMaxSize = size;
		return this;
	}
	
	public MapGenerator roomMinSize(int size) {
		if(size > 0)
			roomMinSize = size;
		return this;
	}
	
	public MapGenerator maxRooms(int number) {
		// Constraint: There always have to be at least ONE room
		if(number > 1)
			maxRooms = number;
		return this;
	}
	
	protected void paint() {
		if(panel != null)
			RenderModule.renderAll(panel, new ArrayList<>(), map);
	}
	public GameMap build() {
		map = new GameMap(mapWidth, mapHeight);
		
		// Have a list / register of all the rooms
		List<Rectangle> rooms = new ArrayList<>();
		
		
		// Just making sure we did not accidentally switched max and min sizes
		Integer maxSize = Math.max(roomMaxSize, roomMinSize);
		Integer minSize = Math.min(roomMaxSize, roomMinSize);
		
		// Clamp the max Size down to minimum of mapHeight and mapWidth if required
		maxSize = Math.min(maxSize, Math.min(mapWidth, mapHeight));
		
		Random generator = new Random();
		Rectangle lastRoom = null;
		
		log.info(String.format("Generating maximum %s rooms. Settings: %s", maxRooms, this));
		for(int i = 0; i < maxRooms; i ++) {
			// Generate random Dimensions for a room
			int w = generator.nextInt(maxSize - minSize) + minSize;
			int h = generator.nextInt(maxSize - minSize) + minSize;
			
			// Random position of the upper left corner without leaving the boundaries
			int x = generator.nextInt(mapWidth - w - 1);
			int y = generator.nextInt(mapHeight - h - 1);
			
			Rectangle newRoom = new Rectangle(x, y, w, h);
			Point center = newRoom.center();
			
			Boolean intersectionFree = true;
			if(i > 0) {
				log.debug("Checking for intersecting rooms, there are " + rooms.size() + " otehr rooms to check for\n======================");
				for(Rectangle otherRoom : rooms) {
					log.debug(String.format("Checking room %s", otherRoom));
					if(newRoom.intersect(otherRoom)) {
						log.debug(String.format("New Room %s intersects with the room %s", newRoom, otherRoom));
						intersectionFree = false;
						break;
					}
				}
			}
			
			log.info("Done checking for intersecting rooms.");
			
			if(intersectionFree) {
				log.info("No intersections found. Playing the room!");
				map.placeRoom(newRoom);
				
				paint();
				
				if(i == 0) {
					// Set a spawn in the first room so we can place the player here later
					map.setSpawn(center);
				} else if(lastRoom != null){
					// Connect the room with the previous room with a tunnel
					Point oldCenter = lastRoom.center();
					// Flip a coin To determine if you go horizontal first, vertical second
					if(generator.nextBoolean()) {
						map.placeTunnel_horizontal((int) oldCenter.getX(), (int) center.getX(), (int) center.getY());
						map.placeTunnel_vertical((int) oldCenter.getX(), (int) oldCenter.getY(), (int) center.getY());
					} else {
						map.placeTunnel_vertical((int) center.getX(), (int) oldCenter.getY(), (int) center.getY());
						map.placeTunnel_horizontal((int) oldCenter.getX(), (int) center.getX(), (int) oldCenter.getY());
					}
					paint();
				}
			} else { 
				// We cannot place this room, so continue with the next try
				log.warn("This room intersected with another. Abadonning this room!");
				continue;
			}
			
			lastRoom = newRoom;
			rooms.add(newRoom);
		}
		
		return map;
	}
	
}
