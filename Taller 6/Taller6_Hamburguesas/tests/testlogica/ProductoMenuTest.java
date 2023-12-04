package testlogica;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.taller2.logica.*;


public class ProductoMenuTest {

    @Test
    public void testGenerarTextoFactura() {
        
        ProductoMenu producto = new ProductoMenu("Hamburguesa", 10, 500);

        
        String resultado = producto.generarTextoFactura();

        assertEquals("\nHamburguesa                                              10.0", resultado);
    }

    @Test
    public void testGetCalorias() {
        
        ProductoMenu producto = new ProductoMenu("Ensalada", 8, 300);

        int calorias = producto.getCalorias();

        
        assertEquals(300, calorias);
    }

    @Test
    public void testEquals() {
    	
        ProductoMenu producto1 = new ProductoMenu("Pizza", 15, 800);

        assertTrue(producto1.equals(producto1));
    }
    
    @Test
    public void testNotEquals() {
    	
        ProductoMenu producto1 = new ProductoMenu("Pizza", 15, 800);
        ProductoMenu producto2 = new ProductoMenu("Ensalada", 8, 300);

        assertFalse(producto1.equals(producto2));
    }
    
    
}