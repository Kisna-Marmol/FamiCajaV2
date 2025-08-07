package modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import dao.PagoPrestamoDAO;

public class Prestamo {
	private int id;
	private int idMiembro;
	private String nombreMiembro;
	private BigDecimal montoOriginal;
	private BigDecimal saldoRestante;
	private Date fechaInicio;
	private Date fechaFinalizacion;
	private boolean estado;
	
	public Prestamo() {}
	
	public Prestamo(int id, int idMiembro, BigDecimal montoOriginal, BigDecimal saldoRestante, Date fechaInicio, Date fechaFinalizacion, boolean estado) {
		this.id = id;
		this.idMiembro = idMiembro;
		this.montoOriginal = montoOriginal;
		this.saldoRestante = saldoRestante;
		this.fechaInicio = fechaInicio;
		this.fechaFinalizacion = fechaFinalizacion;
		this.estado = estado;
	}
	
	public Prestamo(int idMiembro, BigDecimal montoOriginal, BigDecimal saldoRestante, Date fechaInicio, Date fechaFinalizacion, boolean estado) {
		this.idMiembro = idMiembro;
		this.montoOriginal = montoOriginal;
		this.saldoRestante = saldoRestante;
		this.fechaInicio = fechaInicio;
		this.fechaFinalizacion = fechaFinalizacion;
		this.estado = estado;
	}
	
	public Prestamo(int idMiembro, BigDecimal montoOriginal, Date fechaInicio, boolean estado) {
		this.idMiembro = idMiembro;
		this.montoOriginal = montoOriginal;
		this.fechaInicio = fechaInicio;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdMiembro() {
		return idMiembro;
	}

	public void setIdMiembro(int idMiembro) {
		this.idMiembro = idMiembro;
	}
	
	public String getNombreMiembro() {
		return nombreMiembro;
	}

	public void setNombreMiembro(String nombreMiembro) {
		this.nombreMiembro = nombreMiembro;
	}

	public BigDecimal getMontoOriginal() {
		return montoOriginal;
	}

	public void setMontoOriginal(BigDecimal montoOriginal) {
		this.montoOriginal = montoOriginal;
	}

	public BigDecimal getSaldoRestante() {
		return saldoRestante;
	}

	public void setSaldoRestante(BigDecimal saldoRestante) {
		this.saldoRestante = saldoRestante;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public BigDecimal getMontoPagado() {
		BigDecimal totalPagado = BigDecimal.ZERO;
		List<PagoPrestamo> pagos = PagoPrestamoDAO.listarPorPrestamo(this.id);
		
		for(PagoPrestamo pago : pagos) {
			totalPagado = totalPagado.add(pago.getMontoPagado());
		}
		return totalPagado;
	}
	
	@Override
	public String toString() {
		return "Prestamo [id=" + id + ", idMiembro=" + idMiembro + ", montoOriginal=" + montoOriginal
				+ ", saldoRestante=" + saldoRestante + ", fechaInicio=" + fechaInicio + ", fechaFinalizacion="
				+ fechaFinalizacion + ", estado=" + estado + "]";
	}
}
