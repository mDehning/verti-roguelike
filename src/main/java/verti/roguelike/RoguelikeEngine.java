package verti.roguelike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import verti.roguelike.domain.Entity;
import verti.roguelike.domain.GameMap;
import verti.roguelike.modules.RenderModule;
import verti.roguelike.util.RLAction;

public class RoguelikeEngine{

	private static final Logger LOG = LoggerFactory.getLogger(RoguelikeEngine.class);
	
	private static KeyEvent pressedKey = null;
	private static Boolean consoleClosed = false;
	
	public static void main(String[] args){
		new RoguelikeEngine().run(args);
	}
	public void run(String[] args) {
		// Set up
		InputHandler inputHandler = new InputHandler();
		
		JDialog console = new JDialog();
		console.setTitle("Vertis Roguelike");
		console.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		Integer screenWidth 	= 80;
		Integer screenHeight 	= 50;
		
		Integer mapWidth = 80;
		Integer mapHeight = 45;
		
		AsciiFont font = AsciiFont.DRAKE_10x10;
		AsciiPanel panel = new AsciiPanel(screenWidth, screenHeight, font);
		panel.setFocusable(true);
		panel.addKeyListener(inputHandler);
		
		console.add(panel);
		console.pack();	// Adjusts the size of the window to the size of the Panel
		
		console.setVisible(true);
		console.addWindowListener(inputHandler);
		
//		GameMap gameMap = new GameMap(mapWidth, mapHeight);
		GameMap gameMap = new MapGenerator().panel(panel).build();
		
		
		
		// Creating the Entities of the project
		List<Entity> entities = new ArrayList<>(); 
		Entity player = new Entity((int) gameMap.getSpawn().getX(), (int) gameMap.getSpawn().getY(), '@', AsciiPanel.white);
		entities.add(player);
		Entity npc = new Entity((int) gameMap.getSpawn().getX() - 1, (int) gameMap.getSpawn().getY() + 4, '@', AsciiPanel.red);
		entities.add(npc);
		
		
		LOG.debug(String.format("Value X %s, Value Y %s", player.getX(), player.getY()));
		
		while(!Boolean.TRUE.equals(consoleClosed)) {
			panel.setForeground(AsciiPanel.white);
			panel.setDefaultBackgroundColor(AsciiPanel.black);
			RenderModule.renderAll(panel, entities, gameMap);
			
			panel.repaint();
			
			RLAction action = inputHandler.waitForInput();
			LOG.info("Action triggered: " + action);
			
			// TODO: This is currently highly inefficient, as EVERY entity is getting erased and redrawn regardles if they move at all.
			RenderModule.clearAll(panel, entities);
			
			if(Boolean.TRUE.equals(action.getExit()))
				break;
			
			if(action.getMove() != null) {
				if(!gameMap.isBlocked(player.getX() + action.dx(), player.getY() + action.dy())) {
					player.move(action.dx(), action.dy());
				}
				
			}
			
			if(action.getFullscreen() != null) {
				// TODO: implement some kind of Fullscreen !?
			}
		}
		// Tear Down
		console.setModal(false);
		console.setVisible(false);
		console.dispose();
		System.exit(0);
	}
	
	public static void setConsoleClosed(Boolean state) {
		consoleClosed = state;
	}

	
}
