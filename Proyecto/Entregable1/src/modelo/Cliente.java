package modelo;

/**
 * Cliente de CityLink.
 * Todos los atributos son privados y los setters validan los datos.
 */
public class Cliente {

    private int codigo;
    private String nombre;
    private String telefono;
    private String zona;

    /**
     * Constructor vacío.
     */
    public Cliente() {
        this.codigo = 0;
        this.nombre = "";
        this.telefono = "";
        this.zona = "";
    }

    /**
     * Constructor completo.
     */
    public Cliente(int codigo, String nombre, String telefono, String zona) {
        setCodigo(codigo);
        setNombre(nombre);
        setTelefono(telefono);
        setZona(zona);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El código del cliente debe ser mayor que cero.");
        }
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono del cliente no puede estar vacío.");
        }
        this.telefono = telefono.trim();
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        if (zona == null || zona.trim().isEmpty()) {
            throw new IllegalArgumentException("La zona del cliente no puede estar vacía.");
        }
        this.zona = zona.trim();
    }

    @Override
    public String toString() {
        return "Cliente " + codigo + " - " + nombre
                + " - Tel: " + telefono + " - Zona: " + zona;
    }
}
