/**
 * Camioneta del Ejercicio 5 (ampliación).
 * Se usa igual que las demás, a través de una referencia Vehiculo.
 */
public class Camioneta extends Vehiculo {

    private double precioPorDia;
    private double recargoPorTamano;

    public Camioneta(String patente, String marca, String modelo,
                     double precioPorDia, double recargoPorTamano) {
        super(patente, marca, modelo);
        setPrecioPorDia(precioPorDia);
        setRecargoPorTamano(recargoPorTamano);
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        if (precioPorDia <= 0) {
            throw new IllegalArgumentException("El precio por día debe ser mayor que cero.");
        }
        this.precioPorDia = precioPorDia;
    }

    public double getRecargoPorTamano() {
        return recargoPorTamano;
    }

    public void setRecargoPorTamano(double recargoPorTamano) {
        if (recargoPorTamano < 0) {
            throw new IllegalArgumentException("El recargo no puede ser negativo.");
        }
        this.recargoPorTamano = recargoPorTamano;
    }

    @Override
    public double calcularAlquiler(int dias) {
        return (dias * precioPorDia) + recargoPorTamano;
    }
}
