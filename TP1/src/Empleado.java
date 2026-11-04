/**
 * Empleado del Ejercicio 3.
 * Es una clase abstracta porque no hay un "empleado genérico":
 * cada tipo sabe calcular su propio sueldo.
 */
public abstract class Empleado {

    private String nombre;
    private int legajo;

    public Empleado(String nombre, int legajo) {
        setNombre(nombre);
        setLegajo(legajo);
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

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        if (legajo <= 0) {
            throw new IllegalArgumentException("El legajo debe ser mayor que cero.");
        }
        this.legajo = legajo;
    }

    /**
     * Cada subclase define cómo se calcula su sueldo.
     */
    public abstract double calcularSueldo();

    public void mostrarRecibo() {
        System.out.println("Legajo: " + legajo);
        System.out.println("Empleado: " + nombre);
        System.out.println("Sueldo: $" + calcularSueldo());
    }
}
