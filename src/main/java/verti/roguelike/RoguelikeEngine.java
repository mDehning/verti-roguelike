package verti.roguelike;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
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

		Integer screen_width 	= 80;
		Integer screen_height 	= 50;
		
		Integer player_x = screen_width / 2;
		
		Integer player_y = screen_height / 2;
		LOG.debug(String.format("Value X %s, Value Y %s", player_x, player_y));
		AsciiFont font = AsciiFont.DRAKE_10x10;
		AsciiPanel panel = new AsciiPanel(screen_width, screen_height, font);
		panel.setFocusable(true);
		panel.addKeyListener(inputHandler);
		
		
		console.add(panel);
		console.pack();	// Adjusts the size of the window to the size of the Panel

		console.setVisible(true);
		console.addWindowListener(inputHandler);
		
		while(!Boolean.TRUE.equals(consoleClosed)) {
			panel.setForeground(AsciiPanel.white);
			panel.setDefaultBackgroundColor(AsciiPanel.black);
			panel.write('@', player_x, player_y, panel.getDefaultForegroundColor());
			panel.repaint();
			
			RLAction key = inputHandler.waitForInput();
			LOG.info("Key Pressed: " + key);
			
			if(Boolean.TRUE.equals(key.getExit()))
				break;
			
			if(key.getMove() != null) {
				panel.write(' ', player_x, player_y, panel.getDefaultForegroundColor());
				player_x += (int) key.getMove().getX();
				player_y += (int) key.getMove().getY();
			}
			
			if(key.getFullscreen() != null) {
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
