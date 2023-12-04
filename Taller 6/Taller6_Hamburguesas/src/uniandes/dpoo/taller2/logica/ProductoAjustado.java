package uniandes.dpoo.taller2.logica;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductoAjustado implements Producto 
{
	
	private ProductoMenu base;
	private HashMap <String, Ingrediente> agregados;
	private HashMap <String, Ingrediente> eliminados;

	
	public ProductoAjustado(ProductoMenu menuBase) 
	{
		this.base = menuBase;
		HashMap <String, Ingrediente> agregados = new HashMap<>();
		HashMap <String, Ingrediente> eliminados = new HashMap<>();
		this.agregados = agregados;
		this.eliminados = eliminados;
		
	}
	
	public HashMap<String, Ingrediente> getAgregados() {
        return agregados;
    }
	
	public HashMap<String, Ingrediente> getEliminados() {
        return eliminados;
    }
	
	//Metodos implementados de la interfaz. 
	public void agregarIngrediente(Ingrediente ingrediente)
	{
		String nombreIngrediente = ingrediente.getNombre();
		agregados.put(nombreIngrediente, ingrediente);
	}
	
	public void eliminarIngrediente(Ingrediente ingrediente)
	{
		String nombreIngrediente = ingrediente.getNombre();
		agregados.remove(nombreIngrediente);
		eliminados.put(nombreIngrediente, ingrediente);
		
	}
	
	@Override
	public String getNombre() 
	{
		String nombreBase = base.getNombre();
		ArrayList <Ingrediente> ingAgregados = new ArrayList<Ingrediente>(agregados.values());
		ArrayList <Ingrediente> ingEliminados = new ArrayList<Ingrediente>(eliminados.values());
		
		String nombre = nombreBase;
		
		StringBuilder nombreAgregados = new StringBuilder();
		StringBuilder nombreEliminados = new StringBuilder();
		
		if (!ingAgregados.isEmpty()) 
		{
			nombreAgregados.append("Con: ");
			for(Ingrediente ingrediente : ingAgregados)
			{
				nombreAgregados.append(ingrediente.getNombre() + " ");
			}
		}
		
		if (!eliminados.isEmpty()) 
		{
			nombreEliminados.append("Sin: ");
			for(Ingrediente ingrediente : ingEliminados)
			{
				nombreEliminados.append(ingrediente.getNombre() + " ");
			} 
		}
		String nombresAg = nombreAgregados.toString();
		String nombresEl = nombreEliminados.toString();
		
		return nombre +" "+  nombresAg +" "+ nombresEl+ " ";
	}
	
	@Override
	public double getPrecio() 
	{
		double precioBase = base.getPrecio();
		double precioAjustado = 0;
		
		ArrayList <Ingrediente> ingAgregados = new ArrayList<Ingrediente>(agregados.values());
		
		for (Ingrediente ingrediente: ingAgregados)
		{
			precioAjustado+= ingrediente.getCostoAdicional();
		}
		double precioTotal = precioAjustado + precioBase;
		
		return precioTotal;
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
		
		int caloriasBase = base.getCalorias();
		int caloriasAjustadas = 0;
		
		ArrayList <Ingrediente> ingAgregados = new ArrayList<Ingrediente>(agregados.values());
		
		for (Ingrediente ingrediente: ingAgregados)
		{
			caloriasAjustadas+= ingrediente.getCalorias();
		}
		int caloriasTotales =  caloriasAjustadas + caloriasBase;
		
		return caloriasTotales;
		
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    ProductoAjustado otra = (ProductoAjustado) obj;
	    return this.getNombre() == otra.getNombre();
	}
	
	
	
}
