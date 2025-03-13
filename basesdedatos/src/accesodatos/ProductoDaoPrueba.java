package accesodatos;

import entidades.Producto;

public class ProductoDaoPrueba {
	
	ProductoDao dao = new ProductoDao(System.getenv("JDBC_URL"), System.getenv("JDBC_USER"),
			System.getenv("JDBC_PASS"));
	
	
	public Iterable<Producto> mostrarProductos() {
		return dao.obtenerProductos();
	}
	
	public Producto mostrarPorId(Long id) {
		return dao.obtenerPorId(id);
	}
	
	public void borrarProducto(Long id) {
		dao.borrar(id);
	}
	
	public void crearProducto(Producto producto) {
		dao.insertar(producto);
	}
	
	public void modificarProducto(Producto producto) {
		dao.modificar(producto);
	}
}
