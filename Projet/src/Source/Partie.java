package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Scanner;

import Source.Carte.Couleur;
import Source.Carte.Forme;

public class Partie implements ScoreInterface {
	// pattern stratégie pour forme plateau
	private Map<List<Integer>,Carte> plateau = new HashMap<List<Integer>,Carte>(); // plateau de jeu qui lie position et carte
	private Map<List<Integer>,Boolean> plateauBool = new HashMap<List<Integer>,Boolean>(); // plateau contenant les posititons ou on peut mettre des cartes
	
	private Map<List<Integer>,Boolean> plateauBoolCarte = new HashMap<List<Integer>,Boolean>(); // plateau contenant les emplacement où on on peut BOUGER UNE carte
	private Carte carteABouger;
	
	private Scanner clavier = new Scanner(System.in);
	
	// Joueurs de la partie
	private Joueur[] joueur = new Joueur[3];
	
	private ContextPlateau context; // Stock stratégie utilisée
	public enum Context {
		rectangle,triangle;
    }  
	
	private int nbrJoueur;
	private int joueurEnCours;
	private boolean modeAvance;
	private Carte carteCachee; // Stock carte cachée
	
	
	
	public Partie(Context contextePlateau,Boolean modeAvance) {
		
		if (contextePlateau == Context.rectangle) {
			this.context = new ContextPlateau(new PlateauRectangle());
		}
		else {
			this.context = new ContextPlateau(new PlateauTriangle());
		}
		
		//initialisation position de la 1er carte en (0,0)
		List<Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		
		//intialisation plateau booléen
		this.plateauBool.put(position,true);
		this.context.getBorne(this.plateau);
		
		//initialisation pioche
		Pioche pioche = new Pioche();
		this.carteCachee = pioche.piocherCarte();
		
		//initialisation joueurs
		this.modeAvance = modeAvance;
		int IA;
		
		
		// TODO modifier constructeur joueur
		//initalisation joueur 1
		do {
			System.out.println("Joueur 1 IA ? (oui : 1, non : 0) ");
			IA = clavier.nextInt();
			
		}while(IA != 1 && IA != 0);
		if (IA == 1) {
			this.joueur[0] = new Joueur(true);
		}
		else {
			this.joueur[0] = new Joueur(false);
		}
		
		//initalisation joueur 2
		do {
			System.out.println("Joueur 2 IA ? (oui : 1, non : 0) ");
			IA = clavier.nextInt();
			
		}while(IA != 1 && IA != 0);
		if (IA == 1) {
			this.joueur[1] = new Joueur(true);
		}
		else {
			this.joueur[1] = new Joueur(false);
		}
		
		//initalisation joueur 3
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
				this.joueur[2] = new Joueur(true);
			}
			else {
				this.joueur[2] = new Joueur(false);
			}
		}
	}
	
	
	//Ses 2 méthodes seront écrite via un pattern strategy
	
	// Prend en entrée le plateau et en sortie donne un autre plateau de booleen avec où rajouter carte
	public void ouAjouterCarte() {
		this.plateauBool = context.ouAjouterCarte(this.plateau);
	}
	
	// Prend en entrée le plateau et en sortie donne un autre plateau de booleen avec où bougerCarte carte
	// donne en sortie vraie si on peux bouger la carte
	public Boolean ouBougerCarte(List<Integer> position) {
		
		if (plateau.get(position)==null){
			return false;
		}
		else {
			this.plateauBool = context.ouBougerCarte(this.plateau,position);
			this.carteABouger = plateau.get(position);
			return true;
		}
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
		/*
		// utilisateur rentre valeur
		System.out.println("");
		System.out.print("PositionX = ");
		position.add(clavier.nextInt());
		System.out.print("PositionY = ");
		position.add(clavier.nextInt());
		
		while (plateauBool.containsKey(position)==false) {
			System.out.println("impossible de poser une carte sur cette case");
			System.out.print("PositionX = ");
			position.set(0,clavier.nextInt());
			System.out.print("PositionY = ");
			position.set(1,clavier.nextInt());
		}
		Carte carte = new Carte(Couleur.rouge,Forme.carre,true); // place carte de couleur rouge carré pleine
		this.plateauBool.put(position,true);
		
		//ajout de la valeur
		
		*/

	}
	
	public Boolean bougerCarte(List<Integer> positionCarte, List<Integer> positionFinale) {
		
		if (this.plateauBool.containsKey(positionFinale)==false) { //On ne peux pas bouger la carte sur cette case
			System.out.println("la carte ne peux pas être bougée ici");
			return false;
		}
		else {
			this.plateau.remove(positionCarte); // on enlève la carte du plateau pour la remettre dans une nouvelle position
			this.plateau.put(positionFinale,carteABouger); 

			ouAjouterCarte();
			return true;
		}

		/*
		boolean carteBougee = false;
		Carte carte;		
		
		while (carteBougee==false)  {
			System.out.println("");
			System.out.print("PositionX = ");
			position.add(clavier.nextInt());
			System.out.print("PositionY = ");
			position.add(clavier.nextInt());
		
			if (!(plateau.containsKey(position))) {
				System.out.println("cette carte n'existe pas");
			}
			else {
				ouBougerCarte(position);
				if (plateauBool.size() == 0) {
					System.out.println("cette carte ne bouge pas");
				}
				else {
					afficherPlateau();
					carte = plateau.get(position);
					plateau.remove(position);
				
					while (carteBougee==false) {
						System.out.print("PositionX = ");
						position.set(0,clavier.nextInt());
						System.out.print("PositionY = ");
						position.set(1,clavier.nextInt());
					
						if (plateauBool.containsKey(position)==false) {
							System.out.println("cette carte ne peux pas bouger sur cette case");
						}
						else {
							plateau.put(position, carte);
							carteBougee = true;					
						}
					}
				}
			} 
		}
		
		*/
	}
	
	
	public void afficherPlateau() {
		context.afficherPlateau(this.plateau, this.plateauBool);
	}
	
	public void changerJoueur() {
		// Rajouter méthodes pour changer de joueurs
		
		ouAjouterCarte();
	}
	
	public boolean modeAvance() { // joueur a besoin de savoir si on est en mode avancé
		return this.modeAvance;
	}
	
	public Map<List<Integer>,Carte> getPlateau() {
		return this.plateau;
	}
	
	public Joueur[] getJoueur() {
		return this.joueur;
	}
	
	//visiteur
	@Override
	public void accept(ScoreVisitor visitor) {
		 visitor.visit(this);
	}
	
	
	//test
	public static void main(String[] args) {

		Carte carte = new Carte(Couleur.rouge,Forme.carre,true);
		
		//Exemple fonctionnement du code partie avec plateau rectangle 
		
		// On ajoute une carte en (0,0)
		Partie partie = new Partie(Context.rectangle,true);
		List <Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		partie.afficherPlateau();
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();
		
		
		// On ajoute une carte en (1,0)
		position = new ArrayList<Integer>();
		position.add(0,1);
		position.add(1,0);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();
		
		//on bouge la carte en (1,0)
		System.out.println(partie.ouBougerCarte(position));
		partie.afficherPlateau();
		partie.ouAjouterCarte();
		
		
		// On ajoute une carte en (1,1)
		position = new ArrayList<Integer>();
		position.add(0,1);
		position.add(1,1);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();
		
		//on bouge la carte en (1,1) en (0,1)
		System.out.println(partie.ouBougerCarte(position));
		partie.afficherPlateau();
		List <Integer> positionCarte = new ArrayList<Integer>();
		positionCarte.add(0,0);
		positionCarte.add(1,1);
		System.out.println(partie.bougerCarte(position,positionCarte));
		partie.afficherPlateau();

		
		// On ajoute une carte en (2,2) (Impossible)
		position = new ArrayList<Integer>();
		position.add(0,2);
		position.add(1,2);
		partie.ajouterCarte(position, carte);
		
		// On ajoute une carte en (1,1)
		position = new ArrayList<Integer>();
		position.add(0,-1);
		position.add(1,1);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();
		
	
		
		//Exemple fonctionnement du code partie avec plateau rectangle 
		
		/*
		Partie partie = new Partie(Context.triangle,true);
		// On ajoute une carte en (0,0)
		List <Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		partie.afficherPlateau();
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();
		
		
		// On ajoute une carte en (1,0)
		position = new ArrayList<Integer>();
		position.add(0,1);
		position.add(1,0);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();
		
		// On ajoute une carte en (1,1)
		position = new ArrayList<Integer>();
		position.add(0,1);
		position.add(1,1);
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();

		System.out.println(partie.ouBougerCarte(position));
		partie.afficherPlateau();
		List <Integer> positionCarte = new ArrayList<Integer>();
		positionCarte.add(0,0);
		positionCarte.add(1,1);
		System.out.println(partie.bougerCarte(position,positionCarte));
		partie.afficherPlateau();
		
		*/
	}
}
