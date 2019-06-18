package verti.roguelike;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import verti.roguelike.util.RLAction;

public class InputHandler extends KeyAdapter implements WindowListener{

	private static KeyEvent pressedKey = null;
	
	/**
	 * This method will wait till the "pressedKey" Buffer is filled with anything - and then
	 * passes this up to the caller method. 
	 * Note: Synchronized is needed to be able to call the wait() method.
	 * In future iterations maybe more then the keyCode will be passed up - a simplified keyEvent 
	 * (or the event itself under certain conditions) could be possible. Maybe even required for complex 
	 * Key combinations like shift + ctrl + X
	 */
	protected synchronized RLAction waitForInput() {
		
		// Wait till you get a key press
		if(pressedKey == null) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		
		// Process that key
		RLAction action = new RLAction();
		if(pressedKey != null) {
			switch(pressedKey.getKeyCode()) {
			case KeyEvent.VK_UP: 	action.setMove(new Point(0, -1)); 	break;
			case KeyEvent.VK_DOWN:	action.setMove(new Point(0, 1)); 	break;
			case KeyEvent.VK_LEFT: 	action.setMove(new Point(-1, 0)); 	break;
			case KeyEvent.VK_RIGHT: action.setMove(new Point(1, 0)); 	break;
			case KeyEvent.VK_ENTER: if(pressedKey.isAltDown()) action.setFullscreen(true); break;
			case KeyEvent.VK_ESCAPE: action.setExit(true); break;
			default:
			}
		}
		
		// Clear Buffer
		pressedKey = null;
		
		return action;
		
		
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
			RoguelikeEngine.setConsoleClosed(true);
			notifyAll();
		}
	}
	public void windowClosing(WindowEvent e) {	}
	public void windowDeactivated(WindowEvent e) {	}
	public void windowDeiconified(WindowEvent e) {	}
	public void windowIconified(WindowEvent e) {	}
	public void windowOpened(WindowEvent e) {	}
}
