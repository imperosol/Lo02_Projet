package Source;

import java.util.Queue;

import Source.Carte.Couleur;
import Source.Carte.Forme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Pioche {
	private Queue<Carte> pioche = new LinkedList<Carte>();
	
	public Pioche() {
		List<Carte> prePioche = new ArrayList<Carte>();
		Couleur[] couleur = {Couleur.vert,Couleur.rouge,Couleur.bleu};
		Forme[] forme = {Forme.cercle,Forme.carre,Forme.triangle};
		
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				prePioche.add(new Carte(couleur[i],forme[j], true));
				prePioche.add(new Carte(couleur[i],forme[j], false));
			}
		}
		
		Collections.shuffle(prePioche);
		
		for (int i=0;i<18;i++) {
			this.pioche.add(prePioche.get(i));
		}
	}
	
	public Carte piocherCarte(){
		return pioche.remove();
	}
	
	//test
	public static void main(String[] args) {
		Pioche pioche = new Pioche();
		for (int i=0;i<18;i++) {
			System.out.println(pioche.piocherCarte());
		}
	}
}
