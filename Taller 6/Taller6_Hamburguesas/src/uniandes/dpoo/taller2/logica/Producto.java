package uniandes.dpoo.taller2.logica;

public interface Producto {
	//Por ser una interface solo cuenta con metodos
	
	public boolean equals(Object obj);
	
	public double getPrecio();
	
	public String getNombre();
	
	public String generarTextoFactura();
	
	public int getCalorias();
	
}
