package verti.roguelike;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class RoguelikeEngine extends KeyAdapter implements WindowListener{

	private static final Logger LOG = LoggerFactory.getLogger(RoguelikeEngine.class);
	
	private static KeyEvent pressedKey = null;
	private static Boolean consoleClosed = false;
	
	public static void main(String[] args){
		new RoguelikeEngine().run(args);
	}
	public void run(String[] args) {
		// Set up
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
		panel.addKeyListener(this);
		
		
		console.add(panel);
		console.pack();	// Adjusts the size of the window to the size of the Panel

		console.setVisible(true);
		console.addWindowListener(this);
		
		while(!Boolean.TRUE.equals(consoleClosed)) {
			panel.setForeground(AsciiPanel.white);
			panel.setDefaultBackgroundColor(AsciiPanel.black);
			panel.write('@', player_x, player_y, panel.getDefaultForegroundColor());
			panel.repaint();
			
			KeyEvent key = waitForInput();
			LOG.info("Key Pressed: " + key);
			if(key != null && key.getKeyCode() == KeyEvent.VK_ESCAPE)
				break;
		}
		// Tear Down
		console.setModal(false);
		console.setVisible(false);
		console.dispose();
		System.exit(0);
	}
	
	/**
	 * This method will wait till the "pressedKey" Buffer is filled with anything - and then
	 * passes this up to the caller method. 
	 * Note: Synchronized is needed to be able to call the wait() method.
	 * In future iterations maybe more then the keyCode will be passed up - a simplified keyEvent 
	 * (or the event itself under certain conditions) could be possible. Maybe even required for complex 
	 * Key combinations like shift + ctrl + X
	 */
	protected synchronized KeyEvent waitForInput() {
		
		// Wait till you get a key press
		if(pressedKey == null) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		// Process that key
		KeyEvent myKey = pressedKey;
		
		// Clear Buffer
		pressedKey = null;
		
		return myKey;
		
		
	}

	public void keyPressed(KeyEvent event) {
		/*
		 * Supply the waiting main Thread with the current key- if it there is currently none in the Buffer.
		 * Note: This will overshadow any key that was pressed after the current was set
		 */
		synchronized(this) {
			if(pressedKey == null) {
				pressedKey = event;
				this.notifyAll();
			}
		}
		
	}
	public void windowActivated(WindowEvent e) {	}
	public void windowClosed(WindowEvent e) {
		synchronized(this) {
			consoleClosed = true;
			notifyAll();
		}
	}
	public void windowClosing(WindowEvent e) {	}
	public void windowDeactivated(WindowEvent e) {	}
	public void windowDeiconified(WindowEvent e) {	}
	public void windowIconified(WindowEvent e) {	}
	public void windowOpened(WindowEvent e) {	}

	
}
