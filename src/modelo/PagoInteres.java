package modelo;

import java.math.BigDecimal;
import java.sql.Date;

public class PagoInteres {
	private int idPagoIntereses;
	private int idIntereses;
	private BigDecimal montoPagado;
	private Date fechaPago;
	
	public PagoInteres() {}

	/*public PagoInteres(int idPagoIntereses, int idIntereses, BigDecimal montoPagado, Date fechaPago) {
		super();
		this.idPagoIntereses = idPagoIntereses;
		this.idIntereses = idIntereses;
		this.montoPagado = montoPagado;
		this.fechaPago = fechaPago;
	}*/
	public PagoInteres(int idIntereses, BigDecimal montoPagado, Date fechaPago) {
		super();
		this.idIntereses = idIntereses;
		this.montoPagado = montoPagado;
		this.fechaPago = fechaPago;
	}

	public int getIdPagoIntereses() {
		return idPagoIntereses;
	}

	public void setIdPagoIntereses(int idPagoIntereses) {
		this.idPagoIntereses = idPagoIntereses;
	}

	public int getIdIntereses() {
		return idIntereses;
	}

	public void setIdIntereses(int idIntereses) {
		this.idIntereses = idIntereses;
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
		return "PagoIntereses [idPagoIntereses=" + idPagoIntereses + ", idIntereses=" + idIntereses + ", montoPagado="
				+ montoPagado + ", fechaPago=" + fechaPago + "]";
	}
}
