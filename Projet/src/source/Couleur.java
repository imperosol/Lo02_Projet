package source;

public enum Couleur {
	vert("V"),rouge("R"),bleu("B");

     private String abreviation ;  
      
     private Couleur(String abreviation) {  
         this.abreviation = abreviation ;  
    }  
      
     public String getAbreviation() {  
         return  this.abreviation ;  
    }  
}