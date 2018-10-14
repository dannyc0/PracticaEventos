package es.ujaen.dae.practicaeventos.cliente;

import java.util.ArrayList;
import java.util.List;
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
			
			///////////////////////////////////////////////////////////////////////////////////////////
			
			//***REGISTRAR USUARIO***
			//Nota: Devuelve si se registro o no
			
			//Crea los objetos tipo UsuarioDTO inicializando los campos desde el constructor
			UsuarioDTO usuario = new UsuarioDTO(dni,"Juan Perez","jhony@gmail.com","+521551591058");
			UsuarioDTO usuario3 = new UsuarioDTO(dni3,"Luis Perez","jhony@gmail.com","+521551591058");
			
			//Con constructor vacío
			UsuarioDTO usuario2 = new UsuarioDTO();
			
			//Establece el DNI al UsuarioDTO vacío
			usuario2.setDni(dni2);
			
			//Imprime el resultado de registrar los usuarios
			System.out.println(organizadoraEventos.registrarUsuario(usuario,password));
			System.out.println(organizadoraEventos.registrarUsuario(usuario2,password2));//Error: faltan campos obligatorios
			System.out.println(organizadoraEventos.registrarUsuario(usuario3,password3));
			
			///////////////////////////////////////////////////////////////////////////////////////////
			
			//***IDENTIFICACION***
			//Nota: Devuelve el token al iniciar sesión
			
			//Identificaciones incorrectas
			token = organizadoraEventos.identificarUsuario("", "");//Error: campos vacíos
			System.out.println("TOKEN: "+token);
			token = organizadoraEventos.identificarUsuario("dads3d", password);//Error: no registrado
			System.out.println("TOKEN: "+token);
			token = organizadoraEventos.identificarUsuario(dni, "aaaa");//Error: contraseña incorrecta
			System.out.println("TOKEN: "+token);
			
			//Identificacion correcta
			token = organizadoraEventos.identificarUsuario(dni, password);
			System.out.println("TOKEN: "+token);
			
			/////////////////////////////////////////////////////////////////////////////////////////////
			
			//***CREAR EVENTO***
			//Nota: Devuelve si lo registra o no
			
			//Crea los objetos tipo EventoDTO inicializando los campos desde el constructor
			EventoDTO evento = new EventoDTO(1,"Evento ya celebrado", "Descripción de este evento celebrado", "España", "10-05-2018", "Fiesta infantil", 2);
			EventoDTO evento2 = new EventoDTO(2,"Evento por celebrar", "Descripción de este evento por celebrar", "España", "10-05-2019", "Fiesta infantil", 1);
			
			//Con constructor vacío
			EventoDTO evento3 = new EventoDTO();
			
			//Establece algunos campos al EventoDTO vacío. El ID, nombre, descripcion, fecha, lugar y cupo son campos obligatorios
			evento3.setId(8);
			evento3.setNombre("Evento erroneo");
			evento3.setDescripcion("");
			evento3.setFecha("");
			evento3.setLugar("");
			evento3.setCupo(10);
			
			//Imprime el resultado de crear los eventos
			
			System.out.println(organizadoraEventos.crearEvento(evento, token)); //Correcto
			System.out.println(organizadoraEventos.crearEvento(evento2, token)); //Correcto
			System.out.println(organizadoraEventos.crearEvento(evento3, token)); //Error: campos incompletos
			System.out.println(organizadoraEventos.crearEvento(evento, 552252)); //Error: token no registrado (no ha iniciado sesion)
			
			/////////////////////////////////////////////////////////////////////////////////////////////
						
			//***BUSCAR EVENTO***
			
			//Busqueda por palabras clave en descripcion
			List<EventoDTO> eventosBuscados =  organizadoraEventos.buscarEvento("celebrado");
			//Imprime resultado
			System.out.println("\nBusqueda por descripcion");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println(eventoDTO.getNombre());
			}
			
			eventosBuscados.clear();
			
			//Busqueda por descripcion
			eventosBuscados =  organizadoraEventos.buscarEvento("Fiesta infantil");
			//Imprime resultado
			System.out.println("\nBusqueda por tipo");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID: "+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////
						
			//***LISTAR EVENTOS ORGANIZADOS POR CELEBRAR***
			//Nota: Estos son los que pueden ser cancelados
			eventosBuscados.clear();
			
			eventosBuscados = organizadoraEventos.listarEventoOrganizadoPorCelebrar(token);
			System.out.println("\nEventos organizados por celebrar");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			eventosBuscados.clear();
			
			//Token incorrecto
			eventosBuscados = organizadoraEventos.listarEventoOrganizadoPorCelebrar(223432);
			System.out.println("\nEventos organizados por celebrar de usuario inexistente");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////			
			
			//***LISTAR EVENTOS ORGANIZADOS CELEBRADOS***
			//Nota: Estos ya no se pueden cancelar
			eventosBuscados.clear();
			eventosBuscados = organizadoraEventos.listarEventoOrganizadoCelebrado(token);
			System.out.println("\nEventos organizados celebrados");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			eventosBuscados.clear();
			
			eventosBuscados = organizadoraEventos.listarEventoOrganizadoCelebrado(52456222);
			System.out.println("\nEventos organizados celebrados de un usuario inexistente\n");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////
						
			//***CERRAR SESION***
			
			System.out.println(organizadoraEventos.cerrarSesion(token));
						
			/////////////////////////////////////////////////////////////////////////////////////////////
						
			//***INSCRIBIR A EVENTO***
			//Nota: Crea DTO de evento de acuerdo a la busqueda realizada
			//Inicia sesion con un usuario diferente al que creó el evento
			token = organizadoraEventos.identificarUsuario(dni3, password3);
			System.out.println("Sesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			
			EventoDTO eventoInscribir = new EventoDTO();
			eventoInscribir.setId(2);
			
			//Inscribir correctamente
			System.out.println(organizadoraEventos.inscribirEvento(eventoInscribir, token));
			
			//Error: Inscribir de nuevo cuando ya esta inscrito
			System.out.println(organizadoraEventos.inscribirEvento(eventoInscribir, token));
			
			//Error: Inscribir a un evento ya celebrado
			eventoInscribir.setId(1);
			System.out.println(organizadoraEventos.inscribirEvento(eventoInscribir, token));
			
			/////////////////////////////////////////////////////////////////////////////////////////////
						
			//***LISTAR EVENTOS ORGANIZADOS POR CELEBRAR Y CELEBRADOS CUANDO EL USUARIO NO TIENE EVENTOS ORGANIZADOS***
			eventosBuscados.clear();
			
			eventosBuscados = organizadoraEventos.listarEventoOrganizadoPorCelebrar(token);
			System.out.println("\nEventos organizados por celebrar (no tiene)");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			eventosBuscados.clear();
			
			eventosBuscados = organizadoraEventos.listarEventoOrganizadoCelebrado(token);
			System.out.println("\nEventos organizados celebrados (no tiene)");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////
						
			//***LISTAR EVENTOS INSCRITO POR CELEBRAR Y CELEBRADOS***
			eventosBuscados.clear();
			
			eventosBuscados = organizadoraEventos.listarEventoInscritoPorCelebrar(token);
			System.out.println("\nEventos inscritos por celebrar");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			eventosBuscados.clear();
			
			eventosBuscados = organizadoraEventos.listarEventoOrganizadoCelebrado(token);
			System.out.println("\nEventos inscritos celebrados");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			//////////////////////////////////////////////////////////////////////////////////////////////
			
			//***INSCRIBIR DOS USUARIOS A EVENTO EN LISTA DE ESPERA***
			UsuarioDTO usuarioEspera = new UsuarioDTO("52652020F","Juan Perez","jhony@gmail.com","+521551591058");
			System.out.println(organizadoraEventos.cerrarSesion(token));
			System.out.println(organizadoraEventos.registrarUsuario(usuarioEspera,"12dasdas34"));
			token = organizadoraEventos.identificarUsuario("52652020F", "12dasdas34");
			System.out.println("\nSesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			
			//Manda a lista de espera
			eventoInscribir.setId(2);
			System.out.println(organizadoraEventos.inscribirEvento(eventoInscribir, token));
			System.out.println("Usuario:"+"52652020F");
			
			usuarioEspera = new UsuarioDTO("22452523F","Juan Perez","jhony@gmail.com","+521551591058");
			System.out.println(organizadoraEventos.cerrarSesion(token));
			System.out.println(organizadoraEventos.registrarUsuario(usuarioEspera,"1111"));
			token = organizadoraEventos.identificarUsuario("22452523F", "1111");
			System.out.println("\nSesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			
			eventoInscribir.setId(2);
			System.out.println(organizadoraEventos.inscribirEvento(eventoInscribir, token));
			System.out.println("Usuario:"+"22452523F");
			
			/////////////////////////////////////////////////////////////////////////////////////////////
						
			//***LISTAR EVENTOS EN ESPERA POR CELEBRAR***
			eventosBuscados.clear();
			
			System.out.println(organizadoraEventos.cerrarSesion(token));
			token = organizadoraEventos.identificarUsuario("52652020F", "12dasdas34");
			System.out.println("\nSesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			System.out.println("Usuario:"+"52652020F");
			
			eventosBuscados = organizadoraEventos.listarEventoEsperaPorCelebrar(token);
			System.out.println("Eventos en espera por celebrar");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			eventosBuscados.clear();
			
			token = organizadoraEventos.identificarUsuario("22452523F", "1111");
			System.out.println("\nSesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			System.out.println("Usuario:"+"22452523F");
			
			eventosBuscados = organizadoraEventos.listarEventoEsperaPorCelebrar(token);
			System.out.println("Eventos en espera por celebrar");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			//////////////////////////////////////////////////////////////////////////////////////////////
						
			//***CANCELAR INSCRIPCION A EVENTO***
			
			//Inicia sesion con el usuario que se habia inscrito al evento
			long tokenTemporal = token; //Simulando tener 2 sesiones al mismo tiempo
			token = organizadoraEventos.identificarUsuario(dni3, password3);
			System.out.println("\nSesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			
			EventoDTO eventoCancelar = new EventoDTO();
			eventoCancelar.setId(2);
			//Probando que no se puedan cambiar estos valores
			eventoCancelar.setCupo(200);
			
			//Error: Cancela inscripcion con usuario/token invalido
			System.out.println(organizadoraEventos.cancelarInscripcion(eventoCancelar, 25222));
			
			//Error: Cancela inscripcion con usuario que no esta en lista de invitados
			System.out.println(organizadoraEventos.cancelarInscripcion(eventoCancelar, tokenTemporal));
			
			//Cancela inscripcion un invitado
			System.out.println(organizadoraEventos.cancelarInscripcion(eventoCancelar, token));
			
			//Comprobar que este usuario de espera ya salga como invitado
			System.out.println(organizadoraEventos.cerrarSesion(token));
			token = organizadoraEventos.identificarUsuario("52652020F", "12dasdas34");
			System.out.println("\nSesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			System.out.println("Usuario:"+"52652020F");
			
			eventosBuscados.clear();
			
			eventosBuscados = organizadoraEventos.listarEventoInscritoPorCelebrar(token);
			System.out.println("\nEventos inscritos por celebrar");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			//Comprobar las listas de espera para ver que haya salido el usuario
			eventosBuscados.clear();
			eventosBuscados = organizadoraEventos.listarEventoEsperaPorCelebrar(token);
			
			System.out.println("Eventos en espera por celebrar");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			eventosBuscados.clear();
			
			token = organizadoraEventos.identificarUsuario("22452523F", "1111");
			System.out.println("\nSesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			System.out.println("Usuario:"+"22452523F");
			
			eventosBuscados = organizadoraEventos.listarEventoEsperaPorCelebrar(token);
			System.out.println("Eventos en espera por celebrar");
			for (EventoDTO eventoDTO : eventosBuscados) {
				System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////
						
			//***Cancelar evento***
			EventoDTO cancelarEvento = new EventoDTO();
			cancelarEvento.setId(2);
			//Probando cancelar con el usuario actual, que no es el organizador del evento
			System.out.println("TOKEN: "+token);
			System.out.println(organizadoraEventos.cancelarEvento(cancelarEvento, token));
			
			//Cancelar evento con el usuario que si es el organizador
			System.out.println(organizadoraEventos.cerrarSesion(token));
			token = organizadoraEventos.identificarUsuario(dni, password);
			System.out.println("\nSesion nueva con otro usuario");
			System.out.println("TOKEN: "+token);
			System.out.println(organizadoraEventos.cancelarEvento(cancelarEvento, token));
			
		}
		
		//organizadoraEventos.obtenerUsuarios();
		//organizadoraEventos.obtenerEventos();
//		
		//System.out.println("Organizadora:"+organizadoraEventos.getCif());
		//Logica y llamada de metodos
	}

}
