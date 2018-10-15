package es.ujaen.dae.practicaeventos.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;

import es.ujaen.dae.practicaeventos.bean.OrganizadoraEventosImp;
import es.ujaen.dae.practicaeventos.dto.EventoDTO;
import es.ujaen.dae.practicaeventos.dto.UsuarioDTO;
import es.ujaen.dae.practicaeventos.servicio.OrganizadoraEventosService;

public class ClienteOrganizadoraEventos {
	ApplicationContext ctx;

	public ClienteOrganizadoraEventos(ApplicationContext context) {
		this.ctx = context;
	}
	
	public void run() throws IOException {
		OrganizadoraEventosService organizadoraEventos = (OrganizadoraEventosService) ctx.getBean("organizadoraEventosImp");
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

		Interfaz interfaz = new Interfaz();
		int opcion;
		int flag=0;
		boolean sesionIniciada = false;
		
		System.out.println("**Organizadora de eventos**");
		do {
			interfaz.imprimirOpciones("menu-principal");
			System.out.print("Ingrese el número de su elección: ");
			opcion = Integer.parseInt(bf.readLine());
			
			if(opcion==1) {//Registrarse
				UsuarioDTO usuarioDTO = new UsuarioDTO();
				System.out.println("\nComplete los siguientes campos");
				System.out.print("Ingrese su DNI: ");
				usuarioDTO.setDni(bf.readLine());
				System.out.print("Ingrese su nombre completo: ");
				usuarioDTO.setNombre(bf.readLine());
				System.out.print("Ingrese su correo electrónico: ");
				usuarioDTO.setCorreo(bf.readLine());
				System.out.print("Ingrese su número de telefono: ");
				usuarioDTO.setTelefono(bf.readLine());
				System.out.print("Ingrese su contraseña: ");
				System.out.println("\n "+organizadoraEventos.registrarUsuario(usuarioDTO, bf.readLine()));
			}else if(opcion==2) {//Iniciar sesión
//				if (sesionIniciada) {
//					System.out.println("\nDebe cerrar su actual sesión primero");
//				}else {
					System.out.print("Ingrese DNI: ");
					String dni=bf.readLine();
					System.out.print("Ingrese password: ");
					String respuesta=organizadoraEventos.identificarUsuario(dni, bf.readLine())+"";
					if(respuesta.equals("2")) {
						System.out.println("\nContraseña incorrecta");
					}else if(respuesta.equals("1")) {
						System.out.println("\nUsuario no registrado");
					}else if(respuesta.equals("0")) {
						System.out.println("\nCampos incompletos");
					}else {
						System.out.println("\nSe inició sesión correctamente \n Su TOKEN de seguridad es :"+respuesta);
//						sesionIniciada=true;
					}
//				}
			}else if(opcion==3) {//Eventos
				do {
					flag = 1;
					interfaz.imprimirOpciones("menu-eventos");
					System.out.print("Ingrese el número de su elección: ");
					opcion = Integer.parseInt(bf.readLine());
					
					if(opcion==1){//Buscar evento
						System.out.print("Ingrese tipo de evento o palabra clave de la descripción: ");
						String busqueda=bf.readLine();
						List<EventoDTO> eventosBuscados =  organizadoraEventos.buscarEvento(busqueda);
						
						System.out.println("\nResultados de la búsqueda");
						for (EventoDTO eventoDTO : eventosBuscados) {
							System.out.println("ID: " + eventoDTO.getId() + "  " + eventoDTO.getNombre());
						}
						
						System.out.print("¿Desea inscribirse a algún evento de la lista? Si es así, introduzca el ID del evento, "
								+ "si no, introduzca 0 para regresar al menú anterior: ");
						int inscribirse=Integer.parseInt(bf.readLine());
						
						if(inscribirse!=0){//Inscribirse a evento
							System.out.print("Ingrese TOKEN de seguridad ");
							Long token=Long.parseLong(bf.readLine());
							
							EventoDTO eventoInscribir = new EventoDTO();
							eventoInscribir.setId(inscribirse);
							
							System.out.println("\n "+organizadoraEventos.inscribirEvento(eventoInscribir, token));
						}
					}else if(opcion==2) {//Crear evento
						EventoDTO eventoDTO = new EventoDTO();
						//ID, nombre, descripcion, fecha, lugar, tipo y cupo son campos obligatorios
						
						System.out.println("\nComplete los siguientes campos");
						System.out.print("Ingrese el ID del evento: ");
						eventoDTO.setId((Integer.parseInt(bf.readLine())));
						System.out.print("Ingrese el nombre del evento: ");
						eventoDTO.setNombre(bf.readLine());
						System.out.print("Ingrese la descripción del evento: ");
						eventoDTO.setDescripcion(bf.readLine());
						System.out.print("Ingrese la fecha de celebración del evento (dd-mm-aaaa): ");
						eventoDTO.setFecha(bf.readLine());
						System.out.print("Ingrese el lugar donde se llevará a cabo el evento: ");
						eventoDTO.setLugar(bf.readLine());
						System.out.print("Ingrese el tipo de evento: ");
						eventoDTO.setTipo(bf.readLine());
						System.out.print("Ingrese el cupo del evento: ");
						eventoDTO.setCupo(Integer.parseInt(bf.readLine()));
						System.out.print("Ingrese TOKEN de seguridad: ");
						Long token=Long.parseLong(bf.readLine());
						
						System.out.println("\n "+organizadoraEventos.crearEvento(eventoDTO, token));
					}else if(opcion==3) {//Listar eventos organizados
						interfaz.imprimirOpciones("menu-listas");
						System.out.print("Ingrese el número de su elección: ");
						opcion = Integer.parseInt(bf.readLine());
						
						if(opcion==1) {//Por celebrar
							List<EventoDTO> eventosBuscados =  new ArrayList<>();
							System.out.print("Ingrese TOKEN de seguridad: ");
							Long token=Long.parseLong(bf.readLine());
							eventosBuscados = organizadoraEventos.listarEventoOrganizadoPorCelebrar(token);
							System.out.println("\nEventos organizados por celebrar");
							for (EventoDTO eventoDTO : eventosBuscados) {
								System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
							}
							
							System.out.print("¿Desea cancelar algún evento de la lista? Si es así, introduzca el ID del evento, "
									+ "si no, introduzca 0 para regresar al menú anterior: ");
							int cancelar=Integer.parseInt(bf.readLine());
							
							if(cancelar!=0){//Cancelar evento
								System.out.print("Ingrese TOKEN de seguridad: ");
								Long token2=Long.parseLong(bf.readLine());
								
								EventoDTO eventoCancelar = new EventoDTO();
								eventoCancelar.setId(cancelar);
								
								System.out.println("\n"+organizadoraEventos.cancelarEvento(eventoCancelar, token2));
							}
						}else if (opcion==2) {//Celebrado
							List<EventoDTO> eventosBuscados =  new ArrayList<>();
							System.out.print("Ingrese TOKEN de seguridad: ");
							Long token=Long.parseLong(bf.readLine());
							eventosBuscados = organizadoraEventos.listarEventoOrganizadoCelebrado(token);
							System.out.println("\nEventos organizados celebrado");
							for (EventoDTO eventoDTO : eventosBuscados) {
								System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
							}
						}
					}else if(opcion==4) {//Listar eventos inscritos
						interfaz.imprimirOpciones("menu-listas");
						System.out.print("Ingrese el número de su elección: ");
						opcion = Integer.parseInt(bf.readLine());
						
						if(opcion==1) {//Por celebrar
							List<EventoDTO> eventosBuscados =  new ArrayList<>();
							System.out.print("Ingrese TOKEN de seguridad: ");
							Long token=Long.parseLong(bf.readLine());
							eventosBuscados = organizadoraEventos.listarEventoInscritoPorCelebrar(token);
							System.out.println("\nEventos inscrito por celebrar");
							for (EventoDTO eventoDTO : eventosBuscados) {
								System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
							}
							
							System.out.print("¿Desea cancelar la inscripción a un evento de la lista? Si es así, introduzca el ID del evento, "
									+ "si no, introduzca 0 para regresar al menú anterior: ");
							int cancelar=Integer.parseInt(bf.readLine());
							
							if(cancelar!=0){//Cancelar inscripcion
								System.out.print("Ingrese TOKEN de seguridad: ");
								Long token2=Long.parseLong(bf.readLine());
								
								EventoDTO eventoCancelar = new EventoDTO();
								eventoCancelar.setId(cancelar);
								
								System.out.println("\n"+organizadoraEventos.cancelarInscripcion(eventoCancelar, token2));
							}
						}else if (opcion==2) {//Celebrado
							List<EventoDTO> eventosBuscados =  new ArrayList<>();
							System.out.print("Ingrese TOKEN de seguridad: ");
							Long token=Long.parseLong(bf.readLine());
							eventosBuscados = organizadoraEventos.listarEventoInscritoCelebrado(token);
							System.out.println("\nEventos inscritos celebrado");
							for (EventoDTO eventoDTO : eventosBuscados) {
								System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
							}
						}
					}else if(opcion==5) {//Listar eventos en espera
						interfaz.imprimirOpciones("menu-listas");
						System.out.print("Ingrese el número de su elección: ");
						opcion = Integer.parseInt(bf.readLine());
						
						if(opcion==1) {//Por celebrar
							List<EventoDTO> eventosBuscados =  new ArrayList<>();
							System.out.print("Ingrese TOKEN de seguridad: ");
							Long token=Long.parseLong(bf.readLine());
							eventosBuscados = organizadoraEventos.listarEventoEsperaPorCelebrar(token);
							System.out.println("\nEventos en espera por celebrar");
							for (EventoDTO eventoDTO : eventosBuscados) {
								System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
							}
							
							System.out.print("¿Desea cancelar la inscripción de algún evento de la lista? Si es así, introduzca el ID del evento, "
									+ "si no, introduzca 0 para regresar al menú anterior: ");
							int cancelar=Integer.parseInt(bf.readLine());
							
							if(cancelar!=0){//Cancelar lista de espera
								System.out.print("Ingrese TOKEN de seguridad: ");
								Long token2=Long.parseLong(bf.readLine());
								
								EventoDTO eventoCancelar = new EventoDTO();
								eventoCancelar.setId(cancelar);
								
								System.out.println("\n"+organizadoraEventos.cancelarInscripcion(eventoCancelar, token2));
							}
						}else if (opcion==2) {//Celebrado
							List<EventoDTO> eventosBuscados =  new ArrayList<>();
							System.out.print("Ingrese TOKEN de seguridad: ");
							Long token=Long.parseLong(bf.readLine());
							eventosBuscados = organizadoraEventos.listarEventoEsperaCelebrado(token);
							System.out.println("\nEventos en espera celebrado");
							for (EventoDTO eventoDTO : eventosBuscados) {
								System.out.println("ID:"+eventoDTO.getId()+"  "+eventoDTO.getNombre());
							}
						}
					}else if(opcion==6) {//Ir a menú principal
						flag=0;
					}
					
				} while (flag==1);
			}else if(opcion==4) {//Cerrar sesión
				System.out.print("Ingrese TOKEN de seguridad: ");
				Long token=Long.parseLong(bf.readLine());
				
				if(organizadoraEventos.cerrarSesion(token)) {
					System.out.println("\n "+"Sesión terminada. Hasta luego");
					sesionIniciada=false;
				}else {
					System.out.println("\n "+"Token incorrecto");
				}
				
				
			}else if(opcion==5) {//Salir
				System.out.println("Vuelva pronto");
				flag=2;
			}
			
		} while (flag==0);
		
		bf.close();

	}

}
