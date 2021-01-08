package vue;

import java.util.List;

public class ButtonCarteVide extends ButtonCarte{

	private static final long serialVersionUID = 1L;
	List<Integer> position;
	
	public ButtonCarteVide(List<Integer> position) {
		super();
		this.position = position;		
	}
	
	public List<Integer> getPosition(){
		return position;
	}
}