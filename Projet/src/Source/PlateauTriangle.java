package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateauTriangle implements StrategyPlateau {
	
	private Map<List<Integer>, Boolean> plateauBool;
	private Map<List<Integer>, Carte> plateau;
	
	int[][] borne = {  {-3,3} , {-2,2} , {-1,1}};
			
	@Override
	public Map<List<Integer>, Boolean> ouAjouterCarte(Map<List<Integer>, Carte> plateau) {
		this.plateauBool = new HashMap<List<Integer>,Boolean>();
		this.plateau= plateau;
	
		/* On teste toutes les cases adjacentes aux cartes
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
				if (this.carteBool(position)) {
					this.plateauBool.put(position, true);
				}
				
				position = new ArrayList<Integer>();
				position.add(positionMap.get(0));
				position.add(positionMap.get(1)+i);
				if (this.carteBool(position)) {
					this.plateauBool.put(position, true);
				}
			}
			
		}	
		return this.plateauBool;
	}

	@Override
	public Map<List<Integer>, Boolean> ouBougerCarte(Map<List<Integer>, Carte> plateau, List<Integer> positionCarte) {
		this.plateauBool = new HashMap<List<Integer>,Boolean>();
		this.plateau= plateau;
		Carte carteABouger = plateau.get(positionCarte);
		System.out.print(carteABouger);
		this.plateau.remove(positionCarte);
		this.plateauBool = this.ouAjouterCarte(this.plateau);
		this.plateau.put(positionCarte,carteABouger);
		this.plateauBool.remove(positionCarte);
		
		return plateauBool;
	}

	@Override
	public void afficherPlateau(Map<List<Integer>, Carte> plateau, Map<List<Integer>, Boolean> plateauBool) {
		
		List<Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		System.out.println("");
		System.out.print("    ");
		for (int i=-3;i<=3;i++) {
			if (i>=0) {
				System.out.print(i + "    ");
			}
			else {
				System.out.print(i + "   ");
			}
		}
		
		System.out.println(" ");
		
		// ligne 3
		System.out.print(2 + " ");
		System.out.print("     ");
		System.out.print("     ");
		
		for (int j=-1;j<=1;j++) {
			position.set(0,2);
			position.set(1,j);
			if(plateau.containsKey(position)) {
				System.out.print(" " + plateau.get(position) + " ");
			}
			else if(plateauBool.containsKey(position)) {
				System.out.print(" -+- ");
			}
			else {
				System.out.print(" --- ");
			}
		}
		System.out.println(""); // Retour chariot
		
		// ligne 2
		System.out.print(1 + " ");
		System.out.print("     ");
		
		for (int j=-2;j<=2;j++) {
			position.set(0,1);
			position.set(1,j);
			if(plateau.containsKey(position)) {
				System.out.print(" " + plateau.get(position) + " ");
			}
			else if(plateauBool.containsKey(position)) {
				System.out.print(" -+- ");
			}
			else {
				System.out.print(" --- ");
			}
		}
		System.out.println(""); // Retour chariot
		
		// ligne 1
		System.out.print(0 + " ");
		
		for (int j=-3;j<=3;j++) {
			position.set(0,0);
			position.set(1,j);
			if(plateau.containsKey(position)) {
				System.out.print(" " + plateau.get(position) + " ");
			}
			else if(plateauBool.containsKey(position)) {
				System.out.print(" -+- ");
			}
			else {
				System.out.print(" --- ");
			}
		}
		
		
		
		System.out.println(""); // Retour chariot
	}
		
	

	@Override
	public void getBorne(Map<List<Integer>, Carte> plateau) {
		
	}

	public Boolean carteBool(List<Integer> position) {
		int posY = position.get(0);
		int posX = position.get(1);
		
		if (posY > 3){
			return false;
		}
		else if (posY < 0){
			return false;
		}
		else if (posX > this.borne[posY][1]){
			return false;
		}
		else if (posX < this.borne[posY][0]){
			return false;
		}
		else if (!(this.plateau.containsKey(position))){
			return true;
		}
		else {
			return false;
		}
	}
}

