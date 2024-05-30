package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.Iterator;

public class ListaTareas implements Iterable<Tarea> {
    private ArrayList<Tarea> tareas;
    private int tiempoEjecucion;

    public ListaTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
        this._getTiempoEjecucion();
    }

    public ListaTareas() {
        this.tiempoEjecucion = 0;
        this.tareas = new ArrayList<>();
    }

    public int getTiempoEjecucion() {
        return this.tiempoEjecucion;
    }

    private void _getTiempoEjecucion() {
        int tiempo = 0;

        for (Tarea tarea : this.tareas) {
            tiempo += tarea.getTiempo_ejecucion();
        }

        this.tiempoEjecucion = tiempo;
    }

    public int size() {
        return this.tareas.size();
    }

    public ListaTareas getCopia() {
        ArrayList<Tarea> tareasCopia = new ArrayList<>();

        for (Tarea tarea : this.tareas) {
            tareasCopia.add(tarea);
        }

        return new ListaTareas(tareasCopia);
    }

    public Tarea get(int indice) {
        return this.tareas.get(indice);
    }

    public boolean add(Tarea tarea) {
        boolean r = this.tareas.add(tarea);
        this._getTiempoEjecucion();
        return r;
    }

    public boolean remove(Tarea tarea) {
        boolean r = this.tareas.remove(tarea);
        this._getTiempoEjecucion();
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