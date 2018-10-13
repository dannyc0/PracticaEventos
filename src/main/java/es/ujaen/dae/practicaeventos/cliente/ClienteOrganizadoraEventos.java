package es.ujaen.dae.practicaeventos.cliente;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;

import es.ujaen.dae.practicaeventos.bean.OrganizadoraEventosImp;
import es.ujaen.dae.practicaeventos.dto.EventoDTO;
import es.ujaen.dae.practicaeventos.dto.UsuarioDTO;
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
			String dni = "13233232Q";
			String password = "sfsdafe32";
			String dni2 = "13298232Q";
			String password2 = "sadafe32fff";
			String dni3 = "13211232Q";
			String password3 = "sadafe3asad2";
			long token; 
			UsuarioDTO usuario = new UsuarioDTO(dni,"Juan Perez","jhony@gmail.com","+521551591058");
			UsuarioDTO usuario2 = new UsuarioDTO(dni2,"Carlos Perez","jhony@gmail.com","+521551591058");
			UsuarioDTO usuario3 = new UsuarioDTO(dni3,"Luis Perez","jhony@gmail.com","+521551591058");
			
			organizadoraEventos.registrarUsuario(usuario,password);
			organizadoraEventos.registrarUsuario(usuario2,password2);
			organizadoraEventos.registrarUsuario(usuario3,password3);
			
			token = organizadoraEventos.identificarUsuario(dni, password);
			
			System.out.println("TOKEN: "+token);
			
			EventoDTO evento = new EventoDTO(1,"Evento de prueba", "Descripción de este evento", "España", "10-05-2018", "Fiesta infantil", 2);
			EventoDTO evento2 = new EventoDTO(2,"Evento de prueba 2", "Descripción de este evento", "España", "10-05-2019", "Fiesta infantil", 2);
			
			organizadoraEventos.crearEvento(evento, token);
			organizadoraEventos.crearEvento(evento2, token);
		}
		
		//organizadoraEventos.obtenerUsuarios();
		organizadoraEventos.obtenerEventos();
//		
		//System.out.println("Organizadora:"+organizadoraEventos.getCif());
		//Logica y llamada de metodos
	}

}
