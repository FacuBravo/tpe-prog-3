package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Greedy {

    private Servicios servicios;
    private HashMap<Procesador, ListaTareas> solucionOptima;
    private int contadorDeCandidatos;



    public Greedy(Servicios servicios) {
        this.servicios = servicios;
        this.solucionOptima = new HashMap<>();
        this.contadorDeCandidatos = 0;
    }

    public HashMap<Procesador, ListaTareas> asignarTareas(int tiempoProcesadoresRefrigerados) {

        ArrayList<Procesador> procesadoresArr = new ArrayList<>(servicios.getProcesadores());
        ArrayList<Tarea> tareasArr = new ArrayList<>(servicios.getTareas());

        Collections.sort(tareasArr, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea t1, Tarea t2) {
                return Integer.compare(t1.getTiempo_ejecucion(), t2.getTiempo_ejecucion());
            }
        });

        HashMap<Procesador, ListaTareas> solucionGreedy = new HashMap<>();
        for (Procesador procesador : procesadoresArr) {
            solucionGreedy.put(procesador, new ListaTareas());
        }

        for (Tarea tarea : tareasArr) {
            Procesador mejorProcesador = null;
            int menorTiempo = Integer.MAX_VALUE;

            for (Procesador procesador : procesadoresArr) {

                this.contadorDeCandidatos++;
                if (this.esApto(solucionGreedy.get(procesador), tarea, procesador, tiempoProcesadoresRefrigerados)) {
                    int tiempoActual = this.getTiempoEjecucion(solucionGreedy.get(procesador)) + tarea.getTiempo_ejecucion();
                    if (tiempoActual < menorTiempo) {
                        menorTiempo = tiempoActual;
                        mejorProcesador = procesador;
                    }
                }
            }

            if (mejorProcesador != null) {
                solucionGreedy.get(mejorProcesador).add(tarea);
            }
        }

        this.solucionOptima = solucionGreedy;  // Guardar la solución óptima
        return solucionGreedy;
    }
    private int getTiempoEjecucion(ListaTareas tareas) {
        int tiempoFinal = 0;
        for (Tarea t : tareas) {
            tiempoFinal += t.getTiempo_ejecucion();
        }
        return tiempoFinal;
    }

    private boolean esApto(ListaTareas tareas, Tarea t, Procesador p, int tiempoProcesadoresRefrigerados) {
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

                if (tiempo > tiempoProcesadoresRefrigerados) {
                    esApto = false;
                }
            }
        }

        return esApto;
    }

    private int getTiempoFinal(HashMap<Procesador, ListaTareas> procesamientos) {
        int tiempoFinal = 0;

        for (Procesador p : procesamientos.keySet()) {
            int tiempoEjecucion = procesamientos.get(p).getTiempoEjecucion();

            if (tiempoEjecucion > tiempoFinal || tiempoFinal == 0) {
                tiempoFinal = tiempoEjecucion;
            }
        }

        return tiempoFinal;
    }

    public int getTiempoFinal(){
        return getTiempoFinal(this.solucionOptima);
    }

    public int getCantCandidatos() {
        return contadorDeCandidatos;
    }
}
