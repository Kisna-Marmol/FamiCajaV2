package dao;

import sql.Conexion;
import modelo.PagoPrestamo;
import modelo.Prestamo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class PagoPrestamoDAO {
	private static Connection con;
	private static String sql;
	
	public PagoPrestamoDAO() {
		con = Conexion.getConnection();
	}
	
	public static boolean insertar(PagoPrestamo pago) {
		sql = "INSERT INTO pago_prestamos (id_prestamo, monto_pagado, fecha_pago) VALUES (?, ?, ?)";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, pago.getPrestamo().getId());
			stmt.setBigDecimal(2, pago.getMontoPagado());
			stmt.setDate(3, pago.getFechaPago());
			
			int filasIncertadas = stmt.executeUpdate();
			
			/*if(filasIncertadas > 0) {
				PrestamoDAO prestamoDAO = new PrestamoDAO();
				PrestamoDAO.aplicarPagoMultiples(pago.getPrestamo().getIdMiembro(), pago.getMontoPagado(), pago.getFechaPago());
				return true;
			}else {
				return false;
			}*/
			return filasIncertadas > 0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al insertar pago: "+ e.getMessage());
			return false;
		}
	}
	
	public static void insertar(int idPrestamo, BigDecimal montoPagado, Date fechaPago) {
	    PagoPrestamo pago = new PagoPrestamo();
	    pago.setPrestamo(PrestamoDAO.obtenerPorId(idPrestamo));
	    pago.setMontoPagado(montoPagado);
	    pago.setFechaPago(fechaPago);
	    insertar(pago);
	}
	
	public boolean actualizar(PagoPrestamo pago) {
		sql = "UPDATE pago_prestamos SET id_prestamo = ?, monto_pagado = ?, fecha_pago = ? WHERE id_pago_prestamos = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, pago.getPrestamo().getId());
			stmt.setBigDecimal(2, pago.getMontoPagado());
			stmt.setDate(3, pago.getFechaPago());
			stmt.setInt(4, pago.getIdPagoPrestamo());
			
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al actualizar pago: "+ e.getMessage());
			return false;
		}
	}
	
	public boolean eliminar(int idPago) {
		sql = "DELETE FROM pago_prestamos WHERE id_pago_prestamos = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, idPago);
			
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar pago: "+ e.getMessage());
			return false;
		}
	}
	
	
	//listar pagos por prestamo
	public static List<PagoPrestamo> listarPorPrestamo(int idPrestamo){
		List<PagoPrestamo> lista = new ArrayList<>();
		sql = "SELECT id_pago_prestamos, monto_pagado, fecha_pago FROM pago_prestamos WHERE id_prestamo = ?";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, idPrestamo);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				PagoPrestamo pago = new PagoPrestamo();
				pago.setIdPagoPrestamo(rs.getInt("id_pago_prestamos"));
				
				Prestamo prestamo = new Prestamo();
				prestamo.setId(idPrestamo);
				pago.setPrestamo(prestamo);
				
				pago.setMontoPagado(rs.getBigDecimal("monto_pagado"));
				pago.setFechaPago(rs.getDate("fecha_pago"));
				
				lista.add(pago);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al listar pago: "+ e.getMessage());
		}
		return lista;
	}
	
	//para estado de cuenta
	public static BigDecimal getTotalPagado(int idMiembro) {
	    String sql = "SELECT SUM(monto_pagado) FROM pago_prestamos WHERE id_prestamo IN " +
	                 "(SELECT id_prestamo FROM prestamos WHERE id_miembro = ?)";
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
	public double getTotalPagado() {
	    double total = 0.0;
	    try {
	        Connection con = Conexion.getConnection();
	        PreparedStatement ps = con.prepareStatement("SELECT SUM(monto_pagado) FROM pago_prestamos");
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            total = rs.getDouble(1); // obtiene la suma total
	        }
	        rs.close();
	        ps.close();
	        con.close();
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al calcular total pagado: " + e.getMessage());
	    }
	    return total;
	}
}
