/**
 * Actividad del TP0: crear la clase Estudiante, instanciar objetos
 * de las dos formas y recorrer un arreglo.
 */
public class Main {

    public static void main(String[] args) {

        // Dos estudiantes usando el constructor sin parámetros.
        // Después les asigno los datos con el operador punto.
        Estudiante e1 = new Estudiante();
        e1.nombre = "Ana";
        e1.apellido = "García";
        e1.edad = 20;
        e1.carrera = "Licenciatura en Sistemas";
        e1.promedio = 8.5;

        Estudiante e2 = new Estudiante();
        e2.nombre = "Bruno";
        e2.apellido = "Pérez";
        e2.edad = 22;
        e2.carrera = "Contador Público";
        e2.promedio = 7.25;

        // Dos estudiantes usando el constructor con parámetros.
        Estudiante e3 = new Estudiante("Carla", "López", 19, "Ingeniería Civil", 9.1);
        Estudiante e4 = new Estudiante("Diego", "Martínez", 21, "Licenciatura en Sistemas", 6.75);

        // Los guardo en un arreglo de Estudiante.
        Estudiante[] estudiantes = new Estudiante[4];
        estudiantes[0] = e1;
        estudiantes[1] = e2;
        estudiantes[2] = e3;
        estudiantes[3] = e4;

        System.out.println("=== LISTADO DE ESTUDIANTES ===");

        // Recorro el arreglo y muestro nombre y promedio usando el punto.
        for (int i = 0; i < estudiantes.length; i++) {
            Estudiante actual = estudiantes[i];
            System.out.println(actual.nombre + " " + actual.apellido
                    + " - Promedio: " + actual.promedio);
        }

        System.out.println();
        System.out.println("=== DATOS COMPLETOS ===");
        for (int i = 0; i < estudiantes.length; i++) {
            estudiantes[i].mostrarDatos();
            System.out.println("-------------------------");
        }
    }
}
