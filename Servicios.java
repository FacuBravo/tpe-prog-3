package ProgramacionIII.tpe;

import ProgramacionIII.tpe.utils.CSVReader;

import java.util.*;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */

public class Servicios {
	private Hashtable<String, Tarea> tareas;
	private Hashtable<String, Procesador> procesadores;
	private Hashtable<Boolean, ArrayList<Tarea>> tareasByCritica;
	private Hashtable<Procesador, ArrayList<Tarea>> solucionOptima;
	private int tiempoMax;

	/*
	 * Expresar la complejidad temporal del constructor.
	 *
	 * La complejidad computacional del constructor es O(n + m), ya que debe
	 * recorrer cada linea de los archivos de tareas y procesadores.
	 */

	public Servicios(String pathProcesadores, String pathTareas) {
		this.tareas = new Hashtable<>();
		this.procesadores = new Hashtable<>();
		this.tareasByCritica = new Hashtable<>();
		this.solucionOptima = new Hashtable<>();

		CSVReader reader = new CSVReader();
		reader.readProcessors(pathProcesadores, procesadores);
		reader.readTasks(pathTareas, tareas);

		ordenarTareasByCritica();
	}

	/*
	 * Expresar la complejidad temporal del servicio 1.
	 * 
	 * La complejidad computacional del servicio 1 es O(1).
	 * Ya que el Hashtable utiliza la función de hashing
	 * para saber donde está ubicada la información a la que queremos acceder,
	 * si es que está almacenada.
	 * 
	 */

	public Tarea servicio1(String ID) {
		return tareas.get(ID);
	}

	/*
	 * Expresar la complejidad temporal del servicio 2.
	 *
	 * La complejidad computacional del servicio 2 es O(1).
	 * Porque, al igual que en el servicio 1, usamos el Hashtable
	 * y la función de hashing para obtener la ubicación del elemento
	 * con un solo acceso a memoria.
	 * 
	 */

	public List<Tarea> servicio2(boolean esCritica) {
		return tareasByCritica.get(esCritica);
	}

	private void ordenarTareasByCritica() {
		for (Tarea tarea : tareas.values()) {
			if (tareasByCritica.get(tarea.getEs_critica()) == null) {
				tareasByCritica.put(tarea.getEs_critica(), new ArrayList<Tarea>());
				tareasByCritica.get(tarea.getEs_critica()).add(tarea);
			}

			else {
				tareasByCritica.get(tarea.getEs_critica()).add(tarea);
			}
		}
	}

	/*
	 * Expresar la complejidad temporal del servicio 3.
	 *
	 * La complejidad computacional del servicio 3 es O(n).
	 * Ya que debemos recorrer toda nuestra lista de tareas para
	 * decidir si cumplen o no con el rango de prioridad que queremos
	 * 
	 */

	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		List<Tarea> result = new ArrayList<>();

		for (Tarea t : this.tareas.values()) {
			if (t.getNivel_prioridad() >= prioridadInferior && t.getNivel_prioridad() <= prioridadSuperior) {
				result.add(t);
			}
		}

		return result;
	}

	// PARTE 2

	public Hashtable<Procesador, ArrayList<Tarea>> asignarTareas(int tiempoMax) {
		this.tiempoMax = tiempoMax;

		ArrayList<Procesador> procesadoresArr = new ArrayList<>();
		ArrayList<Tarea> tareasArr = new ArrayList<>();

		procesadoresArr.addAll(this.procesadores.values());
		tareasArr.addAll(this.tareas.values());

		this.asignarTareas(procesadoresArr, tareasArr, solucionOptima, 0);

		return this.solucionOptima;
	}

	private void asignarTareas(ArrayList<Procesador> procesadoresArr, ArrayList<Tarea> tareasArr, Hashtable<Procesador, ArrayList<Tarea>> solucion, int indice) {
		if (indice == tareasArr.size()) {
			Hashtable<Procesador, ArrayList<Tarea>> aux = new Hashtable<>();

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

		Tarea tarea = tareasArr.get(indice);

		for (Procesador procesador : procesadoresArr) {
			if (!solucion.containsKey(procesador)) {
				solucion.put(procesador, new ArrayList<>());
			}

			if (this.esApto(solucion.get(procesador), tarea, procesador)) {
				solucion.get(procesador).add(tarea);

				this.asignarTareas(procesadoresArr, tareasArr, solucion, indice + 1);

				solucion.get(procesador).remove(tarea);
			}
		}
	}

	private int getTiempoFinalEjecucion(Hashtable<Procesador, ArrayList<Tarea>> procesamientos) {
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