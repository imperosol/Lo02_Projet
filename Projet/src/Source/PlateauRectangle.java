package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateauRectangle implements StrategyPlateau {
	
	private int[] borneLigne = new int[2];
	private int[] borneColonne = new int[2];
	private boolean bordureDefinitif = false;
	// rajouter condition bordure
	
	 private Map<List<Integer>, Boolean> plateauBool;
	 private Map<List<Integer>, Carte> plateau;
	
	@Override
	public void getBorne(Map<List<Integer>, Carte> plateau) {
		List<Integer> position;
		
		this.borneLigne = new int[2];
		this.borneColonne = new int[2];
		
		
		for (Map.Entry<List<Integer>, Carte> mapEntry : plateau.entrySet()) {
			
			position = mapEntry.getKey();
			
			if (position.get(0) < this.borneLigne[0]) {
				this.borneLigne[0] = position.get(0);
			}
			else if (position.get(0) > this.borneLigne[1]) {
				this.borneLigne[1] = position.get(0);
			}
			
			if (position.get(1) < this.borneColonne[0]) {
				this.borneColonne[0] = position.get(1);
			}
			else if (position.get(1) > this.borneColonne[1]) {
				this.borneColonne[1] = position.get(1);
			}
			
		}
		System.out.println("");

		
		int incrementation = 2 - this.borneLigne[1] + this.borneLigne[0];
		this.borneLigne[0]-=incrementation;
		this.borneLigne[1]+=incrementation;
		
		incrementation = 4 - this.borneColonne[1] + this.borneColonne[0];
		this.borneColonne[0]-=incrementation;
		this.borneColonne[1]+=incrementation;
		
		System.out.println(borneLigne[0] + " " + borneLigne[1]);
		System.out.println(borneColonne[0] + " " + borneColonne[1]);
			
	}
	
	@Override
	public Map<List<Integer>, Boolean> ouAjouterCarte(Map<List<Integer>, Carte> plateau) {
		
		this.plateauBool = new HashMap<List<Integer>,Boolean>();
		this.plateau= plateau;
		
		// 1) trouver bornes colonnes/ligne
		getBorne(plateau);
		/* 2) On teste toutes les cases adjacentes aux cartes
		 * Pour chaque cases :
		 * On vérifie qu'il n'y a pas de carte sur cette case
		 * On vérifie que la case soit à 'interieur des bornes
		 * On met cette case dans le map booléen
		 */		
		
		
		for(Map.Entry<List<Integer>, Carte> mapEntry : plateau.entrySet()) {
			List<Integer> positionMap = mapEntry.getKey(); 
			for (Integer i=-1;i<=1;i+=2) { // permet de trouver les 4 voisins;
				
				List<Integer> position = new ArrayList<Integer>();
				position.add(positionMap.get(0) + i);
				position.add(positionMap.get(1));
				this.carteBool(position);
				
				position = new ArrayList<Integer>();
				position.add(positionMap.get(0));
				position.add(positionMap.get(1)+i);
				this.carteBool(position);		
			}
			
		}		
		return this.plateauBool;
	}

	@Override
	public Map<List<Integer>, Boolean> ouBougerCarte(Map<List<Integer>, Carte> plateau, List<Integer> positionCarte) {
		// getBorne(plateau); ? je pense pas
		
		this.plateauBool = new HashMap<List<Integer>,Boolean>();
		this.plateau= plateau;
		
		for (Integer i=-1;i<=1;i+=2) { // permet de trouver les 4 voisins;
			
			List<Integer> position = new ArrayList<Integer>();
			position.add(positionCarte.get(0) + i);
			position.add(positionCarte.get(1));
			this.carteBool(position);
			
			position = new ArrayList<Integer>();
			position.add(positionCarte.get(0));
			position.add(positionCarte.get(1)+i);
			this.carteBool(position);
			
		}
		return this.plateauBool;
	
	}	
				


	@Override
	public void afficherPlateau(Map<List<Integer>, Carte> plateau, Map<List<Integer>, Boolean> plateauBool) {
		List<Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		
		for (int i=this.borneLigne[0];i<=this.borneLigne[1];i++) {
			System.out.println(" ");
			System.out.print(i + " ");
			
			for (int j=borneColonne[0];j<=borneColonne[1];j++) {
				position.set(0,i);
				position.set(1,j);
				if(plateau.containsKey(position)) {
					System.out.print(plateau.get(position));
				}
				else if(plateauBool.containsKey(position)) {
					System.out.print(" + ");
				}
				else {
					System.out.print(" - ");
				}
			}
		}
		
	}
	
	public void carteBool(List<Integer> position) {
		if (!(position.get(0) >= this.borneLigne[0])){

		}
		else if (!(position.get(0) <= this.borneLigne[1])){

		}
		else if (!(position.get(1) >= this.borneColonne[0])){

		}
		else if (!(position.get(1) <= this.borneColonne[1])){
			
		}
		else if (!(this.plateau.containsKey(position))){
			this.plateauBool.put(position, true);
		}
	}

}
