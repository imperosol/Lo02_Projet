package Source;


import java.util.List;

public interface StratégieJoueur {
	public void jouer(Joueur joueur,Partie partie) ;
	public int getDerniereCarte();
}
