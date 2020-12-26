package modele.plateau;

import java.util.List;
import java.util.Map;

import modele.Carte;

public class ContextPlateau {
	   private StrategyPlateau strategy;

	   public ContextPlateau(StrategyPlateau strategy){
	      this.strategy = strategy;
	   }

	   public Map<List<Integer>, Boolean> ouAjouterCarte(Map<List<Integer>, Carte> plateau){
	      return strategy.ouAjouterCarte(plateau);
	   }
	   
	   public Map<List<Integer>, Boolean> ouBougerCarte(Map<List<Integer>, Carte> plateau, List<Integer> positionCarte){
		      return strategy.ouBougerCarte(plateau, positionCarte);
	   }
   
	   public void afficherPlateau(Map<List<Integer>,Carte> plateau, Map<List<Integer>,Boolean> plateauBool) {
		   strategy.afficherPlateau(plateau,plateauBool);
	   }
	   
	   public int getLongueur() {
		   return strategy.getLongueur();
	   }
	   
	   public int getLongueurMin() {
		   return strategy.getLongueurMin();
	   }
	   
	   public int getLargeur() {
		   return strategy.getLargeur();
	   }
	   
	   public int getLargeurMin() {
		   return strategy.getLargeurMin();
	   }
}
