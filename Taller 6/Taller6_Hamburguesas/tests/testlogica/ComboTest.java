package testlogica;
import org.junit.jupiter.api.*;
import uniandes.dpoo.taller2.logica.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComboTest {
	private static Combo combo;
	private static Combo combo2;

    @BeforeAll
    public static void setUp() {
        // Se ejecuta una vez antes de todas las pruebas
        combo = new Combo("Combo Test", 0.2);
        combo2 = new Combo("Combo Test1", 0.2);
        ProductoMenu producto1 = new ProductoMenu("Producto1", 10, 100);
        ProductoMenu producto2 = new ProductoMenu("Producto2", 15, 150);
        combo.agregarItemACombo(producto1);
        combo.agregarItemACombo(producto2);
    }

    @AfterAll
    public static void tearDown() {
        // Se ejecuta una vez después de todas las pruebas
        combo = null;
        combo2 = null;
    }

    @Test
    public void testAgregarItemACombo() {
        // Verificar que el combo tenga dos elementos después de la configuración
        assertEquals(2, combo.getItemsCombo().size());

        // Agregar un nuevo producto al combo
        ProductoMenu nuevoProducto = new ProductoMenu("Nuevo Producto", 8, 80);
        combo.agregarItemACombo(nuevoProducto);

        // Verificar que el combo tenga tres elementos después de agregar el nuevo producto
        assertEquals(3, combo.getItemsCombo().size());
    }

    @Test
    public void testGetPrecio() {
        // Verificar que el precio del combo se calcule correctamente
        assertEquals(26.4, combo.getPrecio());
    }

    @Test
    public void testGetNombre() {
        // Verificar que el nombre del combo sea correcto
        assertEquals("Combo Test", combo.getNombre());
    }

    @Test
    public void testGenerarTextoFactura() {
        // Verificar que el texto de la factura se genere correctamente
        String expected = "\nCombo Test                                               20.0";
        assertEquals(expected, combo.generarTextoFactura());
    }

    @Test
    public void testGetCalorias() {
        // Verificar que las calorías del combo se calculen correctamente
        assertEquals(330, combo.getCalorias());
    }
    
    @Test
    public void testEquals() {
        
        assertTrue(combo.equals(combo));
        assertFalse(combo.equals(combo2));
    }

}
