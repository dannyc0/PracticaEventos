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
			Usuario usuario = new Usuario("1323123211Q","Juan Perez","jhony@gmail.com","+521551591058","jhony32","sadafe32");
			organizadoraEventos.registrarUsuario(usuario);
		}
		
		organizadoraEventos.obtenerUsuarios();
		
		//System.out.println("Organizadora:"+organizadoraEventos.getCif());
		//Logica y llamada de metodos
	}

}
