package Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Scanner;

import Source.Carte.Couleur;
import Source.Carte.Forme;

public class Partie {
	// pattern stratégie pour forme plateau
	private Map<List<Integer>,Carte> plateau = new HashMap<List<Integer>,Carte>();
	private Map<List<Integer>,Boolean> plateauBool = new HashMap<List<Integer>,Boolean>(); //?
	private Map<Integer,Boolean> plateauBool1 = new HashMap<Integer,Boolean>();
	private Scanner clavier = new Scanner(System.in);
	
	private ContextPlateau context; // Stock stratégie utilisée
	
	
	private int nbrJoueur;
	private boolean modeAvance;
	private Carte carteCachee;
		
	
	public Partie() {
		this.context = new ContextPlateau(new PlateauRectangle());// Pour l'instant que des plateaux rectangles
		List<Integer> position = new ArrayList<Integer>();
		position.add(0,0);
		position.add(1,0);
		this.plateauBool.put(position,true);
		this.plateauBool1.put(1,true);
		System.out.print(this.plateauBool.containsKey(position));
		this.context.getBorne(this.plateau);
	}
	
	
	//Ses 2 méthodes seront écrite via un pattern strategy
	public void ouAjouterCarte() {
		// Prend en entrée le plateau et en sortie donne un autre plateau de bboleen avec où rajouter carte
		System.out.println(plateau);
		plateauBool = context.ouAjouterCarte(this.plateau);
	}
	
	public void ouBougerCarte() {
		// Prend en entrée le plateau et en sortie donne un autre plateau de bboleen avec où bougerCarte carte
	}
	
	public void AfficherOuCarte() {
		
	}
	
	public void ajouterCarte() {
		List<Integer> position = new ArrayList<Integer>();

		
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
		Carte carte = new Carte(Couleur.rouge,Forme.carre,true);
		this.plateauBool.put(position,true);
		
		//ajout de la valeur
		this.plateau.put(position,carte);
	}
	
	public void bougerCarte() {
		
	}
	
	public void afficherPlateau() {
		context.afficherPlateau(this.plateau, this.plateauBool);
	}
	
	public void changerJoueur() {
		
	}
	
	
	
	//test
	public static void main(String[] args) {
		Partie partie = new Partie();
		partie.afficherPlateau();
		partie.ajouterCarte();
		partie.ouAjouterCarte();
		partie.afficherPlateau();
		partie.ajouterCarte();
		partie.ouAjouterCarte();
		partie.afficherPlateau();
		partie.ajouterCarte();
		partie.ouAjouterCarte();
		partie.afficherPlateau();
		partie.ajouterCarte();
		partie.ouAjouterCarte();
		partie.afficherPlateau();
		partie.ajouterCarte();
		partie.ouAjouterCarte();
		partie.afficherPlateau();
	}
	
	
	
}
