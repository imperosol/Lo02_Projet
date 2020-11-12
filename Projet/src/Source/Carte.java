package Source;

public class Carte {
	public enum Couleur {
		vert,rouge,bleu;
	}
	
	public enum Forme {
		triangle,carre,cercle;
	}
	
	private Couleur couleur; // 0 = vert, 1 = rouge, 2 = bleu
	private Forme forme; // 0 = triangle, 1 = carré, 2 = cercle
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
			return this.couleur + " " + this.forme + " plein";
		}
		else {
			return this.couleur + " " + this.forme + " vide";
		}
	}
	
	public static void main(String[] args) {
		Carte carte = new Carte(Couleur.vert,Forme.cercle, true);
		System.out.println(carte);
	}
}

