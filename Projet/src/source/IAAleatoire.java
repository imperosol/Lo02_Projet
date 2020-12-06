package source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class IAAleatoire implements StratégieJoueur{
	
	int numCarteJouee = -1;
	
	public void jouer(Joueur joueur,Partie partie) {
		
		Random random = new Random();
		

		int Aleatoire = (int) random.nextInt(2);
		
		if (Aleatoire==0) {
			Map<List<Integer>,Boolean> plateauAjout = partie.ouAjouterCarte();
			Set<List<Integer>> ListeDeCléLibre = plateauAjout.keySet();
			
			this.numCarteJouee = random.nextInt(joueur.getMain().size());
			
			Carte carte = (Carte) joueur.getMain().get(numCarteJouee);
			List<Integer> place =  (List<Integer>) ListeDeCléLibre.toArray()[random.nextInt(ListeDeCléLibre.size())];
			
			joueur.placerCarteJoueur(carte,place);
			
		}
		if (Aleatoire==1) {
			Map<List<Integer>,Boolean> plateauAjout = partie.ouAjouterCarte();
			Set<List<Integer>> ListeDeCléLibre = plateauAjout.keySet();
			
			this.numCarteJouee = random.nextInt(joueur.getMain().size());
			Carte carte = (Carte) joueur.getMain().get(this.numCarteJouee);
			List<Integer> place =  (List<Integer>) ListeDeCléLibre.toArray()[random.nextInt(ListeDeCléLibre.size())];
			
			
			joueur.placerCarteJoueur(carte,place);
			
			joueur.affPlateau();
			

			Map<List<Integer>,Carte> plateau = partie.getPlateau();
			List<Integer>[] ListeDeCléUtilisé = (List<Integer>[]) plateau.keySet().toArray(new List[0]);
			List<Integer>CléCarteADeplacer = ListeDeCléUtilisé[random.nextInt(ListeDeCléUtilisé.length)];			
			Map<List<Integer>,Boolean> EmplacementValide = partie.ouBougerCarte(CléCarteADeplacer);
			Set<List<Integer>> CléEmplacementValide = EmplacementValide.keySet();
			List<Integer> positionFinale =  (List<Integer>) CléEmplacementValide.toArray()[random.nextInt(CléEmplacementValide.size())];
			joueur.bougerCarteJoueur(CléCarteADeplacer, positionFinale);



		}
		
		if(Aleatoire==2) {
			List<Integer> positionCarte = new ArrayList<Integer>();
			List<Integer> positionFinale = new ArrayList<Integer>();
			boolean fin1 = false;
			while (fin1 == false) {
				positionCarte.add((int)random.nextInt(),(int)random.nextInt());
				positionFinale.add((int)random.nextInt(),(int)random.nextInt());

				joueur.bougerCarteJoueur(positionCarte, positionFinale);
			}			
			
			
			Map<List<Integer>,Boolean> plateau = partie.ouAjouterCarte();
			Set<List<Integer>> clé = plateau.keySet();
			
			this.numCarteJouee = random.nextInt(joueur.getMain().size());
			Carte carte = (Carte) joueur.getMain().get(this.numCarteJouee);
			List<Integer> place =  (List<Integer>) clé.toArray()[random.nextInt(clé.size())];
			
			
			
			joueur.placerCarteJoueur(carte,place);
			

		}
		
	}
	
	@Override
	public int getDerniereCarte() {
		return this.numCarteJouee;
	}
}