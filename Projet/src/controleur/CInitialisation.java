package controleur;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;

import modele.Context;
import modele.Partie;
import vue.InterfaceJeu;

public class CInitialisation {
	
	private JList list_1;
	private JList list_2;
	private JList list_3;
	private JList list_4;
	private JCheckBox ModeAvancé;
	private JButton Bouton;
	

	public CInitialisation(JList list1,JList list2,JList list3,JList list4,JCheckBox modeAvancé,JButton bouton, JFrame frame) {
		
		this.list_1=list1;
		this.list_2=list2;
		this.list_3=list3;
		this.list_4=list4;
		this.ModeAvancé = modeAvancé;
		this.Bouton = bouton;
		
		Bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boolean modeAvance = false;
				if (ModeAvancé.isSelected()) {
					modeAvance= true;
				}
				
				String plateau = list_4.getSelectedValue().toString();
				
				String[] joueur = {list_1.getSelectedValue().toString(),list_2.getSelectedValue().toString(),list_3.getSelectedValue().toString()};
				
				frame.dispose();
				
				Partie partie = new Partie(plateau,modeAvance,joueur);
			}
		});
	}
	
}
