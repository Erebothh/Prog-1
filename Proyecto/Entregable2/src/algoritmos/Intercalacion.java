package algoritmos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import modelo.Pedido;

/**
 * Intercalación de dos listas que ya vienen ordenadas.
 * Se recorre cada una una sola vez, sin volver a ordenar.
 */
public class Intercalacion {

    public static List<Pedido> intercalar(List<Pedido> listaA,
                                          List<Pedido> listaB,
                                          Comparator<Pedido> comparador) {

        List<Pedido> resultado = new ArrayList<Pedido>();
        int i = 0;
        int j = 0;

        while (i < listaA.size() && j < listaB.size()) {
            if (comparador.compare(listaA.get(i), listaB.get(j)) <= 0) {
                resultado.add(listaA.get(i));
                i++;
            } else {
                resultado.add(listaB.get(j));
                j++;
            }
        }

        while (i < listaA.size()) {
            resultado.add(listaA.get(i));
            i++;
        }

        while (j < listaB.size()) {
            resultado.add(listaB.get(j));
            j++;
        }

        return resultado;
    }
}
