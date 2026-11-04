/**
 * Ejercicio 2 del TP1: herencia y constructores.
 */
public class MainEjercicio2 {

    public static void main(String[] args) {

        Alumno alumno1 = new Alumno("Ana García", "40111222", 1001, 8.5);
        Alumno alumno2 = new Alumno("Bruno Pérez", "40222333", 1002, 7.25);
        Alumno alumno3 = new Alumno("Carla López", "40333444", 1003, 9.1);

        System.out.println("=== DATOS DEL ALUMNO 1 ===");
        alumno1.mostrarDatos();

        System.out.println();
        System.out.println("=== DATOS DEL ALUMNO 2 ===");
        alumno2.mostrarDatos();

        System.out.println();
        System.out.println("=== DATOS DEL ALUMNO 3 ===");
        alumno3.mostrarDatos();

        // También puedo usar una referencia Persona para comprobar la herencia.
        System.out.println();
        System.out.println("=== COMO PERSONA ===");
        Persona persona = alumno1;
        persona.mostrarDatos(); // ejecuta la versión sobrescrita en Alumno
    }
}
