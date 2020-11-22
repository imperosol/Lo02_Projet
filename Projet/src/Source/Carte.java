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
	
	public int getForme() {
		return forme.ordinal();
	}
	
	public boolean getPlein() {
		return plein;
	}
	
	
	public String toString() {
		if (this.plein) {
			return this.couleur.getAbreviation() + this.forme.getAbreviation() + "P";
		}
		else {
			return this.couleur.getAbreviation() + this.forme.getAbreviation() + "V";
		}
	}
	
	public static void main(String[] args) {
		Carte carte = new Carte(Couleur.vert,Forme.cercle, true);
		System.out.println(carte);
	}
}

