/**
 * Ejercicio 4 del TP1: polimorfismo.
 * Recorro un arreglo de Empleado sin preguntar de qué tipo es cada uno.
 */
public class MainEjercicio4 {

    public static void main(String[] args) {

        Empleado[] empleados = new Empleado[4];
        empleados[0] = new EmpleadoPlanta("Ana García", 101, 500000, 5);
        empleados[1] = new EmpleadoContratado("Bruno Pérez", 201, 80, 6000);
        empleados[2] = new EmpleadoPlanta("Carla López", 102, 450000, 2);
        empleados[3] = new EmpleadoContratado("Diego Martínez", 202, 120, 5500);

        System.out.println("=== SUELDOS (POLIMORFISMO) ===");

        // No uso instanceof ni switch: Java decide solo qué calcularSueldo ejecutar.
        for (int i = 0; i < empleados.length; i++) {
            System.out.println(empleados[i].getNombre()
                    + " -> $" + empleados[i].calcularSueldo());
        }

        /*
         * Análisis de lo que pasa:
         * - En la posición 0 la referencia es Empleado pero el objeto es EmpleadoPlanta,
         *   entonces se ejecuta el calcularSueldo() de EmpleadoPlanta.
         * - En la posición 1 el objeto es EmpleadoContratado, entonces se ejecuta
         *   el calcularSueldo() de EmpleadoContratado.
         * Esto se llama despacho dinámico: Java mira el objeto real, no el tipo
         * de la variable.
         */
    }
}
