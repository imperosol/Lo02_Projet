package modele.plateau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.Carte;

public class PlateauVariante extends PlateauStatique implements StrategyPlateau {
	private static final int[][] BORNE = { {-1,0}, {-2,1}, {-2,1} , {-2,1} , {-1,0}};
	
	public PlateauVariante(List<Integer> positionCarteVictoire) {
		super(BORNE, -2, 2, positionCarteVictoire);
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


	@Override
	public int getWidth() {
		return super.getWidth();
	}

	@Override
	public int getHeight() {
		return super.getHeight();
	}
	
	@Override
	public int getMinWidth() {
		return super.getMinWidth();
	}

	@Override
	public int getMinHeight() {
		return super.getMinHeight();
	}
}
