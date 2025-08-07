package modelo;

public class Usuario {
	private int idUsuario;
	private String nombreUsuario;
	private String password;
	private String rol;
	
	public Usuario() {}
	
	public Usuario(int id, String nombreUsuario, String password, String rol) {
		this.idUsuario = id;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.rol = rol;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", password=" + password
				+ ", rol=" + rol + "]";
	}
}
