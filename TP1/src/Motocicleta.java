/**
 * Motocicleta del Ejercicio 5.
 * Además del precio por día, suma un pequeño cargo por el casco.
 */
public class Motocicleta extends Vehiculo {

    private double precioPorDia;
    private double cargoCasco;

    public Motocicleta(String patente, String marca, String modelo,
                       double precioPorDia, double cargoCasco) {
        super(patente, marca, modelo);
        setPrecioPorDia(precioPorDia);
        setCargoCasco(cargoCasco);
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

    public double getCargoCasco() {
        return cargoCasco;
    }

    public void setCargoCasco(double cargoCasco) {
        if (cargoCasco < 0) {
            throw new IllegalArgumentException("El cargo por casco no puede ser negativo.");
        }
        this.cargoCasco = cargoCasco;
    }

    @Override
    public double calcularAlquiler(int dias) {
        return (dias * precioPorDia) + cargoCasco;
    }
}
