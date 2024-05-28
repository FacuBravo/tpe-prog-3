package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.Map;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("C:/Users/Usuario/Desktop/tpe-prog-3/datasets/Procesadores.csv", "C:/Users/Usuario/Desktop/tpe-prog-3/datasets/Tareas.csv");

		// PARTE 1

		// System.out.println(servicios.servicio1("T2"));
		// System.out.println(servicios.servicio2(true));
		// System.out.println(servicios.servicio3(30,70));

		// PARTE 2

		System.out.println();



		Backtracking backtracking = new Backtracking(servicios);

		Map<Procesador, ArrayList<Tarea>> tareasAsignadas = backtracking.asignarTareas(60);

		for(Map.Entry<Procesador, ArrayList<Tarea>> entry : tareasAsignadas.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}




	}
}
