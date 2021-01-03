package vue;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.ListSelectionModel;

import controleur.CInitialisation;

import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class InterfaceInitialisation {

	private JFrame frame;
	private final Action action = new SwingAction();

	private JList list_1;
	private JList list_2;
	private JList list_3;
	private JList list_4;
	private JCheckBox ModeAvancé;
	private JButton bouton;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceInitialisation window = new InterfaceInitialisation();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfaceInitialisation() {
		initialize();
		new CInitialisation(list_1,list_2,list_3,list_4,ModeAvancé,bouton,frame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1076, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		String[] J1 = {"Humain","IA"};
		String[] J3 = {"Humain","IA","Désactivé"};
		
		list_3 = new JList(J3);
		list_3.setToolTipText("");
		list_3.setSelectedIndex(2);
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_3.setForeground(Color.BLACK);
		list_3.setBackground(Color.WHITE);
		list_3.setBounds(762, 245, 52, 78);
		frame.getContentPane().add(list_3);
		
		list_1 = new JList(J1);
		list_1.setToolTipText("");
		list_1.setSelectedIndex(0);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setForeground(Color.BLACK);
		list_1.setBackground(Color.WHITE);
		list_1.setBounds(205, 245, 52, 78);
		frame.getContentPane().add(list_1);
		
		list_2 = new JList(J1);
		list_2.setToolTipText("");
		list_2.setSelectedIndex(0);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_2.setForeground(Color.BLACK);
		list_2.setBackground(Color.WHITE);
		list_2.setBounds(477, 245, 52, 78);
		frame.getContentPane().add(list_2);
		
		
		String[] Plateau = {"rectangulaire","triangulaire","variante"};
		
		list_4 = new JList(Plateau);
		list_4.setToolTipText("");
		list_4.setSelectedIndex(0);
		list_4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_4.setForeground(Color.BLACK);
		list_4.setBackground(Color.WHITE);
		list_4.setBounds(338, 115, 73, 78);
		frame.getContentPane().add(list_4);
		
		
		JTextPane txtpnJoueur = new JTextPane();
		txtpnJoueur.setText("Joueur 1");
		txtpnJoueur.setBounds(205, 200, 52, 20);
		frame.getContentPane().add(txtpnJoueur);
		
		JTextPane txtpnJoueur_1 = new JTextPane();
		txtpnJoueur_1.setText("Joueur 2");
		txtpnJoueur_1.setBounds(477, 200, 52, 20);
		frame.getContentPane().add(txtpnJoueur_1);
		
		JTextPane txtpnJoueur_2 = new JTextPane();
		txtpnJoueur_2.setText("Joueur 3");
		txtpnJoueur_2.setBounds(762, 200, 52, 20);
		frame.getContentPane().add(txtpnJoueur_2);
		
		JTextPane txtpnTitreDuJeu = new JTextPane();
		txtpnTitreDuJeu.setText("Titre du jeu");
		txtpnTitreDuJeu.setBounds(366, 32, 308, 20);
		frame.getContentPane().add(txtpnTitreDuJeu);
		
		JTextPane txtpnPlateau = new JTextPane();
		txtpnPlateau.setText("Plateau");
		txtpnPlateau.setBounds(338, 83, 73, 20);
		frame.getContentPane().add(txtpnPlateau);
		
		JTextPane txtpnModeDeJeu = new JTextPane();
		txtpnModeDeJeu.setText("Mode de Jeu");
		txtpnModeDeJeu.setBounds(622, 83, 89, 20);
		frame.getContentPane().add(txtpnModeDeJeu);
		
		bouton = new JButton("Lancer la partie");
		bouton.setBounds(439, 371, 125, 23);
		frame.getContentPane().add(bouton);
		
		ModeAvancé = new JCheckBox("Mode avanc\u00E9");
		ModeAvancé.setBounds(614, 115, 97, 23);
		frame.getContentPane().add(ModeAvancé);
	}
	


	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}