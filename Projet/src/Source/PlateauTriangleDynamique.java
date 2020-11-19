package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateauTriangleDynamique implements StrategyPlateau{

	 private Map<List<Integer>, Boolean> plateauBool;
	 private Map<List<Integer>, Carte> plateau;

	
	//pour les bornes 
	 private Map<Integer,List<Integer>> borne;
	 Boolean lignePleine;
	 int min;
	 int max;
	 
	
	@Override
	public Map<List<Integer>, Boolean> ouAjouterCarte(Map<List<Integer>, Carte> plateau) {
		this.plateau = plateau;
		this.plateauBool = new HashMap<List<Integer>, Boolean>();				
		
		getBorne(plateau);
		
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
		System.out.println(plateauBool);
		return this.plateauBool;
	}

	@Override
	public Map<List<Integer>, Boolean> ouBougerCarte(Map<List<Integer>, Carte> plateau, List<Integer> positionCarte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afficherPlateau(Map<List<Integer>, Carte> plateau, Map<List<Integer>, Boolean> plateauBool) {
		List<Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		System.out.println("");
		System.out.print("     ");
		for (int i=-7;i<=7;i++) {
			if (i>=0) {
				System.out.print(i + "    ");
			}
			else {
				System.out.print(i + "   ");
			}
		}
		
		for (int i=this.max;i>=this.min;i--) {
			System.out.println(" ");
			
			if (i>=0) {
				System.out.print(i + "  ");
			}
			else {
				System.out.print(i + " ");
			}
			
			
			for (int j=-7;j<8;j++) {
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
		
	}

	@Override
	public void getBorne(Map<List<Integer>, Carte> plateau) {
		List<Integer> position = new ArrayList<Integer>();
		List<Integer> positionBorne = new ArrayList<Integer>();
		this.borne = new HashMap<Integer,List<Integer>>();
		int maxBorne = 0;

		
		position.add(0);
		position.add(0);
		
		
		//on lit le plateau ligne par ligne pour trouver les bornes de chaques lignes qu'on stock dans borne
		
		
		for (int i=-2;i<=2;i++) { //change de ligne
			position.set(0, i);
			
			for (int j=-7;j<=7;j++) { // change de colonne
				position.set(1, j);
				
				if (plateau.containsKey(position)) {
					if (!(borne.containsKey(i))) {
						positionBorne = new ArrayList<Integer>();
						positionBorne.add(j);
						positionBorne.add(j);
						borne.put(i, positionBorne);
						maxBorne = i;
					}
					else {
						positionBorne.set(1,j);
						borne.put(i, positionBorne);
					}
						
				}
			}
		}
		
		//Si il y a trois lignes on vérifie si la ligne 3 puis la ligne 2 puis la ligne 11 sont completes
		int taillePlateau = borne.size();
		this.lignePleine = false;
		int inc; // nbr de carte - 1 sur une linge

		
		// Algo de détection de ligne pleine + mise en place des bornes 1
		if (taillePlateau == 3) {
			
			
			inc = (borne.get(maxBorne).get(1) + 7) - (borne.get(maxBorne).get(0) + 7);
			if (inc == 2) { // Ligne 3 pleine
				lignePleine = true;
				positionBorne = this.borne.get(maxBorne);
				inc = positionBorne.get(0);
				positionBorne.set(0, inc-2);
				inc = positionBorne.get(1);
				positionBorne.set(1, inc+2);
			}
			else { //ligne 2 pleine
				maxBorne-=1;
				inc = (borne.get(maxBorne).get(1) + 7) - (borne.get(maxBorne).get(0) + 7);
				if (inc == 4) { // Ligne 2 pleine
					lignePleine = true;
					positionBorne = this.borne.get(maxBorne);
					inc = positionBorne.get(0);
					positionBorne.set(0, inc-1);
					inc = positionBorne.get(1);
					positionBorne.set(1, inc+1);
				}
				else {
					maxBorne-=1;
					inc = (borne.get(maxBorne).get(1) + 7) - (borne.get(maxBorne).get(0) + 7);
					if (inc == 6) { // Ligne 1 pleine
						lignePleine = true;
						positionBorne = this.borne.get(maxBorne);
					}
					maxBorne+=1;
				}
				maxBorne+=1;
			}
			maxBorne-=2;
		}
		else if (taillePlateau == 2) {
			inc = (borne.get(maxBorne).get(1) + 7) - (borne.get(maxBorne).get(0) + 7);
			if (inc == 4) { // Ligne 2 pleine
				lignePleine = true;
				positionBorne = this.borne.get(maxBorne);
				inc = positionBorne.get(0);
				positionBorne.set(0, inc-1);
				inc = positionBorne.get(1);
				positionBorne.set(1, inc+1);
			}
			else {
				maxBorne-=1;
				inc = (borne.get(maxBorne).get(1) + 7) - (borne.get(maxBorne).get(0) + 7);
				if (inc == 6) { // Ligne 1 pleine
					lignePleine = true;
					positionBorne = this.borne.get(maxBorne);
				}
				maxBorne+=1;
			}
			maxBorne-=1;
		}
		else { // taille plateau = 1
			inc = (borne.get(maxBorne).get(1) + 7) - (borne.get(maxBorne).get(0) + 7);
			if (inc == 6) { // Ligne 2 pleine
				lignePleine = true;
				positionBorne = this.borne.get(maxBorne);
			}
		}
		
		//maxBorne initialisé pour être sur la ligne 1
		//2 possibilités, où on a une ligne pleine ou on en a pas
		int incMin;
		int incMax;		
		//si une ligne est pleine
		if (lignePleine) {
			this.min = maxBorne;
			this.max = maxBorne + 2;
			this.borne.put(maxBorne, positionBorne);
			
			maxBorne+=1;
			incMin = positionBorne.get(0);
			incMax = positionBorne.get(1);
			positionBorne = new ArrayList<Integer>();
			positionBorne.add(incMin+1);
			positionBorne.add(incMax-1);
			this.borne.put(maxBorne, positionBorne);
			
			maxBorne+=1;
			
			positionBorne = new ArrayList<Integer>();
			positionBorne.add(incMin+2);
			positionBorne.add(incMax-2);
			this.borne.put(maxBorne, positionBorne);
			System.out.println("ligne pleine");
		}
		//si aucune ligne n'est pleine
		else {
			
			if (taillePlateau == 3) { // une ou 2 carte au dessus
				this.min = maxBorne;
				this.max = maxBorne+2;
				
				inc = (borne.get(maxBorne).get(1)) - (borne.get(maxBorne).get(0));
				positionBorne = this.borne.get(this.max);
				incMin = positionBorne.get(0)-3;
				incMax = positionBorne.get(1)+3;
							
			}
			else if (taillePlateau == 2) {
				//2 possbilité selon qu'il y ai plus ou moins de 3 cartes au dessus 
				// On vérifie donc d'abord s'il y a plus de 3 cartes sur la 2e ligne 
				maxBorne+=1;
				inc = (borne.get(maxBorne).get(1) + 7) - (borne.get(maxBorne).get(0) + 7);
				incMin = positionBorne.get(0)-2;
				incMax = positionBorne.get(1)+2;
				
				if (inc >2) {
					maxBorne-=1;
					this.min = maxBorne;
					this.max = maxBorne+2;	
					
					borne.put(this.max, arg1)
				}
				else {
					maxBorne-=1;
					this.min = maxBorne-1;
					this.max = maxBorne+2;
				}
				
			}
			else if (taillePlateau == 1) {
				//3 possbilité selon qu'il y ai plus ou moins de 3 cartes au dessus / plus ou moins 5 cartes
				// On vérifie donc d'abord s'il y a plus de 3 cartes sur la 2e ligne 
				inc = (borne.get(maxBorne).get(1) + 7) - (borne.get(maxBorne).get(0) + 7);
				if (inc > 4) {
					this.min = maxBorne;
					this.max = maxBorne+2;	
				}
				else if (inc >2) {
					this.min = maxBorne-1;
					this.max = maxBorne+2;	
				}
				else {
					this.min = maxBorne-2;
					this.max = maxBorne+2;
				}
				
			}
		}
		
		System.out.println(borne);
		System.out.println("min = " + min + " | max = " + max);
	}
	
	public Boolean carteBool(List<Integer> position) {
		if (this.lignePleine) {
			System.out.println(position);
			if (position.get(0)<this.min) {
				return false;
			}
			else if (position.get(0)>this.max) {
				return false;
			}
			else if (plateau.containsKey(position)) {
				return false;
			}
			else {
				int borneBool;
				if (position.get(0)== this.min) {
					borneBool = this.min;
				}
				else if (position.get(0)== this.min + 1) {
					borneBool = this.min + 1;
				}
				else {
					borneBool = this.max;
				}
				
				List<Integer> positionBorne = borne.get(borneBool);
				if (position.get(1) < positionBorne.get(0)) {
						return false;
				}
				else if (position.get(1) > positionBorne.get(1)) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		if (!(this.lignePleine)) {
			if (position.get(0)<this.min) {
				return false;
			}
			else if (position.get(0)>this.max) {
				return false;
			}
			else if (!(plateau.containsKey(position))) {
				return true;
			}
			else {
				return false;
			}			
		}
		return true;
	}

}
