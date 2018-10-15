package es.ujaen.dae.practicaeventos.servicio;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import es.ujaen.dae.practicaeventos.dto.EventoDTO;
import es.ujaen.dae.practicaeventos.dto.UsuarioDTO;

public interface OrganizadoraEventosService {
	
	//mostrar usuarios
	//public void obtenerUsuarios();
	//public void obtenerEventos();
	
	/////////////////////////
	public String registrarUsuario(UsuarioDTO usuarioDTO, String password);//Probado
	public long identificarUsuario(String dni, String password);//Probado
	public boolean cerrarSesion(long token);//Probado
	public String crearEvento(EventoDTO eventoDTO, long token);//Probado
	public String inscribirEvento(EventoDTO eventoDTO, long token);//Probado
	public String cancelarInscripcion(EventoDTO eventoDTO, long token);//Probado
	public List<EventoDTO> buscarEvento(String attr);//Probado
	public String cancelarEvento(EventoDTO eventoDTO,long token);//Probado
	public List<EventoDTO> listarEventoInscritoCelebrado(long token);//Probado
	public List<EventoDTO> listarEventoInscritoPorCelebrar(long token);//Probado
	public List<EventoDTO> listarEventoEsperaPorCelebrar(long token); //Probado
	public List<EventoDTO> listarEventoEsperaCelebrado(long token); //Probado
	public List<EventoDTO> listarEventoOrganizadoCelebrado(long token);//Probado
	public List<EventoDTO> listarEventoOrganizadoPorCelebrar(long token);//Probado
	
	/*
	 * Corregido:
	 * El metodo validar fecha no funcionaba correctamente, la lógica estaba mal
	 * Cancelar inscripcion no quitaba el evento de la lista contenida en usuario
	 * Cancelar evento no estaba definido correctamente
	 * 
	 * Añadido:
	 * No existia cerrar sesion
	 * Para cancelar inscripcion, no tiene que haberse celebrado aun
	 * Para cancelar evento, no tiene que haberse celebrado aun
	 * Para inscribirse, no tiene que haberse celebrado aun
	 * Para inscribirse, no tiene que estar inscrito previamente
	 * 
	 * 
	 * Preguntar al profesor:
	 * Si el token debe ser solicitado al cliente cada vez que haga una transaccion o es interno
	 * Si el id del evento lo ingresa el cliente
	 * Si solo puede haber una sesion abierta a la vez
	 * Si el organizador puede inscribirse al evento que organizó
	 * Si se debe validar por ejemplo DNI, fecha en formato correcto, etc
	 * 
	 * */
}
