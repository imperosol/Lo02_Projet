package controleur;

import java.util.*;

import modele.*;
import modele.joueur.Joueur;

public class Controleur {
	private Partie partie;
	
	public Controleur(Partie partie) {
		this.partie = partie;
		
				
	}
	
	public void bougerCarte(List<Integer> positionCarte,List<Integer> positionVide) {
		partie.bougerCarte(positionCarte, positionVide);
		
	}
	
	public void placerCarte(Carte carteMain, List<Integer> positionVide) {
		Joueur joueurEnCours = partie.getJoueurEnCours();
		// Méthode placer carte joueur
	}
	
	public void nouveauTour() {
		partie.nouveauTour();
	}
	
	
}
