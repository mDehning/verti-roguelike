package verti.roguelike;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import verti.roguelike.domain.Entity;
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
		
		Integer player_x = screenWidth / 2;
		Integer player_y = screenHeight / 2;
		
		// Creating the Entities of the project
		List<Entity> entities = new ArrayList<>(); 
		Entity player = new Entity(screenWidth / 2, screenHeight/ 2, '@', AsciiPanel.white);
		entities.add(player);
		Entity npc = new Entity(screenWidth / 2 - 1, screenHeight/ 2 + 1, '@', AsciiPanel.red);
		entities.add(npc);
		
		LOG.debug(String.format("Value X %s, Value Y %s", player_x, player_y));
		AsciiFont font = AsciiFont.DRAKE_10x10;
		AsciiPanel panel = new AsciiPanel(screenWidth, screenHeight, font);
		panel.setFocusable(true);
		panel.addKeyListener(inputHandler);
		
		
		console.add(panel);
		console.pack();	// Adjusts the size of the window to the size of the Panel

		console.setVisible(true);
		console.addWindowListener(inputHandler);
		
		while(!Boolean.TRUE.equals(consoleClosed)) {
			panel.setForeground(AsciiPanel.white);
			panel.setDefaultBackgroundColor(AsciiPanel.black);
			RenderModule.renderAll(panel, entities);
			
			panel.repaint();
			
			RLAction action = inputHandler.waitForInput();
			LOG.info("Action triggered: " + action);
			
			// TODO: This is currently highly inefficient, as EVERY entity is getting erased and redrawn regardles if they move at all.
			RenderModule.clearAll(panel, entities);
			
			if(Boolean.TRUE.equals(action.getExit()))
				break;
			
			if(action.getMove() != null) {
				panel.write(' ', player.getX(), player.getY());
				player.move(action.dx(), action.dy());
				
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
