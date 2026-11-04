/**
 * Clase Estudiante del TP0.
 * Todavía no usamos encapsulamiento: los atributos son públicos porque
 * en este práctico recién estamos viendo clases, objetos y constructores.
 */
public class Estudiante {

    public String nombre;
    public String apellido;
    public int edad;
    public String carrera;
    public double promedio;

    /**
     * Constructor sin parámetros.
     * Java lo agregaría solo, pero lo escribimos igual para practicar.
     */
    public Estudiante() {
        this.nombre = "";
        this.apellido = "";
        this.edad = 0;
        this.carrera = "";
        this.promedio = 0.0;
    }

    /**
     * Constructor con todos los parámetros.
     * Uso this para aclarar que me refiero a los atributos del objeto.
     */
    public Estudiante(String nombre, String apellido, int edad, String carrera, double promedio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    /**
     * Muestra los datos del estudiante en pantalla.
     */
    public void mostrarDatos() {
        System.out.println("Nombre: " + nombre + " " + apellido);
        System.out.println("Edad: " + edad);
        System.out.println("Carrera: " + carrera);
        System.out.println("Promedio: " + promedio);
    }
}
