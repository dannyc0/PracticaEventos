package es.ujaen.dae.practicaeventos.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ujaen.dae.practicaeventos.modelo.Evento;
import es.ujaen.dae.practicaeventos.modelo.Usuario;
import es.ujaen.dae.practicaeventos.servicio.OrganizadoraEventosService;

@Component
public class OrganizadoraEventosImp implements OrganizadoraEventosService{
	String cif;
	String nombre;
	boolean isLogeado;
	
	Map<String, Usuario> usuarios;
	Map<Integer, Evento> eventos;
	
	public OrganizadoraEventosImp() {
		usuarios = new TreeMap<>();
		eventos = new TreeMap<>();
	}
	
	public OrganizadoraEventosImp(String cif, String nombre, Map<String, Usuario> usuarios, Map<Integer, Evento> eventos) {
		this.cif = cif;
		this.nombre = nombre;
		this.usuarios = usuarios;
		this.eventos = eventos;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public void registrarUsuario(Usuario usuario) {
		usuarios.put(usuario.getDni(), usuario);
	}
	
	public Usuario identificarUsuario(String dni) {
		return usuarios.get(dni);
	}
	
	public void crearEvento(Evento evento) {
		eventos.put(evento.getId(), evento);
	}
	
	public List<Evento> buscarEvento(String attr) {
		ArrayList<Evento> eventosBuscados = new ArrayList<>();
		
		for(Evento evento : eventos.values()) {
			if(evento.getTipo().equals(attr)||evento.getDescripcion().toLowerCase().contains(attr.toLowerCase()))
				eventosBuscados.add(evento);
		}
		
		return eventosBuscados;
	}

	public void obtenerUsuarios() {
		for(Usuario usuario : usuarios.values()) {
			System.out.println(usuario.getDni());
		}
		
	}	
	
}
