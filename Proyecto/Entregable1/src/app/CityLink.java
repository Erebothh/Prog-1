package app;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Cliente;
import modelo.EstadoPedido;
import modelo.Facturable;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Rastreable;
import modelo.Repartidor;
import modelo.Vehiculo;

/**
 * Gestor principal de CityLink.
 * Acá están los arreglos y las operaciones del sistema.
 * El Main solamente se encarga del menú y de leer por teclado.
 */
public class CityLink {

    public static final int CAPACIDAD_CLIENTES = 50;
    public static final int CAPACIDAD_PEDIDOS = 100;
    public static final int CAPACIDAD_REPARTIDORES = 10;
    public static final int CAPACIDAD_VEHICULOS = 30;

    private Cliente[] clientes;
    private int cantidadClientes;

    private Pedido[] pedidos;
    private int cantidadPedidos;

    private Repartidor[] repartidores;
    private int cantidadRepartidores;

    // Este arreglo lo uso para mostrar el polimorfismo con Vehiculo.
    private Vehiculo[] vehiculos;
    private int cantidadVehiculos;

    public CityLink() {
        clientes = new Cliente[CAPACIDAD_CLIENTES];
        cantidadClientes = 0;

        pedidos = new Pedido[CAPACIDAD_PEDIDOS];
        cantidadPedidos = 0;

        repartidores = new Repartidor[CAPACIDAD_REPARTIDORES];
        cantidadRepartidores = 0;

        vehiculos = new Vehiculo[CAPACIDAD_VEHICULOS];
        cantidadVehiculos = 0;

        cargarDatosDePrueba();
    }

    // -----------------------------------------------------------------
    // CLIENTES
    // -----------------------------------------------------------------

    /**
     * Registra un cliente nuevo. No permite códigos repetidos.
     */
    public void registrarCliente(Cliente cliente) throws AlmacenamientoLlenoException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (cantidadClientes >= clientes.length) {
            throw new AlmacenamientoLlenoException(
                    "No hay más lugar para clientes (capacidad " + CAPACIDAD_CLIENTES + ").");
        }

        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getCodigo() == cliente.getCodigo()) {
                throw new IllegalArgumentException(
                        "Ya existe un cliente con el código " + cliente.getCodigo() + ".");
            }
        }

        clientes[cantidadClientes] = cliente;
        cantidadClientes++;
    }

    /**
     * Búsqueda por código. Si no aparece, lanza la excepción checked.
     */
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException(
                "No existe un cliente con el código " + codigo + ".");
    }

    /**
     * Sobrecarga de búsqueda: ahora por nombre.
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        if (nombre == null) {
            throw new ClienteNoEncontradoException("El nombre a buscar es nulo.");
        }

        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getNombre().equalsIgnoreCase(nombre.trim())) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException(
                "No existe un cliente llamado " + nombre + ".");
    }

    /**
     * Elimina un cliente y desplaza los siguientes para no dejar huecos.
     * No dejo eliminar un cliente que tenga pedidos registrados.
     */
    public void eliminarCliente(int codigo) throws ClienteNoEncontradoException {
        int posicion = -1;

        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                posicion = i;
            }
        }

        if (posicion == -1) {
            throw new ClienteNoEncontradoException(
                    "No existe un cliente con el código " + codigo + ".");
        }

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCliente().getCodigo() == codigo) {
                throw new IllegalArgumentException(
                        "No se puede eliminar el cliente porque tiene pedidos registrados.");
            }
        }

        for (int i = posicion; i < cantidadClientes - 1; i++) {
            clientes[i] = clientes[i + 1];
        }
        clientes[cantidadClientes - 1] = null;
        cantidadClientes--;
    }

    public void listarClientes() {
        System.out.println("=== CLIENTES ===");
        if (cantidadClientes == 0) {
            System.out.println("No hay clientes cargados.");
            return;
        }
        for (int i = 0; i < cantidadClientes; i++) {
            System.out.println(clientes[i]);
        }
    }

    // -----------------------------------------------------------------
    // REPARTIDORES
    // -----------------------------------------------------------------

    /**
     * Registra un repartidor y también guarda su vehículo para el arreglo
     * de vehículos que se usa después.
     */
    public void registrarRepartidor(Repartidor repartidor)
            throws AlmacenamientoLlenoException {

        if (repartidor == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }
        if (cantidadRepartidores >= repartidores.length) {
            throw new AlmacenamientoLlenoException(
                    "No hay más lugar para repartidores (capacidad "
                            + CAPACIDAD_REPARTIDORES + ").");
        }

        for (int i = 0; i < cantidadRepartidores; i++) {
            if (repartidores[i].getLegajo() == repartidor.getLegajo()) {
                throw new IllegalArgumentException(
                        "Ya existe un repartidor con el legajo " + repartidor.getLegajo() + ".");
            }
        }

        repartidores[cantidadRepartidores] = repartidor;
        cantidadRepartidores++;

        // Agrego el vehículo al arreglo de vehículos si no estaba.
        Vehiculo vehiculo = repartidor.getVehiculo();
        boolean existe = false;
        for (int i = 0; i < cantidadVehiculos; i++) {
            if (vehiculos[i].getPatente().equalsIgnoreCase(vehiculo.getPatente())) {
                existe = true;
            }
        }

        if (!existe) {
            if (cantidadVehiculos >= vehiculos.length) {
                throw new AlmacenamientoLlenoException("No hay más lugar para vehículos.");
            }
            vehiculos[cantidadVehiculos] = vehiculo;
            cantidadVehiculos++;
        }
    }

    public Repartidor buscarRepartidor(int legajo) {
        for (int i = 0; i < cantidadRepartidores; i++) {
            if (repartidores[i].getLegajo() == legajo) {
                return repartidores[i];
            }
        }
        return null;
    }

    public void listarRepartidores() {
        System.out.println("=== REPARTIDORES ===");
        if (cantidadRepartidores == 0) {
            System.out.println("No hay repartidores cargados.");
            return;
        }
        for (int i = 0; i < cantidadRepartidores; i++) {
            System.out.println(repartidores[i]);
        }
    }

    // -----------------------------------------------------------------
    // PEDIDOS
    // -----------------------------------------------------------------

    /**
     * Registra un pedido, controlando que el código no se repita
     * y que el cliente exista.
     */
    public void registrarPedido(Pedido pedido)
            throws AlmacenamientoLlenoException, PedidoInvalidoException,
            ClienteNoEncontradoException {

        if (pedido == null) {
            throw new PedidoInvalidoException("El pedido no puede ser nulo.");
        }
        if (cantidadPedidos >= pedidos.length) {
            throw new AlmacenamientoLlenoException(
                    "No hay más lugar para pedidos (capacidad " + CAPACIDAD_PEDIDOS + ").");
        }

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCodigo() == pedido.getCodigo()) {
                throw new PedidoInvalidoException(
                        "Ya existe un pedido con el código " + pedido.getCodigo() + ".");
            }
        }

        // Si el cliente no existe, buscarCliente lanza la excepción.
        buscarCliente(pedido.getCliente().getCodigo());

        pedidos[cantidadPedidos] = pedido;
        cantidadPedidos++;
    }

    /**
     * Busca un pedido por código.
     */
    public Pedido buscarPedido(int codigo) throws PedidoInvalidoException {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCodigo() == codigo) {
                return pedidos[i];
            }
        }
        throw new PedidoInvalidoException("No existe un pedido con el código " + codigo + ".");
    }

    /**
     * Asigna un pedido a un repartidor. La validación de capacidad
     * la hace el propio repartidor con su vehículo.
     */
    public void asignarPedido(int codigoPedido, int legajoRepartidor)
            throws PedidoInvalidoException, AlmacenamientoLlenoException,
            CapacidadExcedidaException {

        Pedido pedido = buscarPedido(codigoPedido);
        Repartidor repartidor = buscarRepartidor(legajoRepartidor);

        if (repartidor == null) {
            throw new IllegalArgumentException(
                    "No existe un repartidor con el legajo " + legajoRepartidor + ".");
        }
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new IllegalArgumentException(
                    "Solo se pueden asignar pedidos en estado PENDIENTE.");
        }

        repartidor.asignarPedido(pedido);
    }

    /**
     * Cambia el estado de un pedido. Si pasa a ENTREGADO y estaba asignado,
     * se registra la entrega en el repartidor para que cuente la comisión.
     */
    public void cambiarEstadoPedido(int codigoPedido, EstadoPedido nuevoEstado)
            throws PedidoInvalidoException {

        Pedido pedido = buscarPedido(codigoPedido);

        if (nuevoEstado == EstadoPedido.ENTREGADO) {
            Repartidor repartidor = buscarRepartidorDePedido(codigoPedido);
            if (repartidor != null) {
                repartidor.registrarEntrega(pedido);
                return;
            }
        }

        pedido.cambiarEstado(nuevoEstado);
    }

    private Repartidor buscarRepartidorDePedido(int codigoPedido) {
        for (int i = 0; i < cantidadRepartidores; i++) {
            if (repartidores[i].buscarPedido(codigoPedido) != null) {
                return repartidores[i];
            }
        }
        return null;
    }

    /**
     * Lista los pedidos y usa polimorfismo para calcular costo y tiempo.
     * No hay ningún instanceof ni switch por tipo de pedido.
     */
    public void listarPedidosConCostos() {
        System.out.println("=== PEDIDOS REGISTRADOS ===");

        if (cantidadPedidos == 0) {
            System.out.println("No hay pedidos cargados.");
            return;
        }

        for (int i = 0; i < cantidadPedidos; i++) {
            Pedido pedido = pedidos[i];
            System.out.println(pedido);

            if (pedido.getVehiculoAsignado() != null) {
                Facturable facturable = pedido;
                double costo = facturable.calcularImporte();
                double tiempo = pedido.getVehiculoAsignado()
                        .calcularTiempoEntrega(pedido.getDistanciaKm());

                System.out.printf("   Costo: $%.2f - Tiempo estimado: %.1f minutos%n",
                        costo, tiempo);
            } else {
                System.out.println("   Costo: sin calcular (no tiene vehículo asignado)");
            }
        }

        System.out.printf("TOTAL DE LOS PEDIDOS ASIGNADOS: $%.2f%n", calcularCostoTotal());

        // Extra para mostrar el arreglo de Vehiculo y su polimorfismo.
        System.out.println();
        listarTiemposDeEntrega(10);
    }

    /**
     * Suma los importes recorriendo el arreglo de Pedido.
     */
    public double calcularCostoTotal() {
        double total = 0;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getVehiculoAsignado() != null) {
                Facturable facturable = pedidos[i];
                total = total + facturable.calcularImporte();
            }
        }

        return total;
    }

    /**
     * Recorre el arreglo de Vehiculo y llama al método abstracto.
     * Cada vehículo responde con su propio tiempo.
     */
    public void listarTiemposDeEntrega(double km) {
        System.out.println("=== TIEMPO ESTIMADO PARA " + km + " KM ===");
        for (int i = 0; i < cantidadVehiculos; i++) {
            Vehiculo vehiculo = vehiculos[i];
            System.out.printf("%s (%s): %.1f minutos%n",
                    vehiculo.getTipoVehiculo(), vehiculo.getPatente(),
                    vehiculo.calcularTiempoEntrega(km));
        }
    }

    /**
     * Muestra el estado usando la interfaz Rastreable.
     */
    public void consultarEstadoPedido(int codigo) throws PedidoInvalidoException {
        Pedido pedido = buscarPedido(codigo);
        Rastreable rastreable = pedido;
        System.out.println("Pedido " + codigo + ": " + rastreable.obtenerEstadoActual());
    }

    public void consultarEstadoRepartidor(int legajo) {
        Repartidor repartidor = buscarRepartidor(legajo);
        if (repartidor == null) {
            throw new IllegalArgumentException("No existe el repartidor " + legajo + ".");
        }
        Rastreable rastreable = repartidor;
        System.out.println("Repartidor " + repartidor.getNombre() + ": "
                + rastreable.obtenerEstadoActual());
    }

    /**
     * Muestra el importe total y la comisión de cada repartidor.
     */
    public void listarImportesYComisiones() {
        System.out.printf("Importe total de los pedidos: $%.2f%n", calcularCostoTotal());
        System.out.println("Comisiones de los repartidores:");

        for (int i = 0; i < cantidadRepartidores; i++) {
            Facturable facturable = repartidores[i];
            System.out.printf("- %s: $%.2f%n",
                    repartidores[i].getNombre(), facturable.calcularImporte());
        }
    }

    // -----------------------------------------------------------------
    // DATOS DE PRUEBA
    // -----------------------------------------------------------------

    /**
     * Carga 5 clientes, 3 repartidores (uno por vehículo) y 8 pedidos.
     * Algunos pedidos quedan entregados para que se vea la comisión.
     */
    private void cargarDatosDePrueba() {
        try {
            Cliente c1 = new Cliente(1, "Ana García", "555-1111", "Centro");
            Cliente c2 = new Cliente(2, "Bruno Pérez", "555-2222", "Norte");
            Cliente c3 = new Cliente(3, "Carla López", "555-3333", "Sur");
            Cliente c4 = new Cliente(4, "Diego Martínez", "555-4444", "Este");
            Cliente c5 = new Cliente(5, "Elena Rodríguez", "555-5555", "Oeste");

            registrarCliente(c1);
            registrarCliente(c2);
            registrarCliente(c3);
            registrarCliente(c4);
            registrarCliente(c5);

            Vehiculo bici = new Bicicleta("BICI-01", 10, 15, 50);
            Vehiculo moto = new Moto("MOTO-01", 30, 40, 120);
            Vehiculo camioneta = new Camioneta("CAMI-01", 500, 30, 300);

            Repartidor r1 = new Repartidor(1, "Luis Gómez", bici);
            Repartidor r2 = new Repartidor(2, "María Silva", moto);
            Repartidor r3 = new Repartidor(3, "Jorge Ríos", camioneta);

            registrarRepartidor(r1);
            registrarRepartidor(r2);
            registrarRepartidor(r3);

            Pedido p1 = new PedidoNormal(1, c1, "Av. Siempre Viva 123", 2, 5);
            Pedido p2 = new PedidoUrgente(2, c2, "Calle Falsa 456", 5, 8);
            Pedido p3 = new PedidoFragil(3, c3, "Pasaje Verde 789", 1, 3);
            Pedido p4 = new PedidoNormal(4, c4, "Ruta 9 km 12", 8, 12);
            Pedido p5 = new PedidoUrgente(5, c5, "Barrio Norte 22", 3, 6);
            Pedido p6 = new PedidoFragil(6, c1, "Av. Central 555", 4, 7);
            Pedido p7 = new PedidoNormal(7, c2, "Calle 10 N° 45", 20, 10);
            Pedido p8 = new PedidoUrgente(8, c3, "Bulevar 88", 2, 4);

            registrarPedido(p1);
            registrarPedido(p2);
            registrarPedido(p3);
            registrarPedido(p4);
            registrarPedido(p5);
            registrarPedido(p6);
            registrarPedido(p7);
            registrarPedido(p8);

            asignarPedido(1, 1); // bici, normal
            asignarPedido(2, 2); // moto, urgente
            asignarPedido(3, 2); // moto, frágil
            asignarPedido(4, 3); // camioneta, normal
            asignarPedido(5, 2); // moto, urgente
            asignarPedido(6, 3); // camioneta, frágil
            asignarPedido(7, 3); // camioneta, 20 kg
            asignarPedido(8, 1); // bici, urgente

            // Dejo dos entregados y uno en camino para probar estados.
            p1.cambiarEstado(EstadoPedido.EN_CAMINO);
            p1.cambiarEstado(EstadoPedido.ENTREGADO);

            p2.cambiarEstado(EstadoPedido.EN_CAMINO);
            p2.cambiarEstado(EstadoPedido.ENTREGADO);

            p3.cambiarEstado(EstadoPedido.EN_CAMINO);

        } catch (Exception e) {
            System.out.println("Aviso: no se pudieron cargar todos los datos de prueba.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }
}
