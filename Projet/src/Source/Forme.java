package Source;

public enum Forme {
	triangle("T"),carre("C"),cercle("o");
	
     private String abreviation ;  
      
     private Forme(String abreviation) {  
         this.abreviation = abreviation ;  
    }  
      
     public String getAbreviation() {  
         return  this.abreviation ;  
    }
}
