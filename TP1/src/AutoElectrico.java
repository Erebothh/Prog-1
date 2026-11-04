/**
 * Auto eléctrico del Ejercicio 5.
 * Suma un cargo fijo por la carga de la batería.
 */
public class AutoElectrico extends Vehiculo {

    private double precioPorDia;
    private double cargoCarga;

    public AutoElectrico(String patente, String marca, String modelo,
                         double precioPorDia, double cargoCarga) {
        super(patente, marca, modelo);
        setPrecioPorDia(precioPorDia);
        setCargoCarga(cargoCarga);
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

    public double getCargoCarga() {
        return cargoCarga;
    }

    public void setCargoCarga(double cargoCarga) {
        if (cargoCarga < 0) {
            throw new IllegalArgumentException("El cargo de carga no puede ser negativo.");
        }
        this.cargoCarga = cargoCarga;
    }

    @Override
    public double calcularAlquiler(int dias) {
        return (dias * precioPorDia) + cargoCarga;
    }
}
