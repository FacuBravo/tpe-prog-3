package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Backtracking {

    private Servicios servicios;
    private HashMap<Procesador, ArrayList<Tarea>> solucionOptima;
    private int tiempoMax;

    public Backtracking(Servicios servicios) {
        this.servicios = servicios;
        this.solucionOptima = new HashMap<>();
        this.tiempoMax = Integer.MIN_VALUE;
    }

    public HashMap<Procesador, ArrayList<Tarea>> asignarTareas(int tiempoMax) {
        this.tiempoMax = tiempoMax;

        ArrayList<Procesador> procesadoresArr = new ArrayList<>(servicios.getProcesadores());
        ArrayList<Tarea> tareasArr = new ArrayList<>(servicios.getTareas());


        this.asignarTareas(procesadoresArr, tareasArr, solucionOptima, 0);

        return this.solucionOptima;
    }

    private void asignarTareas(ArrayList<Procesador> procesadores, ArrayList<Tarea> tareas, HashMap<Procesador, ArrayList<Tarea>> solucion, int indice) {

        if (indice == tareas.size()) {
            HashMap<Procesador, ArrayList<Tarea>> aux = new HashMap<>();

            for (Procesador p : solucion.keySet()) {
                aux.put(p, new ArrayList<>(solucion.get(p)));
            }

            int minTiempo = this.getTiempoFinalEjecucion(this.solucionOptima);
            int tiempo = this.getTiempoFinalEjecucion(aux);

            if (minTiempo == 0) {
                this.solucionOptima = aux;
                minTiempo = tiempo;
            }

            else {
                if (tiempo <= minTiempo) {
                    this.solucionOptima = aux;
                    minTiempo = tiempo;
                }
            }
            return;
        }

        Tarea tarea = tareas.get(indice);

        for (Procesador procesador : procesadores) {
            if (!solucion.containsKey(procesador)) {
                solucion.put(procesador, new ArrayList<>());
            }

            if (this.esApto(solucion.get(procesador), tarea, procesador)) {
                solucion.get(procesador).add(tarea);

                this.asignarTareas(procesadores, tareas, solucion, indice + 1);

                solucion.get(procesador).remove(tarea);
            }
        }
    }

    private int getTiempoFinalEjecucion(HashMap<Procesador, ArrayList<Tarea>> procesamientos) {
        int tiempoFinal = 0, tiempoActual = 0;

        for (ArrayList<Tarea> tareas : procesamientos.values()) {
            for (Tarea t : tareas) {
                tiempoActual += t.getTiempo_ejecucion();
            }

            if (tiempoFinal < tiempoActual) {
                tiempoFinal = tiempoActual;
            }

            tiempoActual = 0;
        }

        return tiempoFinal;
    }

    private boolean esApto(ArrayList<Tarea> tareas, Tarea t, Procesador p) {
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

                if (tiempo > tiempoMax) {
                    esApto = false;
                }
            }
        }

        return esApto;
    }


}
