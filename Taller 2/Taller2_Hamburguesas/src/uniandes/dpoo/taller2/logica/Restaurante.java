package uniandes.dpoo.taller2.logica;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Restaurante
{
	//Atributos
	
	private HashMap <Integer , Pedido> pedidos;
	
	private Pedido pedidoActivo;
	
	private HashMap <String, Combo> combos;
	
	private HashMap <String, ProductoMenu> menuBase;
	
	private HashMap <String, Ingrediente> ingredientes;
	
		
	//Constructor
	public Restaurante() throws FileNotFoundException, IOException 
	{
		pedidos = new HashMap <Integer , Pedido>();
		ingredientes = new HashMap <String, Ingrediente>();
		menuBase = new HashMap <String, ProductoMenu>();
		combos = new HashMap <String, Combo>();
		
		File fileIngredientes = new File("data/ingredientes.txt");
		File fileMenu = new File("data/menu.txt");
		File fileCombos = new File("data/combos.txt");
		
		cargarInformacionRestaurante(fileIngredientes, fileMenu , fileCombos);
	}
	
	//Metodos
	
	public int iniciarPedido (String nombreCliente, String direccionCliente) 
	{
		if (pedidoActivo ==null )
		{
			pedidoActivo = new Pedido(nombreCliente, direccionCliente);
			return pedidoActivo.getIdPedido();
		}
		else
		{
		System.out.println("Primero debes cerrar el pedido activo");
		return 0;
		}
		
	}
	
	public void agregarItemsPedido(Producto producto) 
	{
		pedidoActivo.agregarProducto(producto);
	}
	
	public void modificarProductoBase(ProductoMenu productoBase, ArrayList <Ingrediente> ingredientesAdd, ArrayList <Ingrediente> ingredientesElim ) 
	{
		ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
		
		//por convencion voy a usar true para añadir y false para eliminar
		
		for (Ingrediente ingrediente: ingredientesAdd) 
		{
			productoAjustado.agregarIngrediente(ingrediente);
		}	
		for (Ingrediente ingrediente: ingredientesElim) 
		{
			productoAjustado.eliminarIngrediente(ingrediente);
		}	
		
		agregarItemsPedido(productoAjustado);
		System.out.println("Se agregó "+ productoAjustado.getNombre() + "al pedido");
	}
	
	public HashMap<Integer , Pedido> getHistorialPedidos() 
	{
		return this.pedidos;
	}
	
	public int cerrarYGuardarPedido() 	
	{
		if (pedidoActivo !=null )
		{
			pedidoActivo.guardarFactura();
			int idPedidoActivo = pedidoActivo.getIdPedido();
			pedidos.put(idPedidoActivo,pedidoActivo);
			pedidoActivo.imprimirFactura(idPedidoActivo);
			pedidoActivo = null;
			return idPedidoActivo;
		}
		else
		{
			System.out.println("Primero debes tener un pedido activo");
			return 0;
		}

	}
	
	public Pedido getPedidoEnCurso() 
	{
		return this.pedidoActivo;
	}
	 
	public ArrayList <ProductoMenu> getMenuBase() 
	{	
		//Se hace un cast de lo que retorna el metodo .values() de un HashMap para que coincida con lo esperado por el metodo.
		Collection<ProductoMenu> colProductosMenu = menuBase.values();
		ArrayList <ProductoMenu> listProductosMenu = new ArrayList<ProductoMenu>(colProductosMenu);
		return listProductosMenu ;
	}
	
	public ArrayList <Combo> getCombos()
	{
		Collection<Combo> colCombos= combos.values();
		ArrayList <Combo> listCombos = new ArrayList<Combo>(colCombos);
		return listCombos;
	}
	
	public ArrayList <Ingrediente> getIngredientes()
	{
		//Se hace un cast de lo que retorna el metodo .values() de un HashMap para que coincida con lo esperado por el metodo.
		Collection<Ingrediente> colIngredientes = ingredientes.values();
		ArrayList <Ingrediente> listIngredientes = new ArrayList<Ingrediente>(colIngredientes);
		return listIngredientes;
	}
	 
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos ) throws FileNotFoundException, IOException
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos); 
	}
	
	private void cargarIngredientes(File archivoIngredientes) throws FileNotFoundException, IOException
	{
		// Abrir el archivo de ingredientes.txt y leerlo
		
		BufferedReader brIngrediente = new BufferedReader(new FileReader(archivoIngredientes));
		String lineaIngrediente = brIngrediente.readLine();
		
		while (lineaIngrediente != null)
		{
			//Lee la linea y separa en el punto y coma para almacenar el nombre y el valor por aparte
			
            String[] partesIngrediente = lineaIngrediente.split(";");
            
            String nombreIngrediente = partesIngrediente[0];
            int CostoAdicional = Integer.parseInt(partesIngrediente[1]);
            
            
            // Si el producto del menu no existe, lo agrega al mapa de ingredientes
            Ingrediente elIngrediente = ingredientes.get(nombreIngrediente);
			if (elIngrediente == null)
			{
				elIngrediente = new Ingrediente(nombreIngrediente,CostoAdicional);
				ingredientes.put(nombreIngrediente, elIngrediente);
			}
			
			lineaIngrediente = brIngrediente.readLine();
		}
		
		brIngrediente.close();	
	}
	
	private void cargarMenu(File archivoMenu) throws FileNotFoundException, IOException
	{
		BufferedReader brMenu = new BufferedReader(new FileReader(archivoMenu));
		String lineaMenu = brMenu.readLine();
		
		while (lineaMenu != null)
		{
			//Lee la linea y separa en el punto y coma para almacenar el nombre y el valor por aparte
			
            String[] partesProductoMenu = lineaMenu.split(";");
            String nombreProductoMenu = partesProductoMenu[0];
            int costoBase = Integer.parseInt(partesProductoMenu[1]);
            
            
            // Si el producto del menu no existe, lo agrega al mapa de menus
            
            ProductoMenu elProductoMenu = menuBase.get(nombreProductoMenu);
			if (elProductoMenu == null)
			{
				elProductoMenu = new ProductoMenu(nombreProductoMenu,costoBase);
				
				menuBase.put(nombreProductoMenu, elProductoMenu);
			}
			
			lineaMenu= brMenu.readLine();
		}
		
		brMenu.close();	
	}
	
	private void cargarCombos(File archivoCombos) throws FileNotFoundException, IOException 
	{
		
		BufferedReader brCombo = new BufferedReader(new FileReader(archivoCombos));
		String lineaCombo = brCombo.readLine();
		
		while (lineaCombo != null)
		{
			//Lee la linea y separa en el punto y coma para almacenar el nombre, el descuento y los productos que incluye por aparte
			
			
            String[] partesCombo = lineaCombo.split(";");
            
            String nombreCombo = partesCombo[0];
            String stringDescuento = partesCombo[1]; // Es necesario quitar el "%" para tratarlo como un numero
            stringDescuento = stringDescuento.replace("%","");
            Double Descuento = Double.parseDouble(stringDescuento);
            Descuento/=100; //lo vuelve un valor decimal
            
            // Si el producto del menu no existe, lo agrega al mapa de menus
            
            Combo elCombo = combos.get(nombreCombo);
			
			ProductoMenu parte1 = menuBase.get(partesCombo[2]);
			ProductoMenu parte2 = menuBase.get(partesCombo[3]);
			ProductoMenu parte3 = menuBase.get(partesCombo[4]);
			
			if (elCombo == null)
			{
				elCombo = new Combo(nombreCombo,Descuento);
				elCombo.agregarItemACombo(parte1);
				elCombo.agregarItemACombo(parte2);
				elCombo.agregarItemACombo(parte3);
				
				combos.put(nombreCombo, elCombo);
			}
			
			lineaCombo= brCombo.readLine();
		}
		
		brCombo.close();
	}
		
}
	 
