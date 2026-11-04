package modelo;

import excepciones.PedidoInvalidoException;
import java.time.LocalDateTime;

/**
 * Pedido frágil: suma $800 fijos y no puede ir en bicicleta.
 */
public class PedidoFragil extends Pedido {

    public PedidoFragil(int codigo, Cliente cliente, String destino,
                        double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    public PedidoFragil(int codigo, Cliente cliente, String destino, double pesoKg,
                        double distanciaKm, LocalDateTime fechaHora)
            throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm, fechaHora);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        double costoBase = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
        return costoBase + 800;
    }

    @Override
    public String getTipo() {
        return "Frágil";
    }
}
