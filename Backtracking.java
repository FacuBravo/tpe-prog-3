package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Backtracking {

    private Servicios servicios;
    private HashMap<Procesador, ListaTareas> solucionOptima;
    private ArrayList<Tarea> tareasArr;
    private ArrayList<Procesador> procesadoresArr;
    private int tiempoProcesadoresRefrigerados, tiempoFinal, cantidadDeEstados;

    public Backtracking(Servicios servicios) {
        this.servicios = servicios;
        this.solucionOptima = new HashMap<>();
        this.cantidadDeEstados = 0;
        this.tiempoFinal = 0;
        this.cargarDatos();
    }

    /*
     * 
     * Explicación:
     * 
     * Para encontrar todas las soluciones posibles y así elegir la mejor
     * recorremos cada procesador, si el procesador no está en la solución actual
     * lo cargamos en el HashMap con un value de una lista de tareas vacía,
     * luego se obtiene la primer tarea del arreglo de tareas y se la asigna al
     * procesador ya seleccionado si es apto (una tarea es apta para un procesador
     * si al asignarla no se rompe con ninguna restricción).
     * El proceso se repite hasta que no quedan tareas (solución encontrada),
     * si el tiempo de ejecución de la mejor solución es 0 o es mayor que el tiempo
     * de ejecución de la solución encontrada, la mejor solución pasa a ser la 
     * solución actual.
     * En el proceso se realiza una poda, si la solución que se va armando supera 
     * el mejor tiempo obtenido hasta el momento entonces no se llama recursivamente
     * y se deshace lo hecho.
     * 
     */

    public HashMap<Procesador, ListaTareas> asignarTareas(int tiempoProcesadoresRefrigerados) {
        this.tiempoProcesadoresRefrigerados = tiempoProcesadoresRefrigerados;
        
        ArrayList<Procesador> procesadoresAux = new ArrayList<>();
        ArrayList<Tarea> tareasAux = new ArrayList<>();

        for (Tarea t : this.tareasArr) {
            tareasAux.add(t);
        }

        for (Procesador p : this.procesadoresArr) {
            procesadoresAux.add(p);
        }

        HashMap<Procesador, ListaTareas> solucionParcial = new HashMap<>();

        this.backtracking(procesadoresAux, tareasAux, solucionParcial);

        return this.solucionOptima;
    }

    private void backtracking(ArrayList<Procesador> procesadores, ArrayList<Tarea> tareas, HashMap<Procesador, ListaTareas> solucionParcial) {
        this.cantidadDeEstados++;

        if (tareas.isEmpty()) {
            int tiempoParcial = this.getTiempoFinalEjecucion(solucionParcial);

            if (this.tiempoFinal == 0 || this.tiempoFinal > tiempoParcial) {
                this.solucionOptima.clear();

                Iterator<Procesador> it = solucionParcial.keySet().iterator();

                while (it.hasNext()) {
                    Procesador p = it.next();
                    ListaTareas li = solucionParcial.get(p).getCopia();

                    this.solucionOptima.put(p, li);
                }

                this.tiempoFinal = tiempoParcial;
            }
        }

        else {
            Iterator<Procesador> it = procesadores.iterator();

            while (it.hasNext()) {
                Procesador p = it.next();

                if (!solucionParcial.containsKey(p)) {
                    solucionParcial.put(p, new ListaTareas());
                }

                Tarea t = tareas.get(0);

                if (this.esApto(solucionParcial.get(p), t, p)) {
                    solucionParcial.get(p).add(t);
                    tareas.remove(t);

                    if (this.getTiempoFinalEjecucion(solucionParcial) < tiempoFinal || tiempoFinal == 0) {
                        this.backtracking(procesadores, tareas, solucionParcial);
                    }

                    tareas.add(t);
                    solucionParcial.get(p).remove(t);
                }
            }
        }
    }

    private int getTiempoFinalEjecucion(HashMap<Procesador, ListaTareas> procesamientos) {
        int tiempo = 0;

        for (Procesador p : procesamientos.keySet()) {
            int tiempoActual = procesamientos.get(p).getTiempoEjecucion();

            if (tiempoActual > tiempo || tiempo == 0) {
                tiempo = tiempoActual;
            }
        }

        return tiempo;
    }

    private boolean esApto(ListaTareas tareas, Tarea t, Procesador p) {
        boolean esApto = false;

        if (!tareas.isEmpty()) {
            int cuentaTareasCriticas = 0;

            for (Tarea tarea : tareas) {
                if (tarea.getEs_critica()) {
                    cuentaTareasCriticas++;
                }
            }

            esApto = cuentaTareasCriticas < 2;

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

    public int getCantidadDeEstados() {
        return cantidadDeEstados;
    }

    private void cargarDatos() {
        this.procesadoresArr = new ArrayList<>(servicios.getProcesadores());
        this.tareasArr = new ArrayList<>(servicios.getTareas());
    }

}