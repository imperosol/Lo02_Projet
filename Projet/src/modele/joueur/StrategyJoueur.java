package modele.joueur;


import java.util.List;

import modele.Partie;

public interface StrategyJoueur {
	public void jouer(Joueur joueur,Partie partie) ;
	public void placerCarte(Joueur joueur);
	public void bougerCarte(Joueur joueur);
	
	public int getDerniereCarte();
}
