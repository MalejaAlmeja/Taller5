package uniandes.dpoo.hamburguesas.tests;
 import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.*;
public class ComboTest {
	private Combo combo1;
    private ArrayList<ProductoMenu> itemsCombo = new ArrayList<ProductoMenu>();
    private ProductoMenu prod1 = new ProductoMenu("prod1", 20000);
    private ProductoMenu prod2= new ProductoMenu("prod2", 40000);
    
    
    @BeforeEach
    void setUp( ) throws Exception
    {
    	itemsCombo.add(prod1);
    	itemsCombo.add(prod2);
        combo1 = new Combo("combo1", 0.03 , itemsCombo);
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
    	assertEquals("combo1", combo1.getNombre(), "El nombre no es el esperado");
    }

    @Test
    void testGetPrecio( )
    {
    	assertEquals(58200, combo1.getPrecio(), "El precio no es el esperado");
    }
    
    @Test
    void testGenerarTextoFactura( )
    {
    	StringBuffer sb = new StringBuffer( );
        sb.append( "Combo " + "combo1" + "\n" );
        sb.append( " Descuento: " + 0.03 + "\n" );
        sb.append( "            " + 58200 + "\n" );
        
        assertEquals(sb.toString( ), combo1.generarTextoFactura(), "La factura no es la esperada");
    }

}
