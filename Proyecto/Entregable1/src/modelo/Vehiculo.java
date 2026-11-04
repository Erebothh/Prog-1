package modelo;

/**
 * Vehículo de CityLink.
 * Es abstracto porque cada tipo tiene su propia velocidad y tiempo extra.
 */
public abstract class Vehiculo {

    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

    public Vehiculo(String patente, double capacidadKg,
                    double velocidadKmH, double costoPorKm) {
        setPatente(patente);
        setCapacidadKg(capacidadKg);
        setVelocidadKmH(velocidadKmH);
        setCostoPorKm(costoPorKm);
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException("La patente no puede estar vacía.");
        }
        this.patente = patente.trim();
    }

    public double getCapacidadKg() {
        return capacidadKg;
    }

    public void setCapacidadKg(double capacidadKg) {
        if (capacidadKg <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.capacidadKg = capacidadKg;
    }

    public double getVelocidadKmH() {
        return velocidadKmH;
    }

    public void setVelocidadKmH(double velocidadKmH) {
        if (velocidadKmH <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser mayor que cero.");
        }
        this.velocidadKmH = velocidadKmH;
    }

    public double getCostoPorKm() {
        return costoPorKm;
    }

    public void setCostoPorKm(double costoPorKm) {
        if (costoPorKm <= 0) {
            throw new IllegalArgumentException("El costo por km debe ser mayor que cero.");
        }
        this.costoPorKm = costoPorKm;
    }

    /**
     * Regla general: el pedido entra si no supera la capacidad.
     * Bicicleta va a agregar además la restricción de los frágiles.
     */
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido == null) {
            return false;
        }
        return pedido.getPesoKg() <= capacidadKg;
    }

    /**
     * Calcula el tiempo en minutos para recorrer una distancia.
     */
    public abstract double calcularTiempoEntrega(double km);

    /**
     * Devuelve el nombre del tipo de vehículo.
     */
    public abstract String getTipoVehiculo();

    @Override
    public String toString() {
        return getTipoVehiculo() + " " + patente
                + " - Capacidad: " + capacidadKg + " kg"
                + " - Velocidad: " + velocidadKmH + " km/h"
                + " - Costo por km: $" + costoPorKm;
    }
}
