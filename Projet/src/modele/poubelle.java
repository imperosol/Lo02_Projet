package modele;

import modele.joueur.IAAleatoire;
import modele.joueur.Joueur;
import modele.joueur.JoueurReel;

public class poubelle {
	/*Demande du nombre de joueurs*/
	
	do
	{
	    System.out.println("Nombre de joueurs ? (2 ou 3) ");
	    this.nbrJoueur = clavier.nextInt();
	}while (this.nbrJoueur!=2 && this.nbrJoueur!=3);
	        
	/*Initialisation des joueurs*/
	for (int i=0; i<this.nbrJoueur; i++)
	{
	    /*On demande à l'utilisateur s'il veut ajouter une IA ou un joueur humain*/
	    do {
	        System.out.println("Joueur" + (i+1) + "IA ? (oui : 1, non : 0) ");
	        IA = clavier.nextInt();
	    }while(IA != 1 && IA != 0);
	            
	    /*On crée un joueur humain ou IA selon le choix utilisateur*/
	    if (IA==1) {
	        this.joueur.add(new Joueur(i,new IAAleatoire() , this, this.pioche));
	    }
	    else {
	        this.joueur.add(new Joueur(i,new JoueurReel() , this, this.pioche));
	    }
	}
}
