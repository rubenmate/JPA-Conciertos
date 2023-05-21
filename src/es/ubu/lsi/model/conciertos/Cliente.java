package es.ubu.lsi.model.conciertos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the CLIENTE database table.
 * 
 */
@Entity
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nif;

	private String apellidos;

	// Se usa un tipo embedded para la direccion
	@Embedded
	private Direccion direccion;

	private String nombre;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="cliente")
	private Set<Compra> compras;

	public Cliente() {
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(Set<Compra> compras) {
		this.compras = compras;
	}

	@Override
	public String toString() {
		return "Cliente [nif=" + nif + ", nombre=" + nombre + ", apellidos=" + apellidos + ", direccionPostal=DireccionPostal [direccion=" + direccion.getDireccion() + ", codigoPostal=" + direccion.getCiudad() + ", ciudad=" + direccion.getCp() + "]]";
	}
}