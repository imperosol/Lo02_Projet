package source;

import java.util.List;
import java.util.Map;

public interface StrategyPlateau {
	public Map<List<Integer>, Boolean> ouAjouterCarte(Map<List<Integer>,Carte> plateau);
	
	public Map<List<Integer>, Boolean> ouBougerCarte(Map<List<Integer>,Carte> plateau, List<Integer> positionCarte);
	
	public void afficherPlateau(Map<List<Integer>,Carte> plateau, Map<List<Integer>,Boolean> plateauBool);
}
