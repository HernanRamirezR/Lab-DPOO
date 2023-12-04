package uniandes.dpoo.taller2.logica;

@SuppressWarnings("serial")
public class ProductoRepetidoException extends HamburguesaException{
	
	private String nomProductosBaseRepetidos;

	public ProductoRepetidoException(String ProductosBaseRepetidos) 
	{
		super("Error: Producto repetido - " + ProductosBaseRepetidos);
		this.nomProductosBaseRepetidos = ProductosBaseRepetidos;
	}
	
	public String getProductosBaseRepetidos()
    {
    	return (nomProductosBaseRepetidos);
    }
	

}
