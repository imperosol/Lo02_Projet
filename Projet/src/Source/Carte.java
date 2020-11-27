package Source;

public class Carte {

	private Couleur couleur;
	private Forme forme;
	private boolean plein;
	
	public Carte(Couleur couleur, Forme forme,boolean plein ) {
		this.couleur = couleur;
		this.forme = forme;
		this.plein = plein;
	}
	
	public int getCouleur() {
		return couleur.ordinal();
	}
	
	public String affCouleur() {
		return couleur.name();
	}
	
	public int getForme() {
		return forme.ordinal();
	}
	
	public String affForme() {
		return forme.name();
	}
	
	public boolean getPlein() {
		return plein;
	}
	
	public String affPlein() {
		if (this.plein) {
			return "formes pleines";
		}
		else {
			return "formes vides";
		}
	}
	
	//renvoi les initiaux de la couleur puis de la forme puis de Vide ou plein
	//utilisé dans l'affichage de plateau
	public String toString() {
		if (this.plein) {
			return this.couleur.getAbreviation() + this.forme.getAbreviation() + "P";
		}
		else {
			return this.couleur.getAbreviation() + this.forme.getAbreviation() + "V";
		}
	}
}

