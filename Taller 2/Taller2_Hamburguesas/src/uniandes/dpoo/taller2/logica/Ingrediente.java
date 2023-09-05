package uniandes.dpoo.taller2.logica;

public class Ingrediente {
	
	//Atributos 
	
	private String nombre;
	private int costoAdicional;
	
	//Metodos
	
	public Ingrediente(String nombre, int costoAdicional)
	{
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
		
	}
	
	public String getNombre() 
	{
		return this.nombre;
	}
	
	public int getCostoAdicional() 
	{
		return this.costoAdicional;
	}
	
}
