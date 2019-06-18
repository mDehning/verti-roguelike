package verti.roguelike;

import java.awt.Window;
import java.io.IOException;

import javax.swing.JDialog;

import asciiPanel.AsciiPanel;

public class RougelikeEngine {

	public static void main(String[] args){
		JDialog console = new JDialog();
		console.setTitle("Vertis Roguelike");
		//console.setSize(800, 600);
		console.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		AsciiPanel panel = new AsciiPanel(80, 30);

		console.setSize(panel.getPreferredSize());
		console.add(panel);
		console.setVisible(true);
		
		System.out.println("test?");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Kill it!!!");
		
		console.setModal(false);
		console.setVisible(false);
		console.dispose();
		System.out.println("Is it dead yet?");
	//	System.exit(0);
	}
}
