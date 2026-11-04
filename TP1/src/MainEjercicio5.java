import java.util.Scanner;

/**
 * Ejercicio 5 del TP1: integración de encapsulamiento, herencia,
 * constructores con super, @Override y polimorfismo.
 */
public class MainEjercicio5 {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        Vehiculo[] vehiculos = new Vehiculo[4];
        vehiculos[0] = new Auto("AA123BB", "Toyota", "Corolla", 45000);
        vehiculos[1] = new Motocicleta("AB456CD", "Honda", "Titan", 18000, 2500);
        vehiculos[2] = new AutoElectrico("AC789EF", "Renault", "Zoe", 52000, 4000);
        vehiculos[3] = new Camioneta("AD012GH", "Ford", "Ranger", 70000, 8000);

        int dias = pedirDias(teclado);

        System.out.println();
        System.out.println("=== ALQUILER POR " + dias + " DÍA(S) ===");

        // Recorro con una referencia Vehiculo. No pregunto el tipo con if ni switch.
        for (int i = 0; i < vehiculos.length; i++) {
            Vehiculo actual = vehiculos[i];
            System.out.println(actual.getPatente() + " - " + actual.getMarca() + " "
                    + actual.getModelo() + " -> $" + actual.calcularAlquiler(dias));
        }

        teclado.close();
    }

    /**
     * Pide una cantidad de días válida (mayor que cero).
     */
    public static int pedirDias(Scanner teclado) {
        int dias = 0;

        while (dias <= 0) {
            System.out.print("Ingrese la cantidad de días de alquiler: ");
            try {
                dias = Integer.parseInt(teclado.nextLine());
                if (dias <= 0) {
                    System.out.println("La cantidad de días tiene que ser mayor que cero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un número. Probá de nuevo.");
            }
        }

        return dias;
    }
}
