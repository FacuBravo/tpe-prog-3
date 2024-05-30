package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String args[]) {
		 Servicios servicios = new Servicios("C:/Users/Usuario/Desktop/tpe-prog-3/datasets/Procesadores.csv", "C:/Users/Usuario/Desktop/tpe-prog-3/datasets/Tareas.csv");
		//Servicios servicios = new Servicios("./ProgramacionIII/tpe/datasets/Procesadores.csv", "./ProgramacionIII/tpe/datasets/Tareas.csv");

		// PARTE 1

		// System.out.println(servicios.servicio1("T2"));
		// System.out.println(servicios.servicio2(true));
		// System.out.println(servicios.servicio3(30,70));

		// PARTE 2

		System.out.println();

		/*****************    BACKTRACKING    **************************/

		Backtracking backtracking = new Backtracking(servicios);

		System.out.println("Backtracking");
		HashMap<Procesador, ListaTareas> tareasAsignadasBacktracking = backtracking.asignarTareas(60);

		for (HashMap.Entry<Procesador, ListaTareas> entry : tareasAsignadasBacktracking.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		System.out.println("Tiempo máximo de ejecución: " + backtracking.getTiempoFinal());
		System.out.println("Cantidad de soluciones generadas: " + backtracking.getCantSoluciones());
		System.out.println("Cantidad de estados generados: " + backtracking.getCantidadDeEstados());

		/*****************    GREEDY    **************************/

		Greedy greedy = new Greedy(servicios);

		Map<Procesador, ListaTareas> tareasAsignadasGreedy = greedy.asignarTareas(60);
		System.out.println();
		System.out.println("Greedy");
		for(Map.Entry<Procesador, ListaTareas> entry : tareasAsignadasGreedy.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		System.out.println("Tiempo máximo de ejecución: " + greedy.getTiempoFinal());
		System.out.println("Cantidad de candidatos considerados: " + greedy.getCantCandidatos());
	}
}
