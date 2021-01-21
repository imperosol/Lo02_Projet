package modele.score;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modele.Carte;
import modele.Partie;
import modele.joueur.Joueur;

/**
 * 
 * La classe Score gère le comptage du score en fin de partie
 * elle gère le plateau le joueur courant, la pioche,...
 * 
 * 
 * 
 *@see ScoreVisitor

 * @author PATEAU T && GRIFFIN S
 *
 */
public class Score implements ScoreVisitor{
	private Map<List<Integer>,Carte> plateau;
	
	private List<Integer> positionCarteVictoire;
	private List<Carte> carteVictoire = new ArrayList<Carte>();
	private List<Joueur> player = new ArrayList<Joueur>();
	private List<Integer> score = new ArrayList<Integer>();
	
	/**
	 * 
	 * Construit le score
	 * 
	 */
	public Score(Partie partie) {
		this.visit(partie);
		Iterator<Joueur> it = this.player.iterator();
		while(it.hasNext()) {
			visit(it.next());
		}
	}
	
	/**
	 * 
	 * Compte le score de la partie
	 * 
	 * @return retourne une liste avec les scores de chaques joueurs
	 * 
	 */
	public List<Integer> scorePartie() {
		
		for(int i=0;i<player.size();i++) {
			score.add(scoreJoueur(i));
			//System.out.println("\ncarte victoire de " + joueur + " est " + carteVictoire.get(i));
			//System.out.println("le score de " + joueur + " est : " + score);
		}		
		
		
		return score;
	}
	
	/**
	 * 
	 * Compte le score d'un joueur
	 * 
	 * @param index du joueur dont il faut compter le score
	 * @return retourne le liste du joueur
	 * 
	 */
	
	public Integer scoreJoueur(int i) {
		Carte carteVictoire = this.carteVictoire.get(i);
		if (this.positionCarteVictoire != null) {
			plateau.put(positionCarteVictoire, carteVictoire);
		}
		
		int score = this.compterScoreLigneColonne(carteVictoire, 0); // compte le score sur les lignes
		score += this.compterScoreLigneColonne(carteVictoire, 1); // compte le score sur les colonnes
		System.out.println("Score joueur " + (i + 1) + " : " + score + "\n" ); 
		
		return score;
	}

	/**
	 * Compte le score de toutes les lignes ou colonnes en fonction d'une carte victoire
	 * 
	 * @param carte victoire d'un joueur
	 * @param 0 si on compte ligne, 1 si on compte colonne
	 * 
	 */
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
	
	/**
	 * 
	 * Retourne une chaine de caractère avec le score d'une série de carte
	 * 
	 * @param prend colonne ou ligne en paramètre
	 * @param numéro de la ligne ou colonne
	 * @param nombre de cartes dans la série
	 * @param forme, couleur ou plein de la série
	 * @param score obtenu avec la série
	 * @return une chaine de caractère avec le score d'une série de carte
	 */
	public String affScore(String pos, int i, int j, String type,int score) {
		return pos + " " + i + " : série de " + j + " " + type + " - " + score + "pts";
	}
	
	/**
	 * 
	 * Place la carte victoire du joueur à l'emplacement dédié si on utilise le plateau Variante
	 * @param Carte victoire du joueur
	 */
	public void placerCarteVictoire(int i) {
		if (this.positionCarteVictoire != null) {
			plateau.put(positionCarteVictoire, this.carteVictoire.get(i));
		}
	}
	
	//Visiteur
	
	@Override
	/**
	 * 
	 * Visite un joueur
	 * @param joueur a visiter
	 * 
	 */
	public void visit(Joueur joueur) {
		this.carteVictoire.add(joueur.getCarteVictoire());
	}
	
	@Override
	/**
	 * 
	 * Visite la partie
	 * @param partie a visiter
	 * 
	 */
	public void visit(Partie partie) {
		this.plateau = partie.getPlateau();
		this.positionCarteVictoire = partie.getPositionCarteVictoire();
		this.player = partie.getJoueur();
	}
}
