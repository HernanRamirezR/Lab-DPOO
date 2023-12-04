package uniandes.dpoo.taller2.consola;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import uniandes.dpoo.taller2.logica.Ingrediente;
import uniandes.dpoo.taller2.logica.Pedido;
import uniandes.dpoo.taller2.logica.Producto;
import uniandes.dpoo.taller2.logica.ProductoMenu;
import uniandes.dpoo.taller2.logica.Restaurante;
import uniandes.dpoo.taller2.logica.TotalPedidoExcedidoException;
import uniandes.dpoo.taller2.logica.Bebida;
import uniandes.dpoo.taller2.logica.Combo;
import uniandes.dpoo.taller2.logica.HamburguesaException;

public class Aplicacion 
{
	
	private Restaurante restaurante;
	
	public void mostrarMenu() throws FileNotFoundException, IOException, HamburguesaException, TotalPedidoExcedidoException {
		
		System.out.println("\n Bienvenido al restaurante \n");
		
		System.out.println("Opciones del restaurante \n");
		
		System.out.println("1. Consultar el menu");
		System.out.println("2. Iniciar un pedido");
		System.out.println("3. Modificar el pedido actual");
		System.out.println("4. Cerrar y guardar el pedido ");
		System.out.println("5. Consultar un pedido del historial");
		System.out.println("6. Salir de la aplicacion");
		
		int opcionSeleccionada = Integer.parseInt(input("Por favor seleccione una opcion"));
		ejecutarOpcion(opcionSeleccionada);
		
	}
	
	public void ejecutarOpcion(int opcionSeleccionada) throws FileNotFoundException, IOException, HamburguesaException, TotalPedidoExcedidoException 
	{
		restaurante = new Restaurante();
		boolean continuar = true;
		while (continuar)
		{
			try
			{
				if (opcionSeleccionada == 1) 
				{
					ejecutarOpcion1();
					opcionSeleccionada=0;
				}
				else if (opcionSeleccionada == 2 && restaurante != null) 
				{
					ejecutarOpcion2();        
			        opcionSeleccionada=0;
			    }
					
				else if (opcionSeleccionada == 3 && restaurante != null)
				{
					ejecutarOpcion3();
					opcionSeleccionada=0;
				}
				else if (opcionSeleccionada == 4 && restaurante != null)
				{
					ejecutarOpcion4();
					// cerrar y guardar un pedido
					opcionSeleccionada=0;
				}
				else if (opcionSeleccionada == 5 && restaurante != null)
				{
					ejecutarOpcion5();
					opcionSeleccionada=0;
				}
				else if (opcionSeleccionada == 6 && restaurante != null)
				{
					System.out.println("Saliendo de la aplicacion ...");
					continuar = false;
					
				}
				else if (restaurante == null)	
				{
					System.out.println("Ha ocurrido un error al cargar la aplicacion. ");
				}
				else if (opcionSeleccionada == 0)	
				{
					
					System.out.println("Opciones del restaurante \n");
					
					System.out.println("1. Consultar el menu");
					System.out.println("2. Iniciar un pedido");
					System.out.println("3. Modificar el pedido actual");
					System.out.println("4. Cerrar y guardar el pedido ");
					System.out.println("5. Consultar un pedido del historial");
					System.out.println("6. Salir de la aplicacion");
					opcionSeleccionada = Integer.parseInt(input("\nPor favor seleccione una opcion"));
				}
				else
				{
					System.out.println("Por favor seleccione una opción valida.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los numeros de las opciones.");
			}
		}
	}

	private void ejecutarOpcion5() 
	{
		// Consultar historial de  pedidos
		System.out.println("Para consultar el historial de pedidos por favor ingrese un ID: ");
		int id = Integer.parseInt(input("Ingrese el ID"));
		
		HashMap <Integer , Pedido> historial = restaurante.getHistorialPedidos();
		Pedido elPedido = historial.get(id);
		
		if (elPedido == null) {
			System.out.println("No existe un pedido con el ID indicado");;
		}
		else {
			elPedido.imprimirFactura(id);
		}
	}

	private void ejecutarOpcion4() 
	{
		//Guardar y cerrar un pedido
		boolean esIdentico = restaurante.verificarProductoIdentico();
		if (!esIdentico) 
		{
			int idPedidoCerrado = restaurante.cerrarYGuardarPedido();
			System.out.println("El id del pedido recientemente cerrado es: "+ idPedidoCerrado);
		}
		else
		{
			System.out.println("\n¡Ya existe un pedido con los mismos productos!\n");
		}
	}

	private void ejecutarOpcion3() throws TotalPedidoExcedidoException 
	{
		//Modificar el pedido actual
		System.out.println("Eligio modificar el pedido actual. Escoga si quiere añadir o eliminar un item: ");
		
		String opcionStr;
		int opcion = 0;
		
        do 
        {	
        	System.out.println("1. Añadir");
            System.out.println("2. Eliminar");
            opcionStr = input("Por favor, seleccione una opcion (1 o 2)");
            try 
            {
                opcion = Integer.parseInt(opcionStr);
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Opcion no valida. Por favor, ingrese 1 o 2.");
            }

            if (opcion < 1 || opcion > 2) 
            {
                System.out.println("Opcion no valida. Por favor, ingrese 1 o 2.");
            }
        } 
        while (opcion < 1 || opcion > 2);

        if (opcion == 1) 
        {
            //Añadir item
        	System.out.println("A continuacion puede añadir un item, puede elegir entre un producto o un combo: \n");
        	try {
        	agregarItem();
        	}
        	catch (TotalPedidoExcedidoException e) {
        		System.err.println("Error al añadir "+ e.getProductoError()+ "\n");
                System.err.println(e.getMessage());
        		
        	}
        } 
        else
        {
        	System.out.println("A continuacion puede eliminar un item de su pedido:  \n");
        	eliminarItem();
        }
	}
	
	private void eliminarItem() 
	{
		Pedido pedidoActual = restaurante.getPedidoEnCurso();
		ArrayList <Producto> itemsPedido = pedidoActual.getItemsPedido();
		
		int opI = 0;
		String opIStr;
		
        do 
        {	int i = 1;
            for (Producto items: itemsPedido) 
            {
            	String nombreCombo = items.getNombre();
            	double precioCombo = items.getPrecio();
            	String index = Integer.toString(i);
            	System.out.println(index+".) " +nombreCombo  +"\t$"+ precioCombo);
            	i++;
            }
            opIStr = input("Por favor, seleccione una opcion (1-"+ itemsPedido.size()+")");
            try 
            {
                opI = Integer.parseInt(opIStr);
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Opcion no valida. Por favor, ingrese un numero entre 1 y "+ itemsPedido.size()+")");
            }

            if (opI < 1 || opI > itemsPedido.size()) 
            {
                System.out.println("Opcion no valida. Por favor, ingrese un numero entre 1 y "+ itemsPedido.size()+")");
            }
        } 
        while (opI < 1 || opI > itemsPedido.size());
        
        Producto eliminado = itemsPedido.get(opI-1);
        String nombreEliminado = eliminado.getNombre();
        itemsPedido.remove(opI-1);
        System.out.println("Se elimino el item : "+ nombreEliminado );
	}
	
	private void agregarItem() throws TotalPedidoExcedidoException
	{
		String opcionStr;
		int opcion = 0;
		
        do 
        {	
        	System.out.println("1. Producto");
            System.out.println("2. Combo");
            System.out.println("3. Bebida");
            opcionStr = input("Por favor, seleccione una opcion (1, 2 o 3)");
            try 
            {
                opcion = Integer.parseInt(opcionStr);
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Opcion no valida. Por favor, ingrese 1, 2 o 3.");
            }

            if (opcion < 1 || opcion > 3) 
            {
                System.out.println("Opcion no valida. Por favor, ingrese 1, 2 o 3.");
            }
        } 
        while (opcion < 1 || opcion > 3);

        if (opcion == 1) 
        {
            pedirProducto();
        } 
        else if (opcion ==2)
        {
        	pedirCombo(); 
        }
        else
        {
        	pedirBebida();
        }
		
	}

	private void ejecutarOpcion2() throws TotalPedidoExcedidoException 
	{
		//iniciar un pedido 
		System.out.println("Va a iniciar un nuevo pedido");
		String nombreCliente = input("Ingrese su nombre ");
		String direccionCliente = input("Ingrese su direccion ");
		
		int id = restaurante.iniciarPedido(nombreCliente, direccionCliente);
		if (id != 0) 
		{
			System.out.println("A continuacion debe añadir un item, puede elegir entre un producto, un combo o una bebida: \n");
			agregarItem();
			System.out.println("El id de su pedido es: " +id);
		}
	}

	private void pedirProducto() throws TotalPedidoExcedidoException
	{
		System.out.println("Ha seleccionado Producto. Escoga uno de los siguientes: \n");
		ArrayList <ProductoMenu> menuBase = restaurante.getMenuBase();
		int op = 0;
		String opStr;
		
        do 
        {	
        	int i = 1;
            for (Producto producto: menuBase) 
            {
            	String nombreProducto = producto.getNombre();
            	double precioProducto = producto.getPrecio();
            	String index = Integer.toString(i);
            	System.out.println(index+".) " +nombreProducto  +"\t$"+ precioProducto);
            	i++;
            }
            opStr = input("Por favor, seleccione una opcion (1 -22)");
            try 
            {
                op = Integer.parseInt(opStr);
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Opcian no valida. Por favor, ingrese 1 o 2.");
            }

            if (op < 1 || op > 22) 
            {
                System.out.println("Opcian no valida. Por favor, ingrese un numero entre 1 y 22.");
            }
        } 
        while (op < 1 || op > 22); 
        
     ProductoMenu productoBase = menuBase.get(op-1);
     String nombreProducto = productoBase.getNombre();
     System.out.println("Eligió: " + nombreProducto);
     
     System.out.println("Desea añadir o eliminar algun ingrediente? ");
     int opcionAjuste = 0;
		String opAjusteStr;
		
     do 
     {	
    	 System.out.println("1. Modificar producto base");
         System.out.println("2. Mantener producto base");
         
         opAjusteStr = input("Por favor, seleccione una opción (1 o 2)");
         try 
         {
             opcionAjuste = Integer.parseInt(opAjusteStr);
         } 
         catch (NumberFormatException e) 
         {
             System.out.println("Opcion no valida. Por favor, ingrese 1 o 2.");
         }

         if (opcionAjuste < 1 || opcionAjuste > 2) 
         {
             System.out.println("Opcion no valida. Por favor, ingrese un numero entre 1 y 2.");
         }
     } 
     while (opcionAjuste < 1 || opcionAjuste > 2);
     
     if (opcionAjuste ==1)
     {
    	 ArrayList <Ingrediente> ingredientes = restaurante.getIngredientes();
    	 
    	 int i = 1;
         for (Ingrediente ingrediente: ingredientes) 
         {
         	String nombreIngrediente = ingrediente.getNombre();
         	double precioIngrediente = ingrediente.getCostoAdicional();
         	String index = Integer.toString(i);
         	System.out.println(index+".) " +nombreIngrediente  +"\t$"+ precioIngrediente);
         	i++;
         }
         
    	 int addSize = Integer.parseInt(input("Cuantos ingredientes desea añadir"));
    	 int elimSize = Integer.parseInt(input("Cuantos ingredientes desea quitar"));
    	 
    	 ArrayList <Ingrediente> ingredientesAdd = new ArrayList <Ingrediente>();
    	 for (int j = 0; j < addSize; j++) 
    	 {
    		int numIngredienteAdd = Integer.parseInt(input("Ingrese un ingrediente de la lista para añadir"));
    		Ingrediente ingredienteAdd = ingredientes.get(numIngredienteAdd-1);
    		ingredientesAdd.add(ingredienteAdd);
    	 }
    	 
    	 ArrayList <Ingrediente> ingredientesElim = new ArrayList <Ingrediente>();
    	 for (int k = 0; k < elimSize; k++) 
    	 {
    		int numIngredienteElim = Integer.parseInt(input("Ingrese un ingrediente de la lista para eliminar"));
    		Ingrediente ingredienteElim = ingredientes.get(numIngredienteElim-1);
    		ingredientesElim.add(ingredienteElim);
    	 }
    	 restaurante.modificarProductoBase(productoBase, ingredientesAdd, ingredientesElim);
     }
     else 
     {
    	 
    	 restaurante.agregarItemsPedido(productoBase);
         System.out.println("Se agrego "+ nombreProducto + " a su pedido");
    	 
     }
      
	}
	
	private void pedirCombo() throws TotalPedidoExcedidoException
	{
		System.out.println("Ha seleccionado un Combo. Escoga uno de los siguientes: \n");
        ArrayList <Combo> combos = restaurante.getCombos();
		int opC = 0;
		String opCStr;
		
        do 
        {	int i = 1;
            for (Combo combo: combos) 
            {
            	String nombreCombo = combo.getNombre();
            	double precioCombo = combo.getPrecio();
            	String index = Integer.toString(i);
            	System.out.println(index+".) " +nombreCombo  +"\t$"+ precioCombo);
            	i++;
            }
            opCStr = input("Por favor, seleccione una opción (1 -4)");
            try 
            {
                opC = Integer.parseInt(opCStr);
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Opción no válida. Por favor, ingrese un numero entre 1 y 4.");
            }

            if (opC < 1 || opC > 4) 
            {
                System.out.println("Opción no válida. Por favor, ingrese un numero entre 1 y 4.");
            }
        } 
        while (opC < 1 || opC > 4);
        
     Combo combo = combos.get(opC-1);
     String nombreCombo = combo.getNombre();
     
     restaurante.agregarItemsPedido(combo);
     System.out.println("Se agregó "+ nombreCombo + " a su pedido");
	}
	
	private void pedirBebida() throws TotalPedidoExcedidoException
	{
		System.out.println("Ha seleccionado una Bebida. Escoga uno de los siguientes: \n");
        ArrayList <Bebida> bebidas = restaurante.getBebidas();
		int opBeb = 0;
		String opBebStr;
		
        do 
        {	int i = 1;
            for (Bebida bebida: bebidas) 
            {
            	String nombreBebida = bebida.getNombre();
            	double precioBebida = bebida.getPrecio();
            	String index = Integer.toString(i);
            	System.out.println(index+".) " +nombreBebida  +"\t$"+ precioBebida);
            	i++;
            }
            opBebStr = input("Por favor, seleccione una opción (1 -3)");
            try 
            {
                opBeb = Integer.parseInt(opBebStr);
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Opción no válida. Por favor, ingrese un numero entre 1 y 4.");
            }

            if (opBeb < 1 || opBeb > 3) 
            {
                System.out.println("Opción no válida. Por favor, ingrese un numero entre 1 y 4.");
            }
        } 
        while (opBeb < 1 || opBeb > 3);
        
     Bebida bebida = bebidas.get(opBeb-1);
     String nombreBebida = bebida.getNombre();
     
     restaurante.agregarItemsPedido(bebida);
     System.out.println("Se agregó "+ nombreBebida + " a su pedido");
	}
	
	private void ejecutarOpcion1() 
	{
		System.out.println("Eligio consultar Menu");
		restaurante.mostrarMenu();
	}

	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, HamburguesaException, TotalPedidoExcedidoException
	{
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.mostrarMenu();
	}
	
}
