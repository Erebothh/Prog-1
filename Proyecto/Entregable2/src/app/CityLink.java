package app;

import algoritmos.Busqueda;
import algoritmos.Intercalacion;
import algoritmos.Ordenamiento;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;
import excepciones.SinPedidosPendientesException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.TreeMap;
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
 * Gestor de CityLink para el Entregable 2.
 * Cambiamos los arreglos por estructuras de java.util:
 * TreeMap para clientes, listas para pedidos y repartidores,
 * colas para pendientes y una pila para deshacer.
 */
public class CityLink {

    private TreeMap<Integer, Cliente> clientes;
    private List<Repartidor> repartidores;
    private List<Pedido> pedidos;
    private List<Vehiculo> vehiculos;

    private Deque<Pedido> colaUrgentes;
    private Deque<Pedido> colaNormales;

    private Deque<Operacion> pilaOperaciones;

    private List<Pedido> turnoManana;
    private List<Pedido> turnoTarde;

    public CityLink() {
        clientes = new TreeMap<Integer, Cliente>();
        repartidores = new ArrayList<Repartidor>();
        pedidos = new ArrayList<Pedido>();
        vehiculos = new ArrayList<Vehiculo>();

        colaUrgentes = new ArrayDeque<Pedido>();
        colaNormales = new ArrayDeque<Pedido>();

        pilaOperaciones = new ArrayDeque<Operacion>();

        turnoManana = new ArrayList<Pedido>();
        turnoTarde = new ArrayList<Pedido>();

        cargarDatosDePrueba();

        // Las operaciones de la precarga no deberían poder deshacerse.
        pilaOperaciones.clear();
    }

    // -----------------------------------------------------------------
    // CLIENTES (TreeMap)
    // -----------------------------------------------------------------

    public void registrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (clientes.containsKey(cliente.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con el código " + cliente.getCodigo() + ".");
        }

        clientes.put(cliente.getCodigo(), cliente);
        pilaOperaciones.push(Operacion.altaCliente(cliente));
    }

    public void bajaCliente(int codigo) throws ClienteNoEncontradoException {
        Cliente cliente = buscarCliente(codigo);
        clientes.remove(codigo);
        pilaOperaciones.push(Operacion.bajaCliente(cliente));
        System.out.println("Cliente dado de baja: " + cliente.getNombre());
    }

    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        Cliente cliente = clientes.get(codigo);
        if (cliente == null) {
            throw new ClienteNoEncontradoException(
                    "No existe un cliente con el código " + codigo + ".");
        }
        return cliente;
    }

    /**
     * Búsqueda por nombre usando el algoritmo lineal de la clase Busqueda.
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        List<Cliente> lista = new ArrayList<Cliente>(clientes.values());
        Cliente cliente = Busqueda.busquedaLinealClientePorNombre(lista, nombre);

        if (cliente == null) {
            throw new ClienteNoEncontradoException(
                    "No existe un cliente llamado " + nombre + ".");
        }
        return cliente;
    }

    /**
     * El TreeMap ya mantiene las claves ordenadas por código.
     */
    public void listarClientesOrdenados() {
        System.out.println("=== CLIENTES ORDENADOS POR CÓDIGO ===");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes cargados.");
            return;
        }

        for (Cliente cliente : clientes.values()) {
            System.out.println(cliente);
        }
    }

    // -----------------------------------------------------------------
    // REPARTIDORES (List)
    // -----------------------------------------------------------------

    public void registrarRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }
        if (buscarRepartidor(repartidor.getLegajo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un repartidor con el legajo " + repartidor.getLegajo() + ".");
        }

        repartidores.add(repartidor);

        Vehiculo vehiculo = repartidor.getVehiculo();
        if (!existeVehiculo(vehiculo.getPatente())) {
            vehiculos.add(vehiculo);
        }

        pilaOperaciones.push(Operacion.altaRepartidor(repartidor));
    }

    public Repartidor buscarRepartidor(int legajo) {
        for (int i = 0; i < repartidores.size(); i++) {
            if (repartidores.get(i).getLegajo() == legajo) {
                return repartidores.get(i);
            }
        }
        return null;
    }

    private boolean existeVehiculo(String patente) {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getPatente().equalsIgnoreCase(patente)) {
                return true;
            }
        }
        return false;
    }

    public void listarRepartidores() {
        System.out.println("=== REPARTIDORES ===");
        if (repartidores.isEmpty()) {
            System.out.println("No hay repartidores cargados.");
            return;
        }
        for (int i = 0; i < repartidores.size(); i++) {
            System.out.println(repartidores.get(i));
        }
    }

    // -----------------------------------------------------------------
    // PEDIDOS Y COLAS
    // -----------------------------------------------------------------

    /**
     * Registra un pedido y lo encola según su tipo.
     * turno: 1 = mañana, 2 = tarde (se usa para la intercalación).
     */
    public void registrarPedido(Pedido pedido, int turno)
            throws PedidoInvalidoException, ClienteNoEncontradoException {

        if (pedido == null) {
            throw new PedidoInvalidoException("El pedido no puede ser nulo.");
        }
        if (turno != 1 && turno != 2) {
            throw new IllegalArgumentException("El turno tiene que ser 1 (mañana) o 2 (tarde).");
        }

        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getCodigo() == pedido.getCodigo()) {
                throw new PedidoInvalidoException(
                        "Ya existe un pedido con el código " + pedido.getCodigo() + ".");
            }
        }

        // Si el cliente no existe, esto lanza la excepción checked.
        buscarCliente(pedido.getCliente().getCodigo());

        pedidos.add(pedido);
        encolarPedido(pedido);

        if (turno == 1) {
            turnoManana.add(pedido);
        } else if (turno == 2) {
            turnoTarde.add(pedido);
        } else {
            throw new IllegalArgumentException("El turno tiene que ser 1 (mañana) o 2 (tarde).");
        }

        pilaOperaciones.push(Operacion.altaPedido(pedido));
    }

    private void encolarPedido(Pedido pedido) {
        if (pedido.getTipo().equals("Urgente")) {
            colaUrgentes.offerLast(pedido);
        } else {
            colaNormales.offerLast(pedido);
        }
    }

    private void quitarDeCola(Pedido pedido) {
        colaUrgentes.remove(pedido);
        colaNormales.remove(pedido);
    }

    /**
     * Atiende el primer pedido de la cola de urgentes y, si no hay,
     * el primero de la cola de normales. Busca un repartidor que pueda
     * transportarlo y lo pone EN_CAMINO.
     */
    public void atenderSiguientePedido()
            throws SinPedidosPendientesException, CapacidadExcedidaException {

        if (colaUrgentes.isEmpty() && colaNormales.isEmpty()) {
            throw new SinPedidosPendientesException(
                    "No hay pedidos pendientes para atender.");
        }

        Deque<Pedido> colaElegida;
        if (!colaUrgentes.isEmpty()) {
            colaElegida = colaUrgentes;
        } else {
            colaElegida = colaNormales;
        }

        Pedido pedido = colaElegida.peekFirst();
        Repartidor elegido = null;

        // Busco el repartidor que pueda llevar el pedido y tenga menos carga.
        for (int i = 0; i < repartidores.size(); i++) {
            Repartidor actual = repartidores.get(i);
            if (actual.getVehiculo().puedeTransportar(pedido)) {
                if (elegido == null
                        || actual.getPedidos().size() < elegido.getPedidos().size()) {
                    elegido = actual;
                }
            }
        }

        if (elegido == null) {
            throw new CapacidadExcedidaException(
                    "Ningún repartidor puede transportar el pedido " + pedido.getCodigo() + ".");
        }

        colaElegida.pollFirst();
        elegido.asignarPedido(pedido);
        pedido.cambiarEstado(EstadoPedido.EN_CAMINO);
        pilaOperaciones.push(Operacion.asignarPedido(pedido, elegido));

        System.out.println("Pedido " + pedido.getCodigo() + " asignado a "
                + elegido.getNombre() + " (" + elegido.getVehiculo().getTipoVehiculo() + ").");
    }

    /**
     * Marca un pedido como entregado y lo agrega al historial del repartidor.
     */
    public void registrarEntrega(int codigoPedido) throws PedidoInvalidoException {
        Pedido pedido = buscarPedido(codigoPedido);
        Repartidor repartidor = buscarRepartidorDePedido(codigoPedido);

        if (repartidor == null) {
            throw new IllegalArgumentException(
                    "El pedido " + codigoPedido + " no está asignado a ningún repartidor.");
        }

        repartidor.registrarEntrega(pedido);
        pilaOperaciones.push(Operacion.entregarPedido(pedido, repartidor));
        System.out.println("Entrega registrada para el pedido " + codigoPedido + ".");
    }

    private Repartidor buscarRepartidorDePedido(int codigoPedido) {
        for (int i = 0; i < repartidores.size(); i++) {
            if (repartidores.get(i).buscarPedido(codigoPedido) != null) {
                return repartidores.get(i);
            }
        }
        return null;
    }

    public Pedido buscarPedido(int codigo) throws PedidoInvalidoException {
        Pedido pedido = Busqueda.busquedaLinealPedido(pedidos, codigo);
        if (pedido == null) {
            throw new PedidoInvalidoException("No existe un pedido con el código " + codigo + ".");
        }
        return pedido;
    }

    // -----------------------------------------------------------------
    // DESHACER (pila)
    // -----------------------------------------------------------------

    public void deshacer() {
        Operacion operacion = pilaOperaciones.pollFirst();

        if (operacion == null) {
            throw new IllegalStateException("No hay operaciones para deshacer.");
        }

        switch (operacion.getTipo()) {
            case ALTA_CLIENTE:
                clientes.remove(operacion.getCliente().getCodigo());
                System.out.println("Se deshizo el alta del cliente "
                        + operacion.getCliente().getNombre() + ".");
                break;

            case BAJA_CLIENTE:
                clientes.put(operacion.getCliente().getCodigo(), operacion.getCliente());
                System.out.println("Se deshizo la baja del cliente "
                        + operacion.getCliente().getNombre() + ".");
                break;

            case ALTA_REPARTIDOR:
                repartidores.remove(operacion.getRepartidor());
                System.out.println("Se deshizo el alta del repartidor "
                        + operacion.getRepartidor().getNombre() + ".");
                break;

            case ALTA_PEDIDO:
                Pedido nuevo = operacion.getPedido();
                pedidos.remove(nuevo);
                quitarDeCola(nuevo);
                turnoManana.remove(nuevo);
                turnoTarde.remove(nuevo);
                if (nuevo.getVehiculoAsignado() != null) {
                    Repartidor duenio = buscarRepartidorDePedido(nuevo.getCodigo());
                    if (duenio != null) {
                        duenio.quitarPedido(nuevo);
                        duenio.quitarDelHistorial(nuevo);
                    }
                }
                System.out.println("Se deshizo el alta del pedido " + nuevo.getCodigo() + ".");
                break;

            case ASIGNAR_PEDIDO:
                Pedido asignado = operacion.getPedido();
                operacion.getRepartidor().quitarPedido(asignado);
                asignado.quitarVehiculo();
                asignado.restaurarEstado(EstadoPedido.PENDIENTE);
                encolarPedido(asignado);
                System.out.println("Se deshizo la asignación del pedido "
                        + asignado.getCodigo() + ".");
                break;

            case ENTREGAR_PEDIDO:
                Pedido entregado = operacion.getPedido();
                operacion.getRepartidor().quitarDelHistorial(entregado);
                entregado.restaurarEstado(EstadoPedido.EN_CAMINO);
                System.out.println("Se deshizo la entrega del pedido "
                        + entregado.getCodigo() + ".");
                break;

            case CAMBIO_ESTADO:
                operacion.getPedido().restaurarEstado(operacion.getEstadoAnterior());
                System.out.println("Se deshizo el cambio de estado del pedido "
                        + operacion.getPedido().getCodigo() + ".");
                break;

            default:
                System.out.println("No se pudo deshacer la operación.");
        }
    }

    // -----------------------------------------------------------------
    // ORDENAMIENTO Y BÚSQUEDA
    // -----------------------------------------------------------------

    /**
     * Ordena la lista de pedidos con el criterio y el algoritmo elegidos.
     */
    public void ordenarYListarPedidos(int criterio, int algoritmo) {
        Comparator<Pedido> comparador = obtenerComparador(criterio);

        if (algoritmo == 1) {
            Ordenamiento.burbuja(pedidos, comparador);
        } else if (algoritmo == 2) {
            Ordenamiento.insercion(pedidos, comparador);
        } else if (algoritmo == 3) {
            Ordenamiento.quicksort(pedidos, comparador);
        } else if (algoritmo == 4) {
            Ordenamiento.mergesort(pedidos, comparador);
        } else {
            throw new IllegalArgumentException("El algoritmo elegido no es válido.");
        }

        System.out.println("=== PEDIDOS ORDENADOS ===");
        for (int i = 0; i < pedidos.size(); i++) {
            mostrarPedido(pedidos.get(i));
        }
    }

    private Comparator<Pedido> obtenerComparador(int criterio) {
        if (criterio == 1) {
            // Ordeno por costo. Si no tiene vehículo, uso 0.
            return new Comparator<Pedido>() {
                @Override
                public int compare(Pedido a, Pedido b) {
                    return Double.compare(costoSeguro(a), costoSeguro(b));
                }
            };
        } else if (criterio == 2) {
            return new Comparator<Pedido>() {
                @Override
                public int compare(Pedido a, Pedido b) {
                    return Double.compare(a.getDistanciaKm(), b.getDistanciaKm());
                }
            };
        } else if (criterio == 3) {
            return new Comparator<Pedido>() {
                @Override
                public int compare(Pedido a, Pedido b) {
                    return a.getFechaHora().compareTo(b.getFechaHora());
                }
            };
        } else {
            throw new IllegalArgumentException("El criterio elegido no es válido.");
        }
    }

    private static double costoSeguro(Pedido pedido) {
        if (pedido.getVehiculoAsignado() == null) {
            return 0;
        }
        return pedido.calcularImporte();
    }

    /**
     * Búsqueda lineal por código.
     */
    public Pedido buscarPedidoLineal(int codigo) throws PedidoInvalidoException {
        Pedido pedido = Busqueda.busquedaLinealPedido(pedidos, codigo);
        if (pedido == null) {
            throw new PedidoInvalidoException("No existe el pedido " + codigo + ".");
        }
        return pedido;
    }

    /**
     * Para la búsqueda binaria armo una copia ordenada por código.
     * No modifico el orden original de la lista.
     */
    public Pedido buscarPedidoBinario(int codigo) throws PedidoInvalidoException {
        List<Pedido> copia = new ArrayList<Pedido>(pedidos);

        Comparator<Pedido> porCodigo = new Comparator<Pedido>() {
            @Override
            public int compare(Pedido a, Pedido b) {
                return Integer.compare(a.getCodigo(), b.getCodigo());
            }
        };

        Ordenamiento.quicksort(copia, porCodigo);
        Pedido pedido = Busqueda.busquedaBinariaPedido(copia, codigo);

        if (pedido == null) {
            throw new PedidoInvalidoException("No existe el pedido " + codigo + ".");
        }
        return pedido;
    }

    // -----------------------------------------------------------------
    // INTERCALACIÓN
    // -----------------------------------------------------------------

    /**
     * Ordena los pedidos de cada turno y los intercala en una sola lista.
     */
    public void intercalarTurnos(int criterio) {
        Comparator<Pedido> comparador = obtenerComparador(criterio);

        List<Pedido> manana = new ArrayList<Pedido>(turnoManana);
        List<Pedido> tarde = new ArrayList<Pedido>(turnoTarde);

        Ordenamiento.quicksort(manana, comparador);
        Ordenamiento.quicksort(tarde, comparador);

        List<Pedido> resultado = Intercalacion.intercalar(manana, tarde, comparador);

        System.out.println("=== PEDIDOS DE LOS DOS TURNOS INTERCALADOS ===");
        if (resultado.isEmpty()) {
            System.out.println("No hay pedidos en los turnos.");
            return;
        }

        for (int i = 0; i < resultado.size(); i++) {
            mostrarPedido(resultado.get(i));
        }
    }

    // -----------------------------------------------------------------
    // LISTADOS Y TOTALES
    // -----------------------------------------------------------------

    public void listarPedidosConCostos() {
        System.out.println("=== PEDIDOS REGISTRADOS ===");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }

        for (int i = 0; i < pedidos.size(); i++) {
            mostrarPedido(pedidos.get(i));
        }

        System.out.printf("TOTAL DE LOS PEDIDOS ASIGNADOS: $%.2f%n", calcularCostoTotal());
        System.out.println();
        listarTiemposDeEntrega(10);
    }

    private void mostrarPedido(Pedido pedido) {
        System.out.println(pedido);

        if (pedido.getVehiculoAsignado() != null) {
            Facturable facturable = pedido;
            double costo = facturable.calcularImporte();
            double tiempo = pedido.getVehiculoAsignado()
                    .calcularTiempoEntrega(pedido.getDistanciaKm());
            System.out.printf("   Costo: $%.2f - Tiempo: %.1f minutos%n", costo, tiempo);
        } else {
            System.out.println("   Costo: sin calcular (no tiene vehículo asignado)");
        }
    }

    public double calcularCostoTotal() {
        double total = 0;

        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getVehiculoAsignado() != null) {
                Facturable facturable = pedidos.get(i);
                total = total + facturable.calcularImporte();
            }
        }

        return total;
    }

    public void listarTiemposDeEntrega(double km) {
        System.out.println("=== TIEMPO ESTIMADO PARA " + km + " KM ===");
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo vehiculo = vehiculos.get(i);
            System.out.printf("%s (%s): %.1f minutos%n",
                    vehiculo.getTipoVehiculo(), vehiculo.getPatente(),
                    vehiculo.calcularTiempoEntrega(km));
        }
    }

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

    public void listarImportesYComisiones() {
        System.out.printf("Importe total de los pedidos: $%.2f%n", calcularCostoTotal());
        System.out.println("Comisiones de los repartidores:");

        for (int i = 0; i < repartidores.size(); i++) {
            Facturable facturable = repartidores.get(i);
            System.out.printf("- %s: $%.2f%n",
                    repartidores.get(i).getNombre(), facturable.calcularImporte());
        }
    }

    public void listarPedidosDeRepartidor(int legajo) {
        Repartidor repartidor = buscarRepartidor(legajo);
        if (repartidor == null) {
            throw new IllegalArgumentException("No existe el repartidor " + legajo + ".");
        }

        System.out.println("=== PEDIDOS DE " + repartidor.getNombre() + " ===");
        List<Pedido> lista = repartidor.getPedidos();
        if (lista.isEmpty()) {
            System.out.println("No tiene pedidos asignados.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            mostrarPedido(lista.get(i));
        }

        System.out.println("=== HISTORIAL DE ENTREGAS ===");
        List<Pedido> historial = repartidor.getHistorial();
        if (historial.isEmpty()) {
            System.out.println("Todavía no tiene entregas.");
        } else {
            for (int i = 0; i < historial.size(); i++) {
                System.out.println(historial.get(i));
            }
        }
    }

    // -----------------------------------------------------------------
    // DATOS DE PRUEBA
    // -----------------------------------------------------------------

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

            registrarPedido(p1, 1);
            registrarPedido(p2, 1);
            registrarPedido(p3, 1);
            registrarPedido(p4, 1);
            registrarPedido(p5, 2);
            registrarPedido(p6, 2);
            registrarPedido(p7, 2);
            registrarPedido(p8, 2);

            // Tres pedidos ya salieron de la cola.
            quitarDeCola(p1);
            r1.asignarPedido(p1);
            p1.cambiarEstado(EstadoPedido.EN_CAMINO);
            r1.registrarEntrega(p1);

            quitarDeCola(p2);
            r2.asignarPedido(p2);
            p2.cambiarEstado(EstadoPedido.EN_CAMINO);
            r2.registrarEntrega(p2);

            quitarDeCola(p3);
            r2.asignarPedido(p3);
            p3.cambiarEstado(EstadoPedido.EN_CAMINO);

        } catch (Exception e) {
            System.out.println("Aviso: no se pudieron cargar todos los datos de prueba.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }
}
