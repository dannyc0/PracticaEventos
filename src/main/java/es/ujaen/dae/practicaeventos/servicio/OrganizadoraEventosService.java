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
	public String registrarUsuario(UsuarioDTO usuarioDTO, String password);
	public long identificarUsuario(String dni, String password);
	public String crearEvento(EventoDTO eventoDTO, long token);
	public String inscribirEvento(EventoDTO eventoDTO, long token);
	public String cancelarInscripcion(EventoDTO eventoDTO, long token);
	public List<EventoDTO> buscarEvento(String attr);
	public String cancelarEvento(EventoDTO eventoDTO,long token);
	public List<EventoDTO> listarEventoInscritoCelebrado(long token);
	public List<EventoDTO> listarEventoInscritoPorCelebrar(long token);
	public List<EventoDTO> listarEventoEsperaPorCelebrar(long token);
	public List<EventoDTO> listarEventoOrganizadoCelebrado(long token);
	public List<EventoDTO> listarEventoOrganizadoPorCelebrar(long token);
}
