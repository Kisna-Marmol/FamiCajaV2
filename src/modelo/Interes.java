package modelo;

import java.math.BigDecimal;
import java.sql.Date;

public class Interes {
	private int idIntereses;
	private Prestamo prestamo;
	private BigDecimal montoInteres;
	private Date fechaGeneracion;
	private boolean pagado;
	private String descripcion;
	
	public Interes() {}
	
	public Interes(int idIntereses, String descripcion) {
		this.idIntereses = idIntereses;
		this.descripcion = descripcion;
	}

	/*public Interes(int idIntereses, Prestamo prestamo, BigDecimal montoInteres, Date fechaGeneracion, boolean pagado) {
		super();
		this.idIntereses = idIntereses;
		this.prestamo = prestamo;
		this.montoInteres = montoInteres;
		this.fechaGeneracion = fechaGeneracion;
		this.pagado = pagado;
	}*/
	
	public Interes(Prestamo prestamo, BigDecimal montoInteres, Date fechaGeneracion, boolean pagado) {
		this.prestamo = prestamo;
		this.montoInteres = montoInteres;
		this.fechaGeneracion = fechaGeneracion;
		this.pagado = pagado;
	}

	public int getIdIntereses() {
		return idIntereses;
	}

	public void setIdIntereses(int idIntereses) {
		this.idIntereses = idIntereses;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public BigDecimal getMontoInteres() {
		return montoInteres;
	}

	public void setMontoInteres(BigDecimal montoInteres) {
		this.montoInteres = montoInteres;
	}

	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	@Override
	/*public String toString() {
		return "Intereses [idIntereses=" + idIntereses + ", prestamo=" + prestamo + ", montoInteres=" + montoInteres
				+ ", fechaGeneracion=" + fechaGeneracion + ", pagado=" + pagado + "]";
	}*/
	
	public String toString() {
		if (descripcion != null) {
	        return descripcion;
	    }
	    return montoInteres != null ? montoInteres + " Lps" : "Interés";
	}
	
	//metodos
	public boolean esPagado() {
		return pagado;
	}
	
	public void marcarComoPagado() {
		this.pagado = true;
	}
}
