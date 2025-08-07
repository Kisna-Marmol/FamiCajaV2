package dao;

import modelo.Miembro;
import sql.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
public class MiembroDAO {
	public static boolean insertar(Miembro miembro) {
		String sql = "INSERT INTO miembros(nombre,fecha_ingreso,estado) VALUES (?, ?, ?)";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql)){
			stmt.setString(1, miembro.getNombre_completo());
			stmt.setDate(2, miembro.getFecha_ingreso());
			stmt.setString(3, miembro.isEstado() ? "Activo" : "Inactivo");
			
			return stmt.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean actualizar(Miembro miembro) {
		String sql = "UPDATE miembros SET Nombre = ?, fecha_ingreso = ?, estado = ? WHERE id_miembro = ?";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql)){
			stmt.setString(1, miembro.getNombre_completo());
			stmt.setDate(2, miembro.getFecha_ingreso());
			stmt.setString(3, miembro.isEstado() ? "Activo" : "Inactivo");
			stmt.setInt(4, miembro.getId());
			
			return stmt.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean eliminar(int id) {
		String sql = "DELETE FROM miembros WHERE id_miembro = ?";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql)){
			stmt.setInt(1, id);
			
			return stmt.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static List<Miembro> listarTodos(){
		List<Miembro> lista = new ArrayList<>();
		String sql = "SELECT * FROM miembros";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			while(rs.next()) {
				int id = rs.getInt("id_miembro");
				String nombre = rs.getString("Nombre");
				Date fecha = rs.getDate("fecha_ingreso");
				boolean estado = rs.getString("estado").equalsIgnoreCase("Activo");
				
				lista.add(new Miembro(id,nombre,estado,fecha));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	public static List<Miembro> listarPorNombre(){
		List<Miembro> lista = new ArrayList<>();
		String sql = "SELECT id_miembro, Nombre FROM miembros WHERE estado = 'Activo'";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			while(rs.next()) {
				int id = rs.getInt("id_miembro");
				String nombre = rs.getString("Nombre");
				//Date fecha = rs.getDate("fecha_ingreso");
				//boolean estado = rs.getString("estado").equalsIgnoreCase("Activo");
				
				lista.add(new Miembro(id,nombre));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public static List<Miembro> buscarMiembro(String nombre){
		List<Miembro> lista = new ArrayList<>();
		String sql = "SELECT * FROM miembros WHERE Nombre LIKE ? ";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql)){
			stmt.setString(1, "%" + nombre + "%");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id_miembro");
				String nombreCompleto = rs.getString("Nombre");
				Date fecha = rs.getDate("fecha_ingreso");
				boolean estado = rs.getString("estado").equalsIgnoreCase("Activo");
				
				lista.add(new Miembro(id,nombreCompleto,estado,fecha));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	//para intereses
	public static Miembro obtenerPorId(int id) {
		Miembro m = null;
		try (Connection conn = Conexion.getConnection()) {
			String sql = "SELECT * FROM miembros WHERE id_miembro = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				m = new Miembro();
				m.setId(rs.getInt("id_miembro"));
				m.setNombre_completo(rs.getString("nombre_completo"));
				// Agrega los demás campos si los usas
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	//para reporte general
	public int contarMiembros() {
	    int total = 0;
	    try {
	        Connection con = Conexion.getConnection();
	        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM miembros");
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            total = rs.getInt(1);
	        }
	        rs.close();
	        ps.close();
	        con.close();
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al contar miembros: " + e.getMessage());
	    }
	    return total;
	}
			
}
