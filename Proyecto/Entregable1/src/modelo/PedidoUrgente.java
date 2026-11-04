package modelo;

import excepciones.PedidoInvalidoException;
import java.time.LocalDateTime;

/**
 * Pedido urgente: suma un 50% sobre el costo normal.
 */
public class PedidoUrgente extends Pedido {

    public PedidoUrgente(int codigo, Cliente cliente, String destino,
                         double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    public PedidoUrgente(int codigo, Cliente cliente, String destino, double pesoKg,
                         double distanciaKm, LocalDateTime fechaHora)
            throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm, fechaHora);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        double costoBase = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
        return costoBase * 1.5;
    }

    @Override
    public String getTipo() {
        return "Urgente";
    }
}
