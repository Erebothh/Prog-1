package modelo;

/**
 * Camioneta: suma 10 minutos por carga y descarga.
 */
public class Camioneta extends Vehiculo {

    public Camioneta(String patente, double capacidadKg,
                     double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return ((km / getVelocidadKmH()) * 60) + 10;
    }

    @Override
    public String getTipoVehiculo() {
        return "Camioneta";
    }
}
