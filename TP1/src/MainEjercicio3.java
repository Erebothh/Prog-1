/**
 * Ejercicio 3 del TP1: clases abstractas y sobrescritura.
 */
public class MainEjercicio3 {

    public static void main(String[] args) {

        EmpleadoPlanta planta1 = new EmpleadoPlanta("Ana García", 101, 500000, 5);
        EmpleadoPlanta planta2 = new EmpleadoPlanta("Bruno Pérez", 102, 450000, 2);

        EmpleadoContratado contratado1 = new EmpleadoContratado("Carla López", 201, 80, 6000);
        EmpleadoContratado contratado2 = new EmpleadoContratado("Diego Martínez", 202, 120, 5500);

        System.out.println("=== EMPLEADOS DE PLANTA ===");
        planta1.mostrarRecibo();
        System.out.println("-------------------------");
        planta2.mostrarRecibo();

        System.out.println();
        System.out.println("=== EMPLEADOS CONTRATADOS ===");
        contratado1.mostrarRecibo();
        System.out.println("-------------------------");
        contratado2.mostrarRecibo();
    }
}
