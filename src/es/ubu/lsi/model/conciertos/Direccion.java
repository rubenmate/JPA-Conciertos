package es.ubu.lsi.model.conciertos;

import javax.persistence.*;

/**
 * Clase embedded Direccion, relativa a la direccion de un cliente
 *
 */
@Embeddable
public class Direccion {

	private String direccion;
	private String ciudad;
	private String cp;

	public String getDireccion() {
		return direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getCp() {
		return cp;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}
}