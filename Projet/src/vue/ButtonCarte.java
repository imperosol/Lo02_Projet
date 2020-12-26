package vue;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import modele.Carte;

public class ButtonCarte extends JToggleButton {

	private static final long serialVersionUID = 1L;
	//Rajouter attribut IA pour rendre boutton inactif  this.setEnabled(false);
	
	public ButtonCarte(){
		super("Vide");
	}
	
	public ButtonCarte(String fileName) {
		super(new ImageIcon(fileName));
	}
	
	public ButtonCarte(String fileName, Dimension dimension) {
		super(imageSize(fileName,dimension));
	}
	
	
	public static ImageIcon imageSize(String fileName, Dimension dimension) {
		Image img;
		try {
			img = ImageIO.read(new File(fileName));
			Image imgScaled = img.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), 0);
			return new ImageIcon(imgScaled);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
	
