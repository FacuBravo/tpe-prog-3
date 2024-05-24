package ProgramacionIII.tpe;

import ProgramacionIII.tpe.utils.CSVReader;

import java.util.*;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
	private Map<String, Tarea> tareas;
	private Map<String, Procesador> procesadores;

	private Map<Boolean, ArrayList<Tarea>> tareasByCritica;

	/** PARTE DOS */

	/*
	 * Expresar la complejidad temporal del constructor.
	 *
	 * La complejidad computacional del constructor es O(n + m), ya que debe
	 * recorrer cada linea de los archivos de tareas y procesadores.
	 */

	public Servicios(String pathProcesadores, String pathTareas) {
		this.tareas = new HashMap<>();
		this.procesadores = new HashMap<>();
		this.tareasByCritica = new HashMap<>();

		CSVReader reader = new CSVReader();
		reader.readProcessors(pathProcesadores, procesadores);
		reader.readTasks(pathTareas, tareas);

		ordenarTareasByCritica();
	}

	/*
	 * Expresar la complejidad temporal del servicio 1.
	 * 
	 * La complejidad computacional del servicio 1 es O(1).
	 * Ya que el HashMap utiliza la función de hashing
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
	 * Porque, al igual que en el servicio 1, usamos el HashMap
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

}