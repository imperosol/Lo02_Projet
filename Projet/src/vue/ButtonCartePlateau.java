package vue;

import java.awt.Dimension;
import java.util.List;


import modele.Carte;

public class ButtonCartePlateau extends ButtonCarte{
	
	private List<Integer> position;
	
	private static final long serialVersionUID = 1L;

	public ButtonCartePlateau(Carte carte, List<Integer> position) {
		super(carte.getFileName());
		
		this.position = position;
	}
	
	public ButtonCartePlateau(Carte carte, List<Integer> position,Dimension dimCarte) {
		super(carte.getFileName(),dimCarte);
	
		this.position = position;
	}
	
	public ButtonCartePlateau(List<Integer> position) {
		super();
		this.position = position;	
	}
	
	public List<Integer> getPosition(){
		return position;
	}
	

}
