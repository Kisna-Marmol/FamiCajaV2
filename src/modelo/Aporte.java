package modelo;

import java.math.BigDecimal;
import java.sql.Date;

public class Aporte {
	private int id;
	private int idMiembro;
	private String nombreMiembro;
	private BigDecimal monto;
	private Date fecha_aporte;
	
	public Aporte() {}
	
	public Aporte(int idMiembro, BigDecimal monto, Date fecha) { 
		this.idMiembro = idMiembro;
		this.monto = monto;
		this.fecha_aporte = fecha;
	}
	
	public Aporte(int id, int idMiembro, BigDecimal monto, Date fecha) { 
		this.id = id;
		this.idMiembro = idMiembro;
		this.monto = monto;
		this.fecha_aporte = fecha;
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

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Date getFecha_aporte() {
		return fecha_aporte;
	}

	public void setFecha_aporte(Date fecha_aporte) {
		this.fecha_aporte = fecha_aporte;
	}

	@Override
	public String toString() {
		return "Aporte [id=" + id + ", idMiembro=" + idMiembro + ", monto=" + monto + ", fecha_aporte=" + fecha_aporte
				+ "]";
	}
}
