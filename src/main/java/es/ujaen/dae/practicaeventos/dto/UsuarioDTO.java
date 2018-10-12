package es.ujaen.dae.practicaeventos.dto;

import java.util.Map;

import es.ujaen.dae.practicaeventos.modelo.Evento;
import es.ujaen.dae.practicaeventos.modelo.Usuario;

public class UsuarioDTO {
	String dni;
	String nombre;
	String password;
	String correo;
	String telefono;
	EventoDTO eventoDTO;
	
	public UsuarioDTO() {
		
	}

	public UsuarioDTO(String dni, String nombre, String correo, String telefono, EventoDTO eventoDTO) {
		this.dni = dni;
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
		this.eventoDTO = eventoDTO;
	}
	
	public UsuarioDTO(String dni, String nombre, String correo, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
	}
	
	public UsuarioDTO(String dni, String nombre, String password, String correo, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.password = password;
		this.correo = correo;
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario toEntity() {
        Usuario usuario = new Usuario();
        if(this.eventoDTO!=null) {
        	Evento evento = new Evento();
		    evento.setId(this.eventoDTO.getId());
		    evento.setNombre(this.eventoDTO.getNombre());
		    evento.setDescripcion(this.eventoDTO.getDescripcion());
		    evento.setLugar(this.eventoDTO.getLugar());
		    evento.setFecha(this.eventoDTO.getFecha());
		    evento.setTipo(this.eventoDTO.getTipo());
		    evento.setCupo(this.eventoDTO.getCupo());
        
        }
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setPassword(password);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        return usuario;
	}
}
