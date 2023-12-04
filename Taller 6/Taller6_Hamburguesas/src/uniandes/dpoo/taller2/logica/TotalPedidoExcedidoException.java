package uniandes.dpoo.taller2.logica;

@SuppressWarnings("serial")
public class TotalPedidoExcedidoException extends Exception {
	private Producto productoErr; 
	public TotalPedidoExcedidoException(String msj, Producto productoE) 
    {
    	super(msj);
    	this.productoErr = productoE;    	
    }
	
	public String getProductoError()
    {
    	return (productoErr.getNombre());
    }

}
