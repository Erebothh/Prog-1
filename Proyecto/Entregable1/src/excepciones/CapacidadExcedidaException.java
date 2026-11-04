package excepciones;

/**
 * Se lanza cuando un pedido no entra en un vehículo por peso
 * o porque es frágil y el vehículo es una bicicleta.
 */
public class CapacidadExcedidaException extends Exception {

    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}
