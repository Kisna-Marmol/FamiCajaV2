package dao;

import modelo.Aporte;
import modelo.Miembro;
import sql.Conexion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class AporteDAO {
	private static Connection con;
	private static String sql;
	
	public AporteDAO() {
		con = Conexion.getConnection();
	}
	
	public boolean insertar(Aporte aporte) {
		sql = "INSERT INTO aportes (id_miembro, monto, fecha_aporte) VALUES (?, ?, ?)";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, aporte.getIdMiembro());
			stmt.setBigDecimal(2, aporte.getMonto());
			stmt.setDate(3, aporte.getFecha_aporte());
			
			int filasIncertadas = stmt.executeUpdate();
			return filasIncertadas > 0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al insertar "+ e.getMessage());
			return false;
		}
	}
	
	public static boolean actualizar(Aporte aporte) {
		String sql = "UPDATE aportes SET id_miembro = ?, monto = ?, fecha_aporte = ? WHERE id_aportes = ?";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql)){
			stmt.setInt(1, aporte.getIdMiembro());
			stmt.setBigDecimal(2, aporte.getMonto());
			stmt.setDate(3, aporte.getFecha_aporte());
			stmt.setInt(4, aporte.getId());
			
			return stmt.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public static boolean eliminar(int id) {
		String sql = "DELETE FROM aportes WHERE id_aportes = ?";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql)){
			stmt.setInt(1, id);
			
			return stmt.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Aporte> listarPorMiembro(int idMiembro){
		List<Aporte> lista = new ArrayList<>();
		sql = "SELECT a.id_aportes, m.Nombre, a.monto, a.fecha_aporte "+
		"FROM aportes a "+
				"INNER JOIN miembros m ON a.id_miembro = m.id_miembro "+
		"WHERE a.id_miembro = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, idMiembro);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Aporte aporte = new Aporte();
				aporte.setId(rs.getInt("id_aportes"));
				aporte.setNombreMiembro(rs.getString("Nombre"));
				aporte.setMonto(rs.getBigDecimal("monto"));
				aporte.setFecha_aporte(rs.getDate("fecha_aporte"));
				
				lista.add(aporte);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al listar aporte por miembro "+ e.getMessage());
			
		}
		return lista;
	}
	
	public static List<Aporte> listarTodo(){
		List<Aporte> lista = new ArrayList<>();
		sql = "SELECT a.id_aportes, m.Nombre, a.monto, a.fecha_aporte " +
			      "FROM aportes a " +
			      "INNER JOIN miembros m ON a.id_miembro = m.id_miembro";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Aporte aporte = new Aporte();
				aporte.setId(rs.getInt("id_aportes"));
				aporte.setNombreMiembro(rs.getString("Nombre"));
				aporte.setMonto(rs.getBigDecimal("monto"));
				aporte.setFecha_aporte(rs.getDate("fecha_aporte"));
				
				lista.add(aporte);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al listar aporte por miembro "+ e.getMessage());
			
		}
		return lista;
	}
	
	//para estado de cuenta
	
	public static BigDecimal getTotalAportado(int idMiembro) {
	    String sql = "SELECT SUM(monto) FROM aportes WHERE id_miembro = ?";
	    try (Connection con = Conexion.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, idMiembro);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : BigDecimal.ZERO;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return BigDecimal.ZERO;
	}

	
	//reporte general
	public int getTotalAportado() {
	    int total = 0;
	    try {
	        Connection con = Conexion.getConnection();
	        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM aportes");
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            total = rs.getInt(1); // obtiene el total desde la primera columna del ResultSet
	        }
	        rs.close();
	        ps.close();
	        con.close();
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al contar aportes: " + e.getMessage());
	    }
	    return total;
	}

}
