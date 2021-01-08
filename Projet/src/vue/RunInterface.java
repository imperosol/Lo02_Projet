package vue;

import modele.Partie;

public class RunInterface implements Runnable {
	private Partie partie;
	
	public RunInterface(Partie partie) {
		this.partie = partie;
		Thread t = new Thread( this);
		t.start();	
	}

	@Override
	public void run() {
		InterfaceJeu.runInterface(partie);
	}
	
}
