package es.ujaen.dae.practicaeventos.cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Interfaz {
    //private final static ArrayList<String> menuOpcionesPrincipal = (ArrayList<String>) Arrays.asList("Registrar usuario", "Login","Eventos");

    private Map<Integer, String> opciones;

    public Interfaz() {
        opciones = new TreeMap<>();
    }

    public void imprimirOpciones(String tipo) {
        cargarOpciones(tipo);
        for (String opcion : opciones.values()) {
            System.out.println(opcion);
        }
    }

    private void cargarOpciones(String tipo) {
        if (tipo.equals("menu-principal")) {
            opciones.clear();
            opciones.put(1, "1. Registrarse");
            opciones.put(2, "2. Iniciar sesión");
            opciones.put(3, "3. Eventos");
            opciones.put(4, "4. Cerrar sesión");
            opciones.put(5, "5. Salir");
        } else if (tipo.equals("menu-eventos")) {
            opciones.clear();
            opciones.put(1, "1. Buscar evento");
            opciones.put(2, "2. Crear Evento");
            opciones.put(3, "3. Listar eventos organizados");
            opciones.put(4, "4. Listar eventos inscritos");
            opciones.put(5, "5. Listar eventos en espera");
            opciones.put(6, "6. Menú principal");
        } else if (tipo.equals("menu-listas")) {
            opciones.clear();
            opciones.put(1, "1. Por celebrar");
            opciones.put(2, "2. Celebrado");
            opciones.put(3, "3. Regresar");
        }

    }

}
