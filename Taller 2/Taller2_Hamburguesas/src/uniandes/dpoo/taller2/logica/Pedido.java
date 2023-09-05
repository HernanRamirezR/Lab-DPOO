package uniandes.dpoo.taller2.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Pedido 
{
	
	//Atributos
	private ArrayList <Producto> itemsPedido;
	
	static int numeroPedidos = 0;
	
	int idPedido;
	String nombreCliente;
	String direccionCliente;
	
	
	//Constructor
	
	public Pedido(String nombreCliente, String direccionCliente ) 
	{
		this.direccionCliente = direccionCliente;
		this.nombreCliente = nombreCliente;
		ArrayList <Producto> itemsPedido = new ArrayList <Producto>();
		this.itemsPedido = itemsPedido;
		numeroPedidos +=1;	
		this.idPedido = numeroPedidos;
		
	}
	
	//Metodos 
	
	public int getIdPedido() 
	{
		return this.idPedido;
	}
	
	public String getNombre() 
	{
		return this.nombreCliente;
	}
	
	public String getDireccion() 
	{
		return this.direccionCliente;
	}
	
	public void agregarProducto(Producto nuevoItem) 
	{
		itemsPedido.add(nuevoItem);
	}
	
	public ArrayList <Producto> getItemsPedido() 
	{
		return this.itemsPedido;
	}
	
	private int getPrecioNetoPedido() 
	{
		int precioNeto =0;
		for (Producto item: itemsPedido)
		{
			precioNeto += item.getPrecio();
		}
		return precioNeto;
	}
	
	private double getPrecioTotalPedido() 
	{
		int precioNeto = getPrecioNetoPedido();
		double precioIva = getPrecioIvaPedido();
		double precioTotal = precioNeto + precioIva;
		return precioTotal;
	}
	
	private double getPrecioIvaPedido() 
	{
		int precioNeto = getPrecioNetoPedido();
		double precioIva = precioNeto *0.19;
		return precioIva;
	}
	
	private String generarTextoFactura()
	{
		int precioNeto = getPrecioNetoPedido();
		double precioTotal = getPrecioTotalPedido();
		double precioIVA = getPrecioIvaPedido();
		
		StringBuilder factura = new StringBuilder();
		factura.append("*************************************************************\n");
		factura.append("                           FACTURA                           \n");
		factura.append("*************************************************************\n");
		for (Producto producto: itemsPedido)
		{
			factura.append(producto.generarTextoFactura()+"\n");
			
		}
		factura.append("**************************************************************\n");
		factura.append("**************************************************************\n");
		factura.append("\nPrecio Neto: " + Integer.toString(precioNeto)+"\n");
		factura.append("\nPrecio IVA: " + Double.toString(precioIVA) +"\n");
		factura.append("\nPrecioTotal " + Double.toString(precioTotal)+"\n");
		factura.append("**************************************************************\n");
		factura.append("*******************Gracias*por*la*compra!*********************\n");
		factura.append("**************************************************************\n");
		factura.append("\nId: " + Integer.toString(getIdPedido())+"\n");
		factura.append("\nNombre: " + getNombre() +"\n");
		factura.append("\nDireccion " +getDireccion()+"\n");
		factura.append("**************************************************************\n");
		factura.append("**************************************************************\n");
		
		
	
		return factura.toString();
	}
	
	public void guardarFactura() 
	
	{
		String nombreCarpeta = "facturas";
		
		String rutaActual = System.getProperty("user.dir"); // Directorio actual del proyecto
		String rutaCarpeta = rutaActual+ "\\docs";
        File carpeta = new File(rutaCarpeta, nombreCarpeta);
        
        if (!carpeta.exists()) 
        {
            if (carpeta.mkdir()) 
            {
                System.out.println("Carpeta \"" + nombreCarpeta + "\" creada.");
            } 
            else 
            {
                System.out.println("No se pudo crear la carpeta \"" + nombreCarpeta + "\".");
                return; // Salir de la función si no se pudo crear la carpeta
            }
        }
        
        String nombreArchivo = "factura"+ Integer.toString(getIdPedido()) +".txt";
        String rutaArchivo = carpeta.getAbsolutePath() + File.separator + nombreArchivo;
        
        try 
        {
            FileWriter escritor = new FileWriter(rutaArchivo);
            escritor.write(generarTextoFactura());
            escritor.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Error al crear o escribir en el archivo.");
            e.printStackTrace();
        }
	}
	
	public void imprimirFactura(int Id) 
	{
		String nombreArchivo = System.getProperty("user.dir")+ "/docs/facturas/factura"+ Integer.toString(Id) +".txt";
		
		try 
		{
            // Abre el archivo para lectura
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));

            // Lee y muestra cada línea del archivo
            String linea;
            while ((linea = lector.readLine()) != null)
            {
                System.out.println(linea);
            }

            // Cierra el archivo
            lector.close();
        } 
		catch (IOException e) 
		{
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
		
	}
	
	
}
