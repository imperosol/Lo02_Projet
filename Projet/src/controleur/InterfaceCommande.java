package controleur;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import modele.*;
import modele.joueur.Joueur;
import vue.Etat;

@SuppressWarnings("deprecation") 
public class InterfaceCommande implements Runnable, Observer {

	private Partie partie;	
	private Scanner inputReader = new Scanner(System.in);
	private ActionCommande action;
	private Joueur joueurEnCours;
	
	
	public InterfaceCommande(Partie partie) {
		this.partie = partie;
		Thread t = new Thread(this);
		t.start();	
	}
	
	
	public void run() {
		partie.addObserver(this);
		
		List<Joueur> joueur = partie.getJoueur();
		Iterator<Joueur> it = joueur.iterator();
		while(it.hasNext()) {
			it.next().addObserver(this);
		}
		
		//premierTour();
		
		//lit les imputs de clavier en boucle
		while(true) 
		    {
		        if (inputReader.hasNext())
		        {
		        	String input = inputReader.next();
		        	
		        	
		        	if (action == ActionCommande.tour) {
		        		if(input.equals("0")) {
		        			placerCarte();
		        		}
		        		
		        		else if (input.equals("1")) {
		        			bougerCarte();
		        		}
		        		
		        		else if (input.equals("2")) {
		        			terminerTour();
		        		}
		        		else {
		        			System.out.println("Saisie invalide");
		        			
		        		}
		        		
		        	}
		        }
		    }
	}
	
	
	
	
	public void imputJoueur() {		
		joueurEnCours = partie.getJoueurEnCours();
		action = ActionCommande.tour;
		partie.afficherPlateau();

		joueurEnCours.getCarteVictoire();
		Boolean aPlacerCarte=joueurEnCours.getAPlaceCarte();
		Boolean aBougerCarte=joueurEnCours.getABougeCarte();
		
		partie.ouAjouterCarte();
		if (!aPlacerCarte) {
			System.out.println("0 : placer une carte");
		}
		
		if (!aBougerCarte) {
			System.out.println("1 : bouger une carte");
		}
			
		System.out.println("2 : terminer le tour");
	}
		
	
	public void placerCarte() {
		if (!joueurEnCours.getAPlaceCarte()) {
			joueurEnCours.placerCarte();
		}
		else {
			System.out.println("action impossible");
		}
	}
	
	public void bougerCarte() {
		if (!joueurEnCours.getABougeCarte()) {
			joueurEnCours.bougerCarte();
		}
		else {
			System.out.println("action impossible");
		}
		
	}	
	
	public void premierTour() {
		partie.afficherPlateau();
		imputJoueur();
	}
	
	public void nouveauTour() {
		imputJoueur();
	}	
	
	public void terminerTour() {
		if (joueurEnCours.getAPlaceCarte()) {
			partie.nouveauTour();
		}
		else {
			System.out.println("action impossible");
		}
	}

	@Override
	public void update(Observable observable, Object arg) {
		if (!partie.getJoueurEnCours().getIA()) {
			if (observable instanceof Joueur) {
				nouveauTour();
			}		
			
			if (observable instanceof Partie) {
				Etat etat = (Etat) arg;
				if (etat == Etat.commande) {
					imputJoueur();
				}
			}
		}		
	}
}