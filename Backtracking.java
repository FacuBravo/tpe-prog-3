package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.HashMap;

public class Backtracking {

    private Servicios servicios;
    private HashMap<Procesador, ListaTareas> solucionOptima;
    private int tiempoProcesadoresRefrigerados, tiempoFinal, contadorDeSoluciones, cantidadDeEstados;

    public Backtracking(Servicios servicios) {
        this.servicios = servicios;
        this.solucionOptima = new HashMap<>();
        this.tiempoFinal = 0;
        this.contadorDeSoluciones = 0;
        this.cantidadDeEstados = 0;
    }

    /**
     * <Breve explicación de la estrategia de resolución>
     * El algoritmo backtracking trata de asignar tareas a los procesadores de manera que se minimice el tiempo total de ejecución.
     * Si una asignación parcial no cumple con las condiciones requeridas (por ejemplo, el tiempo de ejecución supera el
     * límite permitido para procesadores refrigerados), se retrocede (backtrack) y se intenta con otra asignación.
     * */

    public HashMap<Procesador, ListaTareas> asignarTareas(int tiempoProcesadoresRefrigerados) {
        this.tiempoProcesadoresRefrigerados = tiempoProcesadoresRefrigerados;

        ArrayList<Procesador> procesadoresArr = new ArrayList<>(servicios.getProcesadores());
        ArrayList<Tarea> tareasArr = new ArrayList<>(servicios.getTareas());

        ListaTareas listaTareas = new ListaTareas(tareasArr);

        this.asignarTareas(procesadoresArr, listaTareas, solucionOptima, 0);

        return this.solucionOptima;
    }

    private void asignarTareas(ArrayList<Procesador> procesadores, ListaTareas tareas, HashMap<Procesador, ListaTareas> solucion, int indice) {

        this.cantidadDeEstados++;
        if (indice == tareas.size()) {
            this.contadorDeSoluciones++;
            HashMap<Procesador, ListaTareas> aux = new HashMap<>();

            for (Procesador p : solucion.keySet()) {
                aux.put(p, solucion.get(p).getCopia());
            }

            int tiempo = this.getTiempoFinalEjecucion(aux);

            if (tiempoFinal == 0) {
                this.solucionOptima = aux;
                tiempoFinal = tiempo;
            }

            else {
                if (tiempo <= tiempoFinal) {
                    this.solucionOptima = aux;
                    tiempoFinal = tiempo;
                }
            }

        } else {
            Tarea tarea = tareas.get(indice);

            for (Procesador procesador : procesadores) {
                if (!solucion.containsKey(procesador)) {
                    solucion.put(procesador, new ListaTareas());
                }

                if (this.esApto(solucion.get(procesador), tarea, procesador)) {
                    solucion.get(procesador).add(tarea);

                    if (solucion.get(procesador).getTiempoEjecucion() < this.tiempoFinal || this.tiempoFinal == 0) {
                        this.asignarTareas(procesadores, tareas, solucion, indice + 1);
                    }

                    solucion.get(procesador).remove(tarea);
                }
            }
        }
    }

    private int getTiempoFinalEjecucion(HashMap<Procesador, ListaTareas> procesamientos) {
        int tiempoFinal = 0;

        for (Procesador p : procesamientos.keySet()) {
            int tiempoEjecucion = procesamientos.get(p).getTiempoEjecucion();

            if (tiempoEjecucion > tiempoFinal || tiempoFinal == 0) {
                tiempoFinal = tiempoEjecucion;
            }
        }

        return tiempoFinal;
    }

    private boolean esApto(ListaTareas tareas, Tarea t, Procesador p) {
        boolean esApto = false;

        if (!tareas.isEmpty()) {
            Tarea ultimaTarea = tareas.get(tareas.size() - 1);

            if (!(ultimaTarea.getEs_critica() && t.getEs_critica())) {
                esApto = true;
            }
        } else {
            esApto = true;
        }

        if (esApto) {
            if (p.isEsta_refrigerado()) {
                int tiempo = 0;

                for (Tarea tarea : tareas) {
                    tiempo += tarea.getTiempo_ejecucion();
                }

                tiempo += t.getTiempo_ejecucion();

                if (tiempo > this.tiempoProcesadoresRefrigerados) {
                    esApto = false;
                }
            }
        }

        return esApto;
    }

    public int getTiempoFinal() {
        return this.tiempoFinal;
    }

    public int getCantSoluciones() {
        return this.contadorDeSoluciones;
    }

    public int getCantidadDeEstados() {
        return cantidadDeEstados;
    }
}