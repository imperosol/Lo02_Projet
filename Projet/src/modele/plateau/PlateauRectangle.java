package modele.plateau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modele.Carte;

public class PlateauRectangle implements StrategyPlateau {
	
	private int[] borneLigne = new int[2];
	private int[] borneColonne = new int[2];
	
	
	private Map<List<Integer>, Boolean> plateauBool;
	private Map<List<Integer>, Carte> plateau;
	
	// determine dynamiquement les bornes du plateau
	public void getBorne() {
		List<Integer> position;
		
		this.borneLigne = new int[2];
		this.borneColonne = new int[2];
		
		Set<List<Integer>> listeDeClé = plateau.keySet();
		Iterator<List<Integer>> it = listeDeClé.iterator();
		
		//initialisation
		if (it.hasNext()) {
			position = it.next();
			this.borneLigne[0] = position.get(0);
			this.borneLigne[1] = position.get(0);
			this.borneColonne[0] = position.get(1);
			this.borneColonne[1] = position.get(1);
		}

		
		while(it.hasNext()) {
			// recherche position max/min
			position = it.next();
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
		
		//incrémentation = différence entre borne maximale et minimale
		int incrementation = 2 - this.borneLigne[1] + this.borneLigne[0];
		this.borneLigne[0]-=incrementation;
		this.borneLigne[1]+=incrementation;
		
		incrementation = 4 - this.borneColonne[1] + this.borneColonne[0];
		this.borneColonne[0]-=incrementation;
		this.borneColonne[1]+=incrementation;	
	}
	
	@Override
	public Map<List<Integer>, Boolean> ouAjouterCarte(Map<List<Integer>, Carte> plateau) {
		
		this.plateauBool = new HashMap<List<Integer>,Boolean>();
		this.plateau= plateau;
		
		// 1) trouver bornes colonnes/ligne
		getBorne();
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
		this.plateau.remove(positionCarte);
		this.plateauBool = this.ouAjouterCarte(this.plateau);
		this.plateau.put(positionCarte,carteABouger);
		this.plateauBool.remove(positionCarte);
		
		getBorne();
		
		return this.plateauBool;
	
	}	
				


	@Override
	public void afficherPlateau(Map<List<Integer>, Carte> plateau, Map<List<Integer>, Boolean> plateauBool) {
		List<Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		System.out.println("");
		System.out.print("     ");
		for (int i=borneColonne[0];i<=borneColonne[1];i++) {
			if (i>=0) {
				System.out.print(i + "    ");
			}
			else {
				System.out.print(i + "   ");
			}
		}
		
		for (int i=this.borneLigne[1];i>=this.borneLigne[0];i--) {
			System.out.println(" ");
			
			if (i>=0) {
				System.out.print(i + "  ");
			}
			else {
				System.out.print(i + " ");
			}
			
			
			for (int j=borneColonne[0];j<=borneColonne[1];j++) {
				position.set(0,i);
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
		}
		
		System.out.println(""); // Retour chariot
		
	}
	
	public Boolean carteBool(List<Integer> position) {
		// Si dans les bornes
		if (!(position.get(0) >= this.borneLigne[0])){
			return false;
		}
		else if (!(position.get(0) <= this.borneLigne[1])){
			return false;
		}
		else if (!(position.get(1) >= this.borneColonne[0])){
			return false;
		}
		else if (!(position.get(1) <= this.borneColonne[1])){
			return false;
		}
		//Si sur une case non occupée
		else if (!(this.plateau.containsKey(position))){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int getWidth() {
		return borneColonne[1]-borneColonne[0]+1;
	}

	@Override
	public int getHeight() {
		return borneLigne[1]-borneLigne[0]+1;
	}

	@Override
	public int getMinWidth() {
		return borneColonne[0];
	}

	@Override
	public int getMinHeight() {
		return borneLigne[0];
	}
}
