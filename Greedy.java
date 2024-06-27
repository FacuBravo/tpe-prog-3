package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Greedy {
    private Servicios servicios;
    private HashMap<Procesador, ListaTareas> solucionOptima;
    private ArrayList<Tarea> tareasArr;
    private ArrayList<Procesador> procesadoresArr;
    private int contadorDeCandidatos, tiempoProcesadoresRefrigerados, tiempoFinal;

    public Greedy(Servicios servicios) {
        this.servicios = servicios;
        this.solucionOptima = new HashMap<>();
        this.contadorDeCandidatos = 0;
        this.tiempoFinal = 0;
        this.cargarDatos();
    }

    /*
     * 
     * Explicación:
     * 
     * Para el algoritmo greedy seleccionamos del arreglo de tareas la que menor
     * tiempo de ejecución tenga, una vez seleccionada se elige el mejor procesador
     * que sea apto para ejecutar esa tarea (una tarea es apta para un procesador
     * si al asignarla no se rompe con ninguna restricción), el mejor procesador
     * será aquel que tenga el menor tiempo de ejecución de la suma de las tareas que
     * tiene asignadas.
     * Cuándo se seleccionó la mejor tarea y el mejor procesador, se asigna la tarea
     * elegida al procesador correspondiente.
     * 
     */

    public HashMap<Procesador, ListaTareas> asignarTareas(int tiempoProcesadoresRefrigerados) {
        this.tiempoProcesadoresRefrigerados = tiempoProcesadoresRefrigerados;

        HashMap<Procesador, ListaTareas> solucionParcial = new HashMap<>();
        ArrayList<Tarea> tareasAux = new ArrayList<>();

        for (Tarea t : this.tareasArr) {
            tareasAux.add(t);
        }

        for (Procesador p : this.procesadoresArr) {
            solucionParcial.put(p, new ListaTareas());
        }

        this.greedy(tareasAux, solucionParcial);

        return this.solucionOptima;
    }

    private void greedy(ArrayList<Tarea> tareas, HashMap<Procesador, ListaTareas> solucionParcial) {
        while (!tareas.isEmpty()) {
            Tarea t = this.seleccionarMejorTarea(tareas);
            Procesador p = this.getMejorProcesador(solucionParcial, t);

            if (p == null) {
                return;
            }

            this.contadorDeCandidatos++;
            tareas.remove(t);
            solucionParcial.get(p).add(t);
        }

        this.solucionOptima = solucionParcial;
        this.tiempoFinal = this.getTiempoFinalEjecucion(this.solucionOptima);
    }

    private Procesador getMejorProcesador(HashMap<Procesador, ListaTareas> solucionParcial, Tarea t) {
        Procesador mejorProcesador = null;

        Iterator<Procesador> it = solucionParcial.keySet().iterator();

        while (it.hasNext()) {
            Procesador procesador = it.next();

            if (mejorProcesador == null) {
                if (this.esApto(solucionParcial.get(procesador), t, procesador)) {
                    mejorProcesador = procesador;
                }
            }

            else if (solucionParcial.get(mejorProcesador).getTiempoEjecucion() > solucionParcial.get(procesador).getTiempoEjecucion()) {
                if (this.esApto(solucionParcial.get(procesador), t, procesador)) {
                    mejorProcesador = procesador;
                }
            }
        }

        return mejorProcesador;
    }

    private Tarea seleccionarMejorTarea(ArrayList<Tarea> tareas) {
        Tarea t = null;

        for (Tarea tarea : tareas) {
            if (t == null) {
                t = tarea;
            }

            else if (tarea.getTiempo_ejecucion() < t.getTiempo_ejecucion()) {
                t = tarea;
            }
        }

        return t;
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

    private void cargarDatos() {
        this.procesadoresArr = new ArrayList<>(servicios.getProcesadores());
        this.tareasArr = new ArrayList<>(servicios.getTareas());
    }

    public int getCantCandidatos() {
        return this.contadorDeCandidatos;
    }

    public int getTiempoFinal() {
        return this.tiempoFinal;
    }
}