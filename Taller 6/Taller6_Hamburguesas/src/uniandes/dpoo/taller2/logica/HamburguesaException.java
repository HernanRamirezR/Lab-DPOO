package uniandes.dpoo.taller2.logica;

@SuppressWarnings("serial")
public class HamburguesaException extends Exception {
	private String nomProductosRepetidos; 
	
    public HamburguesaException(String productosRepetidos) 
    {
    	super(productosRepetidos );
    	this.nomProductosRepetidos = productosRepetidos;
        
    }
    
    public String getProductosRepetidos()
    {
    	return (nomProductosRepetidos);
    }
    
   
    
}
