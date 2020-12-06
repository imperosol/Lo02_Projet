package source;

public interface ScoreVisitor {
	
	void visit(Joueur joueur);
	void visit(Partie partie);
	
}
