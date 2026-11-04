/**
 * Materia del Ejercicio 1 del TP1.
 * Tiene los atributos encapsulados y valida la calificación.
 */
public class Materia {

    private String nombre;
    private String codigo;
    private int creditos;
    private double calificacion;

    public Materia() {
        this.nombre = "";
        this.codigo = "";
        this.creditos = 0;
        this.calificacion = 0.0;
    }

    public Materia(String nombre, String codigo, int creditos, double calificacion) {
        setNombre(nombre);
        setCodigo(codigo);
        setCreditos(creditos);
        setCalificacion(calificacion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la materia no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la materia no puede estar vacío.");
        }
        this.codigo = codigo;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        if (creditos <= 0) {
            throw new IllegalArgumentException("Los créditos deben ser mayores que cero.");
        }
        this.creditos = creditos;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        if (calificacion < 0 || calificacion > 10) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10.");
        }
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return nombre + " (" + codigo + ") - " + creditos + " créditos - Nota: " + calificacion;
    }
}
