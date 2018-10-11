package es.ujaen.dae.practicaeventos.cliente;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;

import es.ujaen.dae.practicaeventos.bean.OrganizadoraEventosImp;
import es.ujaen.dae.practicaeventos.modelo.Evento;
import es.ujaen.dae.practicaeventos.modelo.Usuario;
import es.ujaen.dae.practicaeventos.servicio.OrganizadoraEventosService;

public class ClienteOrganizadoraEventos {
	ApplicationContext ctx;

	public ClienteOrganizadoraEventos(ApplicationContext context) {
		this.ctx = context;
	}
	
	public void run() {
		OrganizadoraEventosService organizadoraEventos = (OrganizadoraEventosService) ctx.getBean("organizadoraEventosImp");
		Scanner sc = new Scanner(System.in);
		Interfaz interfaz = new Interfaz();
		int opcion;
		
		System.out.println("**Organizadora de eventos**");
		interfaz.imprimirOpciones("principal-no-loggeado");
		
		System.out.print("Ingrese el número de su elección: ");
		while (!sc.hasNextInt()) sc.next();
		opcion = sc.nextInt();
		
		if(opcion==1){
			String dni = "13231232Q";
			String password = "sadafe32";
			long token; 
			Usuario usuario = new Usuario(dni,"Juan Perez","jhony@gmail.com","+521551591058",password);
			organizadoraEventos.registrarUsuario(usuario);
			token = organizadoraEventos.identificarUsuario(dni, password);
			System.out.println("TOKEN: "+token);
			//token = 2556556;
			Evento evento = new Evento("Evento de prueba", "Descripción de este evento", "España", "10-05-2018", "Fiesta infantil", 20);
			organizadoraEventos.crearEvento(evento, token);
		}
		
		organizadoraEventos.obtenerUsuarios();
		organizadoraEventos.obtenerEventos();
		
		//System.out.println("Organizadora:"+organizadoraEventos.getCif());
		//Logica y llamada de metodos
	}

}
