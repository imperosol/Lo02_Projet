package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Source.Carte.Couleur;
import Source.Carte.Forme;

public class Score implements ScoreVisitor{
	private Map<List<Integer>,Carte> plateau = new HashMap<List<Integer>,Carte>();
	
	private Carte[] carteVictoire = new Carte[3];
	private int[] score = new int[2];
	
	private int nbrJoueur;
	private Joueur[] joueur;
	
	public Score(Partie partie) {
		//provisoire donne pour carte vicouire RCP
		this.carteVictoire[0] = new Carte(Couleur.rouge,Forme.carre,true);
		this.visit(partie);
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
		
		this.score[i] = this.compterScoreLigneColonne(carteVictoire[i], 0);
		this.score[i] += this.compterScoreLigneColonne(carteVictoire[i], 1);
		
		return this.score[i];
	}
		
	@Override
	public void visit(Joueur joueur) {
		
	}
	
	@Override
	public void visit(Partie partie) {
		this.plateau = partie.getPlateau();
		this.joueur = partie.getJoueur();
	}
	
	public static void main(String[] args) {
		
		
		Partie partie = new Partie();
		
		Score score = new Score(partie);
		Carte carte = new Carte(Couleur.rouge,Forme.carre,true);
		Carte carte2 = new Carte(Couleur.vert,Forme.cercle,true);
		
		//Exemple fonctionnement du code Score
		
		// On ajoute une carte en (0,0)
		List <Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		
		// On ajoute une carte en (1,0)
		position = new ArrayList<Integer>();
		position.add(0,1);
		position.add(1,0);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
	
		// On ajoute une carte en (0,1)
		position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,1);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		
		// On ajoute une carte en (0,2)
		position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,2);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		
		
		
		// On ajoute une carte en (0,3)
		position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,3);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		
		// On ajoute une carte en (0,3)
		position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,4);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();
		
		System.out.println(score.compterScore(0));
		
	}

}
