package app;

import java.util.Scanner;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Cliente;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;
import modelo.Vehiculo;

/**
 * Menú principal de CityLink (Entregable 2).
 * Se conserva la idea del Entregable 1: Main solamente lee y muestra.
 */
public class Main {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        CityLink sistema = new CityLink();
        boolean salir = false;

        try {
            while (!salir) {
                mostrarMenu();
                int opcion = leerEntero("Elija una opción: ");

                try {
                    switch (opcion) {
                        case 1:
                            menuClientes(sistema);
                            break;
                        case 2:
                            menuRepartidores(sistema);
                            break;
                        case 3:
                            registrarPedido(sistema);
                            break;
                        case 4:
                            sistema.atenderSiguientePedido();
                            break;
                        case 5:
                            registrarEntrega(sistema);
                            break;
                        case 6:
                            sistema.deshacer();
                            break;
                        case 7:
                            ordenarYListar(sistema);
                            break;
                        case 8:
                            buscarPedido(sistema);
                            break;
                        case 9:
                            intercalarTurnos(sistema);
                            break;
                        case 10:
                            System.out.println("Saliendo de CityLink...");
                            salir = true;
                            break;
                        default:
                            System.out.println("Opción incorrecta. Probá de nuevo.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("El sistema sigue funcionando.");
                }
            }
        } finally {
            System.out.println("Cerrando CityLink. ¡Hasta luego!");
            teclado.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("========== CITYLINK (ENTREGABLE 2) ==========");
        System.out.println("1. Gestión de clientes");
        System.out.println("2. Gestión de repartidores");
        System.out.println("3. Registrar un pedido");
        System.out.println("4. Atender el siguiente pedido");
        System.out.println("5. Registrar una entrega");
        System.out.println("6. Deshacer la última operación");
        System.out.println("7. Ordenar y listar pedidos");
        System.out.println("8. Buscar un pedido por código");
        System.out.println("9. Intercalar pedidos de dos turnos");
        System.out.println("10. Salir");
        System.out.println("=============================================");
    }

    // -----------------------------------------------------------------
    // CLIENTES
    // -----------------------------------------------------------------

    private static void menuClientes(CityLink sistema) {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("--- CLIENTES ---");
            System.out.println("1. Alta de cliente");
            System.out.println("2. Baja de cliente");
            System.out.println("3. Buscar por código");
            System.out.println("4. Buscar por nombre");
            System.out.println("5. Listado ordenado por código");
            System.out.println("0. Volver");
            int opcion = leerEntero("Opción: ");

            try {
                switch (opcion) {
                    case 1:
                        altaCliente(sistema);
                        break;
                    case 2:
                        int codigoBaja = leerEntero("Código del cliente a dar de baja: ");
                        sistema.bajaCliente(codigoBaja);
                        break;
                    case 3:
                        int codigo = leerEntero("Código: ");
                        System.out.println(sistema.buscarCliente(codigo));
                        break;
                    case 4:
                        String nombre = leerTexto("Nombre: ");
                        System.out.println(sistema.buscarCliente(nombre));
                        break;
                    case 5:
                        sistema.listarClientesOrdenados();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void altaCliente(CityLink sistema) {
        System.out.println("--- NUEVO CLIENTE ---");
        int codigo = leerEntero("Código: ");
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        String zona = leerTexto("Zona: ");

        Cliente cliente = new Cliente(codigo, nombre, telefono, zona);
        sistema.registrarCliente(cliente);
        System.out.println("Cliente registrado correctamente.");
    }

    // -----------------------------------------------------------------
    // REPARTIDORES
    // -----------------------------------------------------------------

    private static void menuRepartidores(CityLink sistema) {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("--- REPARTIDORES ---");
            System.out.println("1. Alta de repartidor");
            System.out.println("2. Listado de repartidores");
            System.out.println("3. Ver pedidos e historial de un repartidor");
            System.out.println("4. Ver comisiones");
            System.out.println("0. Volver");
            int opcion = leerEntero("Opción: ");

            try {
                switch (opcion) {
                    case 1:
                        altaRepartidor(sistema);
                        break;
                    case 2:
                        sistema.listarRepartidores();
                        break;
                    case 3:
                        int legajo = leerEntero("Legajo: ");
                        sistema.listarPedidosDeRepartidor(legajo);
                        break;
                    case 4:
                        sistema.listarImportesYComisiones();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void altaRepartidor(CityLink sistema) {
        System.out.println("--- NUEVO REPARTIDOR ---");
        int legajo = leerEntero("Legajo: ");
        String nombre = leerTexto("Nombre: ");

        System.out.println("Tipo de vehículo:");
        System.out.println("1. Bicicleta");
        System.out.println("2. Moto");
        System.out.println("3. Camioneta");
        int tipo = leerEntero("Tipo: ");

        String patente = leerTexto("Patente: ");
        Vehiculo vehiculo;

        if (tipo == 1) {
            vehiculo = new Bicicleta(patente, 10, 15, 50);
        } else if (tipo == 2) {
            vehiculo = new Moto(patente, 30, 40, 120);
        } else if (tipo == 3) {
            vehiculo = new Camioneta(patente, 500, 30, 300);
        } else {
            throw new IllegalArgumentException("El tipo de vehículo no es válido.");
        }

        Repartidor repartidor = new Repartidor(legajo, nombre, vehiculo);
        sistema.registrarRepartidor(repartidor);
        System.out.println("Repartidor registrado correctamente.");
    }

    // -----------------------------------------------------------------
    // PEDIDOS
    // -----------------------------------------------------------------

    private static void registrarPedido(CityLink sistema) throws Exception {
        System.out.println("--- NUEVO PEDIDO ---");
        int codigo = leerEntero("Código del pedido: ");
        int codigoCliente = leerEntero("Código del cliente: ");
        String destino = leerTexto("Destino: ");
        double peso = leerDouble("Peso en kg: ");
        double distancia = leerDouble("Distancia en km: ");

        System.out.println("Tipo de pedido:");
        System.out.println("1. Normal");
        System.out.println("2. Urgente");
        System.out.println("3. Frágil");
        int tipo = leerEntero("Tipo: ");

        System.out.println("Turno:");
        System.out.println("1. Mañana");
        System.out.println("2. Tarde");
        int turno = leerEntero("Turno: ");

        Cliente cliente = sistema.buscarCliente(codigoCliente);
        Pedido pedido;

        if (tipo == 1) {
            pedido = new PedidoNormal(codigo, cliente, destino, peso, distancia);
        } else if (tipo == 2) {
            pedido = new PedidoUrgente(codigo, cliente, destino, peso, distancia);
        } else if (tipo == 3) {
            pedido = new PedidoFragil(codigo, cliente, destino, peso, distancia);
        } else {
            throw new IllegalArgumentException("El tipo de pedido no es válido.");
        }

        sistema.registrarPedido(pedido, turno);
        System.out.println("Pedido registrado y encolado correctamente.");
    }

    private static void registrarEntrega(CityLink sistema) throws Exception {
        System.out.println("--- REGISTRAR ENTREGA ---");
        int codigo = leerEntero("Código del pedido: ");
        sistema.registrarEntrega(codigo);
    }

    private static void ordenarYListar(CityLink sistema) {
        System.out.println("--- ORDENAR PEDIDOS ---");
        System.out.println("Criterio:");
        System.out.println("1. Por costo");
        System.out.println("2. Por distancia");
        System.out.println("3. Por fecha de ingreso");
        int criterio = leerEntero("Criterio: ");

        System.out.println("Algoritmo:");
        System.out.println("1. Burbuja");
        System.out.println("2. Inserción");
        System.out.println("3. Quicksort");
        System.out.println("4. Mergesort");
        int algoritmo = leerEntero("Algoritmo: ");

        sistema.ordenarYListarPedidos(criterio, algoritmo);
        sistema.listarImportesYComisiones();
    }

    private static void buscarPedido(CityLink sistema) throws Exception {
        System.out.println("--- BUSCAR PEDIDO ---");
        System.out.println("1. Búsqueda lineal");
        System.out.println("2. Búsqueda binaria (ordena una copia por código)");
        int metodo = leerEntero("Método: ");
        int codigo = leerEntero("Código: ");

        Pedido pedido;
        if (metodo == 1) {
            pedido = sistema.buscarPedidoLineal(codigo);
        } else if (metodo == 2) {
            pedido = sistema.buscarPedidoBinario(codigo);
        } else {
            throw new IllegalArgumentException("El método de búsqueda no es válido.");
        }

        System.out.println("Pedido encontrado:");
        System.out.println(pedido);
        System.out.println("Estado actual: " + pedido.obtenerEstadoActual());
    }

    private static void intercalarTurnos(CityLink sistema) {
        System.out.println("--- INTERCALAR TURNOS ---");
        System.out.println("Criterio para ordenar cada turno:");
        System.out.println("1. Por costo");
        System.out.println("2. Por distancia");
        System.out.println("3. Por fecha de ingreso");
        int criterio = leerEntero("Criterio: ");
        sistema.intercalarTurnos(criterio);
    }

    // -----------------------------------------------------------------
    // LECTURA POR TECLADO
    // -----------------------------------------------------------------

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un número entero. Probá de nuevo.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine();
            try {
                return Double.parseDouble(linea.trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un número válido. Probá de nuevo.");
            }
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }
}
