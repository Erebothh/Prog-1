package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;

/**
 * Repartidor de CityLink.
 * Tiene un vehículo y un arreglo con los pedidos que tiene a cargo.
 */
public class Repartidor implements Rastreable, Facturable {

    public static final int CAPACIDAD_PEDIDOS = 20;

    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private Pedido[] pedidos;
    private int cantidadPedidos;

    public Repartidor() {
        this.legajo = 0;
        this.nombre = "";
        this.vehiculo = null;
        this.pedidos = new Pedido[CAPACIDAD_PEDIDOS];
        this.cantidadPedidos = 0;
    }

    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        setLegajo(legajo);
        setNombre(nombre);
        setVehiculo(vehiculo);
        this.pedidos = new Pedido[CAPACIDAD_PEDIDOS];
        this.cantidadPedidos = 0;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        if (legajo <= 0) {
            throw new IllegalArgumentException("El legajo debe ser mayor que cero.");
        }
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del repartidor no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El repartidor tiene que tener un vehículo.");
        }
        this.vehiculo = vehiculo;
    }

    public int getCantidadPedidos() {
        return cantidadPedidos;
    }

    /**
     * Devuelve una copia de los pedidos cargados, sin los espacios vacíos.
     */
    public Pedido[] getPedidos() {
        Pedido[] copia = new Pedido[cantidadPedidos];
        for (int i = 0; i < cantidadPedidos; i++) {
            copia[i] = pedidos[i];
        }
        return copia;
    }

    /**
     * Asigna un pedido usando el vehículo propio del repartidor.
     */
    public void asignarPedido(Pedido pedido)
            throws AlmacenamientoLlenoException, CapacidadExcedidaException {
        asignarPedido(pedido, vehiculo);
    }

    /**
     * Sobrecarga: permite indicar explícitamente el vehículo.
     */
    public void asignarPedido(Pedido pedido, Vehiculo vehiculo)
            throws AlmacenamientoLlenoException, CapacidadExcedidaException {

        if (pedido == null || vehiculo == null) {
            throw new IllegalArgumentException("El pedido y el vehículo no pueden ser nulos.");
        }

        if (cantidadPedidos >= pedidos.length) {
            throw new AlmacenamientoLlenoException(
                    "El repartidor " + nombre + " no puede tener más pedidos.");
        }

        // No quiero cargar dos veces el mismo pedido.
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCodigo() == pedido.getCodigo()) {
                throw new IllegalArgumentException(
                        "El pedido " + pedido.getCodigo() + " ya está asignado a este repartidor.");
            }
        }

        if (!vehiculo.puedeTransportar(pedido)) {
            if (vehiculo instanceof Bicicleta && pedido instanceof PedidoFragil) {
                throw new CapacidadExcedidaException(
                        "Una bicicleta no puede transportar pedidos frágiles.");
            }
            throw new CapacidadExcedidaException(
                    "El pedido " + pedido.getCodigo() + " pesa " + pedido.getPesoKg()
                            + " kg y el vehículo " + vehiculo.getPatente()
                            + " soporta " + vehiculo.getCapacidadKg() + " kg.");
        }

        pedidos[cantidadPedidos] = pedido;
        cantidadPedidos++;
        pedido.asignarVehiculo(vehiculo);
    }

    /**
     * Marca un pedido como entregado. El pedido tiene que estar EN_CAMINO.
     */
    public void registrarEntrega(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        Pedido encontrado = buscarPedido(pedido.getCodigo());
        if (encontrado == null) {
            throw new IllegalArgumentException(
                    "El pedido " + pedido.getCodigo() + " no está asignado a este repartidor.");
        }

        encontrado.cambiarEstado(EstadoPedido.ENTREGADO);
    }

    /**
     * Busca un pedido por código entre los asignados a este repartidor.
     */
    public Pedido buscarPedido(int codigo) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCodigo() == codigo) {
                return pedidos[i];
            }
        }
        return null;
    }

    /**
     * Comisión: 10% de los pedidos que ya fueron entregados.
     */
    @Override
    public double calcularImporte() {
        double totalEntregado = 0;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getEstado() == EstadoPedido.ENTREGADO) {
                totalEntregado = totalEntregado + pedidos[i].calcularImporte();
            }
        }

        return totalEntregado * 0.10;
    }

    @Override
    public String obtenerEstadoActual() {
        boolean tieneEnCamino = false;
        boolean tienePendiente = false;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getEstado() == EstadoPedido.EN_CAMINO) {
                tieneEnCamino = true;
            }
            if (pedidos[i].getEstado() == EstadoPedido.PENDIENTE) {
                tienePendiente = true;
            }
        }

        if (tieneEnCamino) {
            return "OCUPADO (tiene pedidos en camino)";
        }
        if (tienePendiente) {
            return "CON PEDIDOS ASIGNADOS";
        }
        return "DISPONIBLE";
    }

    @Override
    public String toString() {
        return "Repartidor " + legajo + " - " + nombre
                + " - Vehículo: " + vehiculo.getTipoVehiculo()
                + " (" + vehiculo.getPatente() + ")"
                + " - Pedidos: " + cantidadPedidos;
    }
}
