package excepciones;

/**
 * Se lanza cuando se quiere agregar a un arreglo que ya está lleno.
 */
public class AlmacenamientoLlenoException extends Exception {

    public AlmacenamientoLlenoException(String mensaje) {
        super(mensaje);
    }
}
