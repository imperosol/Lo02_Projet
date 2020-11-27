package Source;

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
	
	Pioche pioche;
	
	
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
			this.context = new ContextPlateau(new PlateauVariante());
			position = new ArrayList<Integer>();
			position.add(-1);
			position.add(0);
			this.context.initialiserPosition(position);
			this.positionCarteVictoire = position;
		}

		
		//intialisation plateau booléen
		
		//initialisation pioche
		Pioche pioche = new Pioche();
		this.pioche = new Pioche();
		this.carteCachee = this.pioche.piocherCarte();
		
		//initialisation joueurs
		this.modeAvance = modeAvance;
		
		
		int IA;
		this.joueur = new ArrayList<Joueur>();
		
		do {
			System.out.println("Joueur 1 IA ? (oui : 1, non : 0) ");
			IA = clavier.nextInt();
		}while(IA != 1 && IA != 0);

		if (IA == 1) {
			this.joueur.add(new Joueur(1,new IAAleatoire() , this, this.pioche));
		}
		else {
			this.joueur.add(new Joueur(1,new JoueurReel() , this, this.pioche));
		}

		//initalisation joueur 2
		do {
			System.out.println("Joueur 2 IA ? (oui : 1, non : 0) ");
			IA = clavier.nextInt();

		}while(IA != 1 && IA != 0);
		if (IA == 1) {
			this.joueur.add(new Joueur(2,new IAAleatoire() , this, this.pioche));
		}
		else {
			this.joueur.add(new Joueur(2,new JoueurReel() , this, this.pioche));
		}
		
		//initialisation joueur 3
		do {
			System.out.println("Rajouter un joueur 3 ? (oui : 1, non : 0) ");
			IA = clavier.nextInt();
		}while(IA != 1 && IA != 0);
		if (IA == 1) {
			this.nbrJoueur = 3;
		}
		else {
			this.nbrJoueur = 2;
		}
		
		if (this.nbrJoueur == 3) {
			do {
				System.out.println("Joueur 3 IA ? (oui : 1, non : 0) ");
				IA = clavier.nextInt();

			}while(IA != 1 && IA != 0);
			if (IA == 1) {
				this.joueur.add(new Joueur(3,new IAAleatoire() , this, this.pioche));
			}
			else {
				this.joueur.add(new Joueur(3,new JoueurReel() , this, this.pioche));
			}
		}
	}

	
	public void jouerPartie() {
		int i=0;
		Joueur joueurEnCours;
		// tant que la pioche n'est pas vide,on joue la partie
		while (!(this.pioche.piocheVide())) {
			joueurEnCours = this.joueur.get(i);
			System.out.println("");
			System.out.println("tour du " + joueurEnCours);
			System.out.println("");
			joueurEnCours.tour(this, this.pioche);
			
			if(i==(nbrJoueur-1)) {
				i=0;
			}
			else {
				i++;
			}
		}
		
		// Si en mode avancé permet de jouer deux tours de plus une fois que la pioche est vide
		i=0;
		int j = 1;
		while (this.modeAvance && j<3) { 
			joueurEnCours = this.joueur.get(i);
			
			System.out.println("");
			System.out.println("tour du " + joueurEnCours);
			System.out.println("");
			
			joueurEnCours.tour(this, this.pioche);
			
			if(i==(nbrJoueur-1)) {
				i=0;
				j++;
			}
			else {
				i++;
			}
		}
		
		List<Carte> main;
		
		// Si en mode avancé détermine la carte victoire de chaque joueurs
		while (this.modeAvance && j<4) {
			joueurEnCours = this.joueur.get(i);
			main = joueurEnCours.getMain();
			joueurEnCours.setCarteVictoire(main.get(0));
			System.out.println("la carte victoire du " + joueurEnCours + " est " + main.get(0));
			if(i==(nbrJoueur-1)) {
				i=0;
				j++;
			}
			else {
				i++;
			}
		}
		
		Score score = new Score(this);
	
	}
	
	public void ajouterJoueur(Joueur joueur) {
		this.joueur.add(joueur);
	}
	
	//Ses 2 méthodes seront écrite via un pattern strategy
	
	// Prend en entrée le plateau et en sortie donne un autre plateau de booleen avec où rajouter carte
	public Map<List<Integer>,Boolean> ouAjouterCarte() {
		
		this.plateauBool = context.ouAjouterCarte(this.plateau);
		
		// Si 1er tour (aucunes cartes sur le plateau), initialisation du plateau booléen en 0
		if (this.plateauBool.size()==0) {
			List<Integer> position = new ArrayList<Integer>();
			position.add(0);
			position.add(0);
			this.plateauBool.put(position, true);
		}
		
		return this.plateauBool;
	}
	
	// Prend en entrée le plateau et en sortie donne un autre plateau de booleen avec où bougerCarte carte
	// donne en sortie vraie si on peux bouger la carte
	public Map<List<Integer>,Boolean> ouBougerCarte(List<Integer> position) {
		this.plateauBool = context.ouBougerCarte(this.plateau,position);
		if (this.plateauBool.size()==0) {
			plateauBool.put(position, true);
		}
		
		return this.plateauBool;
	}
	
	
	
	public Boolean ajouterCarte(List<Integer> position, Carte carte) {
		
		if (this.plateauBool.containsKey(position)) {
			this.plateau.put(position,carte); // rajout de carte dans plateau
			return true; // retourne que la care a été ajoutée
		}
		else {
			System.out.println("impossible de rajouter la carte ici");
			return false; // retourne que la carte n'a pas été ajoutée
		}
	}
	
	public Boolean bougerCarte(List<Integer> positionCarte, List<Integer> positionFinale) {
		
		if (this.plateauBool.containsKey(positionFinale)==false) { //On ne peux pas bouger la carte sur cette case
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
	
	
	public boolean modeAvance() { // joueur a besoin de savoir si on est en mode avancé
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
	//visiteur
	@Override
	public void accept(ScoreVisitor visitor) {
		 visitor.visit(this);
	}
	
	
	
	//test
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
		Partie partie = new Partie(Context.rectangle,false);
		
		partie.jouerPartie();
	}
}
