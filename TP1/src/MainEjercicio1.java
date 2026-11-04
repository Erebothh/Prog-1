/**
 * Ejercicio 1 del TP1: encapsulamiento y relaciones entre clases.
 */
public class MainEjercicio1 {

    public static void main(String[] args) {

        // Creo la carrera.
        Carrera carrera = new Carrera("Licenciatura en Sistemas");

        // Creo las materias.
        Materia programacion = new Materia("Programación I", "PROG1", 8, 8.5);
        Materia matematica = new Materia("Matemática", "MAT1", 6, 7.0);
        Materia ingles = new Materia("Inglés Técnico", "ING1", 4, 9.0);

        Materia baseDatos = new Materia("Base de Datos", "BD1", 6, 8.0);
        Materia redes = new Materia("Redes", "RED1", 6, 6.5);
        Materia algebra = new Materia("Álgebra", "ALG1", 6, 7.5);

        // Creo los estudiantes y les asigno materias.
        Estudiante ana = new Estudiante("Ana", "García", 20, "Licenciatura en Sistemas", 0);
        ana.agregarMateria(programacion);
        ana.agregarMateria(matematica);
        ana.agregarMateria(ingles);
        ana.setPromedio(ana.calcularPromedio());

        Estudiante bruno = new Estudiante("Bruno", "Pérez", 22, "Licenciatura en Sistemas", 0);
        bruno.agregarMateria(baseDatos);
        bruno.agregarMateria(redes);
        bruno.agregarMateria(algebra);
        bruno.setPromedio(bruno.calcularPromedio());

        Estudiante carla = new Estudiante("Carla", "López", 19, "Licenciatura en Sistemas", 9.0);
        carla.agregarMateria(programacion);
        carla.agregarMateria(baseDatos);

        // Agrego los estudiantes a la carrera.
        carrera.agregarEstudiante(ana);
        carrera.agregarEstudiante(bruno);
        carrera.agregarEstudiante(carla);

        // Muestro el promedio de cada uno.
        System.out.println("=== PROMEDIOS ===");
        System.out.println(ana.getNombre() + ": " + ana.calcularPromedio());
        System.out.println(bruno.getNombre() + ": " + bruno.calcularPromedio());
        System.out.println(carla.getNombre() + ": " + carla.calcularPromedio());

        System.out.println();
        System.out.println("=== ESTUDIANTES DE LA CARRERA ===");
        carrera.listarEstudiantes();

        System.out.println();
        System.out.println("=== BÚSQUEDA POR NOMBRE ===");
        Estudiante encontrada = carrera.buscarEstudiante("Ana");
        if (encontrada != null) {
            encontrada.mostrarDatos();
        } else {
            System.out.println("No se encontró a la estudiante.");
        }

        // Pruebo también la clase Universidad, que pide el enunciado.
        System.out.println();
        System.out.println("=== UNIVERSIDAD ===");
        Universidad universidad = new Universidad("Universidad Nacional", "Av. Central 1234");
        universidad.agregarEstudiante(ana);
        universidad.agregarEstudiante(bruno);
        universidad.listarEstudiantes();
    }
}
