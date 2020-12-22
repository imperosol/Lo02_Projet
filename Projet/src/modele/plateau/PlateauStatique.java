package modele.plateau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.Carte;

public class PlateauStatique {
	
	private Map<List<Integer>, Boolean> plateauBool;
	private Map<List<Integer>, Carte> plateau;
	private int[][] borne;
	private int borneMin;
	private int borneMax;
	private int colMin;
	private int colMax;
	private List<Integer> positionCarteVictoire;
	
	public PlateauStatique(int[][] borne, int borneMin,int borneMax, List<Integer> positionCarteVictoire) {
		this.borne = borne;
		this.positionCarteVictoire = positionCarteVictoire;
		this.borneMin = borneMin;
		this.borneMax = borneMax;
		
		/* determine les colonnes min et max */
		colMin = 0;
		colMax = 0;
		for (int i=0;i<=borneMax-borneMin;i++) {
			if (borne[i][0]<colMin) {
				colMin = borne[i][0];
			}
			if (borne[i][1]>colMax) {
				colMax = borne[i][1];
			}
		}
		System.out.println("col min = " + colMin);
		System.out.println("col max = " + colMax);
		
	}
	
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

	public Map<List<Integer>, Boolean> ouBougerCarte(Map<List<Integer>, Carte> plateau, List<Integer> positionCarte) {
		/* On enleve puis remet la carte a bouger du plateau
		 * On execute la méthode pour savoir où ajouter une carte sur le plateau modifié		
		 */ 
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

	public void afficherPlateau(Map<List<Integer>, Carte> plateau, Map<List<Integer>, Boolean> plateauBool) {
		List<Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		
		int ipos;
		System.out.println("");
		System.out.print("    ");
		for (int i=colMin;i<=colMax;i++) {
			if (i>=0) {
				System.out.print(i + "    ");
			}
			else {
				System.out.print(i + "   ");
			}
		}
		
		for (int i = borneMax-borneMin;i>=0;i--) {
			ipos = i + borneMin;
			if (i + borneMin<0) {
				System.out.print("\n" + ipos + " ");
			}
			else {
				System.out.print("\n" + ipos + "  ");
			}
			
			for (int j =colMin;j<borne[i][0];j++) {
				System.out.print("     ");
			}
			
			for (int j=borne[i][0];j<=borne[i][1];j++) {
				position.set(0,ipos);
				position.set(1,j);
				if(plateau.containsKey(position)) {
					System.out.print(" " + plateau.get(position) + " ");
				}
				else if(plateauBool.containsKey(position)) {
					System.out.print(" -+- ");
				}
				else if (positionCarteVictoire != null) {
					if (position.equals(this.positionCarteVictoire)) {
						System.out.print(" -?- ");
					}
					else {
						System.out.print(" --- ");
					}
				}
				else {
					System.out.print(" --- ");
				}
			}
		}
		System.out.println(""); // Retour chariot
	}
	
	public void initialiserPosition(List<Integer> position) {
		positionCarteVictoire = new ArrayList<Integer>();
		this.positionCarteVictoire = position;
	}
	
	public Boolean carteBool(List<Integer> position) {
		int posY = position.get(0) - borneMin;
		int posX = position.get(1);
		
		// Si dans les bornes
		if (posY > (borneMax - borneMin)){
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
		//Si sur une case non occupée
		
		
		else if (!(this.plateau.containsKey(position))){
			if(this.positionCarteVictoire != null) {
				if (position.equals(this.positionCarteVictoire)) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}

}

