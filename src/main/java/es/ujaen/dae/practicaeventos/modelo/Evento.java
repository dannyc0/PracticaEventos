package es.ujaen.dae.practicaeventos.modelo;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Evento {
	int id;
	String nombre;
	String descripcion;
	String lugar;
	String fecha;
	String tipo;
	int cupo;
	
	Usuario organizador;
	List<Usuario> listaEspera;
	Map<String, Usuario> listaInvitados;
	
	public Evento() {
		
	}
	
	public Evento(String nombre, String descripcion, String lugar, String fecha, String tipo, int cupo,Usuario organizador) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.lugar = lugar;
		this.fecha = fecha;
		this.tipo = tipo;
		this.cupo = cupo;
		this.organizador = organizador;
	}

	public int getId() {
		return id;
	}
	
	public Usuario getOrganizador() {
		return organizador;
	}

	public void setOrganizador(Usuario organizador) {
		this.organizador = organizador;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getCupo() {
		return cupo;
	}
	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", lugar=" + lugar
				+ ", fecha=" + fecha + ", tipo=" + tipo + ", cupo=" + cupo + ", organizador=" + organizador
				+ ", listaEspera=" + listaEspera + ", listaInvitados=" + listaInvitados + "]";
	}
	
}
