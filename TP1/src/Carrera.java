import java.util.ArrayList;

/**
 * Carrera del Ejercicio 1.
 * Se relaciona con Estudiante porque guarda un listado de ellos.
 */
public class Carrera {

    private String nombre;
    private ArrayList<Estudiante> estudiantes;

    public Carrera() {
        this.nombre = "";
        this.estudiantes = new ArrayList<Estudiante>();
    }

    public Carrera(String nombre) {
        setNombre(nombre);
        this.estudiantes = new ArrayList<Estudiante>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la carrera no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo.");
        }
        estudiantes.add(estudiante);
    }

    public void listarEstudiantes() {
        System.out.println("Carrera: " + nombre);
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes inscriptos.");
        } else {
            for (int i = 0; i < estudiantes.size(); i++) {
                Estudiante actual = estudiantes.get(i);
                System.out.println((i + 1) + ") " + actual.getNombre() + " "
                        + actual.getApellido() + " - Promedio: " + actual.calcularPromedio());
            }
        }
    }

    /**
     * Busca un estudiante por nombre (sin distinguir mayúsculas).
     * Devuelve null si no lo encuentra.
     */
    public Estudiante buscarEstudiante(String nombre) {
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante actual = estudiantes.get(i);
            if (actual.getNombre().equalsIgnoreCase(nombre)) {
                return actual;
            }
        }
        return null;
    }
}
