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
	
//	public OrganizadoraEventosImp(String cif, String nombre, Map<String, Usuario> usuarios, Map<Integer, Evento> eventos) {
//		this.cif = cif;
//		this.nombre = nombre;
//		this.usuarios = usuarios;
//		this.eventos = eventos;
//		
//	}

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
	
	public String registrarUsuario(UsuarioDTO usuarioDTO, String password) {
		String mensaje = "";
		Usuario usuario = usuarioDTO.toEntity();
		if(usuario.getDni()!=null&&password!=null&&usuario.getNombre()!=null) {
			mensaje = "Usuario registrado";
			usuario.setPassword(password);
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
			if(evento.getId()!=0&&evento.getNombre()!=null&&evento.getDescripcion()!=null&&evento.getFecha()!=null&&evento.getLugar()!=null&&evento.getCupo()!=0) {
				eventos.put(evento.getId(), evento);
				
				System.out.println(usuarios.get(usuariosTokens.get(token)).getDni());
				usuarios.get(usuariosTokens.get(token)).eventosOrganizados.put(evento.getId(), evento);
				//System.out.println("ORGANIZADOR "+usuario);
				
				mensaje = "Evento creado";
			}else {
				mensaje = "Campos incompletos";
			}
		}else {
			mensaje = "Debe iniciar sesión";
		}
		return mensaje;
	}
	
	public String inscribirEvento(EventoDTO eventoDTO, long token) {
		String mensaje = "";
		
		if(validarToken(token)) {
			Evento evento = eventoDTO.toEntity();
			evento = eventos.get(evento.getId());
			
			if(evento.listaInvitados.size()<evento.getCupo()) {
				evento.listaInvitados.put(usuariosTokens.get(token),usuarios.get(usuariosTokens.get(token)) );
				
				usuarios.get(usuariosTokens.get(token)).eventosInvitado.put(evento.getId(), evento);

				mensaje = "Te has inscrito exitosamente";
			}else {
				evento.listaEspera.add(usuarios.get(usuariosTokens.get(token)) );
				usuarios.get(usuariosTokens.get(token)).eventosEspera.put(evento.getId(), evento);

				mensaje = "Estas en lista de espera";
			}
			
		}else{
			mensaje = "Debe iniciar sesión";
		}
		return mensaje;
	}
	
	public String cancelarInscripcion(EventoDTO eventoDTO, long token) {
		String mensaje = "";
		
		if(validarToken(token)) {
			Evento evento = eventoDTO.toEntity();
			evento = eventos.get(evento.getId());
			
			if(evento.listaInvitados.containsKey(usuariosTokens.get(token))) {
				evento.listaInvitados.remove(usuariosTokens.get(token));
				evento.listaInvitados.put(evento.listaEspera.get(0).getDni(), evento.listaEspera.get(0));
				
				usuarios.get(usuariosTokens.get(token)).eventosInvitado.remove(evento);
				usuarios.get(evento.listaEspera.get(0).getDni()).eventosInvitado.put(evento.getId(), evento);
				usuarios.get(evento.listaEspera.get(0).getDni()).eventosEspera.remove(evento.getId(), evento);

				evento.listaEspera.remove(0);
				
				mensaje = "Has cancelado tu inscripcion exitosamente";
			}else {
				mensaje = "No estas en la lista de invitados de este evento";
			}
		}else{
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
	
	public String cancelarEvento(EventoDTO eventoDTO, long token) {
		String mensaje = "";
		Evento evento = eventoDTO.toEntity();
		if(validarToken(token)) {
			if(evento.getOrganizador().getDni().equals(usuariosTokens.get(token))) {
				eventos.remove(evento.getId());
			}
		}
		return "";
	}
	
	public List<EventoDTO> listarEventoInscritoCelebrado(long token) {
		List<EventoDTO> eventosInscritosCelebrados = new ArrayList<EventoDTO>(); 
		if(validarToken(token)) {
			Usuario usuario= usuarios.get(usuariosTokens.get(token));
			for (Evento evento : usuario.eventosInvitado.values()) {
				if(!evento.validarFecha()) {
					eventosInscritosCelebrados.add(new EventoDTO(evento));
				}
			}
		}
		return eventosInscritosCelebrados;
	}

	public List<EventoDTO> listarEventoInscritoPorCelebrar(long token) {
		List<EventoDTO> eventosInscritosPorCelebrar = new ArrayList<EventoDTO>(); 
		if(validarToken(token)) {
			Usuario usuario= usuarios.get(usuariosTokens.get(token));
			for (Evento evento : usuario.eventosInvitado.values()) {
				if(evento.validarFecha()) {
					eventosInscritosPorCelebrar.add(new EventoDTO(evento));
				}
			}
		}
		return eventosInscritosPorCelebrar;
	}

	public List<EventoDTO> listarEventoEsperaPorCelebrar(long token) {
		List<EventoDTO> eventosEsperaPorCelebrar = new ArrayList<EventoDTO>(); 
		if(validarToken(token)) {
			Usuario usuario= usuarios.get(usuariosTokens.get(token));
			for (Evento evento : usuario.eventosEspera.values()) {
				if(evento.validarFecha()) {
					eventosEsperaPorCelebrar.add(new EventoDTO(evento));
				}
			}
		}
		return eventosEsperaPorCelebrar;
	}

	public List<EventoDTO> listarEventoOrganizadoCelebrado(long token) {
		List<EventoDTO> eventosOrganizadosCelebrados = new ArrayList<EventoDTO>(); 
		if(validarToken(token)) {
			Usuario usuario= usuarios.get(usuariosTokens.get(token));
			for (Evento evento : usuario.eventosOrganizados.values()) {
				if(!evento.validarFecha()) {
					eventosOrganizadosCelebrados.add(new EventoDTO(evento));
				}
			}
		}
		return eventosOrganizadosCelebrados;
	}
	
	public List<EventoDTO> listarEventoOrganizadoPorCelebrar(long token) {
		List<EventoDTO> eventosOrganizadosPorCelebrar = new ArrayList<EventoDTO>(); 
		if(validarToken(token)) {
			Usuario usuario= usuarios.get(usuariosTokens.get(token));
			for (Evento evento : usuario.eventosOrganizados.values()) {
				if(evento.validarFecha()) {
					eventosOrganizadosPorCelebrar.add(new EventoDTO(evento));
				}
			}
		}
		return eventosOrganizadosPorCelebrar;
	}

	public void obtenerUsuarios() {
		for(Usuario usuario : usuarios.values()) {
			System.out.println(usuario.toString());
		}
	}
	
	public void obtenerEventos() {
		for(Evento evento : eventos.values()) {
		//	System.out.println(evento.getOrganizador());
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
