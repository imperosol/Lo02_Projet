package vue;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class ButtonCarte extends JToggleButton {

	private static final long serialVersionUID = 1L;
	//Rajouter attribut IA pour rendre boutton inactif  this.setEnabled(false);
	
	public ButtonCarte(){
		super(" --?-- ");
	}
	
	public ButtonCarte(String fileName) {
		super(new ImageIcon(ButtonCarte.class.getResource(fileName)));
	}
	
	public ButtonCarte(String fileName, Dimension dimension) {
		super(imageSize(fileName,dimension));
	}
	
	
	public static ImageIcon imageSize(String fileName, Dimension dimension) {
		Image img;
		try {
			img = ImageIO.read(ButtonCarte.class.getResource(fileName));
			Image imgScaled = img.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), 0);
			return new ImageIcon(imgScaled);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
	
