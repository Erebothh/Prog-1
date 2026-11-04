/**
 * Alumno del Ejercicio 2.
 * Hereda de Persona y agrega legajo y promedio.
 */
public class Alumno extends Persona {

    private int legajo;
    private double promedio;

    public Alumno(String nombre, String dni, int legajo, double promedio) {
        super(nombre, dni); // inicializa los atributos heredados
        setLegajo(legajo);
        setPromedio(promedio);
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

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        if (promedio < 0 || promedio > 10) {
            throw new IllegalArgumentException("El promedio debe estar entre 0 y 10.");
        }
        this.promedio = promedio;
    }

    /**
     * Sobrescribo el método de Persona para agregar los datos del alumno.
     */
    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Legajo: " + legajo);
        System.out.println("Promedio: " + promedio);
    }
}
