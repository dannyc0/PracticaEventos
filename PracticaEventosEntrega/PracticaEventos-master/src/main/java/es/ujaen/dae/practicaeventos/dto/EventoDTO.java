package es.ujaen.dae.practicaeventos.dto;

import java.util.List;
import java.util.Map;

import es.ujaen.dae.practicaeventos.modelo.Evento;
import es.ujaen.dae.practicaeventos.modelo.Usuario;

public class EventoDTO {

    int id;
    String nombre;
    String descripcion;
    String lugar;
    String fecha;
    String tipo;
    int cupo;

    UsuarioDTO organizador;

    public EventoDTO() {

    }

    public EventoDTO(Evento evento) {
        this.id = evento.getId();
        this.nombre = evento.getNombre();
        this.descripcion = evento.getDescripcion();
        this.lugar = evento.getLugar();
        this.fecha = evento.getFecha();
        this.tipo = evento.getTipo();
        this.cupo = evento.getCupo();

        //this.organizador= new UsuarioDTO(evento.getOrganizador());
    }

    public EventoDTO(int id, String nombre, String descripcion, String lugar, String fecha, String tipo, int cupo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cupo = cupo;
        //this.organizador = usuario;
    }

    public EventoDTO(String nombre, String descripcion, String lugar, String fecha, String tipo, int cupo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cupo = cupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public Evento toEntity() {
        Evento evento = new Evento();
        evento.setId(id);
        evento.setNombre(nombre);
        evento.setDescripcion(descripcion);
        evento.setLugar(lugar);
        evento.setFecha(fecha);
        evento.setTipo(tipo);
        evento.setCupo(cupo);
        if (organizador != null) {
            evento.setOrganizador(organizador.toEntity());
        }
        return evento;
    }


}
