package controleur;

import java.util.*;

import modele.*;
import modele.joueur.Joueur;

public class CInterfaceJeu {
	private Partie partie;
	
	public CInterfaceJeu(Partie partie) {
		this.partie = partie;
		
				
	}
	
	public void bougerCarte(List<Integer> positionCarte,List<Integer> positionVide) {
		Joueur joueurEnCours = partie.getJoueurEnCours();
		joueurEnCours.bougerCarteJoueur(positionCarte, positionVide);
		
	}
	
	public void placerCarte(Carte carteMain, List<Integer> positionVide) {
		Joueur joueurEnCours = partie.getJoueurEnCours();
		// Méthode placer carte joueur
		joueurEnCours.placerCarteJoueur(carteMain, positionVide);
		partie.ouAjouterCarteReset();
	}
	
	public void premierTour() {
		partie.changerJoueur();
	}
	
	public void nouveauTour() {
		partie.nouveauTour();
	}
	
	public void updatePlateau(List<Integer> position) {
		partie.ouBougerCarte(position);
	}
	
	public void resetUpdatePlateau() {
		partie.ouAjouterCarte();
	}
	
	
}
