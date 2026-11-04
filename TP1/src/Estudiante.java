import java.util.ArrayList;

/**
 * Estudiante del Ejercicio 1 del TP1.
 * Ahora los atributos son privados y se accede con getters y setters.
 * Además se relaciona con Materia mediante una lista.
 */
public class Estudiante {

    private String nombre;
    private String apellido;
    private int edad;
    private String carrera;
    private double promedio;
    private ArrayList<Materia> materias;

    public Estudiante() {
        this.nombre = "";
        this.apellido = "";
        this.edad = 0;
        this.carrera = "";
        this.promedio = 0.0;
        this.materias = new ArrayList<Materia>();
    }

    public Estudiante(String nombre, String apellido, int edad, String carrera, double promedio) {
        setNombre(nombre);
        setApellido(apellido);
        setEdad(edad);
        setCarrera(carrera);
        setPromedio(promedio);
        this.materias = new ArrayList<Materia>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad <= 16) {
            throw new IllegalArgumentException("La edad debe ser mayor a 16 años.");
        }
        this.edad = edad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        if (carrera == null || carrera.trim().isEmpty()) {
            throw new IllegalArgumentException("La carrera no puede estar vacía.");
        }
        this.carrera = carrera;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        if (promedio < 0 || promedio > 10) {
            throw new IllegalArgumentException("El promedio debe estar entre 0 y 10.");
        }
        this.promedio = promedio;
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    /**
     * Agrega una materia al listado del estudiante.
     */
    public void agregarMateria(Materia materia) {
        if (materia == null) {
            throw new IllegalArgumentException("La materia no puede ser nula.");
        }
        materias.add(materia);
    }

    /**
     * Calcula el promedio de las calificaciones de todas las materias.
     * Si no hay materias cargadas devuelve 0.
     */
    public double calcularPromedio() {
        if (materias.isEmpty()) {
            return 0.0;
        }

        double suma = 0;
        for (int i = 0; i < materias.size(); i++) {
            suma = suma + materias.get(i).getCalificacion();
        }
        return suma / materias.size();
    }

    public void mostrarDatos() {
        System.out.println("Nombre: " + nombre + " " + apellido);
        System.out.println("Edad: " + edad);
        System.out.println("Carrera: " + carrera);
        System.out.println("Promedio guardado: " + promedio);
        System.out.println("Promedio calculado con las materias: " + calcularPromedio());
        System.out.println("Materias:");
        if (materias.isEmpty()) {
            System.out.println("  (no tiene materias cargadas)");
        } else {
            for (int i = 0; i < materias.size(); i++) {
                System.out.println("  - " + materias.get(i));
            }
        }
    }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}
