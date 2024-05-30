package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String args[]) {
		// Servicios servicios = new Servicios("C:/Users/Usuario/Desktop/tpe-prog-3/datasets/Procesadores.csv", "C:/Users/Usuario/Desktop/tpe-prog-3/datasets/Tareas.csv");
		Servicios servicios = new Servicios("./ProgramacionIII/tpe/datasets/Procesadores.csv", "./ProgramacionIII/tpe/datasets/Tareas.csv");

		// PARTE 1

		// System.out.println(servicios.servicio1("T2"));
		// System.out.println(servicios.servicio2(true));
		// System.out.println(servicios.servicio3(30,70));

		// PARTE 2

		System.out.println();

		Backtracking backtracking = new Backtracking(servicios);

		HashMap<Procesador, ListaTareas> tareasAsignadas = backtracking.asignarTareas(60);

		for (HashMap.Entry<Procesador, ListaTareas> entry : tareasAsignadas.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		System.out.println("Tiempo de ejecución: " + backtracking.getTiempoFinal());
		System.out.println("Cantidad de estados generados: " + backtracking.getCantSoluciones());
	}
}
