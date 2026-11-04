package excepciones;

/**
 * Se lanza cuando se quiere atender un pedido y no hay ninguno pendiente.
 * Es checked para obligar al menú a informar la situación.
 */
public class SinPedidosPendientesException extends Exception {

    public SinPedidosPendientesException(String mensaje) {
        super(mensaje);
    }
}
