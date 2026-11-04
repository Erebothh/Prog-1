package algoritmos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import modelo.Pedido;

/**
 * Algoritmos de ordenamiento escritos por nosotros.
 * No usamos Collections.sort ni List.sort.
 */
public class Ordenamiento {

    /**
     * Ordenamiento burbuja.
     */
    public static void burbuja(List<Pedido> lista, Comparator<Pedido> comparador) {
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - 1 - i; j++) {
                if (comparador.compare(lista.get(j), lista.get(j + 1)) > 0) {
                    intercambiar(lista, j, j + 1);
                }
            }
        }
    }

    /**
     * Ordenamiento por inserción.
     */
    public static void insercion(List<Pedido> lista, Comparator<Pedido> comparador) {
        for (int i = 1; i < lista.size(); i++) {
            Pedido actual = lista.get(i);
            int j = i - 1;

            while (j >= 0 && comparador.compare(lista.get(j), actual) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }
    }

    /**
     * Quicksort.
     */
    public static void quicksort(List<Pedido> lista, Comparator<Pedido> comparador) {
        quicksort(lista, 0, lista.size() - 1, comparador);
    }

    private static void quicksort(List<Pedido> lista, int izquierda, int derecha,
                                  Comparator<Pedido> comparador) {
        if (izquierda < derecha) {
            int pivote = particionar(lista, izquierda, derecha, comparador);
            quicksort(lista, izquierda, pivote - 1, comparador);
            quicksort(lista, pivote + 1, derecha, comparador);
        }
    }

    private static int particionar(List<Pedido> lista, int izquierda, int derecha,
                                   Comparator<Pedido> comparador) {
        Pedido pivote = lista.get(derecha);
        int i = izquierda - 1;

        for (int j = izquierda; j < derecha; j++) {
            if (comparador.compare(lista.get(j), pivote) <= 0) {
                i++;
                intercambiar(lista, i, j);
            }
        }

        intercambiar(lista, i + 1, derecha);
        return i + 1;
    }

    /**
     * Mergesort.
     */
    public static void mergesort(List<Pedido> lista, Comparator<Pedido> comparador) {
        if (lista.size() <= 1) {
            return;
        }

        int medio = lista.size() / 2;
        List<Pedido> izquierda = new ArrayList<Pedido>();
        List<Pedido> derecha = new ArrayList<Pedido>();

        for (int i = 0; i < medio; i++) {
            izquierda.add(lista.get(i));
        }
        for (int i = medio; i < lista.size(); i++) {
            derecha.add(lista.get(i));
        }

        mergesort(izquierda, comparador);
        mergesort(derecha, comparador);
        mezclar(lista, izquierda, derecha, comparador);
    }

    private static void mezclar(List<Pedido> resultado, List<Pedido> izquierda,
                                List<Pedido> derecha, Comparator<Pedido> comparador) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            if (comparador.compare(izquierda.get(i), derecha.get(j)) <= 0) {
                resultado.set(k, izquierda.get(i));
                i++;
            } else {
                resultado.set(k, derecha.get(j));
                j++;
            }
            k++;
        }

        while (i < izquierda.size()) {
            resultado.set(k, izquierda.get(i));
            i++;
            k++;
        }

        while (j < derecha.size()) {
            resultado.set(k, derecha.get(j));
            j++;
            k++;
        }
    }

    private static void intercambiar(List<Pedido> lista, int a, int b) {
        Pedido auxiliar = lista.get(a);
        lista.set(a, lista.get(b));
        lista.set(b, auxiliar);
    }
}
