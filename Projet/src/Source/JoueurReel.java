package Source;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class JoueurReel implements StratégieJoueur{
	
	int numCarteJouee = -1;
	Joueur joueur;
	Partie partie;
	Scanner entreeJoueur = new Scanner(System.in);
	
	public void jouer(Joueur joueur,Partie partie) {
		this.joueur=joueur;
		this.partie=partie;
		
		
		

		joueur.consulterCarteMain(partie);
		int action=-1;
		Boolean aPlacerCarte=true;
		Boolean aBougerCarte=true;
		
		while (action !=2) {
			
			partie.ouAjouterCarte();
			partie.afficherPlateau();
			if (aPlacerCarte) {
				System.out.println("0 : placer une carte");
			}
			
			if (aBougerCarte) {
				System.out.println("1 : bouger une carte");
			}
			
			System.out.println("2 : terminer le tour");
			
			do {
				System.out.print("Action : ");
				action = this.entreeJoueur.nextInt();
					
			}while (action < 0||action>2);
			
			switch (action) {
			case 0:
				if(aPlacerCarte) {
					placerCarte();
					aPlacerCarte = false;
				}
				else{
					System.out.println("Tu as déjà placé une carte");
				}
				break;
			
			case 1:
				if(aBougerCarte) {
					bougerCarte();
					aBougerCarte=false;
				}
				else{
					System.out.println("Tu as déjà bougé une carte");
				}
				break;
			case 2:
				if(aPlacerCarte) {
					System.out.println("Tu dois placer une carte avant de terminer ton tour");
					action=-1;
					
				}
				break;
			}
		}
	}
	
	public void placerCarte() {
		int action;
		int i = 1;
		Iterator it = joueur.getMain().iterator();
		while (it.hasNext()) {
			System.out.print(i + ": " + it.next() +"  ");
			i++;
		}
		System.out.print("Choix du numéro de la carte à jouer :");
		
		do {
			action = this.entreeJoueur.nextInt();
		} while(action > joueur.tailleMain()||action<=0);
		
		numCarteJouee = action-1;
		
		Carte carte = (Carte) joueur.getMain().get(numCarteJouee);

		Map<List<Integer>,Boolean> plateauAjout = partie.ouAjouterCarte();
		Set<List<Integer>> listeDeCléLibre = plateauAjout.keySet();
		Iterator<List<Integer>> it2 = listeDeCléLibre.iterator();
	
		i=1;
		while (it2.hasNext()) {
			System.out.print(i + ": " + it2.next() +"  ");
			i++;
		}
		
		action=0;
		System.out.print("Choix de l'emplacement où sera joué la carte : ");
		do {
			action = this.entreeJoueur.nextInt();
		} while(action > listeDeCléLibre.size()||action<=0);

		List<Integer> position =  (List<Integer>) listeDeCléLibre.toArray()[action-1];
		
		
		joueur.placerCarteJoueur(carte, position, partie);
	}
	
	
	public void bougerCarte() {
		Map<List<Integer>,Carte> plateau = partie.getPlateau();
		List<Integer>[] listeDeCléUtilisé = (List<Integer>[]) plateau.keySet().toArray(new List[0]);
		int action;
		
		
		for (int i=0; i <listeDeCléUtilisé.length;i++) {
			System.out.print((i+1) + ":" + listeDeCléUtilisé[i]+"   ");
		}
		
		System.out.print("Emplacement de la carte à déplacer");
		
		do {
			action = this.entreeJoueur.nextInt();
		} while(action > listeDeCléUtilisé.length||action<=0);

		List<Integer>CléCarteADeplacer = listeDeCléUtilisé[action-1];			
		
		
		
		Map<List<Integer>,Boolean> EmplacementValide = partie.ouBougerCarte(CléCarteADeplacer);
		Set<List<Integer>> CléEmplacementValide = EmplacementValide.keySet();
		Iterator<List<Integer>>  it4 = CléEmplacementValide.iterator();
		
		int i = 1;
		while (it4.hasNext()) {
			System.out.print(i + ": " + it4.next()+"  ");
			i++;
		}
		
		
		System.out.print("Choix de l'emplacement où sera déplacé la carte");
		
		do {
			action = this.entreeJoueur.nextInt();
		} while(action > listeDeCléUtilisé.length||action<=0);

		List<Integer> positionFinale =  (List<Integer>) CléEmplacementValide.toArray()[action-1];
		joueur.bougerCarteJoueur(CléCarteADeplacer, positionFinale, partie);
	}
	
	@Override
	public int getDerniereCarte() {
		return this.numCarteJouee;
	}
}
