import java.util.ArrayList;

/**
 * Universidad del Ejercicio 1.
 * Guarda su nombre, su dirección y un listado de estudiantes.
 */
public class Universidad {

    private String nombre;
    private String direccion;
    private ArrayList<Estudiante> estudiantes;

    public Universidad() {
        this.nombre = "";
        this.direccion = "";
        this.estudiantes = new ArrayList<Estudiante>();
    }

    public Universidad(String nombre, String direccion) {
        setNombre(nombre);
        setDireccion(direccion);
        this.estudiantes = new ArrayList<Estudiante>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la universidad no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía.");
        }
        this.direccion = direccion;
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
        System.out.println("Universidad: " + nombre + " (" + direccion + ")");
        for (int i = 0; i < estudiantes.size(); i++) {
            System.out.println("- " + estudiantes.get(i));
        }
    }
}
