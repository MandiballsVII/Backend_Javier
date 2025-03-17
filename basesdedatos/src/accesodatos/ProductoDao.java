package accesodatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.EntidadesException;
import entidades.Producto;

public class ProductoDao {
	private String jdbcUrl;
	private String jdbcUsuario;
	private String jdbcPassword;
	private static String jdbcDriver = System.getenv("JDBC_DRIVER");

	private static final String SQL_SELECT = "SELECT id, nombre, precio, caducidad, descripcion FROM productos";
	private static final String SQL_SELECT_ID = SQL_SELECT + " WHERE id=?";
	private static final String SQL_INSERT = "INSERT INTO productos (nombre, precio, caducidad, descripcion) VALUES (?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE productos SET nombre=?, precio=?, caducidad=?, descripcion=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM productos WHERE id=?";

	public ProductoDao(String jdbcUrl, String jdbcUsuario, String jdbcPassword) {
		super();
		this.jdbcUrl = jdbcUrl;
		this.jdbcUsuario = jdbcUsuario;
		this.jdbcPassword = jdbcPassword;
	}
	
	static {
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			throw new EntidadesException("No se ha encontrado el driver de MySQL");
		}
	}

	public Iterable<Producto> obtenerProductos() {
		try (var con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				var pst = con.prepareStatement(SQL_SELECT);
				var rs = pst.executeQuery()) {
			var productos = new ArrayList<Producto>();
			
			while(rs.next()) {
				var producto = filaAProducto(rs);
				
				productos.add(producto);
			}
			
			return productos;
		} catch (SQLException e) {
			throw new EntidadesException("Ha habido un error en la consulta de todos los productos", e);
			// NOSONAR
		}
	}
	public Producto obtenerPorId(Long id) {

		try (Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				PreparedStatement pst = con.prepareStatement(SQL_SELECT_ID);) {

			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();

			Producto producto = null;

			while (rs.next()) {
				
				producto = filaAProducto(rs);
			}

			return producto;

		} catch (SQLException e) {
			throw new EntidadesException("Ha habido un error en la consulta de producto con id" + id, e);
		}

	}

	public Producto insertar(Producto producto) {
		try (Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				PreparedStatement pst = con.prepareStatement(SQL_INSERT);) {
			
			productoAFila(producto, pst);
			pst.execute();
			
		} catch (SQLException e) {
			throw new EntidadesException("Ha habido un error en la insercion del producto con id " + producto.getId(), e);
		}
		return producto;
	}

	public Producto modificar(Producto producto) {
		try(Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE);){
			
			productoAFila(producto, pst);
			pst.execute();
			
			return producto;
			
		}catch (SQLException e) {
			throw new EntidadesException("Ha habido un error en la modificacion del producto con id " + producto.getId(), e);
		}
		
	}
	public void borrar(Long id) {
		try (Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				PreparedStatement pst = con.prepareStatement(SQL_DELETE);) {
			pst.setLong(1, id);
			pst.execute();
		} catch (SQLException e) {
			throw new EntidadesException("Ha habido un error en el borrado en el producto con id " + id, e);
		}
	}
	
	private Producto filaAProducto(ResultSet rs) throws SQLException {
		var id = rs.getLong("id");
		var nombre = rs.getString("nombre");
		var precio = rs.getBigDecimal("precio");
		var caducidadDate = rs.getDate("caducidad");
		var caducidad = caducidadDate == null ? null : caducidadDate.toLocalDate();
		var descripcion = rs.getString("descripcion");
		
		return new Producto(id, nombre, precio, caducidad, descripcion);
	}
	private void productoAFila(Producto producto, PreparedStatement pst) throws SQLException {
		pst.setString(1, producto.getNombre());
		pst.setBigDecimal(2, producto.getPrecio());
		pst.setDate(3, producto.getCaducidad() == null ? null : java.sql.Date.valueOf(producto.getCaducidad()));
		pst.setString(4, producto.getDescripcion());
		
		if(producto.getId() != null) {
			pst.setLong(5, producto.getId());
		}
	}
}
