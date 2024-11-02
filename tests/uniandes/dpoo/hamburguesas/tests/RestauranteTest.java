package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.*;
import uniandes.dpoo.hamburguesas.excepciones.*;

public class RestauranteTest {
    private static final String RUTA_ARCHIVOS = "./data/";
	  //Ingredientes
	  private static final Ingrediente SALMON = new Ingrediente("salmon", 3000);
	  private static final Ingrediente POLLO = new Ingrediente("pollo", 15000);
	  private static final Ingrediente ARROZ = new Ingrediente("arroz", 12000);
	  private static final Ingrediente ENSALADA = new Ingrediente("ensalada", 10000);
	  private static final Ingrediente AGUACATE = new Ingrediente("aguacate", 8000);
	  
	  //Productos
	  private static final ProductoMenu BOWL_SALMON = new ProductoMenu("bowl_salmon", 40000);
	  private static final ProductoMenu ENSALADA_SALMON = new ProductoMenu("ensalada_salmon", 35000);
	  private static final ProductoMenu TOFU_APANADO = new ProductoMenu("tofu_apanado", 14000);
	  private static final ProductoMenu CHIPS_CAMARON = new ProductoMenu("chips_camaron", 9000);
	  private static final ProductoMenu TE = new ProductoMenu("te", 5000);
	  private static final ProductoMenu SODA = new ProductoMenu("soda", 6000);
	  private static final ProductoMenu MOCHI = new ProductoMenu("mochi", 9500);
	  
	  //Combos
  	  ArrayList<ProductoMenu> prodComboSalmon = new ArrayList<ProductoMenu>();
  	  ArrayList<ProductoMenu> prodComboSalmonEnsa = new ArrayList<ProductoMenu>();
  	  ArrayList<ProductoMenu> prodComboTofu = new ArrayList<ProductoMenu>();
	  
    
    //Clientes
    private static final String CLIENTE1 = "Andres";
    private static final String CLIENTE1_DIR = "Calle 25 # 45";
    private static final String CLIENTE2 = "Luisa";
    private static final String CLIENTE2_DIR = "Carrera 5 # 120";
    
    
    private Restaurante restaurante1;
    
    @BeforeEach
    void setUp( ) throws Exception
    {
    	this.restaurante1 = new Restaurante();
    }
    
    @Test
    void testRestaurante1( ) throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException
    {
    	//Añadimos a los combos
    	prodComboSalmon.add(BOWL_SALMON);
    	prodComboSalmon.add(SODA);
    	prodComboSalmon.add(MOCHI);
    	prodComboSalmonEnsa.add(ENSALADA_SALMON);
    	prodComboSalmonEnsa.add(TE);
    	prodComboSalmonEnsa.add(CHIPS_CAMARON);
    	prodComboTofu.add(TOFU_APANADO);
    	prodComboTofu.add(SODA);
    	prodComboTofu.add(CHIPS_CAMARON);
  	  
	  	Combo COMBO_SALMON = new Combo("combo_salmon", 0.1, prodComboSalmon);
	  	Combo COMBO_SALMON_ENSA = new Combo("combo_salmon_ensalada", 0.11, prodComboSalmonEnsa);
	  	Combo COMBO_TOFU = new Combo("combo_tofu", 0.07, prodComboTofu);
	  	
	  	//Iniciar pedido 1
  	  
    	restaurante1.iniciarPedido(CLIENTE1, CLIENTE1_DIR);
    	Pedido miPedido= restaurante1.getPedidoEnCurso();
    	assertNotNull( miPedido, "Hay un pedido en curso");
    	assertEquals(CLIENTE1, miPedido.getNombreCliente(), "El cliente del pedido es incorrecto");
    	
    	//Añadir productos
    	miPedido.agregarProducto(COMBO_SALMON);
    	miPedido.agregarProducto(TOFU_APANADO);
    	ProductoAjustado modificado= new ProductoAjustado(BOWL_SALMON);
    	modificado.agregarIngrediente(AGUACATE);
    	modificado.agregarIngrediente(POLLO);
    	modificado.quitarIngrediente(SALMON);
    	miPedido.agregarProducto(modificado);
    	
    	assertEquals(126950, miPedido.getPrecioNetoPedido(),"El precio neto no es el esperado");
    	assertEquals(24120, miPedido.getPrecioIVAPedido(),"El iva no es el esperado");
    	assertEquals(151070, miPedido.getPrecioTotalPedido(),"El precio total no es el esperado");
    	
    	restaurante1.cerrarYGuardarPedido();
    	
    	//Verificar para nuevo pedido
    	miPedido= restaurante1.getPedidoEnCurso();
    	assertNull( miPedido, "No hay un pedido en curso");
        
    	//Pedido2
    	restaurante1.iniciarPedido(CLIENTE2, CLIENTE2_DIR);
    	miPedido= restaurante1.getPedidoEnCurso();
    	assertNotNull( miPedido, "Hay un pedido en curso");
    	assertEquals(CLIENTE2, miPedido.getNombreCliente(), "El cliente del pedido es incorrecto");
    	
    	//Añadir productos
    	miPedido.agregarProducto(COMBO_SALMON_ENSA);
    	miPedido.agregarProducto(COMBO_TOFU);
    	ProductoAjustado ajustado= new ProductoAjustado(CHIPS_CAMARON);
    	ajustado.agregarIngrediente(ARROZ);
    	ajustado.agregarIngrediente(ENSALADA);
    	miPedido.agregarProducto(ajustado);
    	
    	assertEquals(101580, miPedido.getPrecioNetoPedido(),"El precio neto no es el esperado");
    	assertEquals(19300, miPedido.getPrecioIVAPedido(),"El iva no es el esperado");
    	assertEquals(120880, miPedido.getPrecioTotalPedido(),"El precio total no es el esperado");
    	
    	restaurante1.cerrarYGuardarPedido();
    	
    }
    
    @Test
    void testCargarRestaurante( )
    {
    	File archivoCombos = new File(RUTA_ARCHIVOS+"combos.txt");
    	File archivoIngredientes = new File(RUTA_ARCHIVOS+"ingredientes.txt");
    	File archivoMenu = new File(RUTA_ARCHIVOS+"menu.txt");
        try
        {
        	restaurante1.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
        }
        catch( Exception e )
        {
            fail( ":(  " + e.getMessage( ) );
        }
        
        //Verificar combos
        ArrayList<Combo> combos = restaurante1.getMenuCombos();
        assertEquals(4, combos.size(),"La cantidad de combos cargada no es la esperada");
        Combo combo1 = combos.get(0);
        Combo combo2 = combos.get(1);
        Combo combo3 = combos.get(2);
        Combo combo4 = combos.get(3);
        
        assertEquals("combo corral",combo1.getNombre(), "El nombre del combo no es el esperado");
        assertEquals(22050 ,combo1.getPrecio(), "El precio del combo no es el esperado");
        assertEquals("combo corral queso",combo2.getNombre(), "El nombre del combo no es el esperado");
        assertEquals(23850 ,combo2.getPrecio(), "El precio del combo no es el esperado");
        assertEquals("combo todoterreno",combo3.getNombre(), "El nombre del combo no es el esperado");
        assertEquals(34317 ,combo3.getPrecio(), "El precio del combo no es el esperado");
        assertEquals("combo especial",combo4.getNombre(), "El nombre del combo no es el esperado");
        assertEquals(31222 ,combo4.getPrecio(), "El precio del combo no es el esperado");
        
        //Verificar ingredientes
        ArrayList<Ingrediente> ingredientes = restaurante1.getIngredientes();
        HashMap<String, Integer> ingredientesEsperados = new HashMap<String, Integer>();
        ArrayList<String> ingredientesMarcados = new ArrayList<String>();
        
        ingredientesEsperados.put("lechuga", 1000);
        ingredientesEsperados.put("tomate", 1000);
        ingredientesEsperados.put("cebolla", 1000);
        ingredientesEsperados.put("queso mozzarella", 2500);
        ingredientesEsperados.put("huevo", 2500);
        ingredientesEsperados.put("queso americano", 2500);
        ingredientesEsperados.put("tocineta express", 2500);
        ingredientesEsperados.put("papa callejera", 2000);
        ingredientesEsperados.put("pepinillos", 2500);
        ingredientesEsperados.put("cebolla grille", 2500);
        ingredientesEsperados.put("suero costeño", 3000);
        ingredientesEsperados.put("frijol refrito", 4500);
        ingredientesEsperados.put("queso fundido", 4500);
        ingredientesEsperados.put("tocineta picada", 6000);
        ingredientesEsperados.put("piña", 2500);
        
        for( Ingrediente ing : ingredientes )
        {
        	String nombre = ing.getNombre();
        	int valor = ing.getCostoAdicional();
        	
            assertTrue( ingredientesEsperados.containsKey(nombre), "El ingrediente era esperado" );
            assertEquals(ingredientesEsperados.get(nombre),valor, "El costo del ingrediente no era esperado" );
            assertFalse( ingredientesMarcados.contains( nombre ), "El ingrediente está repetido" );
            ingredientesMarcados.add(nombre);
        }
        
        //Verificar menu
        ArrayList<ProductoMenu> productos = restaurante1.getMenuBase();
        ArrayList<String> productosMarcados = new ArrayList<String>();
        ArrayList<String> productosEsperados = new ArrayList<String>();
        productosEsperados.add("corral");
        productosEsperados.add("corral queso");
        productosEsperados.add("corral pollo");
        productosEsperados.add("corralita");
        productosEsperados.add("todoterreno");
        productosEsperados.add("1/2 libra");
        productosEsperados.add("especial");
        productosEsperados.add("casera");
        productosEsperados.add("mexicana");
        productosEsperados.add("criolla");
        productosEsperados.add("costeña");
        productosEsperados.add("hawaiana");
        productosEsperados.add("wrap de pollo");
        productosEsperados.add("wrap de lomo");
        productosEsperados.add("ensalada mexicana");
        productosEsperados.add("papas medianas");
        productosEsperados.add("papas grandes");
        productosEsperados.add("papas en casco medianas");
        productosEsperados.add("papas en casco grandes");
        productosEsperados.add("agua cristal sin gas");
        productosEsperados.add("agua cristal con gas");
        productosEsperados.add("gaseosa");
        
        for( Producto producto : productos )
        {
        	String nombre = producto.getNombre();
            assertTrue( productosEsperados.contains( nombre ), "El producto era esperado" );
            assertFalse( productosMarcados.contains( nombre ), "El producto está repetido" );
            productosMarcados.add(nombre);
        }
        
        
    }
    
    
}
