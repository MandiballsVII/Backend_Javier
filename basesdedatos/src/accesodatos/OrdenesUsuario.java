package accesodatos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import entidades.Producto;

public class OrdenesUsuario {
	
	private static boolean salir;
	private static ProductoDaoPrueba pDaoPrueba = new ProductoDaoPrueba();
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
				
		while(!salir) {
			System.out.println("""
					¿Qué operación quieres realizar?
					1-. Ver todos los productos
					2-. Ver un producto
					3-. Crear un producto
					4-. Borrar un producto
					5-. Modificar un producto
					6-. Salir
					""");

			String operacion = sc.nextLine();
			
			switch (operacion) {
			case "1" -> mostrarTodos();
			case "2" -> mostrarUno();
			case "3" -> crearUno();
			case "4" -> borrarUno();
			case "5" -> modificarUno();
			case "6" -> Salir();
			default -> mostrarTodos();
			}
		}
		
		sc.close();
	}

	private static void Salir() {
		salir = true;
		
	}
	
	private static void mostrarTodos() {
		var productos = pDaoPrueba.mostrarProductos();
		for(Producto producto: productos) {
			System.out.println(producto);
		}
	}
	
	private static void mostrarUno() {
		System.out.println("Dime el id del producto que quieres ver");
		Long id = Long.parseLong(sc.nextLine());
		var producto = pDaoPrueba.mostrarPorId(id);
		System.out.println(producto);
		
	}
	
	private static void borrarUno() {
		System.out.println("Dime el id del producto que quieres borrar");
		Long id = Long.parseLong(sc.nextLine());
		pDaoPrueba.borrarProducto(id);
		
	}
	
	private static void crearUno() {
		System.out.println("Dime el nombre del producto que quieres crear");
		String nombre =sc.nextLine();
		System.out.println("Dime el precio del producto que quieres crear");
		BigDecimal precio = new BigDecimal(sc.nextLine());
		System.out.println("Dime la caducidad del producto que quieres crear");
		LocalDate caducidad = LocalDate.parse(sc.nextLine());
		System.out.println("Dime la descripción del producto que quieres crear");
		String descripcion =sc.nextLine();
		
		Producto producto = new Producto(nombre, precio, caducidad, descripcion);
		pDaoPrueba.crearProducto(producto);
	}
	
	private static void modificarUno() {
		System.out.println("Dime el id del producto que quieres modificar");
		Long id = Long.parseLong(sc.nextLine());
		System.out.println("Dime el nombre del producto que quieres modificar");
		String nombre =sc.nextLine();
		System.out.println("Dime el precio del producto que quieres modificar");
		BigDecimal precio = new BigDecimal(sc.nextLine());
		System.out.println("Dime la caducidad del producto que quieres modificar");
		LocalDate caducidad = LocalDate.parse(sc.nextLine());
		System.out.println("Dime la descripción del producto que quieres modificar");
		String descripcion =sc.nextLine();
		
		Producto producto = new Producto(id, nombre, precio, caducidad, descripcion);
		pDaoPrueba.modificarProducto(producto);
	}
}
