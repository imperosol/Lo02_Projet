package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Source.Carte.Couleur;
import Source.Carte.Forme;

public class Score implements ScoreVisitor{
	private Map<List<Integer>,Carte> plateau = new HashMap<List<Integer>,Carte>();
	
	private List<Carte> carteVictoire = new ArrayList<Carte>();
	private List<Integer> score = new ArrayList<Integer>();
	private List<Joueur> joueur = new ArrayList<Joueur>();
	
	public Score(Partie partie) {
		this.visit(partie);
		int score;
		int i=0;
		Joueur joueurEnCours;
		Iterator<Joueur> it = this.joueur.iterator();
		
		
		while(it.hasNext()) {
			joueurEnCours = it.next();
			visit(joueurEnCours);
			score = this.compterScore(i);
			System.out.println("le score du joueur " + i + " est : " +score);
			i++;
		}
	}
	
	
	public int compterScoreLigneColonne(Carte carteVictoire, int posX) { //posX = 0 si on comte ligne et 1 si on compte colonne
		
		int score = 0;
        List <Integer> position = new ArrayList<Integer>();
        position.add(0);
        position.add(0);
        int posY;
        
        if (posX == 0) {
        	posY = 1;
        }
        else {
        	posY = 0;
        }
        
        Carte carte;
        
        
		for (int i=-5;i<=5;i++) { //change de ligne
			position.set(posX, i);
	        int incCouleur = 0;
	        int incForme = 0;
	        int incPlein = 0;
	        
			for (int j=-5;j<=5;j++) { // change de colonne
				position.set(posY, j);
				
				if (plateau.containsKey(position)) {
					carte = plateau.get(position);
					
					//Compte couleurs
					if (carte.getCouleur() == carteVictoire.getCouleur()) {
						incCouleur++;
					}
					else {
						if (incCouleur>2) {
							score += incCouleur + 1;
						}
						incCouleur=0;
					}
					
					// Compte formes
					if (carte.getForme() == carteVictoire.getForme()) {
						incForme++;
					}
					else {
						if (incForme>1) {
							score += incForme - 1;
						}
						incForme=0;
					}
					
					
					// Compte plein
					if (carte.getPlein() == carteVictoire.getPlein()) {
						incPlein++;
					}
					else {
						if (incPlein > 2) {
							score += incPlein;
						}
						incPlein=0;
					}	
				}
			}
			if (incCouleur>2) {
				score += incCouleur + 1;
			}
			
			if (incForme>1) {
				score += incForme - 1;
			}
			
			if (incPlein > 2) {
				score += incPlein;
			}
		}
		
		return score;
	}
	
	
	
	public int compterScore(int i) {
		
		int score = this.compterScoreLigneColonne(carteVictoire.get(i), 0);
		score += this.compterScoreLigneColonne(carteVictoire.get(i), 1);
		
		return score;
	}
	
	
	//Visiteur
	@Override
	public void visit(Joueur joueur) {
		this.carteVictoire.add(joueur.consulterCarteVictoire());
	}
	
	@Override
	public void visit(Partie partie) {
		this.plateau = partie.getPlateau();
		this.joueur = partie.getJoueur();
	}
}
