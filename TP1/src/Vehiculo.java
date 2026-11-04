/**
 * Vehículo del Ejercicio 5.
 * Clase abstracta con los datos comunes a todos los vehículos.
 */
public abstract class Vehiculo {

    private String patente;
    private String marca;
    private String modelo;

    public Vehiculo(String patente, String marca, String modelo) {
        setPatente(patente);
        setMarca(marca);
        setModelo(modelo);
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException("La patente no puede estar vacía.");
        }
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }
        this.modelo = modelo;
    }

    /**
     * Cada vehículo calcula el alquiler de una cantidad de días.
     */
    public abstract double calcularAlquiler(int dias);

    public void mostrarDatos() {
        System.out.println("Patente: " + patente + " - " + marca + " " + modelo);
    }
}
