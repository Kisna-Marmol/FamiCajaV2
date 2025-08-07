package modelo;

import java.math.BigDecimal;
import java.sql.Date;

public class PagoPrestamo {
	private int idPagoPrestamo;
	private Prestamo prestamo;
	private BigDecimal montoPagado;
	private Date fechaPago;
	
	public PagoPrestamo() {}
	
	public PagoPrestamo(int id, Prestamo prestamo, BigDecimal montoPagado, Date fechaPago) {
		this.idPagoPrestamo = id;
		this.prestamo = prestamo;
		this.montoPagado = montoPagado;
		this.fechaPago = fechaPago;
	}
	
	public PagoPrestamo(Prestamo prestamo, BigDecimal montoPagado, Date fechaPago) {
		this.prestamo = prestamo;
		this.montoPagado = montoPagado;
		this.fechaPago = fechaPago;
	}

	public int getIdPagoPrestamo() {
		return idPagoPrestamo;
	}

	public void setIdPagoPrestamo(int idPagoPrestamo) {
		this.idPagoPrestamo = idPagoPrestamo;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public BigDecimal getMontoPagado() {
		return montoPagado;
	}

	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	@Override
	public String toString() {
		return "PagoPrestamo [id = " + idPagoPrestamo + 
				", id_prestamo = " + prestamo.getId() + 
				", montoPagado = " + montoPagado + 
				", fechaPago=" + fechaPago + "]";
	}
}
