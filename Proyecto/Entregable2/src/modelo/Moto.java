package modelo;

/**
 * Moto: suma 5 minutos por estacionamiento.
 */
public class Moto extends Vehiculo {

    public Moto(String patente, double capacidadKg,
                double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return ((km / getVelocidadKmH()) * 60) + 5;
    }

    @Override
    public String getTipoVehiculo() {
        return "Moto";
    }
}
