package ProgramacionIII.tpe;

import java.util.HashMap;

public class Main {
	public static void main(String args[]) {
		Servicios servicios = new Servicios("./ProgramacionIII/tpe/datasets/Procesadores.csv", "./ProgramacionIII/tpe/datasets/Tareas.csv");

		// PARTE 1

		System.out.println(servicios.servicio1("T2"));
		System.out.println(servicios.servicio2(true));
		System.out.println(servicios.servicio3(30,70));

		// PARTE 2

		/*****************    BACKTRACKING    **************************/

		Backtracking backtracking = new Backtracking(servicios);

		System.out.println("Backtracking");
		HashMap<Procesador, ListaTareas> tareasAsignadasBacktracking = backtracking.asignarTareas(10);

		for (HashMap.Entry<Procesador, ListaTareas> entry : tareasAsignadasBacktracking.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		System.out.println("Tiempo máximo de ejecución: " + backtracking.getTiempoFinal());
		System.out.println("Cantidad de estados generados: " + backtracking.getCantidadDeEstados());


		/*****************    GREEDY    **************************/

		Greedy greedy = new Greedy(servicios);

		HashMap<Procesador, ListaTareas> tareasAsignadasGreedy = greedy.asignarTareas(10);

		System.out.println();
		System.out.println("Greedy");

		for(HashMap.Entry<Procesador, ListaTareas> entry : tareasAsignadasGreedy.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		System.out.println("Tiempo máximo de ejecución: " + greedy.getTiempoFinal());
		System.out.println("Cantidad de candidatos considerados: " + greedy.getCantCandidatos());

	}
}