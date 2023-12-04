package uniandes.dpoo.taller2.logica;

public class Bebida implements Producto
{
	private String nombre;
	private int precio;
	private int calorias;
	
	public Bebida(String nombre, int precio, int calorias)
	{
		this.nombre = nombre;
		this.precio = precio;
		this.calorias = calorias;
	}
	
	@Override
	public String getNombre() 
	{
		return this.nombre;
	}
	
	@Override
	public double getPrecio() 
	{
		return this.precio;
	}
	
	@Override
	public String generarTextoFactura()
	{
		
		int lenghtDisponible = 60;
		double precioNumero = getPrecio();
		String precio = Double.toString(precioNumero);
		String nombre = getNombre();
		lenghtDisponible-=(nombre.length()+precio.length());
		StringBuffer spaces = new StringBuffer();
		for(int i = 0; i <lenghtDisponible+1 ; i++)
		{
			spaces.append(" ");
		}
		String spacesf = spaces.toString();
		String texto = "\n" + nombre + spacesf + precio;
		return texto;
	}
	
	@Override
	public int getCalorias() 
	{
		return this.calorias;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Bebida otra = (Bebida) obj;
	    return this.nombre == otra.nombre;
	}
	
}
