/**
 * Auto del Ejercicio 5.
 * Cobra un precio fijo por día.
 */
public class Auto extends Vehiculo {

    private double precioPorDia;

    public Auto(String patente, String marca, String modelo, double precioPorDia) {
        super(patente, marca, modelo);
        setPrecioPorDia(precioPorDia);
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        if (precioPorDia <= 0) {
            throw new IllegalArgumentException("El precio por día debe ser mayor que cero.");
        }
        this.precioPorDia = precioPorDia;
    }

    @Override
    public double calcularAlquiler(int dias) {
        return dias * precioPorDia;
    }
}
