package app;

import excepciones.EstadoInvalidoException;
import java.util.Scanner;
import modelo.Bicicleta;
import modelo.Camioneta;
import modelo.Cliente;
import modelo.EstadoPedido;
import modelo.Moto;
import modelo.Pedido;
import modelo.PedidoFragil;
import modelo.PedidoNormal;
import modelo.PedidoUrgente;
import modelo.Repartidor;
import modelo.Vehiculo;

/**
 * Menú principal de CityLink (Entregable 1).
 * Acá solamente se lee por teclado y se llama al gestor.
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
                            registrarCliente(sistema);
                            break;
                        case 2:
                            registrarRepartidor(sistema);
                            break;
                        case 3:
                            registrarPedido(sistema);
                            break;
                        case 4:
                            asignarPedido(sistema);
                            break;
                        case 5:
                            cambiarEstado(sistema);
                            break;
                        case 6:
                            buscarCliente(sistema);
                            break;
                        case 7:
                            sistema.listarPedidosConCostos();
                            break;
                        case 8:
                            consultarEstado(sistema);
                            break;
                        case 9:
                            sistema.listarImportesYComisiones();
                            break;
                        case 10:
                            System.out.println("Saliendo de CityLink...");
                            salir = true;
                            break;
                        default:
                            System.out.println("Opción incorrecta. Probá de nuevo.");
                    }
                } catch (Exception e) {
                    // El sistema no se corta: avisamos y volvemos al menú.
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("El sistema sigue funcionando.");
                }
            }
        } finally {
            // Este finally se ejecuta siempre, incluso si algo falla.
            System.out.println("Cerrando CityLink. ¡Hasta luego!");
            teclado.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("========== CITYLINK ==========");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar repartidor con su vehículo");
        System.out.println("3. Registrar pedido");
        System.out.println("4. Asignar pedido a un repartidor");
        System.out.println("5. Cambiar estado de un pedido");
        System.out.println("6. Buscar cliente por código o nombre");
        System.out.println("7. Listar pedidos con costo y tiempo");
        System.out.println("8. Consultar estado de un pedido o repartidor");
        System.out.println("9. Ver importe total y comisiones");
        System.out.println("10. Salir");
        System.out.println("==============================");
    }

    private static void registrarCliente(CityLink sistema) throws Exception {
        System.out.println("--- NUEVO CLIENTE ---");
        int codigo = leerEntero("Código: ");
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        String zona = leerTexto("Zona: ");

        Cliente cliente = new Cliente(codigo, nombre, telefono, zona);
        sistema.registrarCliente(cliente);
        System.out.println("Cliente registrado correctamente.");
    }

    private static void registrarRepartidor(CityLink sistema) throws Exception {
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

        // Acá el if es solo para crear el objeto, no para calcular costos.
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

        sistema.registrarPedido(pedido);
        System.out.println("Pedido registrado correctamente.");
    }

    private static void asignarPedido(CityLink sistema) throws Exception {
        System.out.println("--- ASIGNAR PEDIDO ---");
        int codigoPedido = leerEntero("Código del pedido: ");
        int legajo = leerEntero("Legajo del repartidor: ");
        sistema.asignarPedido(codigoPedido, legajo);
        System.out.println("Pedido asignado correctamente.");
    }

    private static void cambiarEstado(CityLink sistema) throws Exception {
        System.out.println("--- CAMBIAR ESTADO ---");
        int codigo = leerEntero("Código del pedido: ");

        System.out.println("1. En camino");
        System.out.println("2. Entregado");
        System.out.println("3. Cancelado");
        int opcion = leerEntero("Nuevo estado: ");

        EstadoPedido nuevoEstado;
        if (opcion == 1) {
            nuevoEstado = EstadoPedido.EN_CAMINO;
        } else if (opcion == 2) {
            nuevoEstado = EstadoPedido.ENTREGADO;
        } else if (opcion == 3) {
            nuevoEstado = EstadoPedido.CANCELADO;
        } else {
            throw new EstadoInvalidoException("La opción de estado no es válida.");
        }

        sistema.cambiarEstadoPedido(codigo, nuevoEstado);
        System.out.println("Estado cambiado a " + nuevoEstado + ".");
    }

    private static void buscarCliente(CityLink sistema) throws Exception {
        System.out.println("--- BUSCAR CLIENTE ---");
        System.out.println("1. Por código");
        System.out.println("2. Por nombre");
        int opcion = leerEntero("Opción: ");

        Cliente encontrado;
        if (opcion == 1) {
            int codigo = leerEntero("Código: ");
            encontrado = sistema.buscarCliente(codigo);
        } else if (opcion == 2) {
            String nombre = leerTexto("Nombre: ");
            encontrado = sistema.buscarCliente(nombre);
        } else {
            throw new IllegalArgumentException("La opción de búsqueda no es válida.");
        }

        System.out.println("Cliente encontrado:");
        System.out.println(encontrado);
    }

    private static void consultarEstado(CityLink sistema) throws Exception {
        System.out.println("--- CONSULTAR ESTADO ---");
        System.out.println("1. Pedido");
        System.out.println("2. Repartidor");
        int opcion = leerEntero("Opción: ");

        if (opcion == 1) {
            int codigo = leerEntero("Código del pedido: ");
            sistema.consultarEstadoPedido(codigo);
        } else if (opcion == 2) {
            int legajo = leerEntero("Legajo del repartidor: ");
            sistema.consultarEstadoRepartidor(legajo);
        } else {
            throw new IllegalArgumentException("La opción no es válida.");
        }
    }

    // -----------------------------------------------------------------
    // Lectura por teclado con control de errores
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
                // Acepto coma o punto decimal.
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
