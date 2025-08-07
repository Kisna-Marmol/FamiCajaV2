package dao;


import sql.Conexion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Interes;
import modelo.Miembro;
import modelo.Prestamo;

public class InteresDAO {

	public static boolean generarInteresMensual(Prestamo prestamo) {
		try (Connection conn = Conexion.getConnection()){
			// Verificar si ya existe interés este mes para este préstamo
			String sql = "SELECT COUNT(*) FROM intereses "
					+ "WHERE id_prestamo = ? AND MONTH(fecha_generacion) = MONTH(CURDATE()) "
					+ "AND YEAR(fecha_generacion) = YEAR(CURDATE())";

			try (PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, prestamo.getId());
				
				ResultSet rs = stmt.executeQuery();
				if(rs.next() && rs.getInt(1) > 0) {
					System.out.println("Ya se generó el interés este mes para el préstamo " + prestamo.getId());
					return false;
				}
			}
			
			// Verificar si han pasado 30 días desde la fecha del préstamo
			LocalDate fechaPrestamo = prestamo.getFechaInicio().toLocalDate(); // ← Asegúrate de tener el getter
			LocalDate hoy = LocalDate.now();

			if (ChronoUnit.DAYS.between(fechaPrestamo, hoy) < 30) {
				System.out.println("Aún no han pasado 30 días del préstamo.");
				return false;
			}
			
			// Calcular el interés
			BigDecimal saldo = prestamo.getSaldoRestante();
			BigDecimal interes = saldo.multiply(new BigDecimal("0.10"));

			// Insertar el interés
			String sqlInsert = "INSERT INTO intereses (id_prestamo, monto_intereses, fecha_generacion, pagado) "
								+ "VALUES (?, ?, CURDATE(), false)";

			try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
				stmtInsert.setInt(1, prestamo.getId());
				stmtInsert.setBigDecimal(2, interes);
				return stmtInsert.executeUpdate() > 0;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al generar el interés: " + e.getMessage());
		}
		return false;
	} 
	
	public List<Interes> listarIntereses(){
		List<Interes> lista = new ArrayList<>();

	    String sql = "SELECT i.id_intereses, i.monto_intereses, i.fecha_generacion, i.pagado, " +
	                 "p.id_prestamo, p.saldo_restante, p.fecha_inicio, " +
	                 "m.id_miembro, m.nombre " +
	                 "FROM intereses i " +
	                 "INNER JOIN prestamos p ON i.id_prestamo = p.id_prestamo " +
	                 "INNER JOIN miembros m ON p.id_miembro = m.id_miembro";

	    try (Connection conn = Conexion.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            // Construir miembro
	            Miembro miembro = new Miembro();
	            miembro.setId(rs.getInt("id_miembro"));
	            miembro.setNombre_completo(rs.getString("nombre"));

	            // Construir préstamo
	            Prestamo prestamo = new Prestamo();
	            prestamo.setId(rs.getInt("id_prestamo"));
	            prestamo.setSaldoRestante(rs.getBigDecimal("saldo_restante"));
	            prestamo.setFechaInicio(rs.getDate("fecha_inicio"));
	            prestamo.setNombreMiembro(rs.getString("nombre"));

	            // Construir interés
	            Interes interes = new Interes();
	            interes.setIdIntereses(rs.getInt("id_intereses"));
	            interes.setMontoInteres(rs.getBigDecimal("monto_intereses"));
	            interes.setFechaGeneracion(rs.getDate("fecha_generacion"));
	            interes.setPagado(rs.getBoolean("pagado"));
	            interes.setPrestamo(prestamo);

	            lista.add(interes);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}
	
	//para pagoIntereses
	public static List<Interes> listarPorIdMiembro(int idMiembro){
	    List<Interes> lista = new ArrayList<>();

	    String sql = "SELECT i.id_intereses, i.monto_intereses, i.fecha_generacion FROM intereses i JOIN prestamos p ON i.id_prestamo = p.id_prestamo WHERE p.id_miembro = ? AND i.pagado = 0";

	    try (Connection con = Conexion.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, idMiembro);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Interes i = new Interes();
	            i.setIdIntereses(rs.getInt("id_intereses"));
	            i.setMontoInteres(rs.getBigDecimal("monto_intereses"));
	            i.setFechaGeneracion(rs.getDate("fecha_generacion"));
	            lista.add(i);
	        }

	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}
	
	//registrar pago
	public static boolean registrarPago(int idIntereses, java.util.Date fechaPago) {
		String sql = "UPDATE intereses SET pagado = 1, fecha_generacion = ? WHERE id_intereses = ?";
		
		try (Connection con = Conexion.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setDate(1, new java.sql.Date(fechaPago.getTime()));
		        ps.setInt(2, idIntereses);

		        int filas = ps.executeUpdate();
		        return filas > 0;

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
	}

	// Inserta el pago en la tabla pagoIntereses
	public static boolean registrarPagoInteres(int idIntereses, BigDecimal monto, java.util.Date fechaPago) {
	    String sql = "INSERT INTO pago_intereses (id_intereses, monto_pagado, fecha_pago) VALUES (?, ?, ?)";

	    try (Connection con = Conexion.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, idIntereses);
	        ps.setBigDecimal(2, monto);
	        ps.setDate(3, new java.sql.Date(fechaPago.getTime()));

	        int filas = ps.executeUpdate();
	        return filas > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	//Eliminar pago Intereses
	public static boolean eliminarPagoInteres(int idInteres) {
	    String sqlDelete = "DELETE FROM pago_intereses WHERE id_intereses = ?";
	    String sqlUpdate = "UPDATE intereses SET pagado = 0 WHERE id_intereses = ?";
	    
	    try (Connection con = Conexion.getConnection()) {
	        con.setAutoCommit(false); // iniciar transacción

	        try (
	            PreparedStatement psDelete = con.prepareStatement(sqlDelete);
	            PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)
	        ) {
	            psDelete.setInt(1, idInteres);
	            int filasBorradas = psDelete.executeUpdate();

	            psUpdate.setInt(1, idInteres);
	            int filasActualizadas = psUpdate.executeUpdate();

	            if (filasBorradas > 0 && filasActualizadas > 0) {
	                con.commit();
	                return true;
	            } else {
	                con.rollback();
	                return false;
	            }
	        } catch (SQLException e) {
	            con.rollback();
	            e.printStackTrace();
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	//para estado de cuenta
	public static BigDecimal getTotalIntereses(int idMiembro) {
	    String sql = "SELECT SUM(i.monto_intereses) " +
	                 "FROM intereses i " +
	                 "JOIN prestamos p ON i.id_prestamo = p.id_prestamo " +
	                 "WHERE p.id_miembro = ?";
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

	public static BigDecimal getInteresesPagados(int idMiembro) {
	    String sql = "SELECT SUM(i.monto_intereses) " +
	                 "FROM intereses i " +
	                 "JOIN prestamos p ON i.id_prestamo = p.id_prestamo " +
	                 "WHERE p.id_miembro = ? AND i.pagado = 1";
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
	public double getTotalInteresGenerado() {
	    double total = 0.0;
	    try {
	        Connection con = Conexion.getConnection();
	        PreparedStatement ps = con.prepareStatement("SELECT SUM(monto_intereses) FROM intereses");
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            total = rs.getDouble(1); // obtiene la suma total de intereses generados
	        }
	        rs.close();
	        ps.close();
	        con.close();
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al calcular total de intereses generados: " + e.getMessage());
	    }
	    return total;
	}


}
