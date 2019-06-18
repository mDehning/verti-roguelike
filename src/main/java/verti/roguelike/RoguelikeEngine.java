package verti.roguelike;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asciiPanel.AsciiPanel;

public class RoguelikeEngine extends KeyAdapter{

	private static final Logger LOG = LoggerFactory.getLogger(RoguelikeEngine.class);
	
	private static Integer pressedKey = null;
	
	public static void main(String[] args){
		new RoguelikeEngine().run(args);
	}
	public void run(String[] args) {
		// Set up
		JDialog console = new JDialog();
		console.setTitle("Vertis Roguelike");
		console.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		AsciiPanel panel = new AsciiPanel(80, 30);
		panel.setFocusable(true);
		panel.addKeyListener(this);
		
		console.setSize(panel.getPreferredSize());
		console.add(panel);
		console.setVisible(true);
		
		
		while(true) {
			Integer key = waitForInput();
			LOG.info("Key Pressed: " + key);
			if(key == KeyEvent.VK_ESCAPE)
				break;
		}
		// Tear Down
		console.setModal(false);
		console.setVisible(false);
		console.dispose();
		System.exit(0);
	}
	
	protected synchronized Integer waitForInput() {
		
		// Wait till you get a key press
		if(pressedKey == null) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		// Process that key
		Integer myKey = pressedKey;
		
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
				if(!event.isActionKey()) {
					pressedKey = event.getKeyCode();
					this.notifyAll();
				}
			}
		}
		
	}

	
}
