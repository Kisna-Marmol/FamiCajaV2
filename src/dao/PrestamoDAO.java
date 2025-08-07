package dao;

import modelo.Prestamo;
import modelo.Miembro;
import sql.Conexion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PrestamoDAO {
	//private static Connection con;
	private static String sql;
	
	/*public PrestamoDAO() {
		con = Conexion.getConnection();
	}*/
	
	public boolean insertar(Prestamo prestamo) {
		sql = "INSERT INTO prestamos (id_miembro, monto_original, saldo_restante, fecha_inicio, estado) VALUES (?, ?, ?, ?, ?)";
		try(Connection con = Conexion.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			
			//Obtener prestamos activos del miembro
			List<Prestamo> prestamosActivos = prestamosActivosPorMiembro(prestamo.getIdMiembro());
			
			BigDecimal saldoAnterior = BigDecimal.ZERO;
			
			//sumar saldos restantes de prestamos activos
			for(Prestamo p : prestamosActivos) {
				saldoAnterior = saldoAnterior.add(p.getSaldoRestante());
			}
			
			//sumar nuevo monto al saldo anterior
			BigDecimal saldoTotal = prestamo.getMontoOriginal().add(saldoAnterior);
			
			//insertar el nuevo prestamo con el saldo combinado
			stmt.setInt(1, prestamo.getIdMiembro());
			stmt.setBigDecimal(2, prestamo.getMontoOriginal());
			stmt.setBigDecimal(3, saldoTotal); //saldo restante total
			stmt.setDate(4, new java.sql.Date(prestamo.getFechaInicio().getTime()));
			//stmt.setDate(4, prestamo.getFechaInicio());
			stmt.setString(5, "Activo");
			//stmt.setString(5, prestamo.isEstado() ? "Activo" : "Finalizado");
			
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al insertar "+ e.getMessage());
			return false;
		}
	}
	
	public static boolean actualizar(Prestamo prestamo) {
		String sql = "UPDATE prestamos SET id_miembro = ?, monto_original = ?, fecha_inicio = ? WHERE id_prestamo = ?";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql)){
			stmt.setInt(1, prestamo.getIdMiembro());
			stmt.setBigDecimal(2, prestamo.getMontoOriginal());
			stmt.setDate(3, prestamo.getFechaInicio());
			stmt.setInt(4, prestamo.getId());
			
			return stmt.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public static boolean actualizarSaldoRestante(int idPrestamo, BigDecimal nuevoSaldo) {
		sql = "UPDATE prestamos SET saldo_restante = ? WHERE id_prestamo = ?";
		
		try (Connection con = Conexion.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setBigDecimal(1, nuevoSaldo);
            stmt.setInt(2, idPrestamo);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar saldo: " + e.getMessage());
        }
        return false;
	}
	
	public static boolean eliminar(int id) {
		String sql = "DELETE FROM prestamos WHERE id_prestamo = ?";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement stmt = conexion.prepareStatement(sql)){
			stmt.setInt(1, id);
			
			return stmt.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//actualizar el saldo restante
	public static boolean actualizarSaldo(int idPrestamo, BigDecimal montoPagado) {
		sql = "UPDATE prestamos SET saldo_restante = ? WHERE id_prestamo = ?";
		try (Connection con = Conexion.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setBigDecimal(1, montoPagado);
			stmt.setInt(2, idPrestamo);
			
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al actualizar saldo restante: " + e.getMessage());
	        return false;
		}
	}
	
	public static void verificarYFinalizarPrestamo(int idPrestamo) {
		sql = "UPDATE prestamos SET estado = 'Finalizado', fecha_finalizacion = CURRENT_DATE " +
	              "WHERE id_prestamo = ? AND saldo_restante <= 0";
		
		try (Connection con = Conexion.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, idPrestamo);
			stmt.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al finalizar prestamo: " + e.getMessage());
		}
	}
	
	public List<Prestamo> listarPorMiembro(int idMiembro){
		List<Prestamo> lista = new ArrayList<>();
		sql = "SELECT * FROM prestamos WHERE id_miembro = ?";
		
		try(Connection con = Conexion.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, idMiembro);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Prestamo prestamo = new Prestamo();
				prestamo.setId(rs.getInt("id_prestamo"));
				prestamo.setIdMiembro(rs.getInt("id_miembro"));
				//prestamo.setNombreMiembro(rs.getString("Nombre"));
				prestamo.setMontoOriginal(rs.getBigDecimal("monto_original"));
				prestamo.setSaldoRestante(rs.getBigDecimal("saldo_restante"));;
				prestamo.setFechaInicio(rs.getDate("fecha_inicio"));
				prestamo.setFechaFinalizacion(rs.getDate("fecha_finalizacion"));
				prestamo.setEstado("Activo".equalsIgnoreCase(rs.getString("Estado")));
				
				lista.add(prestamo);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al listar prestamo por miembro "+ e.getMessage());
			
		}
		return lista;
	}
	
	public static List<Prestamo> listarTodo(){
		List<Prestamo> lista = new ArrayList<>();
		sql = "SELECT p.id_prestamo, p.id_miembro, m.Nombre, p.monto_original, p.saldo_restante, p.fecha_inicio, p.fecha_finalizacion, p.estado "
	               + "FROM prestamos p "
	               + "JOIN miembros m ON p.id_miembro = m.id_miembro";
		
		try(Connection con = Conexion.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Prestamo prestamo = new Prestamo();
				prestamo.setId(rs.getInt("id_prestamo"));
				prestamo.setIdMiembro(rs.getInt("id_miembro"));
				prestamo.setNombreMiembro(rs.getString("Nombre"));
				prestamo.setMontoOriginal(rs.getBigDecimal("monto_original"));
				prestamo.setSaldoRestante(rs.getBigDecimal("saldo_restante"));
				prestamo.setFechaInicio(rs.getDate("fecha_inicio"));
				prestamo.setFechaFinalizacion(rs.getDate("fecha_finalizacion"));
				prestamo.setEstado("Activo".equalsIgnoreCase(rs.getString("estado")));
				
				lista.add(prestamo);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al listar todos los prestamos: "+ e.getMessage());
			
		}
		return lista;
	}
	
	//obtener los prestamos activos de cada miembro
	public static List<Prestamo> prestamosActivosPorMiembro(int idMiembro) {
		List<Prestamo> lista = new ArrayList<>();
		sql =  "SELECT * FROM prestamos WHERE id_miembro = ? AND estado = ? ORDER BY fecha_inicio ASC";
		
		try(Connection con = Conexion.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setInt(1, idMiembro);
			stmt.setString(2, "Activo");
			ResultSet rs = stmt.executeQuery();
			
			
			while (rs.next()) {
				Prestamo prestamo = new Prestamo();
				prestamo.setId(rs.getInt("id_prestamo"));
				prestamo.setIdMiembro(rs.getInt("id_miembro"));
				prestamo.setMontoOriginal(rs.getBigDecimal("monto_original"));
				prestamo.setSaldoRestante(rs.getBigDecimal("saldo_restante"));
				prestamo.setFechaInicio(rs.getDate("fecha_inicio"));
				prestamo.setFechaFinalizacion(rs.getDate("fecha_finalizacion"));
				prestamo.setEstado(rs.getString("estado").equalsIgnoreCase("Activo"));
				
				lista.add(prestamo);
			}
			
			System.out.println("Préstamos encontrados: " + lista.size());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al obtener prestamos activos: "+ e.getMessage());
			return new ArrayList<>(); //no retorna null solo lista vacia
		}
		return lista;
	}
	
	public static Prestamo obtenerPorId(int idPrestamo) {
		sql = "SELECT * FROM prestamos WHERE id_prestamo = ?";
		
		try (Connection con = Conexion.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setInt(1, idPrestamo);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Prestamo prestamo = new Prestamo();
	            prestamo.setId(rs.getInt("id_prestamo"));
	            prestamo.setIdMiembro(rs.getInt("id_miembro"));
	            prestamo.setMontoOriginal(rs.getBigDecimal("monto_original"));
	            prestamo.setSaldoRestante(rs.getBigDecimal("saldo_restante"));
	            prestamo.setFechaInicio(rs.getDate("fecha_inicio"));
	            prestamo.setFechaFinalizacion(rs.getDate("fecha_finalizacion"));
	            prestamo.setEstado("Activo".equalsIgnoreCase(rs.getString("estado")));
	            return prestamo;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al obtener préstamo por ID: " + e.getMessage());
	    }

	    return null;
	}
	
	public static void aplicarPagoMultiples(int idMiembro, BigDecimal montoPagado, Date fecha) {
		List<Prestamo> prestamos = prestamosActivosPorMiembro(idMiembro);
		
		//aplicar los prestamos por orden de antiguedad
		BigDecimal pagoPendiente = montoPagado;
		
		for(Prestamo prestamo: prestamos) {
			BigDecimal saldo = prestamo.getSaldoRestante();
			
			if(montoPagado.compareTo(BigDecimal.ZERO) <= 0) {
				break;//ya no hay por pagar
			}
			
			if(montoPagado.compareTo(saldo) >= 0) {
				//pago cubre completamente este prestamo
				PagoPrestamoDAO.insertar(prestamo.getId(), saldo, fecha);
				actualizarSaldo(prestamo.getId(), BigDecimal.ZERO); //resta el saldo actual
				verificarYFinalizarPrestamo(prestamo.getId()); //cambiar el estado
				pagoPendiente = pagoPendiente.subtract(saldo); //sigue con el siguiente prestamo
			} else {
				//pago parcial, solo cubre una parte del prestamo
				BigDecimal nuevoSaldo = saldo.subtract(pagoPendiente);
				PagoPrestamoDAO.insertar(prestamo.getId(), pagoPendiente, fecha);
				actualizarSaldo(prestamo.getId(), nuevoSaldo);
				pagoPendiente = BigDecimal.ZERO;
				break;
			}
		}
		
		/**
		 * actualizar el saldo de los prestamos restantes
		 * porque todos incluyen el saldo combinado
		 */
		
		for(Prestamo prestamo : prestamos) {
			BigDecimal saldoActual = prestamo.getSaldoRestante();
			if(saldoActual.compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal nuevoSaldo = saldoActual.subtract(montoPagado);
				if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
					nuevoSaldo = BigDecimal.ZERO;
				}
				actualizarSaldo(prestamo.getId(), nuevoSaldo);
			}
		}
	}
	
	//Este método suma todos los saldos restantes de los préstamos activos del miembro
	public static BigDecimal obtenerSaldoTotalPorMiembro(int idMiembro) {
		BigDecimal saldoTotal = BigDecimal.ZERO ;
		List<Prestamo> prestamos = prestamosActivosPorMiembro(idMiembro);
		
		for(Prestamo p : prestamos) {
			//saldoTotal = saldoTotal.add(p.getSaldoRestante());
			//calcular saldo real como monto_total - monto_pagado
			BigDecimal Restante = p.getMontoOriginal().subtract(p.getMontoPagado());
			saldoTotal = saldoTotal.add(Restante);
		}
		
		return saldoTotal;
	}
	
	//Este método devuelve el préstamo activo más antiguo para aplicarle el pago
	public static Prestamo obtenerPrestamoMasAntiguo(int idMiembro) {
		List<Prestamo> prestamos = prestamosActivosPorMiembro(idMiembro);
		
		if(prestamos.isEmpty()) return null;
		
		//ordenar por fecha de inicio (mas antiguo)
		prestamos.sort((p1, p2) -> p1.getFechaInicio().compareTo(p2.getFechaInicio()));
		
		return prestamos.get(0); //mas antiguo
	}
	
	//para intereses
	private static Prestamo construirPrestamoDesdeResultSet(ResultSet rs) throws SQLException {
		Prestamo prestamo = new Prestamo();

	    prestamo.setId(rs.getInt("id_prestamo"));
	    prestamo.setMontoOriginal(rs.getBigDecimal("monto_original"));
	    prestamo.setSaldoRestante(rs.getBigDecimal("saldo_restante"));
	    prestamo.setFechaInicio(rs.getDate("fecha_inicio"));
	    prestamo.setIdMiembro(rs.getInt("id_miembro")); // si tienes este campo en Prestamo

	    return prestamo;
	}
	
	public static List<Prestamo> listarPrestamosActivos() {
		List<Prestamo> lista = new ArrayList<>();

		try (Connection conn = Conexion.getConnection()) {
			String sql = "SELECT id_prestamo, id_miembro, monto_original, saldo_restante, fecha_inicio FROM prestamos WHERE estado = 'activo';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Prestamo p = construirPrestamoDesdeResultSet(rs);
				lista.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	//para estado de cuenta
	public static BigDecimal getTotalPrestamos(int idMiembro) {
	    String sql = "SELECT SUM(monto_original) FROM prestamos WHERE id_miembro = ?";
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
	
	public static BigDecimal getSaldoPrestamos(int idMiembro) {
	    String sql = "SELECT SUM(saldo_restante) FROM prestamos WHERE id_miembro = ?";
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
	public int getTotalPrestamos() {
	    int total = 0;
	    try {
	        Connection con = Conexion.getConnection();
	        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM prestamos");
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            total = rs.getInt(1); // obtiene el número total de registros
	        }
	        rs.close();
	        ps.close();
	        con.close();
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al contar préstamos: " + e.getMessage());
	    }
	    return total;
	}

}
