package ProgramacionIII.tpe;

public class Tarea {

    private String id_tarea;
    private String nombre_tarea;
    private double tiempo_ejecucion;
    private boolean es_critica;
    private int nivel_prioridad;

    public Tarea(String id_tarea, String nombre_tarea, double tiempo_ejecucion, boolean es_critica, int nivel_prioridad) {
        this.id_tarea = id_tarea;
        this.nombre_tarea = nombre_tarea;
        this.tiempo_ejecucion = tiempo_ejecucion;
        this.es_critica = es_critica;
        this.nivel_prioridad = nivel_prioridad;
    }

    public String getId_tarea() {
        return id_tarea;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public double getTiempo_ejecucion() {
        return tiempo_ejecucion;
    }

    public boolean getEs_critica() {
        return es_critica;
    }

    public int getNivel_prioridad() {
        return nivel_prioridad;
    }

    public void setNombre_tarea(String nombre_tarea) {
        this.nombre_tarea = nombre_tarea;
    }

    public void setTiempo_ejecucion(double tiempo_ejecucion) {
        this.tiempo_ejecucion = tiempo_ejecucion;
    }

    public void setEs_critica(boolean es_critica) {
        this.es_critica = es_critica;
    }

    public void setNivel_prioridad(int nivel_prioridad) {
        this.nivel_prioridad = nivel_prioridad;
    }

    @Override
    public String toString() {
        return "Id tarea: " + id_tarea + " - Nombre tarea: " + nombre_tarea;
    }
}