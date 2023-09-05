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
	
	private HashMap <String, Bebida> bebidas;
	
		
	//Constructor
	public Restaurante() throws FileNotFoundException, IOException 
	{
		pedidos = new HashMap <Integer , Pedido>();
		ingredientes = new HashMap <String, Ingrediente>();
		menuBase = new HashMap <String, ProductoMenu>();
		combos = new HashMap <String, Combo>();
		bebidas = new HashMap <String, Bebida>();
		
		
		File fileIngredientes = new File("data/ingredientes.txt");
		File fileMenu = new File("data/menu.txt");
		File fileCombos = new File("data/combos.txt");
		File fileBebidas = new File("data/bebidas.txt");
		
		cargarInformacionRestaurante(fileIngredientes, fileMenu , fileCombos, fileBebidas);
	}
	
	//Metodos
	public void mostrarMenu() 
	{
		ArrayList <ProductoMenu> menuBase = getMenuBase();
		ArrayList <Ingrediente> ingredientes = getIngredientes();
		ArrayList <Combo> combos = getCombos();
		ArrayList <Bebida> bebidas = getBebidas();
		
		int indice = 0; 
		System.out.println("\n ===    	 	Productos:   	===     Adiciones:     		         ===        Combos:     	===            Bebidas:     ===    \n");
		while ( indice < 19) 
		{
            Producto producto = menuBase.get(indice);
           
            //Prodcuto 
            String nombreProd = producto.getNombre();
			String precioProd = Double.toString(producto.getPrecio());
            
			//Ingrediente
			String nombreIn = "";
			String precioIn = "";
			if (indice < 15)
			{
			Ingrediente ingrediente = ingredientes.get(indice);
			nombreIn = ingrediente.getNombre();
			precioIn = Double.toString(ingrediente.getCostoAdicional());
			}
			
			//Combo
			String nombreCo = "";
			String precioCo = "";
			if (indice < 4)
			{
			Combo combo = combos.get(indice);
			nombreCo = combo.getNombre();
			precioCo = Double.toString(combo.getPrecio());
			}
			
			String nombreBeb = "";
			String precioBeb = "";
			if(indice<3){
				Bebida bebida = bebidas.get(indice);
				nombreBeb = bebida.getNombre();
				precioBeb = Double.toString(bebida.getPrecio());
			}
			
			
			String space = " ";
			if (indice <3) 
			{
				int distProd = 25 - nombreProd.length();
				String cadenaRepetida1 = space.repeat(distProd);
				
				int distIng = 50-nombreProd.length()- distProd- nombreIn.length();
				String cadenaRepetida2 = space.repeat(distIng);
				
				int distCo = 70-nombreProd.length()- distProd- nombreIn.length()-distIng-nombreCo.length();
				String cadenaRepetida3 = space.repeat(distCo);
				
				String text = nombreProd+cadenaRepetida1+"$"+precioProd +"\t|"+ nombreIn+cadenaRepetida2+"$"+ precioIn + "\t|"+nombreCo+cadenaRepetida3+"$"+precioCo +"\t|"+ nombreBeb+ "\t$"+precioBeb +"\n";
				System.out.println(text);
				
			}
			else if (indice <4) 
			{
				int distProd = 25 - nombreProd.length();
				String cadenaRepetida1 = space.repeat(distProd);
				
				int distIng = 50-nombreProd.length()- distProd- nombreIn.length();
				String cadenaRepetida2 = space.repeat(distIng);
				
				String text = nombreProd+cadenaRepetida1+"$"+precioProd +"\t|"+ nombreIn+cadenaRepetida2+"$"+ precioIn + "\t|"+nombreCo+"\t$"+precioCo +"\n";
				System.out.println(text);
			}
			else if (indice <15) 
			{
				int distProd = 25 - nombreProd.length();
				String cadenaRepetida1 = space.repeat(distProd);
				
				int distIng = 50-nombreProd.length()- distProd- nombreIn.length();
				String cadenaRepetida2 = space.repeat(distIng);
				
				String text = nombreProd+cadenaRepetida1+"$"+precioProd +"\t|"+ nombreIn+ cadenaRepetida2+"$"+ precioIn + "\t|\n";
				
				System.out.println(text);
			}
			else
			{
				int distProd = 25 - nombreProd.length();
				String cadenaRepetida = space.repeat(distProd);
				
				String text = nombreProd+cadenaRepetida+"$"+precioProd +"\t|\n";
				
				System.out.println(text);
			}
            indice++;
        }
	}
	
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
	 
	public ArrayList <Bebida>getBebidas()
	{
		Collection<Bebida> colBebidas = bebidas.values();
		ArrayList <Bebida> listBebidas = new ArrayList<Bebida>(colBebidas);
		return listBebidas;
	}
	
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas ) throws FileNotFoundException, IOException
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarBebidas(archivoBebidas);
		cargarCombos(archivoCombos);
		
	}
	
	private void cargarBebidas(File archivoBebidas)throws FileNotFoundException, IOException
	{
		// Abrir el archivo de ingredientes.txt y leerlo
		
		BufferedReader brBebida = new BufferedReader(new FileReader(archivoBebidas));
		String lineaBebida = brBebida.readLine();
		
		while (lineaBebida != null)
		{
			//Lee la linea y separa en el punto y coma para almacenar el nombre y el valor por aparte
			
            String[] partesBebida = lineaBebida.split(";");
            
            String nombreBebida = partesBebida[0];
            int costoAdicional = Integer.parseInt(partesBebida[1]);
            int calorias = Integer.parseInt(partesBebida[2]);
            
            
            // Si el producto del menu no existe, lo agrega al mapa de ingredientes
            Bebida laBebida = bebidas.get(nombreBebida);
			if (laBebida == null)
			{
				laBebida = new Bebida(nombreBebida,costoAdicional, calorias);
				bebidas.put(nombreBebida, laBebida);
			}
			
			lineaBebida = brBebida.readLine();
		}
		
		brBebida.close();	
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
            int calorias= Integer.parseInt(partesIngrediente[2]);
            
            
            // Si el producto del menu no existe, lo agrega al mapa de ingredientes
            Ingrediente elIngrediente = ingredientes.get(nombreIngrediente);
			if (elIngrediente == null)
			{
				elIngrediente = new Ingrediente(nombreIngrediente,CostoAdicional, calorias);
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
            int calorias = Integer.parseInt(partesProductoMenu[2]);
            
            
            // Si el producto del menu no existe, lo agrega al mapa de menus
            
            ProductoMenu elProductoMenu = menuBase.get(nombreProductoMenu);
			if (elProductoMenu == null)
			{
				elProductoMenu = new ProductoMenu(nombreProductoMenu,costoBase, calorias);
				
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
			Bebida parte3 = bebidas.get(partesCombo[4]);
			
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
	
	public boolean verificarProductoIdentico() 
	{
		boolean esIdentico = false;
		if (pedidoActivo.getItemsPedido().size() > 0)
		{
			for(Pedido pedido: pedidos.values())
			{
				if(pedidoActivo.equals(pedido))
				{
					esIdentico = true;
				}
				else
				{
					ArrayList<Producto> lista1 = pedido.getItemsPedido();
					ArrayList<Producto> lista2 = pedidoActivo.getItemsPedido();
					
					if(lista1.equals(lista2))
					{
						esIdentico = true;
					}
					else
					{
						if (lista1.size() == lista2.size()) 
						{
							ArrayList<Producto> lista3 = new ArrayList<Producto>();
							for (int i = 0; i< lista1.size(); i++)
							{
								Producto producto1 = lista1.get(i);
								for (int j = 0; j< lista2.size(); j++) 
								{
									Producto producto2 = lista2.get(i);
									if(producto1.equals(producto2)) 
									{
										lista3.add(producto1);
									}
								}
							}
							
							if (lista3.size() == lista1.size()) 
							{
								esIdentico = true;
							}
						}
					}
				 }
					
				}
			}
		return esIdentico;
	}
}