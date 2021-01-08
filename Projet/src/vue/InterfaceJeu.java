package vue;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.CInterfaceJeu;
import modele.*;
import modele.joueur.Joueur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;

@SuppressWarnings("deprecation")
public class InterfaceJeu implements ActionListener,Observer{

	
	private Partie partie;
	private CInterfaceJeu controleur;
	private JPanel panelPlateau;
	private JFrame frame;
	private List<ButtonCarteVide> listeButtonVide = new ArrayList<ButtonCarteVide>();
	private List<ButtonCarteVide> listeButtonVideLarge = new ArrayList<ButtonCarteVide>();
	private List<ButtonCarte> selection = new ArrayList<ButtonCarte>();
	
	
	
	//Si on utilise le plateau variante, contient la position de la carte victoire
	private List<Integer> positionCarteVictoire;
	private Context contextPlateau;
	
	private List<Integer> positionCarte;
	
	//Panel pour la main, la carte victoire, bouton de fin de tour
	private JPanel panelJoueur;
	
	//Bouton de fin de tour
	private JButton finTour;
	private List<JPanel> listPanelMain;
	
	//panel pour le score
	private List<JPanel> listPanelScore = new ArrayList<JPanel>();
	
	//panel et label pour la crate victoire
	private JPanel jPCVictoire;
	private JLabel jLCVictoire;
	
	//panel vide
	private JLabel jLBlankMain;
	private JPanel panelScore;
	private JPanel panel;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public static void initializeInterface(Partie partie) {
		Thread t = new Thread();
		t.start();
	}
	
	
	
	public static void runInterface(Partie partie) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceJeu window = new InterfaceJeu(partie);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public InterfaceJeu(Partie partie) throws IOException {
		initialize(partie);
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize(Partie partie) throws IOException {
		List<Boolean> IAJoueur = new ArrayList<Boolean>();
		IAJoueur.add(false);
		IAJoueur.add(false);;
		
		//Cr�ation partie + ajout observeurs
		
		this.partie = partie;
		partie.addObserver(this);
		
		positionCarteVictoire = partie.getPositionCarteVictoire();
		
		List<Joueur> joueur = partie.getJoueur();
		Iterator<Joueur> it = joueur.iterator();
		while(it.hasNext()) {
			it.next().addObserver(this);
		}
		
		contextPlateau = partie.getContextPlateau();
		
		/*
		 * cr�ation fen�tre
		 */
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelPlateau = new JPanel();
		frame.getContentPane().add(panelPlateau, BorderLayout.WEST);
		
		controleur = new CInterfaceJeu(partie);
		
		panelJoueur = new JPanel();
		frame.getContentPane().add(panelJoueur, BorderLayout.NORTH);
		GridBagLayout gbl_panelJoueur = new GridBagLayout();
		gbl_panelJoueur.rowWeights = new double[]{0.0};
		gbl_panelJoueur.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelJoueur.setLayout(gbl_panelJoueur);
		
		finTour = new JButton("Fin tour");
		finTour.addActionListener(this);
		finTour.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_finTour = new GridBagConstraints();
		gbc_finTour.insets = new Insets(0, 0, 5, 0);
		gbc_finTour.gridx = 7;
		gbc_finTour.gridy = 0;
		panelJoueur.add(finTour, gbc_finTour);
		
		jLCVictoire = new JLabel("Carte Victoire");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelJoueur.add(jLCVictoire, gbc_lblNewLabel);
		
		jPCVictoire = new JPanel();
		GridBagConstraints gbc_carteVictoire = new GridBagConstraints();
		gbc_carteVictoire.insets = new Insets(0, 0, 5, 5);
		gbc_carteVictoire.fill = GridBagConstraints.WEST;
		gbc_carteVictoire.gridx = 1;
		gbc_carteVictoire.gridy = 0;
		panelJoueur.add(jPCVictoire, gbc_carteVictoire);
		
		jLBlankMain = new JLabel();
		jLBlankMain.setPreferredSize(new Dimension(100,150));
		GridBagConstraints gbc_txtMain = new GridBagConstraints();
		gbc_txtMain.insets = new Insets(0, 0, 0, 5);
		gbc_txtMain.gridx = 3;
		gbc_txtMain.gridy = 0;
		panelJoueur.add(jLBlankMain, gbc_txtMain);
		
		listPanelMain = new ArrayList<JPanel>();
		for (int i=4;i<=6;i++) {
			JPanel jPC = new JPanel();
			GridBagConstraints gbc_carte1 = new GridBagConstraints();
			gbc_carte1.insets = new Insets(0, 0, 5, 5);
			gbc_carte1.fill = GridBagConstraints.HORIZONTAL;
			gbc_carte1.gridx = i;
			gbc_carte1.gridy = 0;
			panelJoueur.add(jPC, gbc_carte1);
			listPanelMain.add(jPC);
		}
		
		controleur.premierTour();
		resetPlateau();
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		
		panelScore = new JPanel();
		panelScore.setPreferredSize(new Dimension(150,150));
		
		int nbJoueur = partie.getJoueur().size();
		panelScore.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc_txtMain2 = new GridBagConstraints();
		gbc_txtMain2.insets = new Insets(0, 10, 10, 5);
		gbc_txtMain2.gridx = 8;
		gbc_txtMain2.gridy = 0;
		panelJoueur.add(panelScore,gbc_txtMain2 );
		
		JPanel panel;
		
		for (int i=0;i<nbJoueur;i++) {
			panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = i;
			panelScore.add(panel, gbc_panel);
			listPanelScore.add(panel);
		}
	
		//Ajoute carte Victoire
		if (partie.getModeAvance()) {
			JLabel labelCV = new JLabel();
			labelCV.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			labelCV.setPreferredSize(new Dimension(100,150));
			jPCVictoire.add(labelCV);
		}
	}
	

	// Gestion des boutons
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		// Source bouton plateau
		if (src instanceof ButtonCarte) {
			//ajoute le boutton � la liste des boutons selectionn�s
			selection.add( (ButtonCarte) src );
			
			//si 1 bouton selectionn�
			if (selection.size() == 1) {
				((ButtonCarte) src).setBorder(BorderFactory.createLineBorder(Color.red));
				
				//Si le bouton est sur le plateau et que le joueur n'a pas encore boug� de carte
				if ((src instanceof ButtonCartePlateau) && (!partie.getJoueurEnCours().getABougeCarte())) {
					positionCarte = ((ButtonCartePlateau) src).getPosition();
					controleur.updatePlateau(positionCarte);
					
				}
				
				//si le bouton est dans la main du joueur
				if ((src instanceof ButtonCarteMain) && (!(partie.getJoueurEnCours().getAPlaceCarte()))) {
					resetUpdatePlateau(true);
				}
			}
			
			
			//Si 2 boutons sont selectionn�s
			if(selection.size() == 2) {
				ButtonCarteSelection();
			}
		}
		
		//Si le bouton fin du tour est press�
		if (src instanceof JButton) {
			if ((partie.getJoueurEnCours().getAPlaceCarte()) && (!partie.getFin())) {
				controleur.nouveauTour();
			}
			else {
				((JButton) src).setSelected(false);
			}
			
		}
		
		
	}
	
	//Si 2 boutons sont press�s
	public void ButtonCarteSelection() {
		ButtonCarte bouton1 = selection.get(0);
		ButtonCarte bouton2 = selection.get(1);
		Joueur joueurEnCours = partie.getJoueurEnCours();
			
		//button 1 et 2 identiques --> deselection du bouton
		if (bouton1 == bouton2) {
			selection.removeAll(selection);		
			controleur.resetUpdatePlateau();
			bouton1.setBorder(null);
		}
		
		//bouton 1 main et bouton 2 vide --> met la carte en main sur le plateau
		else if((bouton1 instanceof ButtonCarteMain) && (bouton2 instanceof ButtonCarteVide) && (!(joueurEnCours.getAPlaceCarte()))) {
			//On ajoute la carte de la main au plateau.

			selection.removeAll(selection);
			bouton1.setSelected(false);
			bouton2.setSelected(false);
			placerCarte((ButtonCarteMain)bouton1,(ButtonCarteVide)bouton2);
		}
		
		//bouton 2 main et bouton 1 vide --> met la carte en main sur le plateau
		else if ((bouton1 instanceof ButtonCarteVide) && (bouton2 instanceof ButtonCarteMain) && (!(joueurEnCours.getAPlaceCarte()))) {
			//On ajoute la carte de la main au plateau.
			
			selection.removeAll(selection);
			bouton1.setSelected(false);
			bouton2.setSelected(false);
			placerCarte((ButtonCarteMain)bouton2,(ButtonCarteVide)bouton1);
		}
		
		
		//Bouton 1 carte et bouton 2 vide --> bouge la carte sur le plateau
		else if ((bouton1 instanceof ButtonCartePlateau) && (bouton2 instanceof ButtonCarteVide) && (!(joueurEnCours.getABougeCarte()))) {
			//On bouge la carte du plateau
			selection.removeAll(selection);
			bouton1.setSelected(false);
			bouton2.setSelected(false);
			bougerCarte((ButtonCartePlateau)bouton1,(ButtonCarteVide)bouton2);
		}
		
		//Bouton 2 carte du plateau  --> seul la deuxi�me carte est selectionn�e + update plateau
		else if ((bouton2 instanceof ButtonCartePlateau) && (!(joueurEnCours.getABougeCarte()))){
			selection.removeAll(selection);
			selection.add(bouton2);
			bouton1.setSelected(false);
			bouton1.setBorder(null);
			bouton2.setBorder(BorderFactory.createLineBorder(Color.red));
			
			positionCarte = ((ButtonCartePlateau) bouton2).getPosition();
			controleur.updatePlateau(positionCarte);
		}		
		// on ne peut rien faire --> on selectionne seulement le bouton 2
		else {
			selection.removeAll(selection);
			selection.add(bouton2);
			bouton1.setSelected(false);
			bouton1.setBorder(null);
			bouton2.setBorder(BorderFactory.createLineBorder(Color.red));
			
			// bouton 2 carte de la main --> on update le plateau
			if((bouton2 instanceof ButtonCarteMain) && (!joueurEnCours.getAPlaceCarte())) {
				resetUpdatePlateau(true);
			}
		}
		
	}
	
	public void setScore() {
		List<Integer> listScore = partie.getListScore();
		Iterator<Integer> it = listScore.iterator();
		int nbJoueur = partie.getJoueur().size();
		for (int i=0;i<nbJoueur;i++) {
			JPanel panelScore = listPanelScore.get(i);
			JLabel labelScore = new JLabel("Score du joueur " + (i+1) + " : " + it.next());
			panelScore.add(labelScore);
			panelScore.updateUI();
		}
		
	}
	
	/* r�initialise l'affichage pour correspondre au joueurEnCours */
	public void resetJoueur() {
		Joueur joueurEnCours = partie.getJoueurEnCours();
		Carte carteVictoire = joueurEnCours.getCarteVictoire();
		if (!partie.getModeAvance()) {
			jPCVictoire.removeAll();
			jPCVictoire.add(new JLabel(new ImageIcon(carteVictoire.getFileName())));
		}
		resetMain(5);
		jPCVictoire.updateUI();
	}
	
	/* r�initialise la main, le i d�signe la position d'une carte d�j� utilis� */
	public void resetMain (int vide) {
		List<Carte> main = partie.getJoueurEnCours().getMain();
		ButtonCarte bouton;
		Iterator<Carte> it = main.iterator();
		int i=0;
		while (it.hasNext()) {
			if (i!=vide) {
				bouton = buttonCarteMain(it.next(),i);
				JPanel panelMain = listPanelMain.get(i);
				panelMain.removeAll();
				panelMain.add(bouton);
				panelMain.updateUI();
			}
			else {
				it.next();
				JPanel panelMain = listPanelMain.get(i);
				panelMain.removeAll();
				JLabel labelMain = new JLabel();
				labelMain.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
				labelMain.setPreferredSize(new Dimension(100,150));
				panelMain.add(labelMain);	
				panelMain.updateUI();
			}
			i++;
		}
		
		//rafraichit l'affichage
		
	}
	
	/* permet de recr�er le plateau */
	public void resetPlateau() {
		//nettoie l'ancien plateau et la liste de boutons
		panelPlateau.removeAll();
		listeButtonVide.clear();
		
		int width = partie.getWidthPlateau();
		int height = partie.getHeightPlateau();
		int minWidth = partie.getMinWidthPlateau();
		int minHeight = partie.getMinHeightPlateau();
		Map<List<Integer>,Carte> plateau = partie.getPlateau();
		Map<List<Integer>,Boolean> plateauBool = partie.getPlateauBool();
		
		//Si on a un plateau rectangle et que la largeur ou longueur est mininimale
		if (contextPlateau == Context.rectangle) {
			if (width == 5) {
				width+=2;
				minWidth-=1;
			}
			if (height == 3) {
				height+=2;
				minHeight-=1;
			}	
		}
		
		
		//panelPlateau.setLayout(new GridLayout(10, 1, 0, 0));
		panelPlateau.setLayout(new GridLayout(height, width, 0, 0));
		
		//cr�e un nouveau plateau de la taille sp�cifi�e
		Dimension dimCarte = panelPlateau.getSize();

		//System.out.println("dimension plateau origine =" + dimCarte);
		//System.out.println("width : " + width + " | height : " + height);
		
		dimCarte.setSize((int) (dimCarte.getHeight()*2*width)/(3*height),dimCarte.getHeight());
				
		if (dimCarte.getHeight() != 0) {
			panelPlateau.setMinimumSize(dimCarte);
			panelPlateau.setMaximumSize(dimCarte);
		}
		
		dimCarte.setSize(dimCarte.getWidth()/width, dimCarte.getHeight()/height);
		
		//parcourt toutes les cases du plateau et regarde le contenu de la case
		for (int i = height+minHeight-1;i>=minHeight;i--) {
			for (int j = minWidth;j<=width+minWidth-1;j++) {
				List<Integer> position = new ArrayList<Integer>();
				position.add(i);
				position.add(j);
				
				// si la case contient une carte
				if (plateau.containsKey(position)) {
					panelPlateau.add(buttonCartePlateau(plateau.get(position),position,dimCarte));
				}
				
				// si on peut ajouter une carte sur la position
				else if (plateauBool.containsKey(position)) {
					
					panelPlateau.add(buttonCarteVide(position));
				}
				
				// si la case contient la carte victoire (dans le cas du plateau variante)
				else if (position.equals(positionCarteVictoire)) {
					panelPlateau.add(buttonCarteVictoire(position));
				}
				
				//si la case ne contient rien
				else {
					//labelPlateau.add(Box.createGlue());
					panelPlateau.add(buttonCarteVideLarge(position));
				}
			}
		}
		
		// update l'affichage des plateaux
		panelPlateau.updateUI();
	}
	
	
	// d�sactive ou active les boutons vide selon le plateau booleen
	public void updatePlateau() {
		
		Map<List<Integer>,Boolean> plateauBool = partie.getPlateauBool();
		Iterator<ButtonCarteVide> it = listeButtonVide.iterator();
		List<Integer> position;
		ButtonCarteVide bouton;
		
		while (it.hasNext()) {
			bouton = it.next();
			position = bouton.getPosition();
			if (plateauBool.containsKey(position)) {
				bouton.setEnabled(true);
			}
			else {
				bouton.setEnabled(false);
			}
		}
		
		it = listeButtonVideLarge.iterator();
		while (it.hasNext()) {
			bouton = it.next();
			position = bouton.getPosition();
			if (plateauBool.containsKey(position)) {
				bouton.setEnabled(true);
			}
			else {
				bouton.setEnabled(false);
			}
		}
	}
	
	
	// permet de v�rouiller ou de d�verouiller toutes les cases du plateau o� on peut placer une carte
	public void resetUpdatePlateau(boolean state) {
		partie.getPlateauBool();
		Iterator<ButtonCarteVide> it = listeButtonVide.iterator();
		while (it.hasNext()) {
			it.next().setEnabled(state);
		}
	}
	
	// permet de v�rouiller ou de d�verouiller toutes les cases du plateau (sauf celles o� l'on peut placer des cartes
	public void resetUpdatePlateauLarge(boolean state) {
		partie.getPlateauBool();
		Iterator<ButtonCarteVide> it = listeButtonVideLarge.iterator();
		while (it.hasNext()) {
			it.next().setEnabled(state);
		}
	}
	
	/*communication controleur*/
	
	//Demande au controleur de placer une carte
	public void placerCarte(ButtonCarteMain buttonMain, ButtonCarteVide buttonCarte) {
		controleur.placerCarte(buttonMain.getCarte(), buttonCarte.getPosition());
		resetMain(buttonMain.getPosition());
	}
	
	//Demande au controleur de bouger une carte
	public void bougerCarte(ButtonCartePlateau buttonCarte, ButtonCarteVide buttonVide) {
		controleur.bougerCarte(buttonCarte.getPosition(), buttonVide.getPosition());
	}
	
	//Demande au controleur de commencer le premier tour
	public void premierTour() {
		controleur.premierTour();
	}
	
	//Demande au controleur de terminer le tour en cours et de commencer le suivant
	public void finTour() {
		controleur.nouveauTour();
	}

	/* constructeurs boutons */
	
	// bouton carte victoire sur le plateau
	private ButtonCarte buttonCarteVictoire(List<Integer> position) {
		ButtonCarte buttonCarte = new ButtonCartePlateau(position);
		buttonCarte.setEnabled(false);
		buttonCarte.setBorder(BorderFactory.createEmptyBorder());
		
		return buttonCarte;
	}
	
	
	// bouton carte sur plateau
	private ButtonCarte buttonCartePlateau(Carte carte, List<Integer> position,Dimension dimCarte) {
		ButtonCarte buttonCarte = new ButtonCartePlateau(carte,position,dimCarte);
		buttonCarte.addActionListener(this);
		buttonCarte.setBorder(BorderFactory.createEmptyBorder());
		
		return buttonCarte;
	}

	private ButtonCarte buttonCarteVideLarge(List<Integer> position) {
		ButtonCarte buttonCarte = new ButtonCarteVide(position);
		buttonCarte.addActionListener(this);
		buttonCarte.setEnabled(false);
		
		listeButtonVideLarge.add((ButtonCarteVide)buttonCarte);
		
		return buttonCarte;
	}
	
	// bouton carte vide sur la plateau
	private ButtonCarte buttonCarteVide(List<Integer> position) {
		ButtonCarte buttonCarte = new ButtonCarteVide(position);
		buttonCarte.addActionListener(this);
		
		listeButtonVide.add((ButtonCarteVide)buttonCarte);
		
		
		return buttonCarte;
	}
	
	// bouton carte main
	private ButtonCarte buttonCarteMain(Carte carte,int position) {
		ButtonCarte buttonCarte;
		buttonCarte = new ButtonCarteMain(carte,position);
		buttonCarte.addActionListener(this);
		buttonCarte.setBorder(BorderFactory.createEmptyBorder());
		
		return buttonCarte;
	}
	
	/* interface observeur*/
	
	@Override
	public void update(Observable observable, Object arg) {
		
		
		if (observable instanceof Partie) {
			Etat etat = (Etat) arg;
			if (etat == Etat.reset) {
				resetPlateau();
				resetUpdatePlateau(false);
			}
			else if (etat == Etat.update) {
				updatePlateau();
			}
			else if (etat == Etat.resetUpdate) {
				resetUpdatePlateau(false);
				resetUpdatePlateauLarge(false);
			}
			else if (etat == Etat.score) {
				setScore();
			}
			
		}
		
		if (observable instanceof Joueur) {
			Etat etat = (Etat) arg;
			if (etat == Etat.main) {
				resetMain(partie.getJoueurEnCours().getNumCarteJouee());
			}
			else {
				resetJoueur();
			}
		}
		
	}
}
