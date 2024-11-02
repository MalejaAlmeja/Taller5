package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.*;

public class ProductoMenuTest {
	
	private ProductoMenu producto1;
	
	@BeforeEach
    void setUp( ) throws Exception
    {
		producto1 = new ProductoMenu("producto1", 1000);
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
	
    @Test
    void testGetNombre()
    {
    	assertEquals("producto1", producto1.getNombre(),"El nombre del producto no es correcto");    	
    }
    
    @Test
    void testGetPrecio()
    {
    	assertEquals(1000, producto1.getPrecio(),"El precio del producto no es correcto");
    }
    
    @Test
    void testGenerarFactura()
    {
    	 StringBuffer sb = new StringBuffer( );
         sb.append( "producto1" + "\n" );
         sb.append( "            " + 1000 + "\n" );
         assertEquals(sb.toString( ), producto1.generarTextoFactura(), "El texto generado no es igual");
    }
}
