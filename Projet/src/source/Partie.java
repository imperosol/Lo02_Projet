package source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Scanner;

public class Partie implements ScoreInterface {
	// pattern stratégie pour forme plateau
	private Map<List<Integer>,Carte> plateau = new HashMap<List<Integer>,Carte>(); // plateau de jeu qui lie position et carte
	private Map<List<Integer>,Boolean> plateauBool = new HashMap<List<Integer>,Boolean>(); // plateau contenant les posititons ou on peut mettre des cartes

	// Joueurs de la partie
	private List<Joueur> joueur;
	
	private ContextPlateau context; // Stock stratégie utilisée
	
	private int nbrJoueur;
	private boolean modeAvance;
	private Carte carteCachee; // Stock carte cachée
	private List<Integer> positionCarteVictoire = null;
	
	private Pioche pioche;
	
	
	public Partie(Context contextePlateau,Boolean modeAvance) {
		
		Scanner clavier = new Scanner(System.in);
		
		List<Integer> position;

		
		if (contextePlateau == Context.rectangle) {
			this.context = new ContextPlateau(new PlateauRectangle());
		}
		else if (contextePlateau == Context.triangle) {
			this.context = new ContextPlateau(new PlateauTriangle());
		}
		else {
			position = new ArrayList<Integer>();
			position.add(-1);
			position.add(0);
			this.context = new ContextPlateau(new PlateauVariante(position));
			this.positionCarteVictoire = position;
		}

		
		//intialisation plateau booléen
		
		//initialisation pioche
		Pioche pioche = new Pioche();
		this.pioche = new Pioche();
		this.carteCachee = this.pioche.piocherCarte();
		
		//initialisation joueurs
		this.modeAvance = modeAvance;
		this.joueur = new ArrayList<Joueur>();
		
		int IA;
		/*Demande du nombre de joueurs*/
		do
		{
		    System.out.println("Nombre de joueurs ? (2 ou 3) ");
		    this.nbrJoueur = clavier.nextInt();
		}while (this.nbrJoueur!=2 && this.nbrJoueur!=3);
		        
		/*Initialisation des joueurs*/
		for (int i=0; i<this.nbrJoueur; i++)
		{
		    /*On demande à l'utilisateur s'il veut ajouter une IA ou un joueur humain*/
		    do {
		        System.out.println("Joueur" + (i+1) + "IA ? (oui : 1, non : 0) ");
		        IA = clavier.nextInt();
		    }while(IA != 1 && IA != 0);
		            
		    /*On crée un joueur humain ou IA selon le choix utilisateur*/
		    if (IA==1) {
		        this.joueur.add(new Joueur(i,new IAAleatoire() , this, this.pioche));
		    }
		    else {
		        this.joueur.add(new Joueur(i,new JoueurReel() , this, this.pioche));
		    }
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
	        for (i = 0; i<nbrJoueur; i++){
	            joueurEnCours = this.joueur.get(i);
	            main = joueurEnCours.getMain();
	            joueurEnCours.setCarteVictoire(main.get(0));
	            System.out.println("la carte victoire du " + joueurEnCours + " est " + main.get(0));
	        }
	    }
	    Score score = new Score(this);
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
		
		return this.plateauBool;
	}
	
	/* renvoie plateau avec les positions où on peut bouger la carte en position */
	public Map<List<Integer>,Boolean> ouBougerCarte(List<Integer> position) {
		this.plateauBool = context.ouBougerCarte(this.plateau,position);
		if (this.plateauBool.size()==0) {
			plateauBool.put(position, true);
		}
		
		return this.plateauBool;
	}
	
	/* permet d'ajouter une carte sur plateau
	 * retourne vrai si la carte a été ajoutée */
	public Boolean ajouterCarte(List<Integer> position, Carte carte) {
		
		if (this.plateauBool.containsKey(position)) {
			this.plateau.put(position,carte);
			return true;
		}
		else {
			System.out.println("impossible de rajouter la carte ici");
			return false;
		}
	}
	
	
	/* permet de bouger une carte sur plateau
	 * retourne vrai si la carte a été bougée */
	public Boolean bougerCarte(List<Integer> positionCarte, List<Integer> positionFinale) {
		
		if (this.plateauBool.containsKey(positionFinale)==false) {
			System.out.println("la carte ne peux pas être bougée ici");
			return false;
		}
		else {
			Carte carteABouger = this.plateau.get(positionCarte);
			this.plateau.remove(positionCarte); // on enlève la carte du plateau pour la remettre dans une nouvelle position
			this.plateau.put(positionFinale,carteABouger); 

			ouAjouterCarte();
			return true;
		}
	}
	
	
	public void afficherPlateau() {
		context.afficherPlateau(this.plateau, this.plateauBool);
	}
	
	/*getters*/
	
	public boolean getModeAvance() {
		return this.modeAvance;
	}
	
	public Map<List<Integer>,Carte> getPlateau() {
		return this.plateau;
	}
	

	public List<Joueur> getJoueur() {
		return this.joueur;
	}
	
	public List<Integer> getPositionCarteVictoire(){
		return this.positionCarteVictoire;
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
		Partie partie = new Partie(Context.triangle,false);
		
		partie.jouerPartie();
	}
}
