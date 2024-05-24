package ProgramacionIII.tpe;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./ProgramacionIII/tpe/datasets/Procesadores.csv", "./ProgramacionIII/tpe/datasets/Tareas.csv");

		System.out.println(servicios.servicio1("T2"));
		System.out.println(servicios.servicio2(true));
		System.out.println(servicios.servicio3(30,70));
	}
}