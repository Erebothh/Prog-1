package excepciones;

/**
 * Se lanza cuando se busca un cliente y no existe.
 * Es una excepción checked, por eso extiende Exception.
 */
public class ClienteNoEncontradoException extends Exception {

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
