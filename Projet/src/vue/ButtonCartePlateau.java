package vue;

import java.util.List;

import modele.Carte;

public class ButtonCartePlateau extends ButtonCarte{
	
	private Carte carte;
	private List<Integer> position;

	public ButtonCartePlateau(Carte carte, List<Integer> position) {
		super(carte.getFileName());
		
		this.carte = carte;
		this.position = position;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
