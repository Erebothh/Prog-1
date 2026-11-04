/**
 * Empleado de planta del Ejercicio 3.
 * Su sueldo depende del sueldo base y de la antigüedad.
 */
public class EmpleadoPlanta extends Empleado {

    private double sueldoBase;
    private int antiguedadAnios;

    public EmpleadoPlanta(String nombre, int legajo, double sueldoBase, int antiguedadAnios) {
        super(nombre, legajo);
        setSueldoBase(sueldoBase);
        setAntiguedadAnios(antiguedadAnios);
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(double sueldoBase) {
        if (sueldoBase <= 0) {
            throw new IllegalArgumentException("El sueldo base debe ser mayor que cero.");
        }
        this.sueldoBase = sueldoBase;
    }

    public int getAntiguedadAnios() {
        return antiguedadAnios;
    }

    public void setAntiguedadAnios(int antiguedadAnios) {
        if (antiguedadAnios < 0) {
            throw new IllegalArgumentException("La antigüedad no puede ser negativa.");
        }
        this.antiguedadAnios = antiguedadAnios;
    }

    @Override
    public double calcularSueldo() {
        return sueldoBase + (sueldoBase * 0.02 * antiguedadAnios);
    }
}
