package testlogica;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller2.logica.*;

public class ProductoAjustadoTest {
	
	private static ProductoAjustado productoAjustado;
	private static ProductoMenu productoBase;
	private static Ingrediente ingrediente; 
	
	@BeforeAll
	public static void setUp()
	{
		ingrediente = new Ingrediente("TestA",10,10);
		productoBase = new ProductoMenu("test",10,10);
		productoAjustado = new ProductoAjustado(productoBase);
	}
	
	@AfterAll
	public static void tearDown()
	{
		productoAjustado = new ProductoAjustado(new ProductoMenu("test",10,10));
	}
	
	@Test
	public void testAgregarIngrediente()
	{
		
		productoAjustado.agregarIngrediente(ingrediente);
		
		assertEquals(1, productoAjustado.getAgregados().size());
		assertTrue(productoAjustado.getAgregados().containsKey("TestA"));
	}
	
	
	@Test
	public void testGetPrecio()
	{
		assertEquals(productoBase.getPrecio()+ingrediente.getCostoAdicional(), productoAjustado.getPrecio() );
	}
	
	@Test
	public void testGetCalorias()
	{
		assertEquals(productoBase.getCalorias()+ingrediente.getCalorias(), productoAjustado.getCalorias() );
	}
	
	@Test
	public void testGetnombre()
	{
		productoAjustado.agregarIngrediente(ingrediente);
		Ingrediente ingrediente2 = new Ingrediente("TestB",10,10);
		productoAjustado.eliminarIngrediente(ingrediente2);
		assertEquals("test Con: TestA  Sin: TestB  ", productoAjustado.getNombre());
	}
	
	@Test 
	public void testGenerarFactura() 
	{
		assertEquals("\ntest Con: TestA  Sin: TestB                              20.0",productoAjustado.generarTextoFactura() );
	}
	
	
	@Test
	public void testEliminarIngrediente()
	{
		productoAjustado.eliminarIngrediente(ingrediente);
		
		assertEquals(2, productoAjustado.getEliminados().size());
		assertTrue(productoAjustado.getEliminados().containsKey("TestA"));
		assertEquals(0, productoAjustado.getAgregados().size());
		assertFalse(productoAjustado.getAgregados().containsKey("TestA"));
	}
	

}
