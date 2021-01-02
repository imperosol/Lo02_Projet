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
	   
	   public int getWidth() {
		   return strategy.getWidth();
	   }
	   
	   public int getMinWidth() {
		   return strategy.getMinWidth();
	   }
	   
	   public int getHeight() {
		   return strategy.getHeight();
	   }
	   
	   public int getMinHeight() {
		   return strategy.getMinHeight();
	   }
}
