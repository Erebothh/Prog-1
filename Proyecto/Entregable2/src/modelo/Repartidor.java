package modelo;

import excepciones.CapacidadExcedidaException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repartidor de CityLink (Entregable 2).
 * Ahora los pedidos se guardan en una lista dinámica y también
 * llevamos un historial de entregas.
 */
public class Repartidor implements Rastreable, Facturable {

    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private List<Pedido> pedidos;
    private List<Pedido> historial;

    public Repartidor() {
        this.legajo = 0;
        this.nombre = "";
        this.vehiculo = null;
        this.pedidos = new ArrayList<Pedido>();
        this.historial = new ArrayList<Pedido>();
    }

    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        setLegajo(legajo);
        setNombre(nombre);
        setVehiculo(vehiculo);
        this.pedidos = new ArrayList<Pedido>();
        this.historial = new ArrayList<Pedido>();
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

    /**
     * Devuelve una copia de la lista para no exponer la original.
     */
    public List<Pedido> getPedidos() {
        return new ArrayList<Pedido>(pedidos);
    }

    public List<Pedido> getHistorial() {
        return new ArrayList<Pedido>(historial);
    }

    public void asignarPedido(Pedido pedido) throws CapacidadExcedidaException {
        asignarPedido(pedido, vehiculo);
    }

    /**
     * Sobrecarga de asignarPedido: permite indicar el vehículo.
     */
    public void asignarPedido(Pedido pedido, Vehiculo vehiculo)
            throws CapacidadExcedidaException {

        if (pedido == null || vehiculo == null) {
            throw new IllegalArgumentException("El pedido y el vehículo no pueden ser nulos.");
        }
        if (buscarPedido(pedido.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "El pedido " + pedido.getCodigo() + " ya está asignado a este repartidor.");
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

        pedidos.add(pedido);
        pedido.asignarVehiculo(vehiculo);
    }

    /**
     * Marca el pedido como entregado y lo guarda en el historial.
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

        if (!historial.contains(encontrado)) {
            historial.add(encontrado);
        }
    }

    /**
     * Lo usa "Deshacer" para sacar la entrega del historial.
     */
    public void quitarDelHistorial(Pedido pedido) {
        historial.remove(pedido);
    }

    /**
     * Lo usa "Deshacer" para desasignar un pedido.
     */
    public void quitarPedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    public Pedido buscarPedido(int codigo) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getCodigo() == codigo) {
                return pedidos.get(i);
            }
        }
        return null;
    }

    /**
     * Comisión: 10% de los pedidos entregados (los que están en el historial).
     */
    @Override
    public double calcularImporte() {
        double total = 0;
        for (int i = 0; i < historial.size(); i++) {
            total = total + historial.get(i).calcularImporte();
        }
        return total * 0.10;
    }

    @Override
    public String obtenerEstadoActual() {
        boolean enCamino = false;
        boolean pendiente = false;

        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getEstado() == EstadoPedido.EN_CAMINO) {
                enCamino = true;
            }
            if (pedidos.get(i).getEstado() == EstadoPedido.PENDIENTE) {
                pendiente = true;
            }
        }

        if (enCamino) {
            return "OCUPADO (tiene pedidos en camino)";
        }
        if (pendiente) {
            return "CON PEDIDOS ASIGNADOS";
        }
        return "DISPONIBLE";
    }

    @Override
    public String toString() {
        return "Repartidor " + legajo + " - " + nombre
                + " - Vehículo: " + vehiculo.getTipoVehiculo()
                + " (" + vehiculo.getPatente() + ")"
                + " - Pedidos a cargo: " + pedidos.size()
                + " - Entregas: " + historial.size();
    }
}
