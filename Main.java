package ProgramacionIII.tpe;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./ProgramacionIII/tpe/datasets/Procesadores.csv", "./ProgramacionIII/tpe/datasets/Tareas.csv");

		// PARTE 1

		// System.out.println(servicios.servicio1("T2"));
		// System.out.println(servicios.servicio2(true));
		// System.out.println(servicios.servicio3(30,70));

		// PARTE 2

		System.out.println();
		System.out.println();
		System.out.println(servicios.asignarTareas(60));

	}
}