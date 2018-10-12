package es.ujaen.dae.practicaeventos.servicio;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import es.ujaen.dae.practicaeventos.dto.EventoDTO;
import es.ujaen.dae.practicaeventos.dto.UsuarioDTO;

public interface OrganizadoraEventosService {
	
	//mostrar usuarios
	public void obtenerUsuarios();
	public void obtenerEventos();
	
	/////////////////////////
	public String registrarUsuario(UsuarioDTO usuarioDTO);
	public long identificarUsuario(String dni, String password);
	public String crearEvento(EventoDTO eventoDTO, long token);
	public List<EventoDTO> buscarEvento(String attr);
	public void cancelarEvento(String id,long token);
	//public void inscribirAEvento(String id,long token);
}
