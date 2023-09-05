package uniandes.dpoo.taller2.logica;

import java.util.ArrayList;

public class Combo implements Producto
{
	private double descuento;
	private String nombreCombo;
	private ArrayList <ProductoMenu> itemsCombo;
	
	
	//Constructor
	public Combo(String nombreCombo, double descuento) 
	{
		this.nombreCombo = nombreCombo;
		this.descuento = descuento;
		ArrayList <ProductoMenu> itemsCombo = new ArrayList <ProductoMenu> ();
		this.itemsCombo = itemsCombo;
	}
	
	//Metodos
	
	public void agregarItemACombo(ProductoMenu itemCombo) 
	{
		itemsCombo.add(itemCombo);
	}
	
	
	// Implementaciones de la interfaz
	
	
	@Override
	public double getPrecio()
	{
		double precio = 0;
		for (Producto item: itemsCombo)
		{
			precio += item.getPrecio();
	
		}
		double precioTotal = precio -(this.descuento* precio);
		return precioTotal;
	}
		
	@Override
	public String getNombre()
	{
		return this.nombreCombo;
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
		for(int i = 0;i<lenghtDisponible+1;i++)
		{
			spaces.append(" ");
		}
		String spacesf = spaces.toString();
		String factura= "\n" + nombre + spacesf + precio;
		
		return factura;
	}
	

}
