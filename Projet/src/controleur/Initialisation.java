package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;

import modele.Partie;

public class Initialisation {
	
	private JList list_1;
	private JList list_2;
	private JList list_3;
	private JList list_4;
	private JCheckBox ModeAvancÚ;
	private JButton Bouton;
	

	public Initialisation(JList list1,JList list2,JList list3,JList list4,JCheckBox modeAvancÚ,JButton bouton, JFrame frame) {
		
		this.list_1=list1;
		this.list_2=list2;
		this.list_3=list3;
		this.list_4=list4;
		this.ModeAvancÚ = modeAvancÚ;
		this.Bouton = bouton;
		
		Bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boolean modeAvance = false;
				if (ModeAvancÚ.isSelected()) {
					modeAvance= true;
				}
				
				String Plateau = list_4.getSelectedValue().toString();
				

				String[] Joueur = {list_1.getSelectedValue().toString(),list_2.getSelectedValue().toString(),list_3.getSelectedValue().toString()};
				
				frame.dispose();
				
				Partie partie = new Partie(Plateau,modeAvance,Joueur);
				partie.jouerPartie();
				

			}
		});
	}
	
}
