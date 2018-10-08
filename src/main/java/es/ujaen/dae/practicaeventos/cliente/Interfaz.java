package es.ujaen.dae.practicaeventos.cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Interfaz {
	//private final static ArrayList<String> menuOpcionesPrincipal = (ArrayList<String>) Arrays.asList("Registrar usuario", "Login","Eventos");
	private Map<Integer,String> opciones;
	
	public Interfaz() {
		opciones = new TreeMap<>();
	}
	
	public void imprimirOpciones(String tipo) {
		cargarOpciones(tipo);
		for(String opcion : opciones.values()) {
			System.out.println(opcion);
		}
	}
	
	private void cargarOpciones(String tipo) {
		if(tipo.equals("principal-no-loggeado")) {
			opciones.clear();
			opciones.put(1, "1. Registrarse");
			opciones.put(2, "2. Iniciar sesión");
			opciones.put(3, "2. Eventos");
		}else if(tipo.equals("principal-loggeado")) {
			opciones.clear();
			opciones.put(1, "1. Eventos");
			opciones.put(2, "2. Cerrar sesión");
		}
			
	}
	
}
