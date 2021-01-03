package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;

import modele.joueur.IAAleatoire;
import modele.joueur.Joueur;
import modele.joueur.JoueurReel;
import modele.plateau.*;
import modele.score.Score;
import modele.score.ScoreInterface;
import modele.score.ScoreVisitor;
import vue.Etat;

@SuppressWarnings("deprecation")
public class Partie extends Observable implements ScoreInterface {
	// pattern stratégie pour forme plateau
	private Map<List<Integer>,Carte> plateau = new HashMap<List<Integer>,Carte>(); // plateau de jeu qui lie position et carte
	private Map<List<Integer>,Boolean> plateauBool = new HashMap<List<Integer>,Boolean>(); // plateau contenant les posititons ou on peut mettre des cartes

	// Joueurs de la partie
	private List<Joueur> joueur;
	private Joueur joueurEnCours;
	
	private ContextPlateau context; // Stock stratégie utilisée
	
	private int nbrJoueur;
	private boolean modeAvance;
	private Carte carteCachee; // Stock carte cachée
	private List<Integer> positionCarteVictoire = null;
	private Boolean fin;
	private Context contextPlateau;
	
	private Pioche pioche;
	
	
	public Partie(Context contextPlateau,Boolean modeAvance,Boolean joueur3, List<Boolean> IA) {

		
		List<Integer> position;

		//initialistation plateau
		if (contextPlateau == Context.rectangle) {
			this.context = new ContextPlateau(new PlateauRectangle());
		}
		else if (contextPlateau == Context.triangle) {
			this.context = new ContextPlateau(new PlateauTriangle());
		}
		else {
			position = new ArrayList<Integer>();
			position.add(-1);
			position.add(0);
			this.context = new ContextPlateau(new PlateauVariante(position));
			this.positionCarteVictoire = position;
		}
		this.contextPlateau = contextPlateau;
		System.out.println(contextPlateau);

		//initialisation pioche
		this.pioche = new Pioche();
		this.carteCachee = this.pioche.piocherCarte();
		
		//initialisation joueurs
		this.modeAvance = modeAvance;
		this.joueur = new ArrayList<Joueur>();
		
		nbrJoueur = (joueur3) ? 3 : 2;
		
		for (int i=0;i<nbrJoueur;i++) {
			if (IA.get(i)) {
				this.joueur.add(new Joueur(i+1,new IAAleatoire() , this, this.pioche)); 
			}
			else {
				this.joueur.add(new Joueur(i+1,new JoueurReel() , this, this.pioche));
			}
		}
		fin = false;
		joueurEnCours = joueur.get(0);
	}
	
	public Partie(String Plateau, Boolean ModeAvance,String[] Joueur) {
		
		this.modeAvance = ModeAvance;
		
		//initialisation pioche
				this.pioche = new Pioche();
				this.carteCachee = this.pioche.piocherCarte();
		
		List<Integer> position;

		//initialistation plateau
				if (Plateau == "rectangulaire") {
					this.context = new ContextPlateau(new PlateauRectangle());
					this.contextPlateau = Context.rectangle;
				}
				else if (Plateau == "triangulaire") {
					this.context = new ContextPlateau(new PlateauTriangle());
					this.contextPlateau = Context.triangle;
				}
				else {
					position = new ArrayList<Integer>();
					position.add(-1);
					position.add(0);
					this.context = new ContextPlateau(new PlateauVariante(position));
					this.positionCarteVictoire = position;
					this.contextPlateau = Context.variante;
				}
				
				this.nbrJoueur=0;
				this.joueur = new ArrayList<Joueur>();

				for (int i=0;i<3;i++) {
					if (Joueur[i]=="Humain") {
						this.joueur.add(new Joueur(i+1,new JoueurReel() , this, this.pioche));
						nbrJoueur++;
					}
					
					if (Joueur[i]=="IA") {
						this.joueur.add(new Joueur(i+1,new IAAleatoire() , this, this.pioche)); 
						nbrJoueur++;
						
					
					}
				}
				fin = false;
				joueurEnCours = joueur.get(0);
		
	}
	
	
	
	
	public void nouveauTour() {
		if (!(pioche.piocheVide())) {
			joueurEnCours.finTour();
			changerJoueur();
		}
		else if (modeAvance){
			if (joueurEnCours.tailleMain()>1) {
				joueurEnCours.finTour();
				changerJoueur();
			}
			else {
				determinerCarteVictoire();
				fin = true;
			}
		}
		else {
			joueurEnCours.finTour();
			fin = true;
		}
		
		setChanged();
		notifyObservers(Etat.reset);
	}
	
	public void changerJoueur() {
		//permet de passer au joueur suivant
		int numJoueur = joueurEnCours.getNumJoueur();
		numJoueur = numJoueur%nbrJoueur;
		joueurEnCours = joueur.get(numJoueur);
		joueurEnCours.debutTour();
	}
	
	public void determinerCarteVictoire() {
        for (int i = 0; i<nbrJoueur; i++){
            Joueur joueurEnCours = this.joueur.get(i);
            List<Carte> main = joueurEnCours.getMain();
            joueurEnCours.setCarteVictoire(main.get(0));
            System.out.println("la carte victoire du " + joueurEnCours + " est " + main.get(0));
        }
	}
	
	public void jouerPartie() {
	    int i=0; int j;
	    Joueur joueurEnCours;
	    // tant que la pioche n'est pas vide,on joue la partie
	    while (!(this.pioche.piocheVide())) {
	        joueurEnCours = this.joueur.get(i);
	        tour(joueurEnCours);
	        i = (i + 1) % nbrJoueur; // passage au joueur suivant
	    }
	                
	    /*Si on est en mode avancé, on réalise en plus les opérations suivantes*/
	    if (this.modeAvance) {
	    	List<Carte> main;
	        /*On ajoute deux cartes en plus pour chaque joueur*/
	        for (j = 0; j<2; j++){
	            for (i = 0; i<nbrJoueur; i++){
	                joueurEnCours = this.joueur.get(i);
	                tour(joueurEnCours);
	            }
	        }
	        /*Quand tous les joueurs ont reçu leurs cartes supplémentaires, on détermine la carte victoire de chaque joueur*/
	        determinerCarteVictoire();
	    }
	    Score score = new Score(this);
	    score.scorePartie();
	}
	
	
	public void tour(Joueur joueur) {
        System.out.println("\ntour du " + joueur + "\n");
        joueur.tour();
	}

	
	/*Méthodes du plateau via un pattern strategy*/
	
	/* renvoie plateau avec les positions où on peut rajouter des cartes */
	public Map<List<Integer>,Boolean> ouAjouterCarte() {
		
		this.plateauBool = context.ouAjouterCarte(this.plateau);
		
		/* Aucune carte sur le plateau, on initialise la position en (0,0) */
		if (this.plateauBool.size()==0) {
			List<Integer> position = new ArrayList<Integer>();
			position.add(0);
			position.add(0);
			this.plateauBool.put(position, true);
		}

		setChanged();
		notifyObservers(Etat.resetUpdate);
		
		return this.plateauBool;
	}
	
	//TODO déguelasse à changer si le temps
	public Map<List<Integer>,Boolean> ouAjouterCarteReset() {
		
		this.plateauBool = context.ouAjouterCarte(this.plateau);
		
		/* Aucune carte sur le plateau, on initialise la position en (0,0) */
		if (this.plateauBool.size()==0) {
			List<Integer> position = new ArrayList<Integer>();
			position.add(0);
			position.add(0);
			this.plateauBool.put(position, true);
		}

		setChanged();
		notifyObservers(Etat.reset);
		
		return this.plateauBool;
	}
	
	/* renvoie plateau avec les positions où on peut bouger la carte en position */
	public Map<List<Integer>,Boolean> ouBougerCarte(List<Integer> position) {
		this.plateauBool = context.ouBougerCarte(this.plateau,position);
		if (this.plateauBool.size()==0) {
			plateauBool.put(position, true);
		}
		
		setChanged();
		notifyObservers(Etat.update);
		
		return this.plateauBool;
	}
	
	/* permet d'ajouter une carte sur plateau
	 * retourne vrai si la carte a été ajoutée */
	public void ajouterCarte(List<Integer> position, Carte carte) {
		
		if (this.plateauBool.containsKey(position)) {
			this.plateau.put(position,carte);
		}
		else {
			System.out.println("impossible de rajouter la carte ici");
		}
		
		setChanged();
		notifyObservers(Etat.reset);
	}
	
	
	/* permet de bouger une carte sur plateau
	 * retourne vrai si la carte a été bougée */
	public void bougerCarte(List<Integer> positionCarte, List<Integer> positionFinale) {
		
		if (this.plateauBool.containsKey(positionFinale)==false) {
			System.out.println("la carte ne peux pas être bougée ici");
		}
		else {
			Carte carteABouger = this.plateau.get(positionCarte);
			this.plateau.remove(positionCarte); // on enlève la carte du plateau pour la remettre dans une nouvelle position
			this.plateau.put(positionFinale,carteABouger); 

			ouAjouterCarte();
		}
		
		setChanged();
		notifyObservers(Etat.reset);
	}
	
	
	public void afficherPlateau() {
		context.afficherPlateau(this.plateau, this.plateauBool);
	}
	
	
	/*getters*/
	
	public boolean getModeAvance() {
		return modeAvance;
	}
	
	public Map<List<Integer>,Carte> getPlateau() {
		return plateau;
	}
	
	public Map<List<Integer>,Boolean> getPlateauBool() {
		return plateauBool;
	}
	
	public int getWidthPlateau() {
		return context.getWidth();
	}
	
	public int getHeightPlateau() {
		return context.getHeight();
	}
	
	public int getMinWidthPlateau() {
		return context.getMinWidth();
	}

	public int getMinHeightPlateau() {
		return context.getMinHeight();
	}
	
	public List<Joueur> getJoueur() {
		return joueur;
	}
	
	public Joueur getJoueurEnCours() {
		return joueurEnCours;
	}
	
	public List<Integer> getPositionCarteVictoire(){
		return this.positionCarteVictoire;
	}
	
	public Context getContextPlateau() {
		return contextPlateau;
	}
	
	
	/*visiteur*/
	
	@Override
	public void accept(ScoreVisitor visitor) {
		 visitor.visit(this);
	}
		
	public static void main(String[] args) {

		
		/* Partie(Context,modeAvance)
		 * 
		 * Context permet de déterminer le type de plateau, on a :
		 * - Context.rectangle pour le plateau rectangle
		 * - Context.triangle pour le plateau triangle
		 * - Context.variante pour le plateau variante (dans ce plateau deviendra la carte victoire du joueur à la fin de la partie)
		 * 
		 * si modeAvance = true alors on est en mode anavcé, sinon on est en mode normal
		 */

	}
}