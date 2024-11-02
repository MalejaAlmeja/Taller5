package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.*;

public class PedidoTest {
	private String cliente = "cliente1";
	private String direccionCliente = "direccionCliente1";
	private Pedido pedido1;
	private ProductoMenu prod1 = new ProductoMenu("prod1", 20000);
	private ProductoAjustado prod2 = new ProductoAjustado(prod1);
	private Ingrediente ing1 = new Ingrediente("ing1", 100);
	private Ingrediente ing2 = new Ingrediente("ing2", 150);
	private Ingrediente ing3 = new Ingrediente("ing3", 50);
	
	@BeforeEach
    void setUp( ) throws Exception
    {
		pedido1 = new Pedido(cliente, direccionCliente);
		prod2.agregarIngrediente(ing1);
		prod2.agregarIngrediente(ing2);
		prod2.quitarIngrediente(ing3);
		pedido1.agregarProducto(prod1);
    	pedido1.agregarProducto(prod2);
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
	
    @Test
    void testGetNombre()
    {
    	assertEquals("cliente1", pedido1.getNombreCliente(),"El nombre del cliente no es correcto");
    }
        
    
    @Test
    void testIVA()
    {
    	assertEquals(7647, pedido1.getPrecioIVAPedido(),"El valor del IVA no es correcto");
    	
    }
    
    @Test
    void testPrecioNeto()
    {
    	assertEquals(40250, pedido1.getPrecioNetoPedido(),"El valor neto  no es correcto");
    }
    
    @Test
    void testPrecioTotal()
    {
    	
    	assertEquals(47897, pedido1.getPrecioTotalPedido(),"El valor no es correcto");
    }
        
    @Test
    void testGenerarTextoFactura()
    {
    	StringBuffer sb = new StringBuffer( );

        sb.append( "Cliente: " + "cliente1" + "\n" );
        sb.append( "Dirección: " + "direccionCliente1" + "\n" );
        sb.append( "----------------\n" );

        
        sb.append( prod1.generarTextoFactura( ) );
        sb.append( prod2.generarTextoFactura( ) );

        sb.append( "----------------\n" );
        sb.append( "Precio Neto:  " + 40250 + "\n" );
        sb.append( "IVA:          " + 7647 + "\n" );
        sb.append( "Precio Total: " + 47897 + "\n" );


        assertEquals(sb.toString( ), pedido1.generarTextoFactura(),"El valor no es correcto");

    	
    }
    
    
    @Test
    void testGuardarFactura()
    {	try
        {
    		pedido1.guardarFactura(new File( "./datostest/pruebaPedido1"));
        }
        catch( Exception e )
        {
            fail( "No debería haber entrado acá: " + e.getMessage( ) );
        }
    
    	
    }

}
