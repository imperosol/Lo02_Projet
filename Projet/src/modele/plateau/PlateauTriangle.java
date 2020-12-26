package modele.plateau;

import java.util.List;
import java.util.Map;

import modele.Carte;

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

	@Override
	public int getLongueur() {
		// TODO Auto-generated method stub
		return super.getLongueur();
	}

	@Override
	public int getLargeur() {
		// TODO Auto-generated method stub
		return super.getLargeur();
	}
	
	@Override
	public int getLongueurMin() {
		// TODO Auto-generated method stub
		return super.getLongueurMin();
	}

	@Override
	public int getLargeurMin() {
		// TODO Auto-generated method stub
		return super.getLargeurMin();
	}
}

