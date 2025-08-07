package dao;

import sql.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import modelo.PagoInteres;

public class PagoInteresDAO {
	
	private Connection con;
	String sql = "";
	
	public PagoInteresDAO () {
		con = Conexion.getConnection();
	}
	
	public boolean registrarPago(PagoInteres pagos) {
		sql = "INSERT INTO pago_intereses (id_intereses, monto_pagado, fecha_pago) VALUES (?, ?, ?)";
		
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, pagos.getIdIntereses());
			stmt.setBigDecimal(2, pagos.getMontoPagado());
			stmt.setDate(3, new Date(pagos.getFechaPago().getTime()));
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al registrar el pago de interes: "+ e.getMessage());
			return false;
		}
	}
	
	//reporte general
	public double getTotalPagado() {
	    double total = 0.0;

	    try {
	        Connection conn = Conexion.getConnection();
	        String sql = "SELECT SUM(monto_pagado) FROM pago_intereses";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            total = rs.getDouble(1);
	        }

	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return total;
	}


}
