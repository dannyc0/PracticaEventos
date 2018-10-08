package es.ujaen.dae.practicaeventos.modelo;

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
	Queue<Usuario> listaEspera;
	Map<Integer, Usuario> listaInvitados;
	
	public Evento() {}
	
	public Evento(int id, String nombre, String descripcion, String lugar, String fecha, String tipo, int cupo,
			Usuario organizador, Queue<Usuario> listaEspera, Map<Integer, Usuario> listaInvitados) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.lugar = lugar;
		this.fecha = fecha;
		this.tipo = tipo;
		this.cupo = cupo;
		this.organizador = organizador;
		this.listaEspera = listaEspera;
		this.listaInvitados = listaInvitados;
	}

	public int getId() {
		return id;
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
	
}
