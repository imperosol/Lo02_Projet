package modele.plateau;

import java.util.List;
import java.util.Map;

import modele.Carte;

public interface StrategyPlateau {
	public Map<List<Integer>, Boolean> ouAjouterCarte(Map<List<Integer>,Carte> plateau);
	
	public Map<List<Integer>, Boolean> ouBougerCarte(Map<List<Integer>,Carte> plateau, List<Integer> positionCarte);
	
	public void afficherPlateau(Map<List<Integer>,Carte> plateau, Map<List<Integer>,Boolean> plateauBool);
	
	public int getWidth();
	
	public int getHeight();
	
	public int getMinWidth();
	
	public int getMinHeight();
}
