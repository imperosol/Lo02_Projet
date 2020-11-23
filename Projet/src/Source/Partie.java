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
	public enum Context {
		rectangle,triangle;
    }  
	
	private int nbrJoueur;
	private int joueurEnCours;
	private boolean modeAvance;
	private Carte carteCachee; // Stock carte cachée
	
	Pioche pioche;
	
	
	public Partie(Context contextePlateau,Boolean modeAvance,Pioche pioche) {
		
		Scanner clavier = new Scanner(System.in);
		
		
		if (contextePlateau == Context.rectangle) {
			this.context = new ContextPlateau(new PlateauRectangle());
		}
		else {
			this.context = new ContextPlateau(new PlateauTriangle());
		}
		
		//initialisation position de la 1er carte en (0,0)

		
		//intialisation plateau booléen
		this.context.getBorne(this.plateau);
		List<Integer> position = new ArrayList<Integer>();
		position.add(0);
		position.add(0);
		this.plateauBool.put(position, true);
		
		//initialisation pioche
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
		
		// Si 1er tour
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
	
	//visiteur
	@Override
	public void accept(ScoreVisitor visitor) {
		 visitor.visit(this);
	}
	
	
	
	//test
	public static void main(String[] args) {

		Pioche pioche = new Pioche();
		Partie partie = new Partie(Context.rectangle,false,pioche);
		
		partie.jouerPartie();
	}
}
