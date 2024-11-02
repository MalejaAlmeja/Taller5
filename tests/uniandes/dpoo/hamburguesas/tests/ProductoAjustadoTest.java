package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.*;

public class ProductoAjustadoTest {
	private ProductoAjustado producto1;
	private ProductoMenu productoOriginal = new ProductoMenu("productoOriginal", 1000) ;
	private Ingrediente ing1 = new Ingrediente("ing1", 100);
	private Ingrediente ing2 = new Ingrediente("ing2", 150);
	private Ingrediente ing3 = new Ingrediente("ing3", 50);
	
	@BeforeEach
    void setUp( ) throws Exception
    {
		producto1 = new ProductoAjustado(productoOriginal);
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
	
    @Test
    void testGetNombre()
    {
    	assertEquals("productoOriginal", producto1.getNombre(),"El nombre del producto no es correcto");
    }
    
    @Test
    void testGetPrecio()
    {
    	producto1.agregarIngrediente(ing1);
    	producto1.agregarIngrediente(ing2);
    	assertEquals(1250, producto1.getPrecio(),"El precio del producto no es correcto");
    }
    
    @Test
    void testGenerarTextoFactura()
    {
    	producto1.agregarIngrediente(ing1);
    	producto1.agregarIngrediente(ing2);
    	producto1.quitarIngrediente(ing3);
    	StringBuffer sb = new StringBuffer( );
        sb.append( "productoOriginal" + "\n");
        sb.append( "    +" + "ing1");
        sb.append( "       " + 100 + "\n");
        sb.append( "    +" + "ing2" );
        sb.append( "       " + 150 + "\n" );
        sb.append( "    -" + "ing3" + "\n" );
        sb.append( "            " + 1250 + "\n" );
        
        assertEquals(sb.toString( ), producto1.generarTextoFactura(),"La información de la factura no es correcta");
        
        
    }

}
