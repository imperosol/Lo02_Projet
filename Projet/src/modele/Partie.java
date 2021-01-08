package modele;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;

import controleur.InterfaceCommande;
import modele.joueur.IAAleatoire;
import modele.joueur.Joueur;
import modele.joueur.JoueurReel;
import modele.plateau.*;
import modele.score.Score;
import modele.score.ScoreInterface;
import modele.score.ScoreVisitor;
import vue.Etat;
import vue.InterfaceJeu;
import vue.RunInterface;

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
	private boolean fin = false;
	private Context contextPlateau;
	private List<Integer> listScore;
	
	private Pioche pioche;
	
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
				this.joueur.add(new Joueur(i+1,new JoueurReel(this) , this, this.pioche));
				nbrJoueur++;
			}
		
			if (Joueur[i]=="IA") {
				this.joueur.add(new Joueur(i+1,new IAAleatoire() , this, this.pioche)); 
				nbrJoueur++;
			}
		}
		fin = false;
		joueurEnCours = joueur.get(joueur.size()-1);
	
		InterfaceJeu.runInterface(this);
		InterfaceCommande Commande = new InterfaceCommande(this);
	}
	
	
	
	public void nouveauTour() {
		if (!(pioche.piocheVide())) {
			System.out.println("\n--------NOUVEAU TOUR--------\n");
			joueurEnCours.finTour();
			changerJoueur();
			setChanged();
			notifyObservers(Etat.reset);
		}
		else if (modeAvance){
			if (joueurEnCours.tailleMain()>1) {
				System.out.println("\n--------NOUVEAU TOUR--------\n");
				joueurEnCours.finTour();
				changerJoueur();
				setChanged();
				notifyObservers(Etat.reset);
			}
			else {
				determinerCarteVictoire();
				Score score = new Score(this);
				listScore = score.scorePartie();
				
				setChanged();
				notifyObservers(Etat.score);
				fin = true;
			}
		}
		else {
			joueurEnCours.finTour();
			Score score = new Score(this);
			listScore = score.scorePartie();
			
			setChanged();
			notifyObservers(Etat.score);
			fin = true;
		}
	}
	
	public void changerJoueur() {
		//permet de passer au joueur suivant
		int numJoueur = joueurEnCours.getNumJoueur();
		numJoueur = numJoueur%nbrJoueur;
		joueurEnCours = joueur.get(numJoueur);
		if (joueurEnCours.getIA()) {
			joueurEnCours.tour();
		}
		else {
			joueurEnCours.debutTour();
		}
	}
	
	public void determinerCarteVictoire() {
        for (int i = 0; i<nbrJoueur; i++){
            Joueur joueurEnCours = this.joueur.get(i);
            List<Carte> main = joueurEnCours.getMain();
            joueurEnCours.setCarteVictoire(main.get(0));
            System.out.println("la carte victoire du " + joueurEnCours + " est " + main.get(0));
        }
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
		setChanged();
		notifyObservers(Etat.commande);
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
		setChanged();
		notifyObservers(Etat.commande);
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
	
	public List<Integer> getListScore(){
		return listScore;
	}
	
	public boolean getFin(){
		return fin;
	}
	/*visiteur*/
	
	@Override
	public void accept(ScoreVisitor visitor) {
		 visitor.visit(this);
	}
}