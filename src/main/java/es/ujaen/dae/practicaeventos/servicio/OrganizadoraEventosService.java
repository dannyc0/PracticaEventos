package es.ujaen.dae.practicaeventos.servicio;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import es.ujaen.dae.practicaeventos.modelo.Evento;
import es.ujaen.dae.practicaeventos.modelo.Usuario;

public interface OrganizadoraEventosService {
	
	//mostrar usuarios
	public void obtenerUsuarios();
	public void obtenerEventos();
	
	/////////////////////////
	public void registrarUsuario(Usuario usuario);
	public long identificarUsuario(String dni, String password);
	public void crearEvento(Evento evento, long token);
	public List<Evento> buscarEvento(String attr);
	public void cancelarEvento(String id,long token);
	//public void inscribirAEvento(String id,long token);
}
