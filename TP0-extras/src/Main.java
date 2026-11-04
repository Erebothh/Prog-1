import java.util.Scanner;

/**
 * Resolución de los 7 ejercicios extras del TP0.
 * Puse todo en un mismo programa para poder probarlo de corrido,
 * con un comentario separando cada ejercicio.
 */
public class Main {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        Estudiante[] estudiantes = new Estudiante[5];
        estudiantes[0] = new Estudiante("Ana", "García", 20, "Sistemas", 8.5);
        estudiantes[1] = new Estudiante("Bruno", "Pérez", 22, "Contador", 7.25);
        estudiantes[2] = new Estudiante("Carla", "López", 19, "Civil", 9.1);
        estudiantes[3] = new Estudiante("Diego", "Martínez", 21, "Sistemas", 5.75);
        estudiantes[4] = new Estudiante("Elena", "Rodríguez", 23, "Abogacía", 6.0);

        // -----------------------------------------------------------------
        // Ejercicio 1: mostrar la información completa de todos.
        // -----------------------------------------------------------------
        System.out.println("=== EJERCICIO 1: INFORMACIÓN COMPLETA ===");
        for (int i = 0; i < estudiantes.length; i++) {
            estudiantes[i].mostrarInformacion();
            System.out.println("---------------------------------------");
        }

        // -----------------------------------------------------------------
        // Ejercicio 2: promedio general del curso.
        // -----------------------------------------------------------------
        System.out.println("=== EJERCICIO 2: PROMEDIO GENERAL ===");
        double sumaPromedios = 0;
        for (int i = 0; i < estudiantes.length; i++) {
            sumaPromedios = sumaPromedios + estudiantes[i].promedio;
        }
        double promedioGeneral = sumaPromedios / estudiantes.length;
        System.out.printf("El promedio general del grupo es: %.2f%n", promedioGeneral);
        System.out.println();

        // -----------------------------------------------------------------
        // Ejercicio 3: estudiante con el mejor promedio.
        // -----------------------------------------------------------------
        System.out.println("=== EJERCICIO 3: MEJOR PROMEDIO ===");
        double mejorPromedio = estudiantes[0].promedio;
        int posicionMejor = 0;

        for (int i = 1; i < estudiantes.length; i++) {
            if (estudiantes[i].promedio > mejorPromedio) {
                mejorPromedio = estudiantes[i].promedio;
                posicionMejor = i;
            }
        }

        Estudiante mejor = estudiantes[posicionMejor];
        System.out.println("Mejor promedio: " + mejor.nombre + " " + mejor.apellido
                + " con " + mejor.promedio);
        System.out.println();

        // -----------------------------------------------------------------
        // Ejercicio 4: estudiantes aprobados.
        // -----------------------------------------------------------------
        System.out.println("=== EJERCICIO 4: APROBADOS ===");
        System.out.print("Ingrese la nota de aprobación: ");
        double notaCorte = leerDouble(teclado, 6.0);

        int aprobados = 0;
        for (int i = 0; i < estudiantes.length; i++) {
            if (estudiantes[i].promedio >= notaCorte) {
                System.out.println(estudiantes[i].nombre + " " + estudiantes[i].apellido
                        + " - Promedio: " + estudiantes[i].promedio);
                aprobados++;
            }
        }
        System.out.println("Cantidad de aprobados: " + aprobados);
        System.out.println();

        // -----------------------------------------------------------------
        // Ejercicio 5: buscar un estudiante por nombre.
        // -----------------------------------------------------------------
        System.out.println("=== EJERCICIO 5: BUSCAR POR NOMBRE ===");
        System.out.print("Ingrese el nombre a buscar: ");
        String nombreBuscado = teclado.nextLine().trim();

        boolean encontrado = false;
        for (int i = 0; i < estudiantes.length; i++) {
            if (estudiantes[i].nombre.equalsIgnoreCase(nombreBuscado)) {
                estudiantes[i].mostrarInformacion();
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún estudiante con ese nombre.");
        }
        System.out.println();

        // -----------------------------------------------------------------
        // Ejercicio 6: clasificación según el promedio.
        // -----------------------------------------------------------------
        System.out.println("=== EJERCICIO 6: CLASIFICACIÓN ===");
        for (int i = 0; i < estudiantes.length; i++) {
            Estudiante actual = estudiantes[i];
            String clasificacion;

            if (actual.promedio >= 9) {
                clasificacion = "Excelente";
            } else if (actual.promedio >= 8) {
                clasificacion = "Muy bueno";
            } else if (actual.promedio >= 6) {
                clasificacion = "Aprobado";
            } else if (actual.promedio >= 4) {
                clasificacion = "En recuperación";
            } else {
                clasificacion = "Libre";
            }

            System.out.println(actual.nombre + " " + actual.apellido
                    + ": " + clasificacion + " (" + actual.promedio + ")");
        }
        System.out.println();

        // -----------------------------------------------------------------
        // Ejercicio 7: usar un método que devuelve boolean.
        // -----------------------------------------------------------------
        System.out.println("=== EJERCICIO 7: MÉTODO CON CONDICIÓN ===");
        System.out.println("Estudiantes que pueden rendir el final (promedio >= 6):");
        for (int i = 0; i < estudiantes.length; i++) {
            if (estudiantes[i].estaAprobado(6.0)) {
                System.out.println("- " + estudiantes[i].obtenerNombreCompleto());
            }
        }

        teclado.close();
    }

    /**
     * Lee un double controlando que el usuario no escriba cualquier cosa.
     * Si el dato está mal, devuelve el valor por defecto.
     */
    public static double leerDouble(Scanner teclado, double porDefecto) {
        try {
            String linea = teclado.nextLine();
            return Double.parseDouble(linea);
        } catch (NumberFormatException e) {
            System.out.println("No era un número válido. Uso " + porDefecto + " por defecto.");
            return porDefecto;
        }
    }
}
