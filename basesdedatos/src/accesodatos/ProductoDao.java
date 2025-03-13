package accesodatos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import entidades.Producto;

public class ProductoDao {
	private String jdbcUrl;
	private String jdbcUsuario;
	private String jdbcPassword;

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

	public Iterable<Producto> obtenerProductos() {
		try (var con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				var pst = con.prepareStatement(SQL_SELECT);
				var rs = pst.executeQuery()) {
			var productos = new ArrayList<Producto>();
			
			while(rs.next()) {
				var id = rs.getLong("id");
				var nombre = rs.getString("nombre");
				var precio = rs.getBigDecimal("precio");
				var caducidad = rs.getDate("caducidad").toLocalDate();
				var descripcion = rs.getString("descripcion");
				
				var producto = new Producto(id, nombre, precio, caducidad, descripcion);
				
				productos.add(producto);
			}
			
			return productos;
		} catch (SQLException e) {
			throw new RuntimeException("Ha habido un error en la consulta", e);
		}
	}
	public Producto obtenerPorId(Long id) {

		try (Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				PreparedStatement pst = con.prepareStatement(SQL_SELECT_ID);) {

			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();

			Producto producto = new Producto();

			while (rs.next()) {
				String nombre = rs.getString("nombre");
				BigDecimal precio = rs.getBigDecimal("precio");
				LocalDate caducidad = rs.getDate("caducidad").toLocalDate();
				String descripcion = rs.getString("descripcion");

				producto = new Producto(id, nombre, precio, caducidad, descripcion);
			}

			return producto;

		} catch (SQLException e) {
			throw new RuntimeException("Ha habido un error en la consulta", e);
		}

	}

	public Producto insertar(Producto producto) {
		try (Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				PreparedStatement pst = con.prepareStatement(SQL_INSERT);) {
			pst.setString(1, producto.getNombre());
			pst.setBigDecimal(2, producto.getPrecio());
			pst.setDate(3, java.sql.Date.valueOf(producto.getCaducidad()));
			pst.setString(4, producto.getDescripcion());
			pst.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Ha habido un error en la consulta", e);
		}
		return producto;
	}

	public Producto modificar(Producto producto) {
		try(Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE);){
			
			pst.setString(1, producto.getNombre());
			pst.setBigDecimal(2, producto.getPrecio());
			pst.setDate(3, java.sql.Date.valueOf(producto.getCaducidad()));
			pst.setString(4, producto.getDescripcion());
			pst.setLong(5, producto.getId());
			pst.execute();
			
		}catch (SQLException e) {
			throw new RuntimeException("Ha habido un error en la consulta", e);
		}
		return obtenerPorId(producto.getId());
		
	}

	public void borrar(Long id) {
		try (Connection con = DriverManager.getConnection(jdbcUrl, jdbcUsuario, jdbcPassword);
				PreparedStatement pst = con.prepareStatement(SQL_DELETE);) {
			pst.setLong(1, id);
			pst.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Ha habido un error en la consulta", e);
		}
	}
}
