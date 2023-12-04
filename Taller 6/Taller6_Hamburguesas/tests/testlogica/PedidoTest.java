package testlogica;
import org.junit.jupiter.api.*;
import uniandes.dpoo.taller2.logica.*;


import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class PedidoTest {
	    private static ProductoMenu producto1;
	    private static ProductoMenu producto2;
	    private static ProductoMenu producto3;
	    private static ProductoAjustado productoAjustado1;

	    @BeforeAll
	    public static void setUp()
	    {
	        producto1 = new ProductoMenu("Producto1", 10, 100);
	        producto2 = new ProductoMenu("Producto2", 15, 150);
	        producto3 = new ProductoMenu("Producto3", 12, 120);

	        // Crear un producto ajustado con un ingrediente
	        Ingrediente ingrediente = new Ingrediente("Queso", 2, 30);
	        productoAjustado1 = new ProductoAjustado(producto1);
	        productoAjustado1.agregarIngrediente(ingrediente);
	    }

	    @AfterAll
	    public static void tearDown() {
	        producto1 = null;
	        producto2 = null;
	        producto3 = null;
	        productoAjustado1 = null;
	    }

	    @Test
	    public void testAgregarProducto() throws TotalPedidoExcedidoException {
	        Pedido pedido = new Pedido("Cliente1", "Dirección1");

	        // Agregar productos al pedido sin exceder el límite
	        pedido.agregarProducto(producto1);
	        pedido.agregarProducto(producto2);

	        // Verificar que los productos fueron agregados correctamente
	        assertEquals(2, pedido.getItemsPedido().size());
	    }

	    @Test
	    public void testAgregarProductoConExcesoDeTotal() {
	        Pedido pedido = new Pedido("Cliente2", "Dirección2");

	        // Agregar productos que exceden el límite permitido
	        assertThrows(TotalPedidoExcedidoException.class, () -> {
	            pedido.agregarProducto(producto1);
	            pedido.agregarProducto(producto2);
	            pedido.agregarProducto(producto3);
	        });

	        // Verificar que el pedido no tiene productos después de intentar agregar exceso
	        assertEquals(0, pedido.getItemsPedido().size());
	    }

	    @Test
	    public void testGenerarTextoFactura() throws TotalPedidoExcedidoException {
	        Pedido pedido = new Pedido("Cliente3", "Dirección3");

	        // Agregar productos al pedido
	        pedido.agregarProducto(producto1);
	        pedido.agregarProducto(productoAjustado1);

	        // Verificar que el texto de la factura se genere correctamente
	        String expected = "*************************************************************\n" +
	                "                           FACTURA                           \n" +
	                "*************************************************************\n" +
	                "\nProducto1                              10.0\n" +
	                "\nProducto1 Con: Queso                   12.0\n" +
	                "**************************************************************\n" +
	                "**************************************************************\n" +
	                "\nCalorias Totales: 250\n" +
	                "\nPrecio Neto: 22\n" +
	                "\nPrecio IVA: 4.18\n" +
	                "\nPrecio Total 26.18\n" +
	                "**************************************************************\n" +
	                "*******************Gracias*por*la*compra!*********************\n" +
	                "**************************************************************\n" +
	                "\nId: 1\n" +
	                "\nNombre: Cliente3\n" +
	                "\nDireccion Dirección3\n" +
	                "**************************************************************\n" +
	                "**************************************************************\n";

	        assertEquals(expected, pedido.generarTextoFactura());
	    }

}
