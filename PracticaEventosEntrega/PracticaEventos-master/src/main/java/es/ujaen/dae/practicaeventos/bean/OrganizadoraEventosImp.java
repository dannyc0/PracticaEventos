package es.ujaen.dae.practicaeventos.bean;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ujaen.dae.practicaeventos.dto.EventoDTO;
import es.ujaen.dae.practicaeventos.dto.UsuarioDTO;
import es.ujaen.dae.practicaeventos.exception.CamposVaciosException;
import es.ujaen.dae.practicaeventos.exception.CancelacionInvalidaException;
import es.ujaen.dae.practicaeventos.exception.FechaInvalidaException;
import es.ujaen.dae.practicaeventos.exception.InscripcionInvalidaException;
import es.ujaen.dae.practicaeventos.exception.SesionNoIniciadaException;
import es.ujaen.dae.practicaeventos.exception.UsuarioNoRegistradoNoEncontradoException;
import es.ujaen.dae.practicaeventos.modelo.Evento;
import es.ujaen.dae.practicaeventos.modelo.Usuario;
import es.ujaen.dae.practicaeventos.servicio.OrganizadoraEventosService;

@Component
public class OrganizadoraEventosImp implements OrganizadoraEventosService {

    String cif;
    String nombre;
    boolean isLogeado;
    long token;

    Hashtable<Long, String> usuariosTokens;
    Map<String, Usuario> usuarios;
    Map<Integer, Evento> eventos;

    public OrganizadoraEventosImp() {
        usuarios = new TreeMap<>();
        eventos = new TreeMap<>();
        usuariosTokens = new Hashtable<>();
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void registrarUsuario(UsuarioDTO usuarioDTO, String password) throws CamposVaciosException {
        Usuario usuario = usuarioDTO.toEntity();
        if (usuario.getDni() != null && !usuario.getDni().isEmpty() && password != null && !password.isEmpty() && usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
            usuario.setPassword(password);
            usuarios.put(usuario.getDni(), usuario);
        } else {
            throw new CamposVaciosException();
        }
//			mensaje = "No se ha registrado el usuario. El DNI, nombre y password son campos obligatorios.";		//throw new ExceptionCamposInvalidos();
    }

    public String identificarUsuario(String dni, String password) throws UsuarioNoRegistradoNoEncontradoException, CamposVaciosException {
        Usuario usuario;
        String respuesta;
        if (!dni.isEmpty() && !password.isEmpty()) {
            if (usuarios.containsKey(dni)) {
                usuario = usuarios.get(dni);
                if (usuario.getPassword().equals(password)) {
                    token = generarToken();
                    respuesta = token + "";
                    usuariosTokens.put(token, dni);
                } else {
                    respuesta = 2 + ""; //contraseña incorrecta
                }
            } else {
                throw new UsuarioNoRegistradoNoEncontradoException();
            }
        } else {
            throw new CamposVaciosException();
        }
        return "";
    }

    public boolean cerrarSesion(long token) {
        if (usuariosTokens.remove(token) != null) {
            return true;
        }
        return false;
    }

    public void crearEvento(EventoDTO eventoDTO, long token) throws CamposVaciosException, SesionNoIniciadaException, FechaInvalidaException {
        Evento evento = eventoDTO.toEntity();

        if (validarToken(token)) {
            evento.setOrganizador(usuarios.get(usuariosTokens.get(token)));
            if (evento.getNombre() != null && !evento.getNombre().isEmpty() && evento.getDescripcion() != null && !evento.getDescripcion().isEmpty() && evento.getFecha() != null && !evento.getFecha().isEmpty() && evento.getLugar() != null && !evento.getLugar().isEmpty() && evento.getCupo() != 0) {
                if(validarFecha(evento.getFecha())){
                evento.setId(eventos.size() + 1);
                eventos.put(evento.getId(), evento);
                usuarios.get(usuariosTokens.get(token)).eventosOrganizados.put(evento.getId(), evento);
                }
                } else {
                throw new CamposVaciosException();
            }
        } else {
            throw new SesionNoIniciadaException();
        }
    }

    public void inscribirEvento(EventoDTO eventoDTO, long token) throws InscripcionInvalidaException, SesionNoIniciadaException {

        if (validarToken(token)) {
            Evento evento = eventoDTO.toEntity();
            evento = eventos.get(evento.getId());

            if (eventos.get(evento.getId()).compararConFechaActual()) {
                if (evento.listaInvitados.get(usuariosTokens.get(token)) == null) {
                    if (evento.listaInvitados.size() < evento.getCupo()) {
                        evento.listaInvitados.put(usuariosTokens.get(token), usuarios.get(usuariosTokens.get(token)));
                        usuarios.get(usuariosTokens.get(token)).eventosInvitado.put(evento.getId(), evento);
                    } else {
                        evento.listaEspera.add(usuarios.get(usuariosTokens.get(token)));
                        usuarios.get(usuariosTokens.get(token)).eventosEspera.put(evento.getId(), evento);
                    }
                } else {
                    throw new InscripcionInvalidaException();
                }
            } else {
                throw new InscripcionInvalidaException();
            }

        } else {
            throw new SesionNoIniciadaException();
        }

    }

    public void cancelarInscripcion(EventoDTO eventoDTO, long token) throws CancelacionInvalidaException,
            SesionNoIniciadaException, UsuarioNoRegistradoNoEncontradoException {

        if (validarToken(token)) {
            Evento evento = eventoDTO.toEntity();
            evento = eventos.get(evento.getId());

            if (evento.listaInvitados.containsKey(usuariosTokens.get(token))) {
                if (eventos.get(evento.getId()).compararConFechaActual()) {
                    evento.listaInvitados.remove(usuariosTokens.get(token));
                    evento.listaInvitados.put(evento.listaEspera.get(0).getDni(), evento.listaEspera.get(0));

                    usuarios.get(usuariosTokens.get(token)).eventosInvitado.remove(evento.getId());
                    usuarios.get(evento.listaEspera.get(0).getDni()).eventosInvitado.put(evento.getId(), evento);
                    usuarios.get(evento.listaEspera.get(0).getDni()).eventosEspera.remove(evento.getId(), evento);

                    evento.listaEspera.remove(0);

                } else {
                    throw new CancelacionInvalidaException();
                }
            } else {
                throw new UsuarioNoRegistradoNoEncontradoException();
            }
        } else {
            throw new SesionNoIniciadaException();
        }
    }

    public List<EventoDTO> buscarEvento(String attr) {
        ArrayList<EventoDTO> eventosBuscados = new ArrayList<>();

        for (Evento evento : eventos.values()) {
            if (evento.getTipo().toLowerCase().equals(attr.toLowerCase()) || evento.getDescripcion().toLowerCase().contains(attr.toLowerCase())) {
                eventosBuscados.add(new EventoDTO(evento));
            }
        }
        return eventosBuscados;
    }

    public void cancelarEvento(EventoDTO eventoDTO, long token) throws CancelacionInvalidaException, SesionNoIniciadaException {
        String mensaje = "";
        Evento evento = eventoDTO.toEntity();
        if (validarToken(token)) {
            if (eventos.get(evento.getId()).compararConFechaActual()) {
                if (eventos.get(evento.getId()).getOrganizador().getDni().equals(usuariosTokens.get(token))) {
                    eventos.remove(evento.getId());
                    usuarios.get(usuariosTokens.get(token)).eventosOrganizados.remove(evento.getId());
                } else {
                    throw new CancelacionInvalidaException();
                }
            } else {
                throw new CancelacionInvalidaException();
            }
        } else {
            throw new SesionNoIniciadaException();
        }
    }

    public List<EventoDTO> listarEventoInscritoCelebrado(long token) {
        List<EventoDTO> eventosInscritosCelebrados = new ArrayList<EventoDTO>();
        if (validarToken(token)) {
            Usuario usuario = usuarios.get(usuariosTokens.get(token));
            for (Evento evento : usuario.eventosInvitado.values()) {
                if (!evento.compararConFechaActual()) {
                    eventosInscritosCelebrados.add(new EventoDTO(evento));
                }
            }
        }
        return eventosInscritosCelebrados;
    }

    public List<EventoDTO> listarEventoInscritoPorCelebrar(long token) {
        List<EventoDTO> eventosInscritosPorCelebrar = new ArrayList<EventoDTO>();
        if (validarToken(token)) {
            Usuario usuario = usuarios.get(usuariosTokens.get(token));
            for (Evento evento : usuario.eventosInvitado.values()) {
                if (evento.compararConFechaActual()) {
                    eventosInscritosPorCelebrar.add(new EventoDTO(evento));
                }
            }
        }
        return eventosInscritosPorCelebrar;
    }

    public List<EventoDTO> listarEventoEsperaPorCelebrar(long token) {
        List<EventoDTO> eventosEsperaPorCelebrar = new ArrayList<EventoDTO>();
        if (validarToken(token)) {
            Usuario usuario = usuarios.get(usuariosTokens.get(token));
            for (Evento evento : usuario.eventosEspera.values()) {
                if (evento.compararConFechaActual()) {
                    eventosEsperaPorCelebrar.add(new EventoDTO(evento));
                }
            }
        }
        return eventosEsperaPorCelebrar;
    }

    public List<EventoDTO> listarEventoEsperaCelebrado(long token) {
        List<EventoDTO> eventosEsperaCelebrado = new ArrayList<EventoDTO>();
        if (validarToken(token)) {
            Usuario usuario = usuarios.get(usuariosTokens.get(token));
            for (Evento evento : usuario.eventosEspera.values()) {
                if (!evento.compararConFechaActual()) {
                    eventosEsperaCelebrado.add(new EventoDTO(evento));
                }
            }
        }
        return eventosEsperaCelebrado;
    }

    public List<EventoDTO> listarEventoOrganizadoCelebrado(long token) {
        List<EventoDTO> eventosOrganizadosCelebrados = new ArrayList<EventoDTO>();
        if (validarToken(token)) {
            Usuario usuario = usuarios.get(usuariosTokens.get(token));
            for (Evento evento : usuario.eventosOrganizados.values()) {
                if (!evento.compararConFechaActual()) {
                    eventosOrganizadosCelebrados.add(new EventoDTO(evento));
                }
            }
        }
        return eventosOrganizadosCelebrados;
    }

    public List<EventoDTO> listarEventoOrganizadoPorCelebrar(long token) {
        List<EventoDTO> eventosOrganizadosPorCelebrar = new ArrayList<EventoDTO>();
        if (validarToken(token)) {
            Usuario usuario = usuarios.get(usuariosTokens.get(token));
            for (Evento evento : usuario.eventosOrganizados.values()) {
                if (evento.compararConFechaActual()) {
                    eventosOrganizadosPorCelebrar.add(new EventoDTO(evento));
                }
            }
        }
        return eventosOrganizadosPorCelebrar;
    }

    private long generarToken() {
        Long tok;
        Random random = new Random();
        tok = random.nextLong();
        if (tok < 0) {
            return tok * -1;
        }
        return tok;
    }

    private boolean validarToken(long token) {
        if (usuariosTokens.containsKey(token)) {
            return true;
        }
        return false;
    }

    private boolean validarFecha(String fecha) throws FechaInvalidaException {
        String[] date;
        try{
            date=fecha.split("-");
            if(Integer.parseInt(date[0])>0&&Integer.parseInt(date[0])<32){
                if(Integer.parseInt(date[1])>0&&Integer.parseInt(date[1])<13){
                         if(Integer.parseInt(date[2])>2017&&Integer.parseInt(date[0])<2087){
                             return true;
                         }

                }else{
                    throw new FechaInvalidaException();

                }
                
              }else{
                throw new FechaInvalidaException();
            }
            
        }catch(FechaInvalidaException | NumberFormatException ex){
            throw new FechaInvalidaException();
        }
        return true;

    }
}
