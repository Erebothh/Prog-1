/**
 * Clase Estudiante para los ejercicios extras del TP0.
 * Le agregamos algunos métodos para no repetir código en el Main.
 */
public class Estudiante {

    public String nombre;
    public String apellido;
    public int edad;
    public String carrera;
    public double promedio;

    public Estudiante() {
        this.nombre = "";
        this.apellido = "";
        this.edad = 0;
        this.carrera = "";
        this.promedio = 0.0;
    }

    public Estudiante(String nombre, String apellido, int edad, String carrera, double promedio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    /**
     * Ejercicio 1: muestra toda la información del estudiante.
     */
    public void mostrarInformacion() {
        System.out.println("Nombre y apellido: " + nombre + " " + apellido);
        System.out.println("Edad: " + edad);
        System.out.println("Carrera: " + carrera);
        System.out.println("Promedio: " + promedio);
    }

    public String obtenerNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Ejercicio 7: devuelve true si el promedio llega a la nota de corte.
     */
    public boolean estaAprobado(double notaCorte) {
        return promedio >= notaCorte;
    }
}
