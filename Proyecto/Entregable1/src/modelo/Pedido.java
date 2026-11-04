package modelo;

import excepciones.EstadoInvalidoException;
import excepciones.PedidoInvalidoException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Pedido de CityLink.
 * Es abstracta porque el costo cambia según el tipo de pedido.
 * Implementa Rastreable y Facturable (herencia múltiple de interfaces).
 */
public abstract class Pedido implements Rastreable, Facturable {

    private int codigo;
    private Cliente cliente;
    private String destino;
    private double pesoKg;
    private double distanciaKm;
    private LocalDateTime fechaHora;
    private EstadoPedido estado;
    private Vehiculo vehiculoAsignado;

    /**
     * Constructor sin fecha: usa la fecha y hora actual.
     */
    public Pedido(int codigo, Cliente cliente, String destino,
                  double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        this(codigo, cliente, destino, pesoKg, distanciaKm, LocalDateTime.now());
    }

    /**
     * Constructor completo.
     */
    public Pedido(int codigo, Cliente cliente, String destino, double pesoKg,
                  double distanciaKm, LocalDateTime fechaHora) throws PedidoInvalidoException {

        if (codigo <= 0) {
            throw new PedidoInvalidoException("El código del pedido debe ser mayor que cero.");
        }
        if (cliente == null) {
            throw new PedidoInvalidoException("El pedido tiene que tener un cliente.");
        }
        if (fechaHora == null) {
            throw new PedidoInvalidoException("La fecha y hora del pedido no puede ser nula.");
        }

        this.codigo = codigo;
        this.cliente = cliente;
        setDestino(destino);
        setPesoKg(pesoKg);
        setDistanciaKm(distanciaKm);
        this.fechaHora = fechaHora;
        this.estado = EstadoPedido.PENDIENTE;
        this.vehiculoAsignado = null;
    }

    public int getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) throws PedidoInvalidoException {
        if (destino == null || destino.trim().isEmpty()) {
            throw new PedidoInvalidoException("El destino no puede estar vacío.");
        }
        this.destino = destino.trim();
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) throws PedidoInvalidoException {
        if (pesoKg <= 0) {
            throw new PedidoInvalidoException("El peso tiene que ser mayor que cero.");
        }
        this.pesoKg = pesoKg;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) throws PedidoInvalidoException {
        if (distanciaKm <= 0) {
            throw new PedidoInvalidoException("La distancia tiene que ser mayor que cero.");
        }
        this.distanciaKm = distanciaKm;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    public void asignarVehiculo(Vehiculo vehiculo) {
        this.vehiculoAsignado = vehiculo;
    }

    public void quitarVehiculo() {
        this.vehiculoAsignado = null;
    }

    /**
     * Cambia el estado solamente si la transición está permitida.
     * PENDIENTE -> EN_CAMINO o CANCELADO.
     * EN_CAMINO -> ENTREGADO.
     */
    public void cambiarEstado(EstadoPedido nuevoEstado) {
        if (nuevoEstado == null) {
            throw new EstadoInvalidoException("El estado nuevo no puede ser nulo.");
        }

        boolean transicionValida =
                (estado == EstadoPedido.PENDIENTE
                        && (nuevoEstado == EstadoPedido.EN_CAMINO
                        || nuevoEstado == EstadoPedido.CANCELADO))
                || (estado == EstadoPedido.EN_CAMINO
                        && nuevoEstado == EstadoPedido.ENTREGADO);

        if (!transicionValida) {
            throw new EstadoInvalidoException(
                    "No se puede pasar de " + estado + " a " + nuevoEstado + ".");
        }

        this.estado = nuevoEstado;
    }

    /**
     * Cada subclase aplica el recargo que le corresponde.
     */
    public abstract double calcularCosto(Vehiculo vehiculo);

    /**
     * Devuelve el nombre del tipo de pedido para mostrarlo en pantalla.
     */
    public abstract String getTipo();

    /**
     * Implementación de Facturable: usa el vehículo asignado.
     * Si todavía no tiene vehículo, no se puede calcular el importe.
     */
    @Override
    public double calcularImporte() {
        if (vehiculoAsignado == null) {
            throw new IllegalStateException(
                    "El pedido " + codigo + " todavía no tiene un vehículo asignado.");
        }
        return calcularCosto(vehiculoAsignado);
    }

    @Override
    public String obtenerEstadoActual() {
        return estado.name();
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String vehiculo = "sin asignar";
        if (vehiculoAsignado != null) {
            vehiculo = vehiculoAsignado.getPatente();
        }

        return "Pedido " + codigo + " [" + getTipo() + "]"
                + " - Cliente: " + cliente.getNombre()
                + " - Destino: " + destino
                + " - Peso: " + pesoKg + " kg"
                + " - Distancia: " + distanciaKm + " km"
                + " - Ingreso: " + fechaHora.format(formato)
                + " - Estado: " + estado
                + " - Vehículo: " + vehiculo;
    }
}
