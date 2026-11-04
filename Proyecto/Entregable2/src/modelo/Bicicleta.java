package modelo;

/**
 * Bicicleta: no puede llevar pedidos frágiles y no tiene tiempo extra.
 */
public class Bicicleta extends Vehiculo {

    public Bicicleta(String patente, double capacidadKg,
                     double velocidadKmH, double costoPorKm) {
        super(patente, capacidadKg, velocidadKmH, costoPorKm);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60;
    }

    /**
     * Además de la capacidad, rechaza los pedidos frágiles.
     */
    @Override
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido instanceof PedidoFragil) {
            return false;
        }
        return super.puedeTransportar(pedido);
    }

    @Override
    public String getTipoVehiculo() {
        return "Bicicleta";
    }
}
