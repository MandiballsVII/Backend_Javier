package accesodatos;

import entidades.Categoria;
import entidades.Producto;

public class CategoriaDaoPrueba {
	CategoriaDao dao = new CategoriaDao(System.getenv("JDBC_URL"), System.getenv("JDBC_USER"),
			System.getenv("JDBC_PASS"));
	
	
	public Iterable<Categoria> mostrarCategorias() {
		return dao.obtenerTodos();
	}
	
	public Categoria mostrarCategoriasPorId(Long id) {
		return dao.obtenerPorId(id);
	}
	
	public void borrarCategoria(Long id) {
		dao.borrar(id);
	}
	
	public void crearCategoria(Categoria categoria) {
		dao.insertar(categoria);
	}
	
	public void modificarCategoria(Categoria categoria) {
		dao.modificar(categoria);
	}
}
