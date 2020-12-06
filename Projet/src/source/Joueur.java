package source;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Joueur implements ScoreInterface {
	
	private Carte carteVictoire;
	private int numeroJoueur;
	private StratégieJoueur stratégie ; // pour pattern stratégie
	private List<Carte> main;
	private Pioche pioche;
	private Partie partie;
	
	public Joueur(int numeroJoueur,  StratégieJoueur strategie, Partie partie, Pioche pioche) { 
		this.numeroJoueur = numeroJoueur;
		
		this.stratégie = strategie;	
		this.pioche = pioche;
		this.partie = partie;
		if (partie.getModeAvance()==false) {
			this.carteVictoire = pioche.piocherCarte();
		}
		
		this.main= new ArrayList<Carte>();
		
		if (partie.getModeAvance()) {
			piocherCarte();
			piocherCarte();
			piocherCarte();
		}
		
	}
	
	public List<Carte> getMain() {
		return main;
	}
	
	public void setCarteVictoire(Carte carteVictoire) {
		this.carteVictoire = carteVictoire;
	}
	
	
	/* la méthode piocherCarte permet aussi de supprimer la carte utilisé
	 * Elle réduit la taille de la main quand la pioche est vide
	 */
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
	
	
	public Carte getCarteVictoire() {
		return this.carteVictoire;
	}
	
	
	/* affiche la main du joueur */
	public void affCarteMain() {
		System.out.print("\ntu as en main :");
		
		for (Carte carte : this.main) {
			System.out.print(" " + carte);
		}
		System.out.println("\nta Carte victoire est : " + this.carteVictoire);
	}
	
	public int tailleMain() {
		return main.size();
	}
	
	public boolean bougerCarteJoueur(List<Integer> positionCarte, List<Integer> positionFinale) {
		return partie.bougerCarte(positionCarte, positionFinale);
	}
	
	public boolean placerCarteJoueur(Carte carte, List<Integer> position) {
		return partie.ajouterCarte(position, carte);
	}
	
	public void  affPlateau() {
		partie.afficherPlateau();
	}
	

	public void tour() {
		if (partie.getModeAvance()==false) {
			piocherCarte();
		}
	
		partie.ouAjouterCarte();
		stratégie.jouer(this,partie);
		affPlateau();
		
		if (partie.getModeAvance()==true) {
			piocherCarte();
		}		
	}

	public String toString() {
		return "joueur " + this.numeroJoueur;
	}
	
	/* Visteur */

	@Override
	public void accept(ScoreVisitor visitor) {
		 visitor.visit(this);
	}
}


