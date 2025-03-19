package accesodatos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import entidades.Categoria;
import entidades.Producto;

public class OrdenesUsuario {

	private static boolean salir;
	private static ProductoDaoPrueba pDaoPrueba = new ProductoDaoPrueba();
	private static CategoriaDaoPrueba cDaoPrueba = new CategoriaDaoPrueba();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		while (!salir) {
			System.out.println("""
					¿Sobre qué datos quieres trabajar?
					1-. Productos
					2-. Categorías
					3-. Salir
					""");

			String tabla = sc.nextLine();
			
			if(tabla.equals("3")){
				salir();
				break;
			}
			System.out.println("""
					¿Qué operación quieres realizar?
					1-. Ver todos los registros
					2-. Ver un registro
					3-. Crear un registro
					4-. Borrar un registro
					5-. Modificar un registro
					""");

			String operacion = sc.nextLine();

			switch (operacion) {
			case "1" -> mostrarTodos(tabla);
			case "2" -> mostrarUno(tabla);
			case "3" -> crearUno(tabla);
			case "4" -> borrarUno(tabla);
			case "5" -> modificarUno(tabla);
			default -> mostrarTodos(tabla);
			}
		}

		sc.close();
	}

	private static void salir() {
		salir = true;

	}

	private static void mostrarTodos(String tabla) {
		if (tabla.equals("1")) {
			var productos = pDaoPrueba.mostrarProductos();
			for (Producto producto : productos) {
				System.out.println(producto);
			}
		}
		else if(tabla.equals("2")) {
			var categorias = cDaoPrueba.mostrarCategorias();
			for (Categoria categoria : categorias) {
				System.out.println(categoria);
			}
		}

	}

	private static void mostrarUno(String tabla) {
		if (tabla.equals("1")) {
			System.out.println("Dime el id del producto que quieres ver");
			Long id = Long.parseLong(sc.nextLine());
			var producto = pDaoPrueba.mostrarPorId(id);
			System.out.println(producto);
		}
		else if(tabla.equals("2")) {
			System.out.println("Dime el id de la categoría que quieres ver");
			Long id = Long.parseLong(sc.nextLine());
			var categoria = cDaoPrueba.mostrarCategoriasPorId(id);
			System.out.println(categoria);
		}
	}

	private static void borrarUno(String tabla) {
		if (tabla.equals("1")) {
			System.out.println("Dime el id del producto que quieres borrar");
			Long id = Long.parseLong(sc.nextLine());
			pDaoPrueba.borrarProducto(id);
		}
		else if(tabla.equals("2")) {
			System.out.println("Dime el id de la categoría que quieres borrar");
			Long id = Long.parseLong(sc.nextLine());
			cDaoPrueba.borrarCategoria(id);
		}
	}

	private static void crearUno(String tabla) {
		if (tabla.equals("1")) {
			System.out.println("Dime el nombre del producto que quieres crear");
			String nombre = sc.nextLine();
			System.out.println("Dime el precio del producto que quieres crear");
			BigDecimal precio = new BigDecimal(sc.nextLine());
			System.out.println("Dime la caducidad del producto que quieres crear");
			LocalDate caducidad = LocalDate.parse(sc.nextLine());
			System.out.println("Dime la descripción del producto que quieres crear");
			String descripcion = sc.nextLine();
			System.out.println("Dime la categoría del producto que quieres crear");
			
			var categorias = cDaoPrueba.mostrarCategorias();
			
			for(Categoria categoria : categorias) {
				System.out.println(categoria.getId() + "-. " + categoria.getNombre());
			}
			
			String categoriaId = sc.nextLine();
			Categoria categoriaParaCrear = new Categoria();
			
			for(Categoria cat : categorias) {
				if(Long.parseLong(categoriaId) == cat.getId()) {
					categoriaParaCrear = cat;
					
				}
			}

			Producto producto = new Producto(nombre, precio, caducidad, descripcion, categoriaParaCrear);
			pDaoPrueba.crearProducto(producto);
		}
		else if(tabla.equals("2")) {
			System.out.println("Dime el nombre de la categoría que quieres crear");
			String nombre = sc.nextLine();
			System.out.println("Dime la descripción de la categoría que quieres crear");
			String descripcion = sc.nextLine();
			
			Categoria categoria = new Categoria(nombre, descripcion);
			cDaoPrueba.crearCategoria(categoria);
		}
	}

	private static void modificarUno(String tabla) {
		if (tabla.equals("1")) {
			System.out.println("Dime el id del producto que quieres modificar");
			Long id = Long.parseLong(sc.nextLine());
			System.out.println("Dime el nombre del producto que quieres modificar");
			String nombre = sc.nextLine();
			System.out.println("Dime el precio del producto que quieres modificar");
			BigDecimal precio = new BigDecimal(sc.nextLine());
			System.out.println("Dime la caducidad del producto que quieres modificar");
			LocalDate caducidad = LocalDate.parse(sc.nextLine());
			System.out.println("Dime la descripción del producto que quieres modificar");
			String descripcion = sc.nextLine();
			
			System.out.println("Dime la categoría del producto que quieres crear");
			
			var categorias = cDaoPrueba.mostrarCategorias();
			
			for(Categoria categoria : categorias) {
				System.out.println(categoria.getId() + "-. " + categoria.getNombre());
			}
			
			String categoriaId = sc.nextLine();
			Categoria categoriaParaCrear = new Categoria();
			
			for(Categoria cat : categorias) {
				if(Long.parseLong(categoriaId) == cat.getId()) {
					categoriaParaCrear = cat;
					
				}
			}

			Producto producto = new Producto(id, nombre, precio, caducidad, descripcion, categoriaParaCrear);
			pDaoPrueba.modificarProducto(producto);
		}
		else if(tabla.equals("2")) {
			System.out.println("Dime el id de la categoría que quieres modificar");
			Long id = Long.parseLong(sc.nextLine());
			System.out.println("Dime el nombre de la categoría que quieres modificar");
			String nombre = sc.nextLine();
			System.out.println("Dime la descripción de la categoría que quieres modificar");
			String descripcion = sc.nextLine();
			
			Categoria categoria = new Categoria(id, nombre, descripcion);
			cDaoPrueba.modificarCategoria(categoria);
		}
	}
}
