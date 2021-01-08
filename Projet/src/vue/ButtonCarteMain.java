package vue;

import modele.Carte;

public class ButtonCarteMain extends ButtonCarte {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int position;
	private Carte carte;
	
	public ButtonCarteMain(Carte carte, int position) {
		super(carte.getFileName());
		this.carte = carte;
		this.position = position;
	}
	

	
	public int getPosition() {
		return position;
	}
	
	public Carte getCarte() {
		return carte;
	}

}
