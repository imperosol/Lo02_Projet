package modele;

import java.util.Queue;
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
		
		//initialisation pioche
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
		if (!(this.piocheVide())) {
			return pioche.remove();
		}
		else {
			return null;
		}
		
	}
	
	public Boolean piocheVide() {
		return pioche.isEmpty();
	}

}
