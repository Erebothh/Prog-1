package app;

import modelo.Cliente;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Repartidor;

/**
 * Guarda los datos de una operación para poder deshacerla.
 * Cada objeto va a la pila del gestor.
 */
public class Operacion {

    public enum TipoOperacion {
        ALTA_CLIENTE,
        BAJA_CLIENTE,
        ALTA_REPARTIDOR,
        ALTA_PEDIDO,
        ASIGNAR_PEDIDO,
        ENTREGAR_PEDIDO,
        CAMBIO_ESTADO
    }

    private TipoOperacion tipo;
    private Cliente cliente;
    private Repartidor repartidor;
    private Pedido pedido;
    private EstadoPedido estadoAnterior;

    private Operacion(TipoOperacion tipo, Cliente cliente, Repartidor repartidor,
                      Pedido pedido, EstadoPedido estadoAnterior) {
        this.tipo = tipo;
        this.cliente = cliente;
        this.repartidor = repartidor;
        this.pedido = pedido;
        this.estadoAnterior = estadoAnterior;
    }

    public static Operacion altaCliente(Cliente cliente) {
        return new Operacion(TipoOperacion.ALTA_CLIENTE, cliente, null, null, null);
    }

    public static Operacion bajaCliente(Cliente cliente) {
        return new Operacion(TipoOperacion.BAJA_CLIENTE, cliente, null, null, null);
    }

    public static Operacion altaRepartidor(Repartidor repartidor) {
        return new Operacion(TipoOperacion.ALTA_REPARTIDOR, null, repartidor, null, null);
    }

    public static Operacion altaPedido(Pedido pedido) {
        return new Operacion(TipoOperacion.ALTA_PEDIDO, null, null, pedido, null);
    }

    public static Operacion asignarPedido(Pedido pedido, Repartidor repartidor) {
        return new Operacion(TipoOperacion.ASIGNAR_PEDIDO, null, repartidor, pedido, null);
    }

    public static Operacion entregarPedido(Pedido pedido, Repartidor repartidor) {
        return new Operacion(TipoOperacion.ENTREGAR_PEDIDO, null, repartidor, pedido, null);
    }

    public static Operacion cambioEstado(Pedido pedido, EstadoPedido estadoAnterior) {
        return new Operacion(TipoOperacion.CAMBIO_ESTADO, null, null, pedido, estadoAnterior);
    }

    public TipoOperacion getTipo() {
        return tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public EstadoPedido getEstadoAnterior() {
        return estadoAnterior;
    }
}
