package modele;

public class Carte {

	private Couleur couleur;
	private Forme forme;
	private boolean plein;
	
	public Carte(Couleur couleur, Forme forme,boolean plein ) {
		this.couleur = couleur;
		this.forme = forme;
		this.plein = plein;
	}
	
	/*getters*/
	
	/*
	 * permet d'avoir le nom du fichier de la carte
	 */
	public String getFileName() {
		StringBuffer nomFichier = new StringBuffer();
		nomFichier.append("/images/");
		nomFichier.append(this.couleur.getAbreviation());
		nomFichier.append(this.forme.getAbreviation());
		nomFichier.append(!(this.plein) ? "P" : "V");
		nomFichier.append(".jpg");
		return nomFichier.toString();
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
	    return !(this.plein) ? "formes pleines" : "formes vides";
	}
	
	/*affiche les initiaux de la couleur puis de la forme puis de vide ou plein*/
	public String toString() {
	    String cartePleine = !(this.plein) ? "P" : "V";
	    return this.couleur.getAbreviation() + this.forme.getAbreviation() + cartePleine;
	}
}

