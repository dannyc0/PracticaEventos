package es.ujaen.dae.practicaeventos.servicio;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import es.ujaen.dae.practicaeventos.modelo.Evento;
import es.ujaen.dae.practicaeventos.modelo.Usuario;

public interface OrganizadoraEventosService {
	
	//mostrar usuarios
	public void obtenerUsuarios();
	public void registrarUsuario(Usuario usuario);
	public Usuario identificarUsuario(String dni);
	public void crearEvento(Evento evento);
	public List<Evento> buscarEvento(String attr);
}
