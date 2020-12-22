package vue;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import controleur.Controleur;
import modele.*;
import modele.joueur.Joueur;

import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

@SuppressWarnings("deprecation")
public class InterfaceJeu implements ActionListener,Observer{

	
	Partie partie;
	Controleur controleur;
	private JPanel labelJoueur;
	private JPanel labelPlateau;
	private JFrame frame;
	private List<ButtonCarte> selection = new ArrayList<ButtonCarte>();
	private JButton finTour;
	private JPanel labelMain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceJeu window = new InterfaceJeu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public InterfaceJeu() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		List<Boolean> IAJoueur = new ArrayList<Boolean>();
		IAJoueur.add(false);
		IAJoueur.add(false);;
		
		partie = new Partie(Context.triangle,false,false,IAJoueur);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 886, 690);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		labelJoueur = new JPanel();
		frame.getContentPane().add(labelJoueur, BorderLayout.NORTH);
		labelJoueur.setLayout(new BorderLayout(0, 0));
		
		finTour = new JButton("Fin tour");
		finTour.setHorizontalAlignment(SwingConstants.LEFT);
		finTour.addActionListener(this);
		labelJoueur.add(finTour, BorderLayout.EAST);
		
		labelMain = new JPanel();
		labelJoueur.add(labelMain, BorderLayout.CENTER);
		GridBagLayout gridMain = new GridBagLayout();
		gridMain.columnWidths = new int[]{0};
		gridMain.rowHeights = new int[]{0};
		gridMain.columnWeights = new double[]{Double.MIN_VALUE};
		gridMain.rowWeights = new double[]{Double.MIN_VALUE};
		labelMain.setLayout(gridMain);
		
		labelPlateau = new JPanel();
		frame.getContentPane().add(labelPlateau, BorderLayout.WEST);
		labelPlateau.setLayout(new GridLayout(3, 7, 0, 0));
		
		controleur = new Controleur(partie);
		
		
		/*
	
		BufferedImage buttonIcon = ImageIO.read(new File("./images/carte.png"));
		buttonIcon.getScaledInstance( 100, 200,  java.awt.Image.SCALE_SMOOTH ) ;
		ImageIcon imageIcon2 = new ImageIcon(getScaledInstance(buttonIcon,100,200,RenderingHints.VALUE_INTERPOLATION_BICUBIC,true));
		
		JButton button = new JButton(imageIcon2);
		
		
		
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.print("PLOP");
			}
		});
		

		
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		plateau.add(button);
		
		*/
		
		Carte carte = new Carte(Couleur.bleu,Forme.cercle,false);
		Map<List<Integer>,Carte> plateau = new HashMap<List<Integer>,Carte>();
		List<Integer> position = new ArrayList<Integer>();
		position.add(1);
		position.add(2);
		
		
		plateau.put(position, carte);
		
		position = new ArrayList<Integer>();
		position.add(2);
		position.add(2);
		plateau.put(position, carte);
		
		
		
		Map<List<Integer>,Boolean> plateauBool = new HashMap<List<Integer>,Boolean>();
		position = new ArrayList<Integer>();
		position.add(1);
		position.add(1);
		plateauBool.put(position, true);
		
		position = new ArrayList<Integer>();
		position.add(0);
		position.add(1);
		plateauBool.put(position, true);
		
		resetPlateau(5,5,plateau,plateauBool);
		partie.nouveauTour();
		partie.getJoueurEnCours().piocherCarte();
		resetMain();
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
	}
	
	/*
	 * Permet de recréer un nouveau plateau à chaques changements
	 */
	

	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		// Source bouton plateau
		if (src instanceof ButtonCarte) {
			//ajoute le boutton à la liste des boutons selectionnés
			selection.add( (ButtonCarte) src );
			
			//Si 2 boutons sont selectionnés
			if(selection.size() == 2) {
				ButtonCarteSelection();
			}
		}
		
		if (src instanceof JButton) {
			//rajouter condition joueur
			controleur.nouveauTour();
		}
		
		
	}
	
	
	public void ButtonCarteSelection() {
		ButtonCarte bouton1 = selection.get(0);
		ButtonCarte bouton2 = selection.get(1);
		
		
		//button 1 et 2 identiques
		if (bouton1 == bouton2) {
			selection.removeAll(selection);		
			System.out.println("PLOP identique");
		}
		
		//bouton 1 main et bouton 2 vide
		else if((bouton1 instanceof ButtonCarteMain) && (bouton2 instanceof ButtonCarteVide)) {
			//On ajoute la carte de la main au plateau.
			selection.removeAll(selection);
			System.out.println("PLOP placer");
			bouton1.setSelected(false);
			bouton2.setSelected(false);
		}
		
		//bouton 2 main et bouton 1 vide
		else if ((bouton1 instanceof ButtonCarteVide) && (bouton2 instanceof ButtonCarteMain)) {
			//On ajoute la carte de la main au plateau.
			selection.removeAll(selection);
			System.out.println("PLOP placer");
			bouton1.setSelected(false);
			bouton2.setSelected(false);
		}
		
		
		//Bouton 1 carte et bouton 2 vide
		else if ((bouton1 instanceof ButtonCartePlateau) && (bouton2 instanceof ButtonCarteVide)) {
			//On bouge la carte du plateau
			selection.removeAll(selection);
			System.out.println("PLOP bouger");
			bouton1.setSelected(false);
			bouton2.setSelected(false);
		}
		
		//Bouton 2 carte et bouton 1 vide
		else if ((bouton1 instanceof ButtonCarteVide) && (bouton2 instanceof ButtonCartePlateau)) {			
			//On bouge la carte du plateau
			selection.removeAll(selection);
			System.out.println("PLOP bouger");
			bouton1.setSelected(false);
			bouton2.setSelected(false);
		}
		
		//2 cartes selectionnées mais on ne peut rien faire, seul la deuxième carte est selectionnée
		else {
			selection.removeAll(selection);
			selection.add(bouton2);
			bouton1.setSelected(false);
			System.out.println("PLOP deselection");
		}		
	}
	
	public void resetMain () {
		labelMain.removeAll();
		List<Carte> main = partie.getJoueurEnCours().getMain();
		
		labelMain.setLayout(new GridLayout(1, main.size(), 0, 0));
		
		Iterator<Carte> it = main.iterator();
		int i=0;
		while (it.hasNext()) {
			labelMain.add(buttonCarteMain(it.next(),i));
			i++;
		}
		
		
	}
	
	
	public void resetPlateau(int longueur,int largeur,Map<List<Integer>,Carte> plateau, Map<List<Integer>,Boolean> plateauBool) {
		//nettoie l'ancien plateau
		labelPlateau.removeAll();
		
		
		//crée un nouveau plateau de la taille spécifiée
		labelPlateau.setLayout(new GridLayout(longueur, largeur, 0, 0));
		for (int i = 0;i<longueur;i++) {
			for (int j = 0;j<largeur;j++) {
				List<Integer> position = new ArrayList<Integer>();
				position.add(i);
				position.add(j);
				if (plateau.containsKey(position)) {
					labelPlateau.add(buttonCartePlateau(plateau.get(position),position));
					
				}
				else if (plateauBool.containsKey(position)) {
					labelPlateau.add(buttonCarteVide(position));
				}
				else {
					labelPlateau.add(Box.createGlue());
				}
			}
		}
		
	}
	
	public void finTour() {
		
	}
	
	private ButtonCarte buttonCartePlateau(Carte carte, List<Integer> position) {
		ButtonCarte buttonCarte = new ButtonCartePlateau(carte,position);
		buttonCarte.addActionListener(this);
		
		return buttonCarte;
	}

	private ButtonCarte buttonCarteVide(List<Integer> position) {
		ButtonCarte buttonCarte = new ButtonCarteVide(position);
		buttonCarte.addActionListener(this);
		
		return buttonCarte;
	}
	
	private ButtonCarte buttonCarteMain(Carte carte,int position) {
		ButtonCarte buttonCarte = new ButtonCarteMain(carte,position);
		buttonCarte.addActionListener(this);
		
		return buttonCarte;
	}
	
	

	@Override
	public void update(Observable observable, Object arg1) {
		
		if (observable instanceof Partie) {
			Partie partie = (Partie) observable;
			resetPlateau(partie.getLongueurPlateau(),partie.getLargeurPlateau(),partie.getPlateau(),partie.getPlateauBool());
		}
		
	}
}
