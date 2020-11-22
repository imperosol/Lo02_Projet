package Source;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class JoueurReel implements StratégieJoueur{
	public void jouer(Joueur joueur,Partie partie) {
		
		Scanner EntreeJoueur = new Scanner(System.in);

		System.out.println("Déroulement de votre tour?");
		 int action = EntreeJoueur.nextInt();
		
		if (action==0) {
				Iterator it = joueur.getMain().iterator();
				while (it.hasNext()) {
					System.out.print(it.next()+"  ");
				}
				System.out.println("Choix du numéro de la carte à jouer");
				Carte carte = (Carte) joueur.getMain().get(EntreeJoueur.nextInt()-1);

				Map<List<Integer>,Boolean> plateauAjout = partie.ouAjouterCarte();
				Set<List<Integer>> ListeDeCléLibre = plateauAjout.keySet();
				Iterator it2 = ListeDeCléLibre.iterator();
				while (it2.hasNext()) {
					System.out.print(it2.next()+"  ");
				}
				System.out.println("Choix de l'emplacement où sera joué la carte");

				List<Integer> position =  (List<Integer>) ListeDeCléLibre.toArray()[EntreeJoueur.nextInt()-1];
				
				
				joueur.placerCarteJoueur(carte, position, partie);
			
		}
		if (action==1) {
			Iterator it = joueur.getMain().iterator();
			while (it.hasNext()) {
				System.out.print(it.next()+"  ");
			}
			System.out.println("Choix du numéro de la carte à jouer");
			Carte carte = (Carte) joueur.getMain().get(EntreeJoueur.nextInt()-1);

			Map<List<Integer>,Boolean> plateauAjout = partie.ouAjouterCarte();
			Set<List<Integer>> ListeDeCléLibre = plateauAjout.keySet();
			Iterator it2 = ListeDeCléLibre.iterator();
			while (it2.hasNext()) {
				System.out.print(it2.next()+"  ");
			}
			System.out.println("Choix de l'emplacement où sera joué la carte");

			List<Integer> position =  (List<Integer>) ListeDeCléLibre.toArray()[EntreeJoueur.nextInt()-1];
			
			
			joueur.placerCarteJoueur(carte, position, partie);
			
			joueur.regarderPlateau(partie);
			

			Map<List<Integer>,Carte> plateau = partie.getPlateau();
			List<Integer>[] ListeDeCléUtilisé = (List<Integer>[]) plateau.keySet().toArray(new List[0]);
			for (int i=0; i <ListeDeCléUtilisé.length;i++) {
				System.out.print(ListeDeCléUtilisé[i]+"   ");
			}
			System.out.println("Emplacement de la carte à déplacer");
			List<Integer>CléCarteADeplacer = ListeDeCléUtilisé[EntreeJoueur.nextInt()-1];			
			
			
			
			Map<List<Integer>,Boolean> EmplacementValide = partie.ouBougerCarte(CléCarteADeplacer);
			Set<List<Integer>> CléEmplacementValide = EmplacementValide.keySet();
			Iterator it4 = CléEmplacementValide.iterator();
			while (it4.hasNext()) {
				System.out.print(it4.next()+"  ");
			}
			System.out.println("Choix de l'emplacement où sera déplacé la carte");
			List<Integer> positionFinale =  (List<Integer>) CléEmplacementValide.toArray()[EntreeJoueur.nextInt()-1];
			joueur.bougerCarteJoueur(CléCarteADeplacer, positionFinale, partie);

		}
		if(action==2) {			

			Map<List<Integer>,Carte> plateau = partie.getPlateau();
			List<Integer>[] ListeDeCléUtilisé = (List<Integer>[]) plateau.keySet().toArray(new List[0]);
			for (int i=0; i <ListeDeCléUtilisé.length;i++) {
				System.out.print(ListeDeCléUtilisé[i]+"   ");
			}
			System.out.println("Emplacement de la carte à déplacer");
			List<Integer>CléCarteADeplacer = ListeDeCléUtilisé[EntreeJoueur.nextInt()-1];			
			
			
			
			Map<List<Integer>,Boolean> EmplacementValide = partie.ouBougerCarte(CléCarteADeplacer);
			Set<List<Integer>> CléEmplacementValide = EmplacementValide.keySet();
			Iterator it4 = CléEmplacementValide.iterator();
			while (it4.hasNext()) {
				System.out.print(it4.next()+"  ");
			}
			System.out.println("Choix de l'emplacement où sera déplacé la carte");
			List<Integer> positionFinale =  (List<Integer>) CléEmplacementValide.toArray()[EntreeJoueur.nextInt()-1];
			joueur.bougerCarteJoueur(CléCarteADeplacer, positionFinale, partie);
			

			joueur.regarderPlateau(partie);
			
			
			Iterator it = joueur.getMain().iterator();
			while (it.hasNext()) {
				System.out.print(it.next()+"  ");
			}
			System.out.println("Choix du numéro de la carte à jouer");
			Carte carte = (Carte) joueur.getMain().get(EntreeJoueur.nextInt()-1);

			Map<List<Integer>,Boolean> plateauAjout = partie.ouAjouterCarte();
			Set<List<Integer>> ListeDeCléLibre = plateauAjout.keySet();
			Iterator it2 = ListeDeCléLibre.iterator();
			while (it2.hasNext()) {
				System.out.print(it2.next()+"  ");
			}
			System.out.println("Choix de l'emplacement où sera joué la carte");

			List<Integer> position =  (List<Integer>) ListeDeCléLibre.toArray()[EntreeJoueur.nextInt()-1];
			
			
			joueur.placerCarteJoueur(carte, position, partie);
		}
		
	}
}
