package es.ujaen.dae.practicaeventos.bean;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
	long token;
	
	Hashtable<Long, String> usuariosActivos;
	Map<String, Usuario> usuarios;
	Map<Integer, Evento> eventos;
	
	public OrganizadoraEventosImp() {
		usuarios = new TreeMap<>();
		eventos = new TreeMap<>();
		usuariosActivos = new Hashtable<>();
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
	
	public long identificarUsuario(String dni, String password) {
		Usuario usuario;
		if(usuarios.containsKey(dni)) {
			usuario = usuarios.get(dni);
			if ( usuario.getPassword() == password) {
				 token = generarToken();
				 usuariosActivos.put(token, dni);
				 return token;
			}
		}
		return 0;
	}
	
	public void crearEvento(Evento evento, long token) {
		if(validarToken(token)) {
			evento.setOrganizador(usuarios.get(usuariosActivos.get(token)));
			eventos.put(evento.getId(), evento);
		}
	}
	
	public List<Evento> buscarEvento(String attr) {
		ArrayList<Evento> eventosBuscados = new ArrayList<>();
		
		for(Evento evento : eventos.values()) {
			if(evento.getTipo().toLowerCase().equals(attr)||evento.getDescripcion().toLowerCase().contains(attr.toLowerCase()))
				eventosBuscados.add(evento);
		}return eventosBuscados;
	}
	
	public void cancelarEvento(String id, long token) {
		
	}

	public void obtenerUsuarios() {
		for(Usuario usuario : usuarios.values()) {
			System.out.println(usuario.toString());
		}
	}
	
	public void obtenerEventos() {
		for(Evento evento : eventos.values()) {
			System.out.println(evento.toString());
		}
	}
	
	private long generarToken() {
		Long tok;
		Random random = new Random();
		tok=random.nextLong();
		if(tok<0) {
			return tok*-1;
		}
		return tok;
	}

	private boolean validarToken(long token) {
		if(usuariosActivos.containsKey(token))
			return true;
		return false;
	}
}
