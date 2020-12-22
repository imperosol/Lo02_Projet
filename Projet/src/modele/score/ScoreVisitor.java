package modele.score;

import modele.Partie;
import modele.joueur.Joueur;

public interface ScoreVisitor {
	
	void visit(Joueur joueur);
	void visit(Partie partie);
	
}
