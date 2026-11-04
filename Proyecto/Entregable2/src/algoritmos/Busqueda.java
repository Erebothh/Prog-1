package algoritmos;

import java.util.List;
import modelo.Cliente;
import modelo.Pedido;

/**
 * Algoritmos de búsqueda escritos a mano.
 * No usamos binarySearch de Java.
 */
public class Busqueda {

    /**
     * Búsqueda lineal de un pedido por código.
     * Devuelve null si no lo encuentra.
     */
    public static Pedido busquedaLinealPedido(List<Pedido> pedidos, int codigo) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getCodigo() == codigo) {
                return pedidos.get(i);
            }
        }
        return null;
    }

    /**
     * Búsqueda lineal de un cliente por nombre.
     * La lista se puede armar desde los valores del TreeMap.
     */
    public static Cliente busquedaLinealClientePorNombre(List<Cliente> clientes, String nombre) {
        if (nombre == null) {
            return null;
        }
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNombre().equalsIgnoreCase(nombre.trim())) {
                return clientes.get(i);
            }
        }
        return null;
    }

    /**
     * Búsqueda binaria iterativa.
     * La lista tiene que estar ordenada por código de menor a mayor.
     */
    public static Pedido busquedaBinariaPedido(List<Pedido> pedidosOrdenados, int codigo) {
        int inicio = 0;
        int fin = pedidosOrdenados.size() - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            int codigoMedio = pedidosOrdenados.get(medio).getCodigo();

            if (codigoMedio == codigo) {
                return pedidosOrdenados.get(medio);
            } else if (codigoMedio < codigo) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return null;
    }

    /**
     * Versión recursiva de la búsqueda binaria.
     */
    public static Pedido busquedaBinariaPedidoRecursiva(List<Pedido> pedidosOrdenados, int codigo) {
        return buscarRecursivo(pedidosOrdenados, codigo, 0, pedidosOrdenados.size() - 1);
    }

    private static Pedido buscarRecursivo(List<Pedido> pedidos, int codigo, int inicio, int fin) {
        if (inicio > fin) {
            return null;
        }

        int medio = (inicio + fin) / 2;
        int codigoMedio = pedidos.get(medio).getCodigo();

        if (codigoMedio == codigo) {
            return pedidos.get(medio);
        } else if (codigoMedio < codigo) {
            return buscarRecursivo(pedidos, codigo, medio + 1, fin);
        } else {
            return buscarRecursivo(pedidos, codigo, inicio, medio - 1);
        }
    }
}
