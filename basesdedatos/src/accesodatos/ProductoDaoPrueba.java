package accesodatos;

import java.util.ArrayList;
import java.util.Scanner;

import entidades.Producto;

public class ProductoDaoPrueba {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ProductoDao dao = new ProductoDao(System.getenv("JDBC_URL"), System.getenv("JDBC_USER"),
				System.getenv("JDBC_PASS"));
		
		ArrayList<Producto> productos = dao.obtenerProductos();
		
		for(Producto producto: productos) {
			System.out.println(producto);
		}
		
		System.out.println("Dime el id del producto que quieres ver");
		long idProducto = Long.parseLong(sc.nextLine());
		
		System.out.println(dao.obtenerPorId(idProducto));
		
		sc.close();
	}
}
