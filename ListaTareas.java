package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.Iterator;

public class ListaTareas implements Iterable<Tarea> {
    private ArrayList<Tarea> tareas;
    private int tiempoEjecucion;

    public ListaTareas(ArrayList<Tarea> tareasArr) {
        this.tareas = new ArrayList<>(tareasArr);

        for (Tarea tarea : tareasArr) {
            tiempoEjecucion += tarea.getTiempo_ejecucion();
        }
    }

    public ListaTareas() {
        this.tiempoEjecucion = 0;
        this.tareas = new ArrayList<>();
    }

    public int getTiempoEjecucion() {
        return this.tiempoEjecucion;
    }

    public int size() {
        return this.tareas.size();
    }

    public ListaTareas getCopia() {
        ListaTareas tareasCopia = new ListaTareas();

        for (Tarea tarea : this.tareas) {
            tareasCopia.add(tarea);
        }

        return tareasCopia;
    }

    public Tarea get(int indice) {
        return this.tareas.get(indice);
    }

    public boolean add(Tarea tarea) {
        boolean r = this.tareas.add(tarea);
        this.tiempoEjecucion += tarea.getTiempo_ejecucion();
        return r;
    }

    public boolean remove(Tarea tarea) {
        boolean r = this.tareas.remove(tarea);
        this.tiempoEjecucion -= tarea.getTiempo_ejecucion();
        return r;
    }

    public boolean isEmpty() {
        return this.tareas.isEmpty();
    }

    @Override
    public Iterator<Tarea> iterator() {
        return this.tareas.iterator();
    }

    public String toString() {
        return this.tareas.toString();
    }
}