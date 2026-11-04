/**
 * Empleado contratado del Ejercicio 3.
 * Cobra según las horas trabajadas y el valor de la hora.
 */
public class EmpleadoContratado extends Empleado {

    private int horasTrabajadas;
    private double valorHora;

    public EmpleadoContratado(String nombre, int legajo, int horasTrabajadas, double valorHora) {
        super(nombre, legajo);
        setHorasTrabajadas(horasTrabajadas);
        setValorHora(valorHora);
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        if (horasTrabajadas < 0) {
            throw new IllegalArgumentException("Las horas trabajadas no pueden ser negativas.");
        }
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        if (valorHora <= 0) {
            throw new IllegalArgumentException("El valor de la hora debe ser mayor que cero.");
        }
        this.valorHora = valorHora;
    }

    @Override
    public double calcularSueldo() {
        return horasTrabajadas * valorHora;
    }
}
