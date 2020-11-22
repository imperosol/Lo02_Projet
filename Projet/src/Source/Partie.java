package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Scanner;

import Source.Carte.Couleur;
import Source.Carte.Forme;

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
	
	
	public Partie(Context contextePlateau,Boolean modeAvance,Pioche pioche) {
		
		
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
		this.carteCachee = pioche.piocherCarte();
		
		//initialisation joueurs
		this.modeAvance = modeAvance;

		this.joueur = new ArrayList();
		

	}
	
	
	public void ajouterJoueur(Joueur joueur) {
		this.joueur.add(joueur);
	}
	
	//Ses 2 méthodes seront écrite via un pattern strategy
	
	// Prend en entrée le plateau et en sortie donne un autre plateau de booleen avec où rajouter carte
	public Map<List<Integer>,Boolean> ouAjouterCarte() {
		return this.plateauBool = context.ouAjouterCarte(this.plateau);
	}
	
	// Prend en entrée le plateau et en sortie donne un autre plateau de booleen avec où bougerCarte carte
	// donne en sortie vraie si on peux bouger la carte
	public Map<List<Integer>,Boolean> ouBougerCarte(List<Integer> position) {
			return this.plateauBool = context.ouBougerCarte(this.plateau,position);
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

		Carte carte = new Carte(Couleur.rouge,Forme.carre,true);
		
		//Exemple fonctionnement du code partie avec plateau rectangle 
		
		// On ajoute une carte en (0,0)
		Pioche pioche = new Pioche();

		Partie partie = new Partie(Context.rectangle,true,pioche);
		
		List <Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		partie.ajouterCarte(position, carte);
		
		
		Joueur Moi = new  Joueur(1,new JoueurReel(),partie,pioche);
		Moi.piocherCarte(pioche);
		Moi.piocherCarte(pioche);
		Moi.piocherCarte(pioche);
		partie.ajouterJoueur(Moi);
		Moi.tour(partie, pioche);
		
		
		
		//Exemple fonctionnement du code partie avec plateau rectangle 
		
		/*
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
		System.out.println(position.get(position.size()-1));
		System.out.println(position.size());
		Iterator<Integer> i=position.iterator();
		while (i.hasNext()) {
			System.out.println(i.next());
		}
		
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		partie.ajouterCarte(position, carte);
		partie.changerJoueur();
		partie.afficherPlateau();

		System.out.println(partie.ouBougerCarte(position));
		partie.afficherPlateau();
		List <Integer> positionCarte = new ArrayList<Integer>();
		positionCarte.add(0,0);
		positionCarte.add(1,1);
		System.out.println(position.size());
		Iterator<Integer> it=position.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println(partie.bougerCarte(position,positionCarte));
		partie.afficherPlateau();
		
	*/	
	}
}
