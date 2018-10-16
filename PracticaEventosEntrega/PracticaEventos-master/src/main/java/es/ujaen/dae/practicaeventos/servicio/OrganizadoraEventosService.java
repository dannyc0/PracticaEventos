package es.ujaen.dae.practicaeventos.servicio;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import es.ujaen.dae.practicaeventos.dto.EventoDTO;
import es.ujaen.dae.practicaeventos.dto.UsuarioDTO;
import es.ujaen.dae.practicaeventos.exception.CamposVaciosException;
import es.ujaen.dae.practicaeventos.exception.CancelacionInvalidaException;
import es.ujaen.dae.practicaeventos.exception.FechaInvalidaException;
import es.ujaen.dae.practicaeventos.exception.InscripcionInvalidaException;
import es.ujaen.dae.practicaeventos.exception.SesionNoIniciadaException;
import es.ujaen.dae.practicaeventos.exception.UsuarioNoRegistradoNoEncontradoException;

public interface OrganizadoraEventosService {

    //mostrar usuarios
    //public void obtenerUsuarios();
    //public void obtenerEventos();
    /////////////////////////
    public void registrarUsuario(UsuarioDTO usuarioDTO, String password) throws CamposVaciosException;//Probado

    public String identificarUsuario(String dni, String password) throws UsuarioNoRegistradoNoEncontradoException, CamposVaciosException;//Probado

    public boolean cerrarSesion(long token);//Probado

    public void crearEvento(EventoDTO eventoDTO, long token) throws CamposVaciosException, SesionNoIniciadaException, FechaInvalidaException          ;//Probado

    public void inscribirEvento(EventoDTO eventoDTO, long token) throws InscripcionInvalidaException, SesionNoIniciadaException;//Probado

    public void cancelarInscripcion(EventoDTO eventoDTO, long token) throws CancelacionInvalidaException, SesionNoIniciadaException, UsuarioNoRegistradoNoEncontradoException;//Probado

    public List<EventoDTO> buscarEvento(String attr);//Probado

    public void cancelarEvento(EventoDTO eventoDTO, long token) throws CancelacionInvalidaException, SesionNoIniciadaException;//Probado

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
