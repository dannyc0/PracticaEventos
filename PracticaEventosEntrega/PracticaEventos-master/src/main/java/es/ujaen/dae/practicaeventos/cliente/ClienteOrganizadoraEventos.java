package es.ujaen.dae.practicaeventos.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import es.ujaen.dae.practicaeventos.dto.EventoDTO;
import es.ujaen.dae.practicaeventos.dto.UsuarioDTO;
import es.ujaen.dae.practicaeventos.exception.CamposVaciosException;
import es.ujaen.dae.practicaeventos.exception.CancelacionInvalidaException;
import es.ujaen.dae.practicaeventos.exception.FechaInvalidaException;
import es.ujaen.dae.practicaeventos.exception.InscripcionInvalidaException;
import es.ujaen.dae.practicaeventos.exception.SesionNoIniciadaException;
import es.ujaen.dae.practicaeventos.exception.UsuarioNoRegistradoNoEncontradoException;
import es.ujaen.dae.practicaeventos.servicio.OrganizadoraEventosService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteOrganizadoraEventos {

    ApplicationContext ctx;

    public ClienteOrganizadoraEventos(ApplicationContext context) {
        this.ctx = context;
    }

    public void run() throws IOException {
        OrganizadoraEventosService organizadoraEventos = (OrganizadoraEventosService) ctx.getBean("organizadoraEventosImp");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        Interfaz interfaz = new Interfaz();
        int opcion = 0;
        int flag = 0;
        boolean sesionIniciada = false;
        long token = 0;

        System.out.println("**Organizadora de eventos**");
        do {
            interfaz.imprimirOpciones("menu-principal");
            System.out.print("Ingrese el número de su elección: ");
            try {
                opcion = Integer.parseInt(bf.readLine());
            } catch (NumberFormatException nf) {

            }
            if (opcion == 1) {//Registrarse
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
                try {
                    organizadoraEventos.registrarUsuario(usuarioDTO, bf.readLine());
                } catch (CamposVaciosException ce) {
                }
            } else if (opcion == 2) {//Iniciar sesión
                if (sesionIniciada) {
                    System.out.println("\nDebe cerrar su actual sesión primero");
                } else {
                    System.out.print("Ingrese DNI: ");
                    String dni = bf.readLine();
                    System.out.print("Ingrese password: ");
                    String respuesta = "";
                    try {
                        try {
                            respuesta = organizadoraEventos.identificarUsuario(dni, bf.readLine()) + "";
                        } catch (CamposVaciosException ex) {
                        }
                    } catch (UsuarioNoRegistradoNoEncontradoException unr) {

                    }
                    if (respuesta.equals("2")) {
                        System.out.println("\nContraseña incorrecta");
                    } else {
                        System.out.println("\nSe inició sesión correctamente");
                        sesionIniciada = true;
                        token = Long.parseLong(respuesta);
                    }
                }
            } else if (opcion == 3) {//Eventos
                do {
                    flag = 1;
                    interfaz.imprimirOpciones("menu-eventos");
                    System.out.print("Ingrese el número de su elección: ");
                    try {
                        opcion = Integer.parseInt(bf.readLine());
                    } catch (NumberFormatException nf) {

                    }

                    if (opcion == 1) {//Buscar evento
                        System.out.print("Ingrese tipo de evento o palabra clave de la descripción: ");
                        String busqueda = bf.readLine();
                        List<EventoDTO> eventosBuscados = organizadoraEventos.buscarEvento(busqueda);

                        System.out.println("\nResultados de la búsqueda");
                        for (EventoDTO eventoDTO : eventosBuscados) {
                            System.out.println("ID: " + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                        }

                        System.out.print("¿Desea inscribirse a algún evento de la lista? Si es así, introduzca el ID del evento, "
                                + "si no, introduzca 0 para regresar al menú anterior: ");
                        int inscribirse = Integer.parseInt(bf.readLine());

                        if (inscribirse != 0) {//Inscribirse a evento
                            EventoDTO eventoInscribir = new EventoDTO();
                            eventoInscribir.setId(inscribirse);

                            try {
                                try {
                                    organizadoraEventos.inscribirEvento(eventoInscribir, token);
                                } catch (SesionNoIniciadaException ex) {
                                }
                            } catch (InscripcionInvalidaException e) {
                            }
                        }
                    } else if (opcion == 2) {//Crear evento
                        EventoDTO eventoDTO = new EventoDTO();
                        //ID, nombre, descripcion, fecha, lugar, tipo y cupo son campos obligatorios

                        System.out.println("\nComplete los siguientes campos");
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

                        try {
                            try {
                                try {
                                    organizadoraEventos.crearEvento(eventoDTO, token);
                                } catch (FechaInvalidaException ex) {
                                }
                            } catch (SesionNoIniciadaException ex) {
                            }
                        } catch (CamposVaciosException ce) {

                        }
                    } else if (opcion == 3) {//Listar eventos organizados
                        interfaz.imprimirOpciones("menu-listas");
                        System.out.print("Ingrese el número de su elección: ");
                        try {
                            opcion = Integer.parseInt(bf.readLine());
                        } catch (NumberFormatException nf) {

                        }
                        if (opcion == 1) {//Por celebrar
                            List<EventoDTO> eventosBuscados = new ArrayList<>();

                            eventosBuscados = organizadoraEventos.listarEventoOrganizadoPorCelebrar(token);
                            System.out.println("\nEventos organizados por celebrar");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }

                            System.out.print("¿Desea cancelar algún evento de la lista? Si es así, introduzca el ID del evento, "
                                    + "si no, introduzca 0 para regresar al menú anterior: ");
                            int cancelar = Integer.parseInt(bf.readLine());

                            if (cancelar != 0) {//Cancelar evento
                                System.out.print("Ingrese TOKEN de seguridad: ");
                                Long token2 = Long.parseLong(bf.readLine());

                                EventoDTO eventoCancelar = new EventoDTO();
                                eventoCancelar.setId(cancelar);

                                try {
                                    try {
                                        organizadoraEventos.cancelarEvento(eventoCancelar, token2);
                                    } catch (SesionNoIniciadaException ex) {
                                    }
                                } catch (CancelacionInvalidaException e) {
                                }
                            }
                        } else if (opcion == 2) {//Celebrado
                            List<EventoDTO> eventosBuscados = new ArrayList<>();

                            eventosBuscados = organizadoraEventos.listarEventoOrganizadoCelebrado(token);
                            System.out.println("\nEventos organizados celebrado");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }
                        }
                    } else if (opcion == 4) {//Listar eventos inscritos
                        interfaz.imprimirOpciones("menu-listas");
                        System.out.print("Ingrese el número de su elección: ");
                        try {
                            opcion = Integer.parseInt(bf.readLine());
                        } catch (NumberFormatException nf) {

                        }
                        if (opcion == 1) {//Por celebrar
                            List<EventoDTO> eventosBuscados = new ArrayList<>();
                            eventosBuscados = organizadoraEventos.listarEventoInscritoPorCelebrar(token);
                            System.out.println("\nEventos inscrito por celebrar");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }

                            System.out.print("¿Desea cancelar la inscripción a un evento de la lista? Si es así, introduzca el ID del evento, "
                                    + "si no, introduzca 0 para regresar al menú anterior: ");
                            int cancelar = Integer.parseInt(bf.readLine());

                            if (cancelar != 0) {//Cancelar inscripcion
                                System.out.print("Ingrese TOKEN de seguridad: ");
                                Long token2 = Long.parseLong(bf.readLine());

                                EventoDTO eventoCancelar = new EventoDTO();
                                eventoCancelar.setId(cancelar);

                                try {
                                    try {
                                        try {
                                            organizadoraEventos.cancelarInscripcion(eventoCancelar, token2);
                                        } catch (UsuarioNoRegistradoNoEncontradoException ex) {
                                        }
                                    } catch (SesionNoIniciadaException ex) {
                                    }
                                } catch (CancelacionInvalidaException e) {
                                }
                            }
                        } else if (opcion == 2) {//Celebrado
                            List<EventoDTO> eventosBuscados = new ArrayList<>();
                            eventosBuscados = organizadoraEventos.listarEventoInscritoCelebrado(token);
                            System.out.println("\nEventos inscritos celebrado");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }
                        }
                    } else if (opcion == 5) {//Listar eventos en espera
                        interfaz.imprimirOpciones("menu-listas");
                        System.out.print("Ingrese el número de su elección: ");
                        opcion = Integer.parseInt(bf.readLine());

                        if (opcion == 1) {//Por celebrar
                            List<EventoDTO> eventosBuscados = new ArrayList<>();

                            eventosBuscados = organizadoraEventos.listarEventoEsperaPorCelebrar(token);
                            System.out.println("\nEventos en espera por celebrar");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }

                            System.out.print("¿Desea cancelar la inscripción de algún evento de la lista? Si es así, introduzca el ID del evento, "
                                    + "si no, introduzca 0 para regresar al menú anterior: ");
                            int cancelar = Integer.parseInt(bf.readLine());

                            if (cancelar != 0) {//Cancelar lista de espera

                                EventoDTO eventoCancelar = new EventoDTO();
                                eventoCancelar.setId(cancelar);

                                try {
                                    try {
                                        organizadoraEventos.cancelarInscripcion(eventoCancelar, token);
                                    } catch (SesionNoIniciadaException | UsuarioNoRegistradoNoEncontradoException ex) {
                                    }
                                } catch (CancelacionInvalidaException e) {

                                }
                            }
                        } else if (opcion == 2) {//Celebrado
                            List<EventoDTO> eventosBuscados = new ArrayList<>();

                            eventosBuscados = organizadoraEventos.listarEventoEsperaCelebrado(token);
                            System.out.println("\nEventos en espera celebrado");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }
                        }
                    } else if (opcion == 6) {//Ir a menú principal
                        flag = 0;
                    }

                } while (flag == 1);
            } else if (opcion == 4) {//Cerrar sesión

                if (organizadoraEventos.cerrarSesion(token)) {
                    System.out.println("\n " + "Sesión terminada. Hasta luego");
                    sesionIniciada = false;
                } else {
                    System.out.println("\n " + "Token incorrecto");
                }

            } else if (opcion == 5) {//Salir
                System.out.println("Vuelva pronto");
                flag = 2;
            }

        } while (flag == 0);

        bf.close();

    }

}
