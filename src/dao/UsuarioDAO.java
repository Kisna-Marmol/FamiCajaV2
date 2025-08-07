package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import modelo.Usuario;
import sql.Conexion;
public class UsuarioDAO {
	public static Usuario login(String nombreUsuario, String password) {
		Usuario usuario = null;
		try {
			Connection con = Conexion.getConnection();
			if(con == null) {
				throw new SQLException("No se pudo establecer conexión con la base de datos.");
			}
			String sql = "SELECT * FROM usuarios WHERE user = ? AND password = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nombreUsuario);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setIdUsuario(rs.getInt("id_usuarios"));
				usuario.setNombreUsuario(rs.getString("user"));
				usuario.setPassword(rs.getString("password"));
				usuario.setRol(rs.getString("rol"));
			}
			
			rs.close();
			ps.close();
			con.close();
		}catch (Exception e) {
			 JOptionPane.showMessageDialog(null, "Error en el login: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return usuario;
	}
}
