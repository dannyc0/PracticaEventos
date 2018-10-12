package es.ujaen.dae.practicaeventos.bean;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ujaen.dae.practicaeventos.dto.EventoDTO;
import es.ujaen.dae.practicaeventos.dto.UsuarioDTO;
import es.ujaen.dae.practicaeventos.modelo.Evento;
import es.ujaen.dae.practicaeventos.modelo.Usuario;
import es.ujaen.dae.practicaeventos.servicio.OrganizadoraEventosService;

@Component
public class OrganizadoraEventosImp implements OrganizadoraEventosService{
	String cif;
	String nombre;
	boolean isLogeado;
	long token;
	
	Hashtable<Long, String> usuariosTokens;
	Map<String, Usuario> usuarios;
	Map<Integer, Evento> eventos;
	
	public OrganizadoraEventosImp() {
		usuarios = new TreeMap<>();
		eventos = new TreeMap<>();
		usuariosTokens = new Hashtable<>();
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
	
	public String registrarUsuario(UsuarioDTO usuarioDTO) {
		String mensaje = "";
		Usuario usuario = usuarioDTO.toEntity();
		if(usuario.getDni()!=null&&usuario.getPassword()!=null&&usuario.getNombre()!=null) {
			mensaje = "Usuario registrado";
			usuarios.put(usuario.getDni(), usuario);
		}else
			mensaje = "No se ha registrado el usuario. El DNI, nombre y password son campos obligatorios.";
		return mensaje;
	}
	
	public long identificarUsuario(String dni, String password) {
		Usuario usuario;
		long respuesta = 0;
		if(dni!=null&&password!=null) {
			if(usuarios.containsKey(dni)) {
				usuario = usuarios.get(dni);
				if ( usuario.getPassword() == password) {
					 token = generarToken();
					 respuesta = token;
					 usuariosTokens.put(token, dni);
				}else {
					respuesta = 2; //contraseña incorrecta
				}
			}else {
				respuesta = 1; //no registrado
			}
		}else {
			respuesta = 0;//no ingreso datos
		}return respuesta;
	}
	
	public String crearEvento(EventoDTO eventoDTO, long token) {
		Evento evento = eventoDTO.toEntity();
		String mensaje = "";
		if(validarToken(token)) {
			evento.setOrganizador(usuarios.get(usuariosTokens.get(token)));
			eventos.put(evento.getId(), evento);
			mensaje = "Evento creado";
		}else {
			mensaje = "Debe iniciar sesión";
		}
		return mensaje;
	}
	
	public List<EventoDTO> buscarEvento(String attr) {
		ArrayList<EventoDTO> eventosBuscados = new ArrayList<>();
		
		for(Evento evento : eventos.values()) {
			if(evento.getTipo().toLowerCase().equals(attr)||evento.getDescripcion().toLowerCase().contains(attr.toLowerCase()))
				eventosBuscados.add(new EventoDTO(evento));
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
		if(usuariosTokens.containsKey(token))
			return true;
		return false;
	}
}
