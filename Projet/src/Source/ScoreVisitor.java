package Source;

public interface ScoreVisitor {
	
	void visit(Joueur joueur);
	void visit(Partie partie);
	
}
