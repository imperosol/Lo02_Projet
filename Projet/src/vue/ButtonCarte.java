package vue;

import java.awt.Button;
import java.util.List;

import javax.swing.JToggleButton;

import modele.Carte;

public class ButtonCarte extends JToggleButton{

	private static final long serialVersionUID = 1L;
	//Rajouter attribut IA pour rendre boutton inactif  this.setEnabled(false);
	
	public ButtonCarte(){
		super("Vide");
	}
	
	public ButtonCarte(String fileName) {
		super(fileName);
	}
	
}
