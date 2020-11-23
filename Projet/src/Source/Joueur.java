package Source;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Joueur implements ScoreInterface {
	
	private Carte carteVictoire;
	private int numeroJoueur;
	private StratégieJoueur stratégie ;
	private List<Carte> main;
	private Pioche pioche;

	// Private Partie;
	
	public Joueur(int numeroJoueur,  StratégieJoueur strategie, Partie partie, Pioche pioche) { 
		// Rajouter partie, on en a besoin pour faire les méthodes
		this.numeroJoueur = numeroJoueur;
		
		this.stratégie = strategie;	
		this.pioche = pioche;
		if (partie.modeAvance()==false) {
			this.carteVictoire = pioche.piocherCarte();
		}
		
		this.main= new ArrayList<Carte>();
		
		if (partie.modeAvance()) {
			piocherCarte();
			piocherCarte();
			piocherCarte();
		}
		
	}
	
	public List<Carte> getMain() {
		return this.main;
	}
	
	public void setCarteVictoire(Carte carteVictoire) {
		this.carteVictoire = carteVictoire;
	}
	
	public void piocherCarte() {
		Carte carte = this.pioche.piocherCarte();
		int i = stratégie.getDerniereCarte();
		
		if (carte != null) {
			if (this.stratégie instanceof JoueurReel) {
				System.out.println("tu as pioché : " + carte);
			}
			
			if (i != -1){
				this.main.set(i,carte);
			}
			else {
				this.main.add(carte);
			}
		}
		else {
			
			this.main.set(i,null);
			List<Carte> mainNew = new ArrayList<Carte>();
			Iterator<Carte> it = this.main.iterator();
			while (it.hasNext()) {
				carte = it.next();
				
				if (carte != null) {
					mainNew.add(carte);
				}
			}
			this.main = mainNew;
			
		}
	}
	
	public Carte consulterCarteVictoire() {
		return this.carteVictoire;
	}
	
	public void consulterCarteMain(Partie partie) {
		System.out.println("");
		System.out.print("tu as en main :");
		
		for (Carte carte : this.main) {
			System.out.print(" " + carte);
		}
		
		System.out.println("");
		System.out.println("ta Carte victoire est : " + this.carteVictoire);
	}
	
	public int tailleMain() {
		return main.size();
	}
	
	public boolean bougerCarteJoueur(List<Integer> positionCarte, List<Integer> positionFinale,Partie partie) {
		return partie.bougerCarte(positionCarte, positionFinale);
	}
	
	public boolean placerCarteJoueur(Carte carte, List<Integer> position, Partie partie) {
		return partie.ajouterCarte(position, carte);
	}
	
	public void  regarderPlateau(Partie partie) {
		partie.afficherPlateau();
	}
	

	public void tour(Partie partie,Pioche pioche) {

		if (partie.modeAvance()==false) {
			this.piocherCarte();
		}
		
		
		partie.ouAjouterCarte();
		this.consulterCarteVictoire();
		
		stratégie.jouer(this,partie);
	
		
		this.regarderPlateau(partie);
		
		if (partie.modeAvance()==true) {
			this.piocherCarte();
		}		
	}

	public String toString() {
		return "joueur " + this.numeroJoueur;
	}
	// Visteur

	@Override
	public void accept(ScoreVisitor visitor) {
		 visitor.visit(this);
	}
}


