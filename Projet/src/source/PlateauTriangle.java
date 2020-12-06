package source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateauTriangle extends PlateauStatique implements StrategyPlateau  {
	private static final int[][] BORNE = {{-3,3} , {-2,2} , {-1,1}};
			
	public PlateauTriangle() {
		super(BORNE,0,2,null);
	}
	
	@Override
	public Map<List<Integer>, Boolean> ouAjouterCarte(Map<List<Integer>, Carte> plateau) {
		return super.ouAjouterCarte(plateau);
	}

	@Override
	public Map<List<Integer>, Boolean> ouBougerCarte(Map<List<Integer>, Carte> plateau, List<Integer> positionCarte) {
		return super.ouBougerCarte(plateau, positionCarte);
	}

	@Override
	public void afficherPlateau(Map<List<Integer>, Carte> plateau, Map<List<Integer>, Boolean> plateauBool) {
		super.afficherPlateau(plateau, plateauBool);
	}
}

