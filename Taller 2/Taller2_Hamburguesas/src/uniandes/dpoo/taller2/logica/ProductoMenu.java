package uniandes.dpoo.taller2.logica;

public class ProductoMenu implements Producto
{
	// Atributos
	
	String nombre;
	int precioBase;
	
	
	public ProductoMenu(String nombre, int precioBase)
	{
		this.nombre = nombre;
		this.precioBase = precioBase;
	}
	
	//Metodos implementados de la interfaz. 
	
		@Override
		public String getNombre() 
		{
			return this.nombre;
		}
		
		@Override
		public double getPrecio() 
		{
			return precioBase;
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
}
