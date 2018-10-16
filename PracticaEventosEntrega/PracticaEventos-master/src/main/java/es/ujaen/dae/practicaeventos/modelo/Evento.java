package es.ujaen.dae.practicaeventos.modelo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Evento {

    int id;
    String nombre;
    String descripcion;
    String lugar;
    String fecha;
    String tipo;
    int cupo;

    public Usuario organizador;
    public List<Usuario> listaEspera;
    public Map<String, Usuario> listaInvitados;

    public Evento() {
        listaEspera = new ArrayList<>();
        listaInvitados = new TreeMap<>();
    }

    public Evento(String nombre, String descripcion, String lugar, String fecha, String tipo, int cupo, Usuario organizador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cupo = cupo;
        this.organizador = organizador;
        listaEspera = new ArrayList<>();
        listaInvitados = new TreeMap<>();
    }

    public int getId() {
        return id;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Evento [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", lugar=" + lugar
                + ", fecha=" + fecha + ", tipo=" + tipo + ", cupo=" + cupo + ", organizador=" + organizador
                + ", listaEspera=" + listaEspera + ", listaInvitados=" + listaInvitados + "]";
    }

    public boolean compararConFechaActual() {
        LocalDate hoy = LocalDate.now();
        String[] fechaEvento = getFecha().split("-");

        String format = hoy + "";
        String fechaActual[] = format.split("-");

        if (Integer.parseInt(fechaEvento[2]) > Integer.parseInt(fechaActual[0])) {
            return true;
        } else if (Integer.parseInt(fechaEvento[2]) == Integer.parseInt(fechaActual[0])) {
            if (Integer.parseInt(fechaEvento[1]) > Integer.parseInt(fechaActual[1])) {
                return true;
            } else if (Integer.parseInt(fechaEvento[1]) == Integer.parseInt(fechaActual[1])) {
                if (Integer.parseInt(fechaEvento[0]) > Integer.parseInt(fechaActual[2])) {
                    return true;
                }
            }
        }
        return false;
    }
}
