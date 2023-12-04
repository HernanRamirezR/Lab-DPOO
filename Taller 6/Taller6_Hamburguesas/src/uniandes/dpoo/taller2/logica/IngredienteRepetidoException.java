package uniandes.dpoo.taller2.logica;

@SuppressWarnings("serial")
public class IngredienteRepetidoException extends HamburguesaException {
	private String nomIngredientesRepetidos;

	public IngredienteRepetidoException(String ingredientesRepetidos) 
	{
        super("Error: Ingrediente(s) repetido(s) - " + ingredientesRepetidos);
        this.nomIngredientesRepetidos = ingredientesRepetidos;
    }
	
	public String getIngredientesRepetidos()
    {
    	return (nomIngredientesRepetidos);
    }
	
}
