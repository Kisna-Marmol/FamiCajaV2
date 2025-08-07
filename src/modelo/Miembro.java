package modelo;

import java.sql.Date;

public class Miembro {
	private int id;
	private String nombre_completo;
	private boolean activo;
	private Date fecha_ingreso;
	
	public Miembro() {}
	
	public Miembro (String nombre, boolean estado, Date fecha) {
		this.nombre_completo = nombre;
		this.activo = estado;
		this.fecha_ingreso = fecha;
	}
	
	public Miembro (int id,String nombre, boolean estado, Date fecha) {
		this.id = id;
		this.nombre_completo = nombre;
		this.activo = estado;
		this.fecha_ingreso = fecha;
	}

	public Miembro(int id, String nombre) {
		this.id = id;
		this.nombre_completo = nombre;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public boolean isEstado() {
		return activo;
	}

	public void setEstado(boolean estado) {
		this.activo = estado;
	}

	public Date getFecha_ingreso() {
		return fecha_ingreso;
	}

	public void setFecha_ingreso(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	@Override
	/*public String toString() {
		return "Miembro [id=" + id + ", nombre_completo=" + nombre_completo + ", estado=" + activo + ", fecha_ingreso="
				+ fecha_ingreso + "]";
	}*/
	
	public String toString() {
		return id +". "+ nombre_completo;
	}
	
	//metodos
	/*public boolean puedeSolicitarPrestamo() {
		return activo;
	}*/
}
