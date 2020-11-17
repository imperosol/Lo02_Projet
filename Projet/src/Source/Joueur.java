package Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Joueur {
	
	private Carte carteVictoire;
	private int numeroJoueur;
	private int action;
	private boolean human;
	private Pioche pioche;
	private List<Carte> main = new ArrayList();
	private Partie partie;
	private Scanner clavier = new Scanner(System.in);
	// Private Partie;
	
	public Joueur(Pioche pioche, int numeroJoueur, boolean human, Partie partie) { 
		// Rajouter partie, on en a besoin pour faire les méthodes
		this.numeroJoueur = numeroJoueur;
		this.human = human;	
		this.pioche = pioche;
		this.carteVictoire = pioche.piocherCarte();
		this.partie = partie;
		
		if (partie.modeAvance() == false) { //Si en mode avancé pioche 3 cartes
			Carte carte = pioche.piocherCarte();
			this.main.add(0, carte);	
		}
		else {
			piocherCarte();
			piocherCarte();
			piocherCarte();
		}	
	}
	
	public void piocherCarte() {
		Carte carte = pioche.piocherCarte();
		
		if (human) {
			System.out.println("tu as pioché : " + carte);
		}
		this.main.set(0, carte);	
	}
	
	public Carte consulterCarteVictoire() {
		return this.carteVictoire;
	}
	
	public void consulterCarteMain() {
		
		if (partie.modeAvance() == false) { // une seule carte à montrer
			
			System.out.print("tu as en main : " + this.main.get(0));
		}
		else { // une à trois cartes
			System.out.print("tu as en main :");
			for (Carte carte : this.main) {
				System.out.print(" " + carte);
			}
		}
		System.out.println("");
		System.out.println("ta carte victoire est : " + this.carteVictoire);

	}
	
	public void tour() {
		System.out.println("c'est au tour du joueur " + this.numeroJoueur);
		System.out.println("entrez n'importe quoi pour continuer");
		clavier.next();
		this.partie.afficherPlateau();
		consulterCarteMain();
		boolean finTour = false;
		boolean placerCarte = false;
		boolean bougerCarte = false;
		
		while (finTour == false) {
			System.out.println("");
			if (placerCarte==false) {
				System.out.println("entrez 1 pour placer une carte");
			}
			if (bougerCarte==false) {
				System.out.println("entrez 2 pour bouger une carte");
			}
			System.out.println("entrez 3 pour terminer le tour");
			
			int action = clavier.nextInt();
			
			if (placerCarte == false && action == 1) {
				partie.ajouterCarte();
				placerCarte = true;
			}
			else if (bougerCarte == false && action == 2) {
				partie.bougerCarte();
				bougerCarte = true;
			}
			else if (placerCarte && action == 3) {
				finTour = true;
			}
			else {
				System.out.println("action impossible");
			}
			
			if (bougerCarte && placerCarte) {
				finTour = true;
			}
		}
		
		this.partie.changerJoueur();
	}
	
	
	// Méthodes nécessitant partie
	public void placerCarte() {
		
	}
	
	public void bougerCarte() {
		
	}
	
	public void regarderPlateau() {
		
	}
	
	
	// Visteur
	public void accepterVisiteur() {
		
	}
}
