package modele.score;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modele.Carte;
import modele.Partie;
import modele.joueur.Joueur;

public class Score implements ScoreVisitor{
	private Map<List<Integer>,Carte> plateau;
	
	private List<Integer> positionCarteVictoire;
	private List<Carte> carteVictoire = new ArrayList<Carte>();
	private List<Joueur> player = new ArrayList<Joueur>();
	private List<Integer> score = new ArrayList<Integer>();
	
	public Score(Partie partie) {
		this.visit(partie);
		Iterator<Joueur> it = this.player.iterator();
		while(it.hasNext()) {
			visit(it.next());
		}
	}
	
	
	public List<Integer> scorePartie() {
		
		for(int i=0;i<player.size();i++) {
			score.add(scoreJoueur(i));
			//System.out.println("\ncarte victoire de " + joueur + " est " + carteVictoire.get(i));
			//System.out.println("le score de " + joueur + " est : " + score);
		}		
		
		
		return score;
	}
	
	public Integer scoreJoueur(int i) {
		Carte carteVictoire = this.carteVictoire.get(i);
		if (this.positionCarteVictoire != null) {
			plateau.put(positionCarteVictoire, carteVictoire);
		}
		
		int score = this.compterScoreLigneColonne(carteVictoire, 0); // compte le score sur les lignes
		score += this.compterScoreLigneColonne(carteVictoire, 1); // compte le score sur les colonnes
		
		return score;
	}

	public int compterScoreLigneColonne(Carte carteVictoire, int posX) { //posX = 0 si on comte ligne et 1 si on compte colonne
		
		int score = 0;
        List <Integer> position = new ArrayList<Integer>();
        position.add(0);
        position.add(0);
        int posY;
        String pos;
        
        if (posX == 0) { //posX = 0 on a ligne
        	posY = 1;
        	pos = "Ligne";
        }
        
        else { // posX = 1 on a colonnes
        	posY = 0;
        	pos = "colonne";
        }
        
        Carte carte;
        
        
		for (int i=-5;i<=5;i++) { //change de ligne/colonne
			position.set(posX, i);
	        int incCouleur = 0;
	        int incForme = 0;
	        int incPlein = 0;
	        
			for (int j=-5;j<=5;j++) { // change de colonne/ligne
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
							System.out.println(this.affScore(pos, i, incCouleur, carteVictoire.affCouleur(), incCouleur + 1));
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
							System.out.println(this.affScore(pos, i, incForme, carteVictoire.affForme(), incForme - 1));
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
							System.out.println(this.affScore(pos, i, incPlein, carteVictoire.affPlein(), incPlein));
						}
						incPlein=0;
					}	
				}
			}
			//fin de ligne ou colonne
			if (incCouleur>2) {
				score += incCouleur + 1;
				System.out.println(this.affScore(pos, i, incCouleur, carteVictoire.affCouleur(), incCouleur + 1));
			}
			
			if (incForme>1) {
				score += incForme - 1;
				System.out.println(this.affScore(pos, i, incForme, carteVictoire.affForme(), incForme - 1));
			}
			
			if (incPlein > 2) {
				score += incPlein;
				System.out.println(this.affScore(pos, i, incPlein, carteVictoire.affPlein(), incPlein));
			}
		}
		
		return score;
	}
	
	public String affScore(String pos, int i, int j, String type,int score) {
		return pos + " " + i + " : série de " + j + " " + type + " - " + score + "pts";
	}
	
	public void placerCarteVictoire(int i) {
		if (this.positionCarteVictoire != null) {
			plateau.put(positionCarteVictoire, this.carteVictoire.get(i));
		}
	}
	
	//Visiteur
	@Override
	public void visit(Joueur joueur) {
		this.carteVictoire.add(joueur.getCarteVictoire());
	}
	

	@Override
	public void visit(Partie partie) {
		this.plateau = partie.getPlateau();
		this.positionCarteVictoire = partie.getPositionCarteVictoire();
		this.player = partie.getJoueur();
	}
}
