package excepciones;

/**
 * Se lanza cuando se intenta una transición de estado que no está permitida.
 * Es unchecked porque es un error de uso del sistema.
 */
public class EstadoInvalidoException extends RuntimeException {

    public EstadoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
