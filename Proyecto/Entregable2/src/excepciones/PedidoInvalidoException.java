package excepciones;

/**
 * Se lanza cuando los datos de un pedido no son válidos:
 * peso o distancia menores o iguales a cero, destino vacío o código repetido.
 */
public class PedidoInvalidoException extends Exception {

    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
